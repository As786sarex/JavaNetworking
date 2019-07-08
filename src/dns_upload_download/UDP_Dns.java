package dns_upload_download;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

@SuppressWarnings("Duplicates")
public class UDP_Dns extends Thread {
    static final int DNS_SOCKET_PORT = 12345;

    private static HashMap<String, Integer> map;
    private Socket socket;

    public UDP_Dns(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws IOException {
        map = new HashMap<>();
        ServerSocket serverSocket = new ServerSocket(DNS_SOCKET_PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            String host = inputStream.readUTF();
            int x = inputStream.readInt();
            System.out.println(x + " is connected");
            map.put(host, x);
            new UDP_Dns(socket).start();
        }
    }

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            String choice = dataInputStream.readUTF();

            if (choice.equals("Movie")) {
                outputStream.writeInt(map.get("Movie"));
            } else if (choice.equals("Music")) {
                outputStream.writeInt(map.get("Music"));
            } else if (choice.equals("Photo")) {

                outputStream.writeInt(map.get("Photo"));

            }


            socket.close();
            dataInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
