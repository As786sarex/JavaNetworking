package dns_upload_download;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

@SuppressWarnings("Duplicates")
public class UDP_PhotoServer extends Thread{
    private Socket ss;

    private UDP_PhotoServer(Socket ss) {
        this.ss = ss;
    }

    public static void main(String[] args) throws IOException {
        Socket socket2=new Socket("localhost", UDP_Dns.DNS_SOCKET_PORT);
        DataOutputStream dout=new DataOutputStream(socket2.getOutputStream());
        dout.writeUTF("Photo");
        dout.writeInt(13000);
        dout.flush();
        ServerSocket serverSocket=new ServerSocket(13000);
        while(true){
            Socket socket=serverSocket.accept();
            new UDP_PhotoServer(socket).start();
        }
    }

    @Override
    public void run() {
        try {
            DataOutputStream outputStream=new DataOutputStream(ss.getOutputStream());
            DataInputStream inputStream=new DataInputStream(ss.getInputStream());
            outputStream.writeUTF("Download or Upload?");
            UDP_MovieServer movieServer=new UDP_MovieServer();
            String choice=inputStream.readUTF();
            if (choice.equals("Download")){
                List<String> list=movieServer.getAllFiles("PhotoServer");
                for (String fname:list){
                    outputStream.writeUTF(fname);
                }
                int filePos=inputStream.readInt();
                movieServer.downloadWork(inputStream,outputStream,list.get(filePos));
            }
            else if (choice.equals("Upload")){
                movieServer.uploadWork();
            }
            ss.close();
            outputStream.close();
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
