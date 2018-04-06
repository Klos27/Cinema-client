package com.kloslabs.cinemaclient.connections;

/**
 * Created by Klos on 01.03.2018.
 */

import android.util.Log;
import android.widget.TextView;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

public class UDPReciver extends Thread{
    private final static int PACKETSIZE = 255;
    private final static int JAVA_SERVER_PORT = 50001;
    protected DatagramSocket rxsocket = null;
    private volatile boolean StopServer = false;
    StatusCallback mCallBack = null;

    private void onData(String data) {
//        final String toprint = "Got data: " + data;
//        mTextView.setText(toprint);
//        mTextView.post(new Runnable() {
//            public void run() {
//                mTextView.setText(toprint);
//            }
//        });
//        System.out.println(toprint);
        this.mCallBack.updateStatus(data);
//        System.out.println("Got data!! " + data);
        Log.d("AndroidApp", "Got data!! " + data);
    }

    public UDPReciver(StatusCallback callBack) throws IOException {
        this.mCallBack = callBack;
        rxsocket = new DatagramSocket(JAVA_SERVER_PORT);
        rxsocket.setSoTimeout(250);
    }

    public void run() {
        Log.d("UDPReciver", "Starting server...");
        try {
            while(!StopServer) {
                byte buf[] = new byte[PACKETSIZE];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                    Log.d("UDPReciver SoTime", "New Try");
//                    ConnectionUDP.sendUDPMsgNoThread("STATUS");
                    rxsocket.receive(packet);
                    Log.d("UDPReciver SoTime", "Process data");
                    String data = new String(packet.getData(), 0, packet.getLength());
                    onData(data);
                } catch(SocketTimeoutException e) {
                    Log.d("UDPReciver SoTime", e.getMessage());
                }
            }

        } catch (IOException e) {
            Log.d("UDPReciver IOEx", e.getMessage());
        }
        finally {
            Log.d("UDPReciver", "Server STOP!");
            rxsocket.close();
        }
    }

    public void close() {
        StopServer = true;
    }
}
