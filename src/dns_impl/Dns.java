package dns_impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class Dns extends Thread{
    static final int DNS_SOCKET_PORT=10021;

    static List<Integer> list;
    Socket socket;

    public Dns(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws IOException {
        list = new ArrayList<>();
        ServerSocket serverSocket = new ServerSocket(DNS_SOCKET_PORT);
        while(true) {
            Socket socket = serverSocket.accept();
            DataInputStream inputStream=new DataInputStream(socket.getInputStream());
            int x=inputStream.readInt();
            System.out.println(x+" is connected");
            list.add(x);
            new Dns(socket).start();
        }
    }

    @Override
    public void run() {
        try{
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        String choice = dataInputStream.readUTF();

        if (choice.equals("add")) {
            outputStream.writeInt(list.get(0));
        } else if (choice.equals("sub")) {
            outputStream.writeInt(Dns.list.get(1));
        } else if (choice.equals("mul")) {
            outputStream.writeInt(Dns.list.get(2));
        } else if (choice.equals("div")) {
            outputStream.writeInt(Dns.list.get(3));
        }


        socket.close();
        dataInputStream.close();
        outputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}
