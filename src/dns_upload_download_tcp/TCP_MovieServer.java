package dns_upload_download_tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("Duplicates")
public class TCP_MovieServer extends Thread {
    private Socket ss;

    public TCP_MovieServer(Socket ss) {
        this.ss = ss;
    }

    public TCP_MovieServer() {
    }

    public static void main(String[] args) throws IOException {
        Socket socket2 = new Socket("localhost", TCP_Dns.DNS_SOCKET_PORT);
        DataOutputStream dout = new DataOutputStream(socket2.getOutputStream());
        dout.writeUTF("Movie");
        dout.writeInt(11000);
        dout.flush();
        ServerSocket serverSocket = new ServerSocket(11000);
        while (true) {
            Socket socket = serverSocket.accept();
            new TCP_MovieServer(socket).start();
        }
    }

    @Override
    public void run() {
        try {
            DataOutputStream outputStream = new DataOutputStream(ss.getOutputStream());
            DataInputStream inputStream = new DataInputStream(ss.getInputStream());
            outputStream.writeUTF("Download or Upload?");
            String choice = inputStream.readUTF();
            if (choice.equals("Download")) {
                List<String> list = getAllFiles("MovieServer");
                for (String fname : list) {
                    outputStream.writeUTF(fname);
                }
                int filePos = inputStream.readInt();
                downloadWork(inputStream, outputStream, list.get(filePos));
            } else if (choice.equals("Upload")) {
                uploadWork();
            }
            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<String> getAllFiles(String path) {
        List<String> list = new ArrayList<>();
        File file = new File(path);
        File[] listofFiles = file.listFiles();
        if (listofFiles != null) {

            for (File file1 : listofFiles) {
                if (file1.isFile()) {
                    list.add(file1.getPath());
                }
            }
            list.add("EOF");
        }
        return list;
    }

    public void downloadWork(DataInputStream inputStream, DataOutputStream outputStream, String s) throws Exception {

        try {

            Random random = new Random();
            int randomPort = random.nextInt(2000) + 9000;
            outputStream.writeInt(randomPort);
            outputStream.writeUTF(s.split("\\\\")[1]);
            ServerSocket ssock = new ServerSocket(randomPort);

            Socket soocket = ssock.accept();

            File file = new File(s);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            OutputStream os = soocket.getOutputStream();


            byte[] contents;
            long fileLength = file.length();
            long current = 0;

            long start = System.nanoTime();
            while (current != fileLength) {
                int size = 10000;
                if (fileLength - current >= size)
                    current += size;
                else {
                    size = (int) (fileLength - current);
                    current = fileLength;
                }
                contents = new byte[size];
                bis.read(contents, 0, size);
                os.write(contents);
                System.out.println("Sending file ... " + (current * 100) / fileLength + "% complete!");
                Thread.sleep(8);
            }

            os.flush();
            //File transfer done. Close the socket connection!
            soocket.close();
            ssock.close();
            System.out.println("File sent succesfully!");
            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void uploadWork() {
    }
}
