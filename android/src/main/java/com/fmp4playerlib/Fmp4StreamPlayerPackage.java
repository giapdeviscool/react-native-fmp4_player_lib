package com.fmp4playerlib;

import com.facebook.react.BaseReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.uimanager.ViewManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fmp4StreamPlayerPackage extends BaseReactPackage {

  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    return Collections.singletonList(new StreamViewManager(reactContext));
  }
  
  @Override
  public NativeModule getModule(String name, ReactApplicationContext reactContext) {
    if (StreamViewManager.REACT_CLASS.equals(name)) {
      return new StreamViewManager(reactContext);
    } else if (name.equals(Fmp4StreamPlayerModule.NAME)) {
      return new Fmp4StreamPlayerModule(reactContext);
    } else {
      return null;
    }
  }

  @Override
  public ReactModuleInfoProvider getReactModuleInfoProvider() {
    return new ReactModuleInfoProvider() {
      @Override
      public Map<String, ReactModuleInfo> getReactModuleInfos() {
        Map<String, ReactModuleInfo> map = new HashMap<>();
        map.put(Fmp4StreamPlayerModule.NAME, new ReactModuleInfo(
          Fmp4StreamPlayerModule.NAME,       // name
          Fmp4StreamPlayerModule.NAME,       // className
          false, // canOverrideExistingModule
          false, // needsEagerInit
          false, // isCXXModule
          true   // isTurboModule
        ));
        map.put(StreamViewManager.REACT_CLASS, new ReactModuleInfo(
                StreamViewManager.REACT_CLASS, // name
                StreamViewManager.REACT_CLASS, // className
                false,                           // canOverrideExistingModule
                false,                           // needsEagerInit
                false,                           // isCxxModule
                true                             // isTurboModule
        ));
        return map;
      }
    };
  }
}