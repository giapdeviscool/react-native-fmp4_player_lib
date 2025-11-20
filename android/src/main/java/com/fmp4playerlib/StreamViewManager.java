
package com.fmp4playerlib;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.viewmanagers.StreamViewManagerInterface;
import com.facebook.react.viewmanagers.StreamViewManagerDelegate;

import java.util.HashMap;
import java.util.Map;

@ReactModule(name = StreamViewManager.REACT_CLASS)
public class StreamViewManager extends SimpleViewManager<StreamView> implements StreamViewManagerInterface<StreamView> {

  private final StreamViewManagerDelegate<StreamView, StreamViewManager> delegate = new StreamViewManagerDelegate<>(this);

  private ReactApplicationContext context;

  public StreamViewManager(ReactApplicationContext reactcontext) {
    this.context = reactcontext;
  }

  @Override
  public ViewManagerDelegate<StreamView> getDelegate() {
    return delegate;
  }

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public StreamView createViewInstance(ThemedReactContext context) {
    return new StreamView(context);
  }

  @ReactProp(name = "streamId")
  @Override
  public void setStreamId(StreamView view, String streamId) {
    if (streamId == null) {
      return;
    }
    view.setStreamId(streamId);
  }

  public static final String REACT_CLASS = "StreamView";
}