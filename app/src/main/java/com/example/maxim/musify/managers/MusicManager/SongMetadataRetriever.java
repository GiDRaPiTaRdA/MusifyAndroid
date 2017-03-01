package com.example.maxim.musify.managers.MusicManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

/**
 * Created by ${Maxim} on ${07.01.2017}.
 */

public class SongMetadataRetriever {
    public static Bitmap GetImage(Song song){
        if(song==null)
            return null;

        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();

        try {
            metaRetriver.setDataSource(song.getPath());
        }
        catch (Exception e) {
            return null;
        }

        byte[] art = metaRetriver.getEmbeddedPicture();

        Bitmap songImage;

        if(art!=null)
            songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
        else
            songImage = null;

        return songImage;
    }
    public static String GetTitle(Song song){
        if(song==null)
            return null;

        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(song.getPath());

        String title = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

        return title;
    }
    public static String GetAuthor(Song song){
        if(song==null)
            return null;

        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(song.getPath());

        String title = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

        return title;
    }
}
