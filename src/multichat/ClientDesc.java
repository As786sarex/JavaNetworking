package multichat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientDesc {
    private String name;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Socket socket;

    public ClientDesc(String name,DataInputStream inputStream, DataOutputStream outputStream, Socket socket) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.socket = socket;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
