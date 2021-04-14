package com.example.mymiwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager sAudioManager;

    AudioManager.OnAudioFocusChangeListener sOnAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener() {
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
    MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        sAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //String [] words={"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
       final ArrayList<Word> words= new ArrayList<Word>();
//        words.add("one");
//        words.add("two");
//        words.add("three");
//        words.add("four");
//        words.add("five");
//        words.add("six");
//        words.add("seven");
//        words.add("eight");
//        words.add("nine");
//        words.add("ten");
        words.add(new Word("one","एकम(ekam)", R.drawable.number_one, R.raw.ekam ));
        words.add(new Word("two","द्वे(dve)", R.drawable.number_two, R.raw.dve));
        words.add(new Word("three","त्रीनि(trini)", R.drawable.number_three, R.raw.trini));
        words.add(new Word("four","चत्वारि(chatvari)", R.drawable.number_four, R.raw.chatvari));
        words.add(new Word("five","पन्च(pancha)", R.drawable.number_five, R.raw.panch));
        words.add(new Word("six","षट(sath)", R.drawable.number_six, R.raw.sath));
        words.add(new Word("seven","सप्त(sapta)",R.drawable.number_seven, R.raw.sapta));
        words.add(new Word("eight","अष्ट(ashta)", R.drawable.number_eight, R.raw.asth));
        words.add(new Word("nine","नव(nava)", R.drawable.number_nine, R.raw.nava));
        words.add(new Word("ten","दश(dasha)", R.drawable.number_ten, R.raw.dasha));

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
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);
        ListView listItem= (ListView)findViewById(R.id.list);
        listItem.setAdapter(adapter);
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                Word word=words.get(i);

                int result=sAudioManager.requestAudioFocus(sOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mediaPlayer=MediaPlayer.create(NumbersActivity.this, word.getaudioResourceid());
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
            sAudioManager.abandonAudioFocus(sOnAudioFocusChangeListener);
        }
    }
}