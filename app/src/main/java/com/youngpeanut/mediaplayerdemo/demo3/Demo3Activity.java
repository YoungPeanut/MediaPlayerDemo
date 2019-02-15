package com.youngpeanut.mediaplayerdemo.demo3;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import com.youngpeanut.mediaplayerdemo.R;

/**
 * 创建时间: 2019/2/15
 * <p>
 * 作者: chenshao
 * <p>
 * 描述:  VideoSurfaceView extends GLSurfaceView
 */
public class Demo3Activity extends Activity {

  private static final String TAG = "MediaPlayerSurfaceStub";

  protected Resources mResources;

  private VideoSurfaceView mVideoView = null;
  private MediaPlayer mMediaPlayer = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mResources = getResources();
    mMediaPlayer = new MediaPlayer();

    try {
      AssetFileDescriptor afd = mResources.openRawResourceFd(R.raw.testvideo);
      mMediaPlayer.setDataSource(
          afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
      afd.close();
    } catch (Exception e) {
      Log.e(TAG, e.getMessage(), e);
    }

    mVideoView = new VideoSurfaceView(this, mMediaPlayer);
    setContentView(mVideoView);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mVideoView.onResume();
  }
}