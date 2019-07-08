import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpClient {
    public static void main(String[] args) throws Exception{
        ServerSocket ssock = new ServerSocket(5000);
        Socket socket = ssock.accept();

        File file = new File("MovieServer/DSC_1738MP4_upload_version.mp4");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);

        OutputStream os = socket.getOutputStream();


        byte[] contents;
        long fileLength = file.length();
        long current = 0;

        long start = System.nanoTime();
        while(current!=fileLength){
            int size = 10000;
            if(fileLength - current >= size)
                current += size;
            else{
                size = (int)(fileLength - current);
                current = fileLength;
            }
            contents = new byte[size];
            bis.read(contents, 0, size);
            os.write(contents);
            System.out.println("Sending file ... "+(current*100)/fileLength+"% complete!");
            Thread.sleep(8);
        }

        os.flush();
        //File transfer done. Close the socket connection!
        socket.close();
        ssock.close();
        System.out.println("File sent succesfully!");
    }

}
