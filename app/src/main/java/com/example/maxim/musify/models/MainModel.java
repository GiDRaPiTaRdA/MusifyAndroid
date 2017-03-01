package com.example.maxim.musify.models;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.widget.SeekBar;

import com.example.maxim.musify.activities.MusicListView;
import com.example.maxim.musify.helpers.AsyncTimer;
import com.example.maxim.musify.helpers.BroadcastReceiverHelper;
import com.example.maxim.musify.helpers.Notifications;
import com.example.maxim.musify.helpers.TimeManager;
import com.example.maxim.musify.managers.FileManager.FileManager;
import com.example.maxim.musify.managers.FileManager.OpenFileDialog;
import com.example.maxim.musify.managers.MusicManager.MediaPlayerSongsManager;
import com.example.maxim.musify.managers.MusicManager.Song;
import com.example.maxim.musify.managers.MusicManager.SongMetadataRetriever;

import java.io.File;

/**
 * Created by Maxim on 05.01.2017.
 */

public class MainModel extends BaseObservable implements OpenFileDialog.OpenDialogListener, MediaPlayerSongsManager.MediaManagerListener, AsyncTimer.TimerTickListener,BroadcastReceiverHelper.BroadcastReceiverListener {

    private static final String MAIN_MODEL_TAG = "MAIN_MODEL_TAG";
    private static final int TIMER_UPDATE_FREQUENCY = 100;

    private Context context;

    private OpenFileDialog fileDialog;
    public MediaPlayerSongsManager songsManager;
    private BroadcastReceiverHelper broadcastReceiver;
    private AsyncTimer timer;

    public MainModel(Context context) {
        this.context = context;
        songsManager = new MediaPlayerSongsManager(context);
        broadcastReceiver =  new BroadcastReceiverHelper(context);
        timer = new AsyncTimer(TIMER_UPDATE_FREQUENCY);
        timer.setTimerTickListener(this);
        broadcastReceiver.setBroadcastReceiverListener(this);
    }


    //BINDING ACTIONS
    public void OpenSearch() {
        this.fileDialog = new OpenFileDialog(context);
        fileDialog.setOpenDialogListener(this);
        songsManager.setMediaPlayerListener(this);
        fileDialog.show();
    }

    public void OpenSearchFilters() {
        Notifications.ShowToast(context, "Open search filters");
    }

    public void OpenSongsList() {
        Intent intent = new Intent(context, MusicListView.class);
        context.startActivity(intent);
    }

    public void TogglePlay() {}

    public void NextTrack() {
        songsManager.Next();
    }

    public void PreviousTrack() {
        Notifications.ShowToast(context, "Previous track");
    }

    public void ToggleShuffle() {
        Notifications.ShowToast(context, "Toggle shuffle [" + String.valueOf(songsManager.shuffle).toUpperCase() + "]");
    }

    public void ToggleRepeat() {
        Notifications.ShowToast(context, "Toggle repeat [" + String.valueOf(songsManager.repeat).toUpperCase() + "]");
    }

    public void SeekBarChange(SeekBar seekBar, int progressValue, boolean fromUser){
         if(fromUser) {
             songsManager.SeekTo(progressValue);
             notifyChange();
         }
    }


    // HELP
    @Override
    public void OnSelectedFile(String fileName) {
        File file = new File(fileName);
        if(FileManager.IsFile(file))
            songsManager.AddSong(new Song(fileName));
        else {
            File[] files = FileManager.GetExtendedFilesFromFolder(file,"mp3");
            for (File current : files) {
                songsManager.AddSong(new Song(current.getPath()));
            }
        }
    }   //Executes when file selected in Open file dialog
    @Override
    public void OnCurrentSongChanged() {
        notifyChange();
    }   //Executes when current song changed
    @Override
    public void OnPlay() {
        timer.Start();
    }
    @Override
    public void OnPause() {
        timer.Pause();
    }

    public Drawable getMainImage() {
        Bitmap img;
        if (songsManager.getCurrentSong() != null) {
            img = SongMetadataRetriever.GetImage(songsManager.getCurrentSong());
            if (img != null) {
                return new BitmapDrawable(img);
            }
        }
            return null;
    }   //gets image correct from current song
    public String IntToTimeFormat(int msc){
        return TimeManager.MillisecondsToTimeFormat(msc,0,0,1,1,0);
    }   //helps to bind time in correct format

    @Override
    public void OnTimerTickUpdate() {
        notifyChange();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action.compareTo(AudioManager.ACTION_AUDIO_BECOMING_NOISY) == 0)
        {
            songsManager.Pause();
            notifyChange();
        }
    }
}
