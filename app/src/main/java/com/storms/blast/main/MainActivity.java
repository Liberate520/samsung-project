package com.storms.blast.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.storms.blast.R;
import com.storms.blast.main.connect.ConnectionService;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    public static String android_id;

    public static Game game;

    public static int screenWidth;
    public static int screenHeight;

    Button buttonCreate, buttonAbout, buttonExit;
    private ProgressBar progressBar;

    public static MediaPlayer playerBack, playerPop, playerDamage;

    public static final String MAIN_ACTIVITY = "главный экран";
    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String answer = intent.getStringExtra("text");
            Log.d("My", "сообщение: " + answer);
            switch (answer) {
                case "start game 1":
                    progressBar.setVisibility(View.INVISIBLE);
                    game = new Game(MainActivity.this);
                    setContentView(game);
                    game.startGame(true);
                    break;
                case "start game 2":
                    progressBar.setVisibility(View.INVISIBLE);
                    game = new Game(MainActivity.this);
                    setContentView(game);
                    game.startGame(false);
                    break;
                case "lose":
                    setContentView(R.layout.activity_main);
                    findView();
                    Toast.makeText(MainActivity.this, "Вы проиграли...", Toast.LENGTH_LONG).show();
                    game = null;
                    Game.commands = new ArrayList<>();
                    break;
                case "win":
                    setContentView(R.layout.activity_main);
                    findView();
                    Toast.makeText(MainActivity.this, "Вы выиграли!", Toast.LENGTH_LONG).show();
                    game = null;
                    Game.commands = new ArrayList<>();
                    break;
                case "leave":
                    setContentView(R.layout.activity_main);
                    findView();
                    Toast.makeText(MainActivity.this, "Ваш противник отключился", Toast.LENGTH_LONG).show();
                    game = null;
                    Game.commands = new ArrayList<>();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(receiver, new IntentFilter(MAIN_ACTIVITY));
        //уберает верхную панель
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        //определил размеры экрана
        setContentView(R.layout.activity_main);
        findView();
        getID();

        playerBack = MediaPlayer.create(this, R.raw.back);
        playerBack.setLooping(true);
//        playerBack.start();
        playerDamage = MediaPlayer.create(this, R.raw.damage);
        playerPop = MediaPlayer.create(this, R.raw.pop);
    }

    private void findView(){
        buttonCreate = findViewById(R.id.buttonCreate);
        buttonAbout = findViewById(R.id.buttonAbout);
        buttonExit = findViewById(R.id.buttonExit);
        progressBar = findViewById(R.id.progressBar);

        buttonCreate.setOnClickListener(this);
        buttonAbout.setOnClickListener(this);
        buttonExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonCreate:
                Intent intent = new Intent(this, ConnectionService.class);
                startService(intent);
                progressBar.setVisibility(View.VISIBLE);
//                game = new Game(this);
//                setContentView(game);
//                game.startGame(true);
                break;
            case R.id.buttonAbout:
                Toast.makeText(this, "Пока не готово", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonExit:
                finish();
                break;
        }
    }

    public void getID(){
         android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d("My", android_id);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(ConnectionService.CONNECTION_SERVICE);
        intent.putExtra("text", "stop");
        sendBroadcast(intent);
        super.onDestroy();
    }
}