package dns_upload_download;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

@SuppressWarnings("Duplicates")
public class UDP_Client_2 {
    public static void main(String[] args) throws Exception {
        Socket s=new Socket("localhost", UDP_Dns.DNS_SOCKET_PORT);
        DataInputStream din=new DataInputStream(s.getInputStream());
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        dout.writeUTF("Client");
        dout.writeInt(11021);
        dout.flush();
        System.out.println("So... What are You Thinking About Today?\n\n Movie \n Music \n Photos");
        String str=br.readLine();
        dout.writeUTF(str);
        dout.flush();

        System.out.println("Getting Server Info...");

        int a=din.readInt();
        Socket socket=new Socket("localhost",a);
        DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream=new DataInputStream(socket.getInputStream());
        System.out.println(inputStream.readUTF());
        String number=br.readLine();
        dataOutputStream.writeUTF(number);
        dataOutputStream.flush();
        int i=1;
        while(true){
            String fnames=inputStream.readUTF();
            if (fnames.equals("EOF")){
                break;
            }
            System.out.println(i+". "+fnames);
            i++;
        }
        int ch= Integer.parseInt(br.readLine());
        dataOutputStream.writeInt(ch-1);
        Thread.sleep(10);
        double nosofpackets=inputStream.readDouble();
        DatagramSocket serverSocket = new DatagramSocket(inputStream.readInt());
        String filename=inputStream.readUTF();
        int packetsize = 1024;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            byte[] mybytearray = new byte[packetsize];
            DatagramPacket receivePacket = new DatagramPacket(mybytearray, mybytearray.length);

            System.out.println(nosofpackets + " " + mybytearray + " " + packetsize);

            for (double j = 0; j < nosofpackets + 1; j++) {

                serverSocket.receive(receivePacket);
                byte[] audioData = receivePacket.getData();
                System.out.println("Packet:" + (j + 1));
                bos.write(audioData, 0, audioData.length);
                bos.flush();
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

        din.close();
        dout.close();
        inputStream.close();
        dataOutputStream.close();
        socket.close();
        s.close();
    }
}
