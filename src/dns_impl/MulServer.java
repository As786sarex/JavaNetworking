package dns_impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@SuppressWarnings("Duplicates")
public class MulServer {
    public static void main(String[] args) throws IOException {
        Socket socket2=new Socket("localhost", Dns.DNS_SOCKET_PORT);
        DataOutputStream dout=new DataOutputStream(socket2.getOutputStream());
        dout.writeInt(6561);
        dout.flush();
        ServerSocket socket=new ServerSocket(6561);
        Socket socket1=socket.accept();
        DataOutputStream outputStream=new DataOutputStream(socket1.getOutputStream());
        DataInputStream inputStream=new DataInputStream(socket1.getInputStream());
        String req=inputStream.readUTF();
        System.out.println(req);
        String[] number=req.split(" ");
        int a=Integer.parseInt(number[0])*Integer.valueOf(number[1]);
        outputStream.writeInt(a);

        socket.close();
        socket1.close();
        socket2.close();
        dout.close();
        inputStream.close();
        outputStream.close();
    }
}
