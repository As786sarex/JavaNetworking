package dns_upload_download_tcp;

import dns_upload_download.UDP_Dns;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCP_Client1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket s = new Socket("localhost", TCP_Dns.DNS_SOCKET_PORT);
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        dout.writeUTF("Client");
        dout.writeInt(10021);
        dout.flush();
        System.out.println("So... What are You Thinking About Today?\n\n->Movie \n->Music \n->Photo");
        String str = br.readLine();
        dout.writeUTF(str);
        dout.flush();

        System.out.println("Getting Server Info...");

        int a = din.readInt();
        Socket socket = new Socket("localhost", a);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        System.out.println(inputStream.readUTF());
        String number = br.readLine();
        dataOutputStream.writeUTF(number);
        dataOutputStream.flush();
        int i = 1;
        while (true) {
            String fnames = inputStream.readUTF();
            if (fnames.equals("EOF")) {
                break;
            }
            System.out.println(i + ". " + fnames);
            i++;
        }
        int ch = Integer.parseInt(br.readLine());
        dataOutputStream.writeInt(ch - 1);
        Thread.sleep(10);
        int randomPort=inputStream.readInt();
        String filename=inputStream.readUTF();
        Socket sockett = new Socket(InetAddress.getByName("localhost"), randomPort);
        byte[] contents = new byte[10000];

        //Initialize the FileOutputStream to the output file's full path.
        FileOutputStream fos = new FileOutputStream(filename);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        InputStream is = sockett.getInputStream();

        //No of bytes read in one read() call
        int bytesRead = 0;

        while((bytesRead=is.read(contents))!=-1) {
            bos.write(contents, 0, bytesRead);
        }

        bos.flush();
        sockett.close();
        s.close();
        socket.close();
        din.close();
        dout.close();
        inputStream.close();
        dataOutputStream.close();
        fos.close();
        bos.close();

        System.out.println("File saved successfully!");


    }

}
