package dns_upload_download;

import dns_impl.Dns;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("Duplicates")
public class UDP_MovieServer extends Thread{
    private Socket ss;

    public UDP_MovieServer(Socket ss) {
        this.ss = ss;
    }

    public UDP_MovieServer() {
    }

    public static void main(String[] args) throws IOException {
        Socket socket2=new Socket("localhost", UDP_Dns.DNS_SOCKET_PORT);
        DataOutputStream dout=new DataOutputStream(socket2.getOutputStream());
        dout.writeUTF("Movie");
        dout.writeInt(11000);
        dout.flush();
        ServerSocket serverSocket=new ServerSocket(11000);
        while(true){
            Socket socket=serverSocket.accept();
            new UDP_MovieServer(socket).start();
        }
    }

    @Override
    public void run() {
        try {
            DataOutputStream outputStream=new DataOutputStream(ss.getOutputStream());
            DataInputStream inputStream=new DataInputStream(ss.getInputStream());
            outputStream.writeUTF("Download or Upload?");
            String choice=inputStream.readUTF();
            if (choice.equals("Download")){
                List<String> list=getAllFiles("MovieServer");
                for (String fname:list){
                    outputStream.writeUTF(fname);
                }
                int filePos=inputStream.readInt();
                downloadWork(inputStream,outputStream,list.get(filePos));
            }
            else if (choice.equals("Upload")){
                uploadWork();
            }
            ss.close();
            outputStream.close();
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public List<String> getAllFiles(String path){
        List<String> list=new ArrayList<>();
        File file=new File(path);
        File[] listofFiles=file.listFiles();
        if (listofFiles!=null) {

            for (File file1 : listofFiles) {
                if (file1.isFile()){
                    list.add(file1.getPath());
                }
            }
            list.add("EOF");
        }
        return list;
    }
    public void downloadWork(DataInputStream inputStream, DataOutputStream outputStream, String s) throws Exception {
        File myFile = new File(s);
        DatagramSocket ds = null;
        BufferedInputStream bis = null;
        try {
            ds = new DatagramSocket();
            DatagramPacket dp;
            int packetsize = 1024;
            double nosofpackets;
            Random random=new Random();
            int randomPort=random.nextInt(2000)+9000;
            nosofpackets = Math.ceil(((int) myFile.length()) / packetsize);
            outputStream.writeDouble(nosofpackets);
            outputStream.writeInt(randomPort);
            outputStream.writeUTF(s.split("\\\\")[1]);

            bis = new BufferedInputStream(new FileInputStream(myFile));
            for (double i = 0; i < nosofpackets + 1; i++) {
                byte[] mybytearray = new byte[packetsize];
                bis.read(mybytearray, 0, mybytearray.length);
                System.out.println("Packet:" + (i + 1));
                dp = new DatagramPacket(mybytearray, mybytearray.length, InetAddress.getByName("127.0.0.1"), randomPort);
                ds.send(dp);
            }

        }finally {
            if(bis!=null)
                bis.close();
            if(ds !=null)
                ds.close();
        }



    }



    public void uploadWork(){}
}
