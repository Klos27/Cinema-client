package com.kloslabs.cinemaclient.connections;

import android.util.Log;

/**
 * Created by Klos on 17.03.2018.
 */

public class StatusTrigger extends Thread{
    @Override
    public void run(){
//        Log.d("StatusTrigger", "Starting server...");
        try{
            while(true){
                try{
                    Thread.sleep(500);
                    ConnectionUDP.sendUDPMsg("STATUS");
                }
                catch(Exception e){
//                    Log.d("Ex in StatusTrigger", e.getMessage());
                }
            }
        }
        finally {
            Log.d("StatusTrigger", "Server STOP!");
        }

    }
}
