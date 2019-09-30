package com.example.soundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.inputmethodservice.Keyboard;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.media.SoundPool.OnLoadCompleteListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundBoardActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = SoundBoardActivity.class.getSimpleName();
    private Button aNoteButton;
    private Button bFlatNoteButton;
    private Button bNoteButton;
    private Button scaleButton;
    private SoundPool soundPool;
    private int aNoteSound;
    private int bNoteSound;
    private int bbNoteSound;
    private Map<Integer, Integer> noteMap;
    private Song scaleTrack = new Song();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_board);
        Log.d(TAG, "onCreate: ");
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        wireWidgets();
        loadSounds();
        createScale();
        setListeners();
    }

    private void createScale() {
        Note note1 = new Note(aNoteSound,600);
        Note note2 = new Note(bbNoteSound,600);
        Note note3 = new Note(bNoteSound,400);
        Note note4 = new Note(aNoteSound,400);
        Note note5 = new Note(bbNoteSound,400);
        Note note6 = new Note(bNoteSound,200);
        Note note7 = new Note(aNoteSound,200);
        Note note8 = new Note(bbNoteSound,200);
        Note note9 = new Note(bNoteSound,0);
        scaleTrack.addNote(note1);
        scaleTrack.addNote(note2);
        scaleTrack.addNote(note3);
        scaleTrack.addNote(note4);
        scaleTrack.addNote(note5);
        scaleTrack.addNote(note6);
        scaleTrack.addNote(note7);
        scaleTrack.addNote(note8);
        scaleTrack.addNote(note9);
    }

    private void loadSounds() {
        aNoteSound = soundPool.load(this, R.raw.scalea, 1);
        bNoteSound = soundPool.load(this, R.raw.scaleb, 1);
        bbNoteSound = soundPool.load(this, R.raw.scalebb, 1);

        noteMap = new HashMap<>();
        noteMap.put(aNoteButton.getId(), aNoteSound);
        noteMap.put(bNoteButton.getId(), bNoteSound);
        noteMap.put(bFlatNoteButton.getId(), bbNoteSound);
    }

    private void wireWidgets() {
        aNoteButton = findViewById(R.id.button_main_a);
        bFlatNoteButton = findViewById(R.id.button_main_bflat);
        bNoteButton = findViewById(R.id.button_main_b);
        scaleButton = findViewById(R.id.button_main_scale);
    }

    private void setListeners() {
//        aNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//                    float volume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//                    // Is the sound loaded already?
//                    if (loaded) {
//                        soundPool.play(aNoteSound, volume, volume, 1, 0, 1f);
//                        Log.e("Test", "Played sound");
//                    }
//                }
//        });
//
//        bFlatNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//                float volume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//                // Is the sound loaded already?
//                if (loaded) {
//                    soundPool.play(bbNoteSound, volume, volume, 1, 0, 1f);
//                    Log.e("Test", "Played sound");
//                }
//            }
//        });
//        bNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//                float volume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//                // Is the sound loaded already?
//                if (loaded) {
//                    soundPool.play(bNoteSound, volume, volume, 1, 0, 1f);
//                    Log.e("Test", "Played sound");
//                }
//            }
//        });
//
//        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//            @Override
//            public void onLoadComplete(SoundPool soundPool, int sampleId,
//                                       int status) {
//                loaded = true;
//            }
//        });
        KeyboardListener keyboardListener = new KeyboardListener();
        aNoteButton.setOnClickListener(keyboardListener);
        bNoteButton.setOnClickListener(keyboardListener);
        bFlatNoteButton.setOnClickListener(keyboardListener);

        scaleButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    // activity is now running

    // another activity is covering part of this activity
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    // this activity is completely hidden
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    // when activity is finished
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private void delay(int millisDelay) {
        try {
            Thread.sleep(millisDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_main_scale: {
                scaleTrack.playSong();
            }
        }
    }

    private class KeyboardListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int songId = noteMap.get(view.getId());
            if (songId != 0) {
                soundPool.play(songId, 1, 1, 1, 0, 1);
            }
        }
    }

    private class Note {

        private int soundId;
        private int millisDelay;

        public Note(int soundId, int millisDelay) {
            this.soundId = soundId;
            this.millisDelay = millisDelay;
        }

        public int getSoundId() {
            return soundId;
        }

        public void setSoundId(int soundId) {
            this.soundId = soundId;
        }

        public int getMillisDelay() {
            return millisDelay;
        }

        public void setMillisDelay(int millisDelay) {
            this.millisDelay = millisDelay;
        }
    }

    private class Song {
        private List<Note> song;

        public Song() {
            song = new ArrayList<Note>();
        }

        public void addNote(Note note) {
            song.add(note);
        }

        public void playSong() {
            for (int i = 0; i < song.size(); i++) {
                soundPool.play(song.get(i).getSoundId(), 1, 1, 1, 0, 1);
                delay(song.get(i).getMillisDelay());
            }
        }
    }
}
