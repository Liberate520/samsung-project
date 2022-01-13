package com.storms.blast.main;

import static android.os.SystemClock.sleep;
import static com.storms.blast.main.connect.ConnectionService.SERVER_IP;
import static com.storms.blast.main.connect.ConnectionService.SERVER_PORT;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.storms.blast.R;
import com.storms.blast.main.connect.ClientReader;
import com.storms.blast.main.connect.ClientWriter;
import com.storms.blast.main.connect.ConnectionService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static String android_id;
    private String[] menu;

    public static Game game;

    public static int screenWidth;
    public static int screenHeight;

    private ConstraintLayout res;
    private Button back;
    private ProgressBar progressBar;
    private ListView listView;

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
        menu = getResources().getStringArray(R.array.start_list);
        findView();
        getID();

        playerBack = MediaPlayer.create(this, R.raw.back);
        playerBack.setLooping(true);
//        playerBack.start();
        playerDamage = MediaPlayer.create(this, R.raw.damage);
        playerPop = MediaPlayer.create(this, R.raw.pop);

    }

    private void findView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_text_view, menu);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        res = findViewById(R.id.res);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
//                Intent intent = new Intent(this, ConnectionService.class);
//                startService(intent);
//                progressBar.setVisibility(View.VISIBLE);
                game = new Game(this);
                setContentView(game);
                game.startGame(true);
                break;
            case 1:
                getStat();
                break;
            case 2:
                Toast.makeText(this, "Пока не готово", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                finish();
                break;
        }
    }

    private void getStat(){
        progressBar.setVisibility(View.VISIBLE);
        listView.setClickable(false);
        new ConnectionThread().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                res.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                break;
        }
    }

    class ConnectionThread extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Socket socket = null;
            Scanner in = null;
            PrintWriter printWriter = null;
            boolean success = false;
            String answer = "";
            try {
                // подключаемся к серверу
                InetAddress ipAddress = InetAddress.getByName(SERVER_IP);
                socket = new Socket(ipAddress, SERVER_PORT);
                in = new Scanner(socket.getInputStream());
                printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.println(android_id);
                printWriter.flush();
                int timeOut = 2000;
                while (timeOut > 0){
                    if (in.hasNext()){
                        answer = in.nextLine();
                        Log.d("My", answer);
                        break;
                    }
                    timeOut -= 100;
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    printWriter.close();
                    in.close();
                    socket.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("")){
                String[] args = s.split(" ");
                listView.setVisibility(View.GONE);
                res.setVisibility(View.VISIBLE);

                TextView wins = findViewById(R.id.winCount);
                TextView loses = findViewById(R.id.loseCount);
                wins.setText(args[1]);
                loses.setText(args[2]);
            }
            progressBar.setVisibility(View.INVISIBLE);
            listView.setClickable(true);
        }
    }
}