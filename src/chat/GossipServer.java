package chat;

import java.net.*;
import java.io.*;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class  GossipServer
{
    public static void main(String args[]) throws Exception
    {
        ServerSocket ss=new ServerSocket(9999);
        Scanner scanner=new Scanner(System.in);
        Socket sk=ss.accept();
        DataInputStream inputStream=new DataInputStream(sk.getInputStream());
        DataOutputStream outputStream=new DataOutputStream(sk.getOutputStream());
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
                System.out.println("CLIENT : "+msg);
            }
        });
        thread.start();
        thread1.start();


    }
}