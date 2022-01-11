package com.storms.blast.main.connect;

import static com.storms.blast.main.Constant.GUN_SIDE_X;
import static com.storms.blast.main.Constant.GUN_SIDE_Y;
import static com.storms.blast.main.MainActivity.screenHeight;
import static com.storms.blast.main.MainActivity.screenWidth;
import static com.storms.blast.main.Utils.Bitmaps.choiceRocketColor;

import android.app.Service;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.storms.blast.main.Constant;
import com.storms.blast.main.Game;
import com.storms.blast.main.MainActivity;
import com.storms.blast.main.entities.Rocket;

import java.io.InputStream;
import java.util.Scanner;

public class ClientReader extends Thread {

    private InputStream reader;
    private Game game = MainActivity.game;
    boolean work = true;
    private ConnectionService service;

    public ClientReader(InputStream reader, ConnectionService service) {
        this.service = service;
        this.reader = reader;
    }

    public void run() {
        Scanner inMessage = new Scanner(reader);

        // бесконечный цикл
        while (work) {
            // если есть входящее сообщение
            if (inMessage.hasNext()) {
                // считываем его
                String command = inMessage.next();
                String args = inMessage.nextLine();
                Log.d("My", "из потока: " + command + args);
                args = args.substring(1);
                switch (command){
                    case "поехали":
                        Intent intent = new Intent(MainActivity.MAIN_ACTIVITY);
                        intent.putExtra("text", "start game " + args);
                        service.sendBroadcast(intent);
                        while (game == null){
                            try {
                                game = MainActivity.game;
                                sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "crLine":
                        createLine(args);
                        break;
                    case "plPos":
                        game.getOpponentPLayer().setX(MainActivity.screenWidth / 100f * Float.parseFloat(args));
                        game.getOpponentGun().setX(Float.parseFloat(args) + GUN_SIDE_X /2f);
                        break;
                    case "gunRot":
                        game.getOpponentGun().setAngle(Integer.parseInt(args));
                        break;
                    case "shoot":
                        shoot(args);
                        break;
                    case "lose":
                        lose();
                        break;
                    case "leave":
                        leave();
                        break;
                    default:
                        Log.d("My", "Неизвестная команда: " + command);
                        break;
                }
            }
        }
        Log.d("My", "закончили");
        inMessage.close();
    }

    public void createLine(String args){
        String[] s = args.split(" ");
        int[] colors = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            colors[i] = Integer.parseInt(s[i]);
        }
        game.createLine(colors);
    }

    public void shoot(String args){
        String[] s = args.split(" ");
        Rocket rocket = new Rocket(choiceRocketColor(Integer.parseInt(s[0])),
                Integer.parseInt(s[1]), Integer.parseInt(s[2]),
                Constant.adaptiveVarX(Float.parseFloat(s[3])), Constant.adaptiveVarY(Float.parseFloat(s[4])), Integer.parseInt(s[0]));
        game.getOpponentBullets().add(rocket);
    }

    public void lose(){
        Intent intent = new Intent(MainActivity.MAIN_ACTIVITY);
        intent.putExtra("text", "lose");
        service.sendBroadcast(intent);
        service.stop();
    }

    public void leave(){
        Intent intent = new Intent(MainActivity.MAIN_ACTIVITY);
        intent.putExtra("text", "leave");
        service.sendBroadcast(intent);
        service.stop();
    }

    public void disconnect(){
        work = false;
    }
}
