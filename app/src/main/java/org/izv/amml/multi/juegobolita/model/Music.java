package org.izv.amml.multi.juegobolita.model;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import org.izv.amml.multi.juegobolita.R;

public class Music {

    private static MediaPlayer mediaPlayer;
    private SoundPool soundPool;

    private int sound_levelSelected, sound_error, sound_victory;

    private void createMediaPlayer(Context context, int resId){
        mediaPlayer = MediaPlayer.create(context, resId);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopMediaPlayer();
            }
        });
    }

    public void playMediaPlayer(Context context, int resId){
        if(mediaPlayer == null){
            createMediaPlayer(context, resId);
        } else if(mediaPlayer.getAudioSessionId() != resId){
            stopMediaPlayer();
            createMediaPlayer(context, resId);
        }
        mediaPlayer.start();
    }

    public void stopMediaPlayer() {
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void createSoundPool(Context context){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(audioAttributes)
                .build();
        sound_levelSelected = soundPool.load(context, R.raw.ui_quirky_levelselected, 1);
        sound_error = soundPool.load(context, R.raw.ui_quirky_error, 1);
        sound_victory = soundPool.load(context, R.raw.ta_da_victory, 1);
    }

    public void playSoundPool(Context context, int resId){
        switch (resId){
            case R.raw.ui_quirky_levelselected:
                soundPool.autoPause();
                soundPool.play(sound_levelSelected, 1, 1, 0, 0, 1);
                break;
            case R.raw.ui_quirky_error:
                soundPool.autoPause();
                soundPool.play(sound_error, 1, 1, 0, 0, 1);
                break;
            case R.raw.ta_da_victory:
                soundPool.autoPause();
                soundPool.play(sound_victory, 1, 1, 0, 0, 1);
                break;
        }
    }

    public void stopSoundPool() {
        if(soundPool != null){
            soundPool.release();
            soundPool = null;
        }
    }
}
