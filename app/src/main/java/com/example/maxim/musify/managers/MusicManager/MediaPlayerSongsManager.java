package com.example.maxim.musify.managers.MusicManager;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.maxim.musify.helpers.Notifications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by ${Maxim} on ${07.01.2017}.
 */

public class MediaPlayerSongsManager implements OnCompletionListener {

    private final String MY_LOG = "MediaPlayerSongsManager";    //MAIN LOG

    private final int SLEEP_TIME_BETWEEN_TRACKS = 500;

    private MediaPlayer mediaPlayer; //USED MEDIA PLAYER
    private Context context;    //CURRENT CONTEXT

    private Song currentSong; //backing field of currentSong PROPERTY
    private ArrayList<Song> playList; //ACTUAL LIST OF SONGS

    private boolean isPrepared = false; //STATUS WHEN MEDIA PLAYER IS PREPARED TO PLAY

    public boolean shuffle = false; //Binding SHUFFLE
    public boolean repeat = false;  //Binding REPEAT

    private MediaManagerListener listener;  //listener


    //LISTENER INTERFACE
    public interface MediaManagerListener{
        void OnCurrentSongChanged();
        void OnPlay();
        void OnPause();
    }

    //CONSTRUCTOR
    public MediaPlayerSongsManager(Context context) {
        this.context = context;

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);

        playList = new ArrayList<Song>();
    }

    //UPCOMING FEATURES
    public void AddSong(Song song) {
        playList.add(song);
    }
    public boolean RemoveSong(Song song) {
        return playList.remove(song);
    }
    public void AddManySongs(Song[] songs) {
        for (Song song : songs) {
            AddSong(song);
        }
    }
    public boolean RemoveSongs(Song[] songs) {
        boolean result = true;
        for (Song song : songs) {
            boolean res = RemoveSong(song);
            result &= res;
        }
        return result;
    }
    public void InitPlayList(Song[] songs) {
        playList = new ArrayList<>(Arrays.asList(songs));
    }
    public void Previous() {
        Next(getPlaying());
    }
    public void Previous(boolean wasPlaying) {
        if (wasPlaying) Pause();
        setCurrentSong(GetNext());
        PrepareMediaPlayer(Uri.parse(currentSong.getPath()));

        if (wasPlaying) {
            try {
                Thread.sleep(500);
                Play();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public void SeekTo(int value){
        if(value>0&&value<=mediaPlayer.getDuration())
            mediaPlayer.seekTo(value);
    }

    //BUTTONS
    public void Play() {

        if(playList.isEmpty()) {
            Notifications.ShowToast(context,"playlist is empty");
        }


            if (!isPrepared)//for first entry when media player isn't prepared yet
                Next();

            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                if(listener!=null)
                    listener.OnPlay();
            }



    }
    public void Pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            if(listener!=null)
                listener.OnPause();
        }
    }
    public void Next() {
        Next(getPlaying());
    }
    public void Next(boolean wasPlaying) {
        if (wasPlaying) Pause();
        setCurrentSong(GetNext());

        if(currentSong!=null)
            PrepareMediaPlayer(Uri.parse(currentSong.getPath()));


        if (wasPlaying) //  make a pause before switching the track
            try {
                Thread.sleep(SLEEP_TIME_BETWEEN_TRACKS);
                Play();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }

    //HELP
    @Nullable
    private Song GetNext() {
        Song song;

        if(playList.isEmpty())
            return null;

        // IF REPEAT
        if (repeat) {
            song = currentSong;
        }
        // IF SHUFFLE (get random)
        else if (shuffle) {
            song = playList.get(new Random().nextInt(playList.size()));
        }
        // IF !SHUFFLE (get next by index, if last - get first)
        else {
            int indexOfCurrentSong = playList.indexOf(currentSong);
            if (indexOfCurrentSong != playList.size() - 1)
                song = playList.get(indexOfCurrentSong + 1);
            else
                song = playList.get(0);
        }

        return song;
    }
    private boolean PrepareMediaPlayer(Uri uri) {
        boolean result;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.prepare();
            result = true;

            isPrepared = true;
        } catch (Exception e) {
            Log.d(MY_LOG, "Exception ", e);
            Log.d(MY_LOG, "Uri : " + String.valueOf(uri));
            result = false;
        }
        return result;
    }
    public void setMediaPlayerListener(MediaManagerListener listener){
        this.listener = listener;
    }

    ///LISTENER
    @Override
    public void onCompletion(MediaPlayer mp) {
        if(listener!=null)
            listener.OnPause();
        Next(true);
    }

    ///PROPERTIES
    public Song getCurrentSong() {
        return currentSong;
    }
    private void setCurrentSong(Song song){
        currentSong = song;
        if(listener!=null)
            listener.OnCurrentSongChanged();
    }

    //like a property of playing
    public boolean getPlaying() {
        return mediaPlayer.isPlaying();
    }
    public void setPlaying(boolean playing) {
        if (playing) {
            Play();
        }
        else {
            Pause();
        }

    }

    public int getDuration(){
        if(isPrepared)
        return mediaPlayer.getDuration();
        else
            return 0;
    }
    public int getPosition(){
        if(isPrepared)
            return mediaPlayer.getCurrentPosition();
        else
            return 0;
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        mediaPlayer.release();
    }

}
