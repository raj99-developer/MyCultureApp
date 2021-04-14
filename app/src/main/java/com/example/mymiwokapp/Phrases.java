package com.example.mymiwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Phrases extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager sAudioManager;
    AudioManager.OnAudioFocusChangeListener sonAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS)
            {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN)
            {
                releaseMediaPlayer();
            }
        }
    };
    MediaPlayer.OnCompletionListener mCompletionListener= new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
        sAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words= new ArrayList<Word>();

        words.add(new Word("Welcome","स्वागतम्", R.raw.swagtam));
        words.add(new Word("Hello","नमस्कारः", R.raw.namaskar));
        words.add(new Word("How are you?","कथमस्ति भवान्", R.raw.hareyou));
        words.add(new Word("I am fine","अहं कुशली", R.raw.iamfine));
        words.add(new Word("What's your name?","तव नाम किम्", R.raw.whatsyourname));
        words.add(new Word("Where are you from?","भवान् कुत्रत्य:", R.raw.whereareyoufrom));
        words.add(new Word("Pleased to meet you","भवता सह संयोग: सन्तोषकर:", R.raw.pleasedtomeetyou));
        words.add(new Word("Good morning","सुप्रभातम्", R.raw.suprabhatam));
        words.add(new Word("Good night","शुभरात्री", R.raw.subhratri));
        words.add(new Word("Goodbye","पुनर्मिलाम", R.raw.punarmilam));
        words.add(new Word("Good luck!","सौभाग्यम्", R.raw.sobhagyam));
        words.add(new Word("Cheers! Good health!","शुभमस्तु", R.raw.subhamastu));
        words.add(new Word("Have a nice day","सुदिनमस्तु ", R.raw.sudinamastu));
        words.add(new Word("Have a nice meal","भोजनं स्वादिष्टमस्तु", R.raw.haveanicemeal));
        words.add(new Word("Have a good journey","शुभयात्रा", R.raw.subhyatra));




//       LinearLayout rootView= (LinearLayout) findViewById(R.id.rootView);
//       int index=0;
//       while(index<words.size())
//       {
//           TextView wordview= new TextView(this);
//
//           wordview.setText(words.get(index));
//
//           rootView.addView(wordview);
//           index++;
//       }
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);
        ListView listItem= (ListView)findViewById(R.id.list);
        listItem.setAdapter(adapter);
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                Word word=words.get(i);
                int result=sAudioManager.requestAudioFocus(sonAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mediaPlayer=MediaPlayer.create(Phrases.this, word.getaudioResourceid());
                    //start the audio file
                    mediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            sAudioManager.abandonAudioFocus(sonAudioFocusChangeListener);
        }
    }
}