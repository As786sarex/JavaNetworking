package chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient extends Thread{
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    static String string="";

    public ChatClient(DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner=new Scanner(System.in);
        Socket socket=new Socket("localhost",9999);
        DataInputStream inputStream=new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream=new DataOutputStream(socket.getOutputStream());
        String name="mylife.mov";
        FileOutputStream fileOutputStream=new FileOutputStream(name);
        BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
        InputStream inputStream1=socket.getInputStream();

        byte[] content=new byte[10000];
        int byteRecievd=0;
        while ((byteRecievd=inputStream1.read(content))!=-1){
            bufferedOutputStream.write(content,0,byteRecievd);
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        inputStream.close();
        outputStream.close();
        socket.close();

    }


}
