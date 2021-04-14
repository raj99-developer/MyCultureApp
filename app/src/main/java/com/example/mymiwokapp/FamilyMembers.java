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

public class FamilyMembers extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager sAudioManager;
    AudioManager.OnAudioFocusChangeListener sOnAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
          if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
          {
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
        setContentView(R.layout.activity_family_members);
        sAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
       final ArrayList<Word> words= new ArrayList<Word>();

        words.add(new Word("Father","पिता", R.drawable.family_father, R.raw.pita));
        words.add(new Word("Mother","माता", R.drawable.family_mother, R.raw.mata));
        words.add(new Word("Daughter","पुत्री", R.drawable.family_daughter, R.raw.putri));
        words.add(new Word("Son","पुत्रः", R.drawable.family_son, R.raw.putra));
        words.add(new Word("Elder Brother","ज्येष्ठभ्राता", R.drawable.family_older_brother, R.raw.jbrata));
        words.add(new Word("Younger Brother","कनिष्ठभ्राता", R.drawable.family_younger_brother, R.raw.kbrata));
        words.add(new Word("Elder Sister","ज्येष्ठभगिनी", R.drawable.family_older_sister, R.raw.jbgini));
        words.add(new Word("Younger Sister","कनिष्ठभगिनी", R.drawable.family_younger_sister, R.raw.kbgini));
        words.add(new Word("Grandfather","मातामह", R.drawable.family_grandfather, R.raw.matamh));
        words.add(new Word("Grandmother","मातामही", R.drawable.family_grandmother, R.raw.matamahi));




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
        WordAdapter adapter = new WordAdapter(this, words,R.color.category_family_members);
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
                    mediaPlayer=MediaPlayer.create(FamilyMembers.this, word.getaudioResourceid());
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

    public void releaseMediaPlayer()
    {
        // If the media player is not null, then it may be currently playing a sound.
        if(mediaPlayer!=null)
        {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer=null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            sAudioManager.abandonAudioFocus(sOnAudioFocusChangeListener);
        }
    }
}