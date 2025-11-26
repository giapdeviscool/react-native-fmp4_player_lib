package com.fmp4playerlib;

import android.content.Context;
import android.net.Uri;

import android.util.Log;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;

import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.common.MediaItem;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSource.Factory;
import androidx.media3.datasource.DataSpec;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import com.facebook.react.bridge.LifecycleEventListener;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.stream.Stream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import androidx.media3.common.AudioAttributes;
import android.media.AudioManager;

public class Fmp4StreamPlayerModule extends NativeFmp4PlayerLibSpec implements LifecycleEventListener {

    public static final String NAME = "NativeFmp4PlayerLib";
    public AudioAttributes attributes;
    public static String StreamId = ""; 
    public ExoPlayer player;
    private WebSocket webSocket;
    private PipedOutputStream pipedOutputStream;
    private PipedInputStream pipedInputStream;
    private boolean isStreaming = false;
    private boolean isListenerAdded = false;
    public Fmp4StreamPlayerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addLifecycleEventListener(this);
        initPlayer(reactContext);
    }

    @Override
    public String getName() {
        return NAME;
    }
    
    
    public void initPlayer(Context context) {

        if (player != null) {
            player.release(); // Giải phóng player nếu đã tồn tại
        }
        player = new ExoPlayer
        .Builder(context)
        .setLooper(Looper.getMainLooper())
        .build();
        try {
            pipedOutputStream = new PipedOutputStream();
            pipedInputStream = new PipedInputStream(pipedOutputStream, 1024 * 1024);
        } catch (IOException e) {
            Log.e("test", "Error initializing piped streams", e);
            return; 
        }
    }



    @Override
    public void startStreaming() {
        if (player == null) {
            initPlayer(getReactApplicationContext());
            isStreaming = true;
        }
        
        DataSource.Factory dataSourceFactory = new DataSource.Factory() {
            @Override
            public DataSource createDataSource() {
                return new Fmp4DataSource(pipedInputStream);
            }
        };

        ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse("fmp4://stream")));

        new android.os.Handler(Looper.getMainLooper()).post(() -> {
            
            player.setMediaSource(mediaSource);
            player.prepare();
            player.setPlayWhenReady(true);
            StreamView.attachPlayerToView(player);
        });

        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        //060f350f-9da8-422d-b14d-eb9642bea92a
        Request request = new Request.Builder()
                .url("wss://sfu-do-streaming.ermis.network/stream-gate/software/Ermis-streaming/" + Fmp4StreamPlayerModule.StreamId)
                //.url("wss://sfu-do-streaming.ermis.network/stream-gate/software/Ermis-streaming/060f350f-9da8-422d-b14d-eb9642bea92a")
                .addHeader("Sec-WebSocket-Protocol", "fmp4")
                .build();
                
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                byte[] data = bytes.toByteArray();
                
                byte[] fmp4Data = new byte[data.length - 1];
                System.arraycopy(data, 1, fmp4Data, 0, fmp4Data.length);
                try {
                    pipedOutputStream.write(fmp4Data);
                    pipedOutputStream.flush();
                    
                } catch (IOException e) {
                    Log.e("test", "Error writing to pipe", e);
                }
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                Log.e("test", "WebSocket failure: " + t.getMessage());
            }
        });
    }

    @Override
    public void stopStreaming() {
        if (webSocket != null) {
            webSocket.close(1000, "User stopped");
            webSocket = null;
        }
        try {
            pipedOutputStream.close();
            pipedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (player != null) {
            new android.os.Handler(Looper.getMainLooper()).post(() -> {
                player.release();
                player = null;
            });
            
        }
    }
    @Override
    public void onHostResume() {
        if(player != null) {
            player.setPlayWhenReady(true);
        } 
    }
    @Override
    public void onHostPause() {
        if(player != null) {
            player.setPlayWhenReady(false);
        }
    }
    @Override
    public void onHostDestroy() {
        stopStreaming();    
        getReactApplicationContext().removeLifecycleEventListener(this);
    }
    
}
