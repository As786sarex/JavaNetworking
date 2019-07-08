package chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class GossipClient {
    public static void main(String[] args) throws Exception {
        Scanner scanner=new Scanner(System.in);
        Socket socket=new Socket("localhost",9999);
        DataInputStream inputStream=new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream=new DataOutputStream(socket.getOutputStream());

        Thread thread=new Thread(()->{
            while(true){
                String msg=scanner.nextLine();
                try {
                    outputStream.writeUTF(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread1=new Thread(()->{
            while(true){
                String msg= null;
                try {
                    msg = inputStream.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("SERVER : "+msg);
            }
        });
        thread.start();
        thread1.start();

    }
}