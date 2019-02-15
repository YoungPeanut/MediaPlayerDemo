package com.youngpeanut.mediaplayerdemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.*;

import java.io.IOException;

/**
 * MediaPlayer + SurfaceView
 */
public class Demo1Activity extends AppCompatActivity implements
    View.OnClickListener {

  MediaPlayer mediaPlayer;

  private final int NORMAL = 0; //闲置
  private final int PLAYING = 1; //播放中
  private final int PAUSING = 2; //暂停
  private final int STOPING = 3; //停止
  private int currentState = NORMAL;
  boolean isStopUdate = false;

  EditText editText;
  Button play, button3, button2, button;
  SeekBar seekBar;
  TextView textView2, textView;
  SurfaceView surfaceView;
  SurfaceHolder holder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_media_player_demo1);
    editText = findViewById(R.id.editText);
    play = findViewById(R.id.button4);
    button3 = findViewById(R.id.button3);
    button2 = findViewById(R.id.button2);
    button = findViewById(R.id.button);
    seekBar = findViewById(R.id.seekBar);
    textView2 = findViewById(R.id.textView2);
    textView = findViewById(R.id.textView);
    surfaceView = findViewById(R.id.surfaceView);

    button.setOnClickListener(this);
    button2.setOnClickListener(this);
    button3.setOnClickListener(this);
    play.setOnClickListener(this);

    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });

    holder = surfaceView.getHolder();
    holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.button4:
        start();
        break;
      case R.id.button3:
        pause();
        break;
      case R.id.button2:
        stop();
        break;
      case R.id.button:
        rePlay();
        break;
    }
  }

  private void play() {
    String path = editText.getText().toString().trim();
    mediaPlayer = new MediaPlayer();
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    // 视频流显示到surfaceView上
    mediaPlayer.setDisplay(holder);

    try {

      mediaPlayer.setDataSource(path);
      mediaPlayer.prepare();
      mediaPlayer.start();
      currentState = PLAYING;

      mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
          Toast.makeText(Demo1Activity.this, "play finish", Toast.LENGTH_SHORT).show();
        }
      });
      int duration = mediaPlayer.getDuration();
      seekBar.setMax(duration);

      textView2.setText("00:00");
      int m = duration / 1000 / 60;
      int s = duration / 1000 % 60;
      textView.setText("/" + m + ":" + s);

      isStopUdate = false;
      new Thread(new ProgressUpdateRunnable()).start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void rePlay() {

    if (null != mediaPlayer) {
      mediaPlayer.reset();
      mediaPlayer.release();
      play();
    }
  }

  private void stop() {

    if (null != mediaPlayer) {
      currentState = STOPING;
      mediaPlayer.stop();
      mediaPlayer.reset();
      mediaPlayer.release();
      mediaPlayer = null;
      isStopUdate = true;
    }
  }

  private void pause() {
    if (null != mediaPlayer && currentState == PLAYING) {
      mediaPlayer.pause();
      currentState = PAUSING;
      isStopUdate = true;
    }
  }

  private void start() {

    if (mediaPlayer != null) {

      if (currentState == PAUSING) {
        mediaPlayer.start();
        currentState = PLAYING;
        return;
      }

      //if (currentState == STOPING) {
      //  mediaPlayer.reset();
      //  mediaPlayer.release();
      //}
    }

    play();
  }

  class ProgressUpdateRunnable implements Runnable {

    @Override
    public void run() {
      // 每秒读取进度给seekbar
      if (!isStopUdate) {

        final int curPos = mediaPlayer.getCurrentPosition();
        seekBar.setProgress(curPos);

        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            int m = curPos / 1000 / 60;
            int s = curPos / 1000 % 60;

            textView2.setText(m + ":" + s);
          }
        });

        SystemClock.sleep(1000);
      }
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    pause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    stop();
  }
}
