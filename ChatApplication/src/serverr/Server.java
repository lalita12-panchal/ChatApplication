/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverr;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server {
    private JFrame serverframe;
    private JTextArea ta;
    private JScrollPane scrollpane;
    private JTextField tf;

    private ServerSocket serversocket;
    private InetAddress inet_address;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    Thread thread = new Thread() {
        public void run() {
            while (true) {
                readMessage();
            }
        }
    };

    public Server() {
        serverframe = new JFrame("Server");
        serverframe.setSize(500, 500);
        serverframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ta = new JTextArea();
        ta.setEditable(false);
        Font font = new Font("Arial", Font.BOLD, 16);
        ta.setFont(font);
        scrollpane = new JScrollPane(ta);
        serverframe.add(scrollpane);

        tf = new JTextField();
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(tf.getText());
                ta.append("You: " + tf.getText() + "\n");
                tf.setText("");
            }
        });
        tf.setEditable(false);
        serverframe.add(tf, BorderLayout.SOUTH);

        serverframe.setVisible(true);
    }

    public void waitingForClient() {
        try {
            String ipaddress = getIpAddress();
            serversocket = new ServerSocket(1111);
            ta.setText("To connect with server, please provide IP Address: " + ipaddress);
            socket = serversocket.accept();
            ta.setText("Client Connected\n");
            ta.append("----------------------------------------------------\n");
            tf.setEditable(true);
            setIoStreams();
        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    public String getIpAddress() {
        String ip_address = "";
        try {
            inet_address = InetAddress.getLocalHost();
            ip_address = inet_address.getHostAddress();
        } catch (Exception e) {
            System.out.println("IP Address error: " + e.getMessage());
        }
        return ip_address;
    }

    void setIoStreams() {
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            thread.start();
        } catch (Exception e) {
            System.out.println("I/O Stream error: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (Exception e) {
            System.out.println("Send message error: " + e.getMessage());
        }
    }

    public void readMessage() {
        try {
            String message = dis.readUTF();
            showMessage("Client: " + message);
        } catch (Exception e) {
            System.out.println("Read message error: " + e.getMessage());
        }
    }

    public void showMessage(String message) {
        ta.append(message + "\n");
        chatSound();
    }

    public void chatSound() {
        try {
            File file_name = new File("src\\sound\\chat_sound.mp3");
            FileInputStream fis = new FileInputStream(file_name.getAbsolutePath());
            // Player p = new Player(fis); // Uncomment this line and import the required class
            // p.play();
        } catch (Exception e) {
            System.out.println("Chat sound error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.waitingForClient();
    }
}
