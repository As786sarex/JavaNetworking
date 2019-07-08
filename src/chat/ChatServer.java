package chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer extends Thread{

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public ChatServer(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    static String str="";
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket = serverSocket.accept();
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());

        File file=new File("MovieServer/CotswoldSequence1.mov");
        FileInputStream fileInputStream=new FileInputStream(file);
        BufferedInputStream bufferedInputStream=new BufferedInputStream(fileInputStream);

        OutputStream inputStream1=socket.getOutputStream();
        byte[] contents;
        long filelength=file.length();
        long current=0;
        while(current!=filelength){
            int size=10000;
            if (filelength-current>=size){
                current=current+size;
            }
            else{
                size= (int) (filelength-current);
                current=filelength;
            }
            contents=new byte[size];
            bufferedInputStream.read(contents,0,size);
            inputStream1.write(contents);
            System.out.println("Sending file ... " + (current * 100) / filelength + "% complete!");
            Thread.sleep(8);

        }
        bufferedInputStream.close();
        outputStream.close();
        inputStream.close();
        socket.close();

    }
}
