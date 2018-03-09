package com.kloslabs.cinemaclient.connections;
import java.net.*;
/**
 * Created by Klos on 27.02.2018.
 */

public class ConnectionUDP {
    private static String ServerIP = "192.168.1.32";    // set there IP of python server
    private static int ServerPort = 50000;
    public static void sendUDPMsg(String msg){
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
