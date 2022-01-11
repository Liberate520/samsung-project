package com.storms.blast.main.connect;

import android.content.Intent;
import android.util.Log;

import com.storms.blast.main.Game;
import com.storms.blast.main.MainActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ClientWriter extends Thread {

    private PrintWriter writer;
    boolean work = true;
    private ConnectionService service;

    public ClientWriter(OutputStream writer, ConnectionService service) {
        this.writer = new PrintWriter(writer);
        this.service = service;
    }

    @Override
    public void run() {
        while (work) {
            if (Game.commands.size() != 0) {
                if (Game.commands.get(0) == null){
                    continue;
                }
                writer.println(Game.commands.get(0));
                writer.flush();
                if (Game.commands.get(0).equals("win 11")){
                    Log.d("My", "command win");
                    win();
                }
                Game.commands.remove(0);
            }
        }
        // отправляем служебное сообщение, которое является признаком того, что клиент вышел
        writer.println("##session##end##");
        writer.flush();
        writer.close();
    }

    public void sendMsg(String s) {
        // отправляем сообщение
        writer.println(s);
        writer.flush();
    }

    public void win(){
        Intent intent = new Intent(MainActivity.MAIN_ACTIVITY);
        intent.putExtra("text", "win");
        service.sendBroadcast(intent);
        service.stop();
    }

    public void disconnect() {
        work = false;
    }
}
