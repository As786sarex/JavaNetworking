package dns_impl;

import java.net.*;
import java.io.*;

@SuppressWarnings("Duplicates")
class ClientProgram {
    public static void main(String[] args)throws Exception{
        Socket s=new Socket("localhost",Dns.DNS_SOCKET_PORT);
        DataInputStream din=new DataInputStream(s.getInputStream());
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        dout.writeInt(10021);
        dout.flush();
        System.out.println("What you want to do?(add/sub/mul/div)");
        String str=br.readLine();
        dout.writeUTF(str);
        dout.flush();

        System.out.println("Getting Server Info...");

        int a=din.readInt();
        Socket socket=new Socket("localhost",a);
        DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
        System.out.println("Enter One Number Followed By a Space then the Second Number... ");
        String number=br.readLine();
        dataOutputStream.writeUTF(number);
        dataOutputStream.flush();
        DataInputStream inputStream=new DataInputStream(socket.getInputStream());
        System.out.println(inputStream.readInt());

        din.close();
        dout.close();
        inputStream.close();
        dataOutputStream.close();
        socket.close();
        s.close();
    }}
