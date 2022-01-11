package com.storms.blast.main.connect;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.storms.blast.main.Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ConnectionService extends Service {

    // клиентский сокет
    private Socket clientSocket;
    ClientReader clientReader;
    ClientWriter clientWriter;

    public static String SERVER_IP = "85.115.189.213";
    public static final int SERVER_PORT = 8081;

    public static final String CONNECTION_SERVICE = "Сервер соединения";

    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String answer = intent.getStringExtra("text");
            if (answer.equals("stop")){
                stop();
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("My", "start service");
        ConnectionThread connectionThread = new ConnectionThread();
        connectionThread.start();
        registerReceiver(receiver, new IntentFilter(CONNECTION_SERVICE));
        return START_NOT_STICKY;
    }

//    public String getIPAddress(boolean useIPv4) {
//        try {
//            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
//            for (NetworkInterface intf : interfaces) {
//                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
//                for (InetAddress addr : addrs) {
//                    if (!addr.isLoopbackAddress()) {
//                        String sAddr = addr.getHostAddress();
//                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
//                        boolean isIPv4 = sAddr.indexOf(':')<0;
//
//                        if (useIPv4) {
//                            if (isIPv4)
//                                return sAddr;
//                        } else {
//                            if (!isIPv4) {
//                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
//                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception ignored) { } // for now eat exceptions
//        return "";
//    }

    class ConnectionThread extends Thread {
        @Override
        public void run() {
            try {
                // подключаемся к серверу
                InetAddress ipAddress = InetAddress.getByName(SERVER_IP);
                clientSocket = new Socket(ipAddress, SERVER_PORT);
                clientReader = new ClientReader(clientSocket.getInputStream(), ConnectionService.this);
                clientWriter = new ClientWriter(clientSocket.getOutputStream(), ConnectionService.this);
                clientReader.start();
                clientWriter.start();

            } catch (IOException e) {
                Log.d("My", "Не удалось создать потоки");
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        clientWriter.disconnect();
        clientReader.disconnect();
        try {
            clientWriter.join();
            clientReader.join();
            clientSocket.getInputStream().close();
            clientSocket.getOutputStream().close();
            clientSocket.close();
            Log.d("My", "отключился");
            stopSelf();
        } catch (InterruptedException | IOException e){
            e.printStackTrace();
        }
    }
}
