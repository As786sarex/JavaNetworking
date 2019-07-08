package dns_upload_download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        new UDP_Client().send();
    }
    private void send() throws IOException, InterruptedException {
        File myFile = new File("song.mp3");
        DatagramSocket ds = null;
        BufferedInputStream bis = null;
        try {
            ds = new DatagramSocket();
            DatagramPacket dp;
            int packetsize = 1024;
            double nosofpackets;
            nosofpackets = Math.ceil(((int) myFile.length()) / packetsize);

            bis = new BufferedInputStream(new FileInputStream(myFile));
            for (double i = 0; i < nosofpackets + 1; i++) {
                byte[] mybytearray = new byte[packetsize];
                bis.read(mybytearray, 0, mybytearray.length);
                System.out.println("Packet:" + (i + 1));
                dp = new DatagramPacket(mybytearray, mybytearray.length, InetAddress.getByName("127.0.0.1"), 4000);
                ds.send(dp);
                Thread.sleep(8);
            }

        }finally {
            if(bis!=null)
                bis.close();
            if(ds !=null)
                ds.close();
        }

    }
}

