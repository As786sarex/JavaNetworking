package dns_upload_download;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDP_Server {


    public static void main(String[] args) throws IOException {

        DatagramSocket serverSocket = new DatagramSocket(4000);
        int packetsize = 1024;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("zz.mp3");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            double nosofpackets = Math.ceil(((int) (new File("song.mp3")).length()) / packetsize);
            byte[] mybytearray = new byte[packetsize];
            DatagramPacket receivePacket = new DatagramPacket(mybytearray, mybytearray.length);

            System.out.println(nosofpackets + " " + mybytearray + " " + packetsize);

            for (double i = 0; i < nosofpackets + 1; i++) {

                serverSocket.receive(receivePacket);
                byte[] audioData = receivePacket.getData();
                System.out.println("Packet:" + (i + 1));
                bos.write(audioData, 0, audioData.length);
            }
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}

