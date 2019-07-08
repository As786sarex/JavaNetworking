package multichat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static List<ClientDesc> clientDescs;
    public static void main(String[] args) throws IOException {
        clientDescs=new ArrayList<>();
        ServerSocket serverSocket=new ServerSocket(9999);
    }
}
