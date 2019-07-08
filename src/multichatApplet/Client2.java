package multichatApplet;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@SuppressWarnings("Duplicates")
public class Client2 extends Applet implements ActionListener {
    TextField textField;
    TextArea textArea;
    Button button;
    DataOutputStream outputStream;
    DataInputStream inputStream;
    Socket socket;
    StringBuilder text;

    @Override
    public void init() {
        text=new StringBuilder();
        textArea=new TextArea();
        textField=new TextField(100);
        button=new Button("Send");

        button.addActionListener(this);
        textArea.setBounds(200,200,500,500);
        this.add(textArea);
        this.add(textField);
        this.add(button);
        try {
            socket = new Socket("localhost", 9999);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream.writeUTF(String.valueOf(socket.hashCode()));
            new Thread(() -> {
                while (true) {
                    try {
                        text.append(inputStream.readUTF());
                        text.append("\n");
                        textArea.setText(String.valueOf(text));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg=textField.getText();
        try {
            outputStream.writeUTF(msg);
            text.append("ME : ");
            text.append(msg);
            text.append("\n");
            textArea.setText(String.valueOf(text));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    @Override
    public void destroy() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
