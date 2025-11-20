package com.fmp4playerlib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.events.Event;

import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class StreamView extends FrameLayout {

  private static PlayerView playerView;
  public StreamView(Context context) {
    super(context);
    configure(context);
  }

  public StreamView(Context context, AttributeSet attrs) {
    super(context, attrs);
    configure(context);
  }

  public StreamView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    configure(context);
  }

  private void configure(Context context) {
    StreamView.playerView = new PlayerView(context);
    this.addView(playerView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
  }

  public static void attachPlayerToView(ExoPlayer player) {
    StreamView.playerView.setPlayer(player);
  }

  public void setStreamId(String value) {
    if(value == null) return;
    else Fmp4StreamPlayerModule.StreamId = value;
  }
}