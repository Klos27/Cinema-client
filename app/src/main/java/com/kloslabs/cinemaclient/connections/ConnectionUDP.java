package com.kloslabs.cinemaclient.connections;
import java.net.*;
/**
 * Created by Klos on 27.02.2018.
 */

public class ConnectionUDP extends Thread{
    private static String ServerIP = "10.0.2.2";    // set there IP of python server 10.0.2.2  192.168.200.51
    private static int ServerPort = 50000;
    String msg;
    public static void sendUDPMsg(String msg){
//        try {
//            InetAddress servAddr = InetAddress.getByName(ServerIP);
//            byte buf[] = msg.getBytes();
//            DatagramSocket socket = new DatagramSocket();
//            socket.send(new DatagramPacket(buf, buf.length, servAddr, ServerPort));
//            socket.close();
//        } catch (Exception e) {
//            System.err.println(e);
//        }
        ConnectionUDP conn = new ConnectionUDP(msg);
        conn.start();
    }
    ConnectionUDP(String msg){
        this.msg = msg;
    }
    @Override
    public void run(){
        try {
            InetAddress servAddr = InetAddress.getByName(ServerIP);
            byte buf[] = msg.getBytes();
            DatagramSocket socket = new DatagramSocket();
            socket.send(new DatagramPacket(buf, buf.length, servAddr, ServerPort));
            socket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    public static void sendUDPMsgNoThread(String msg){
        try {
            InetAddress servAddr = InetAddress.getByName(ServerIP);
            byte buf[] = msg.getBytes();
            DatagramSocket socket = new DatagramSocket();
            socket.send(new DatagramPacket(buf, buf.length, servAddr, ServerPort));
            socket.close();
        } catch (Exception e) {
            System.err.println(e);
        }

    }
}
