package com.cdivtc.musicplayertest;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
    //第一个变量，用于logcat输出相关的信息
    private static final String TAG = "MusicService";
    //定义一个音乐播放器
    public MediaPlayer mediaPlayer;
    public MusicService() {
    }

    //创建一个代理服务类，用来播放音乐
    class MyBinder extends Binder{
        //播放音乐
        public void play(){
            //判断mediaPlayer音乐播放器是否为空
            if (mediaPlayer == null){
                //创建一个音乐播放器
                //mediaPlayer = new MediaPlayer();
                mediaPlayer = MediaPlayer.create(MusicService.this,R.raw.dahai);
                //执行播放方法
                mediaPlayer.start();
            }else {
                //获取播放的进度，自定义一个获取进度的方法
                int position = getCurrentProgress();
                //通过调用seekto方法，跳转到对应的进度位置
                mediaPlayer.seekTo(position);
                //执行播放方法
                mediaPlayer.start();
            }
        }

        //暂停播放
        public void pause(){
            //判断当前的播放器是否为空，或者是否正在播放
            if (mediaPlayer != null && mediaPlayer.isPlaying()){
                //暂停播放
                mediaPlayer.pause();
                //否则判断播放器不为空，但是没有播放，表示处于暂停状态，则让其播放
            }else if (mediaPlayer != null && (!mediaPlayer.isPlaying())){
                //执行播放方法
                mediaPlayer.start();
            }
        }
    }

    //重写onCreate方法
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "调用了onCreate方法 ");
    }

    //获取播放的进度
    private int getCurrentProgress() {
        //判断是否正在播放
        if (mediaPlayer != null & mediaPlayer.isPlaying()){
            return mediaPlayer.getCurrentPosition();
        }else if (mediaPlayer != null & (!mediaPlayer.isPlaying())){
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    //结束播放
    @Override
    public void onDestroy() {
        //判断播放器是否为空
        if (mediaPlayer != null){
            //停止播放
            mediaPlayer.stop();
            //释放播放器
            mediaPlayer.release();
            //将播放器置为空
            mediaPlayer = null;
        }
        Log.i(TAG, "调用了onDestroy方法");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // 第一步执行onBind方法
        return new MyBinder();
    }
}
