package dns_impl;

import java.net.*;
import java.io.*;

@SuppressWarnings("Duplicates")
class SubtractServer {

    public static void main(String[] args) throws IOException {
        Socket socket2=new Socket("localhost", Dns.DNS_SOCKET_PORT);
        DataOutputStream dout=new DataOutputStream(socket2.getOutputStream());
        dout.writeInt(5561);
        dout.flush();
        ServerSocket socket=new ServerSocket(5561);
        Socket socket1=socket.accept();
        DataOutputStream outputStream=new DataOutputStream(socket1.getOutputStream());
        DataInputStream inputStream=new DataInputStream(socket1.getInputStream());
        String[] number=inputStream.readUTF().split(" ");
        int a=Integer.parseInt(number[0])-Integer.valueOf(number[1]);
        outputStream.writeInt(a);

        socket.close();
        socket1.close();
        socket2.close();
        dout.close();
        inputStream.close();
        outputStream.close();
    }

}
