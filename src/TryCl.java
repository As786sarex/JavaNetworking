import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class TryCl {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Your Name");
        String name = scanner.nextLine();
        System.out.println("Type your message...");
        Socket socket = new Socket("localhost", 9999);
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        outputStream.writeUTF(name);
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println(inputStream.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        }).start();
        while (true) {
            String msg = scanner.nextLine();
            if (msg.equalsIgnoreCase("STOP")) {
                break;
            }
            outputStream.writeUTF(msg);
        }
        outputStream.close();
        inputStream.close();
        socket.close();
    }
}
