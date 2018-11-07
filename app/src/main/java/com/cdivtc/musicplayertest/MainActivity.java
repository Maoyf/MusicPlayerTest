package com.cdivtc.musicplayertest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //第一步：定义变量
    //定义一个意图类的变量
    private Intent intent;
    //定义一个连接类的变量
    private MyConn myConn;
    //定义一个MusicService中的代理类对象
    private MusicService.MyBinder binder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化一个myConn;
        myConn = new MyConn();
        //实例化一个Intent对象，显示Intent
        intent = new Intent(this,MusicService.class);
        //绑定服务
        bindService(intent,myConn,BIND_AUTO_CREATE);
    }

    public void play(View view) {
        binder.play();
    }

    public void pause(View view) {
        binder.pause();
    }

    private class MyConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MusicService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        //解绑服务
        unbindService(myConn);
        super.onDestroy();
    }
}
