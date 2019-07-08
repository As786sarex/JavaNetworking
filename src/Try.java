import multichat.ClientDesc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


@SuppressWarnings("Duplicates")
public class Try {
    static Vector<ClientDesc> clientDescs = new Vector<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream inputStream=new DataInputStream(socket.getInputStream());
            String name=inputStream.readUTF();
            System.out.println(socket.getLocalPort() + " is connected");
            ClientDesc clientDesc = new ClientDesc(name,inputStream ,
                    new DataOutputStream(socket.getOutputStream()), socket);
            clientDescs.add(clientDesc);
            new Mythread(socket, clientDesc.getOutputStream(), clientDesc.getInputStream(), clientDesc.getName())
                    .start();

        }
    }
}

class Mythread extends Thread {
    private String name;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Socket socket1;

    public Mythread(Socket socket, DataOutputStream outputStream, DataInputStream inputStream, String name) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.socket1 = socket;
        this.name = name;
    }


    @Override
    public void run() {
        try {
            while (true) {
                String msg;
                if (inputStream != null) {
                    msg = inputStream.readUTF();
                    System.out.println(msg);
                    for (ClientDesc desc : Try.clientDescs) {
                        if (!name.equals(desc.getName())) {
                            desc.getOutputStream().writeUTF(name+" : "+msg);
                        }

                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
