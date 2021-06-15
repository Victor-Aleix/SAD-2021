package src.client;

import src.swing.ChatFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;
import java.util.Date;

public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 1234;

    private static String nickname;
    private static ChatFrame chatFrame;
    private static MySocket s;

    public static void main (String [] args){
        chatFrame = new ChatFrame();
        setupNickname();
    }


    public static void setupChat(String connection){
        chatFrame.setupChatPanel(nickname);
        chatFrame.getChatPanel().getChatText().append(connection);

        // Send clicked button
        chatFrame.getChatPanel().getSendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String message = chatFrame.getChatPanel().getMessageField().getText();
                if(message.isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "Message couldn't be sent, please write at least one character",
                            "Messa is empty",
                            JOptionPane.ERROR_MESSAGE);
                }else{
                    sendMessage(message);
                    chatFrame.getChatPanel().getMessageField().setText("");
                }
            }
        });

        // Disconnect clicked button
        chatFrame.getChatPanel().getDisconnectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                disconnect();
            }
        });
    }

    public static void setupNickname(){
        // When CR hit
        chatFrame.getLoginPanel().getNicknameField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chatFrame.getLoginPanel().getJoinButton().doClick();
            }
        });

        // Join clicked button
        chatFrame.getLoginPanel().getJoinButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                nickname = chatFrame.getLoginPanel().getNicknameField().getText();
                if(nickname.isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "Chat couldn't be joined without a nickname",
                            "Nickname is empty",
                            JOptionPane.ERROR_MESSAGE);
                }else{
                    connectToServer();
                }
            }
        });
    }

    public static void connectToServer(){
        s = new MySocket(nickname, HOST, PORT);

        // thread that listens to server responses
        Thread outputThread = new Thread(() -> {
            String line;
            while ((line = s.readString()) != null){
                if (line.contains("=== Hello! ===")){
                    String str = line.substring(line.indexOf("]") + 2);
                    setupChat(str);

                    line = s.readString();
                    str = line.substring(line.indexOf("]") + 1);
                    while(str.contains(";")){
                        String user;
                        if (str.indexOf(";", 1) != -1){
                            user = str.substring(1, str.indexOf(";", 1));
                            str = str.substring(str.indexOf(";", 1));
                        }else{
                            user = str.substring(1);
                            str = "";
                        }
                        chatFrame.getChatPanel().getUsersList().addElement(user);
                        chatFrame.revalidate();
                        chatFrame.repaint();
                    }

                }else if (line.contains("=== Client Connected ===")){
                    String str = line.substring(line.indexOf("]") + 2);
                    chatFrame.getChatPanel().getUsersList().addElement(str);

                }else if (line.contains("=== Client Disconnected ===")) {
                    String str = line.substring(line.indexOf("]") + 2);
                    chatFrame.getChatPanel().getUsersList().removeElement(str);

                }else {
                    writeInChat(line);
                }
            }
        });
        outputThread.start();
    }

    public static void sendMessage(String message){
        s.writeString(message);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        writeInChat(dateFormat.format(new Date())+" << [ME]: "+message);
    }

    public static void writeInChat(String text){
        chatFrame.getChatPanel().getChatText().append("\n" + text);
    }

    public static void disconnect(){
        s.close();
        chatFrame.dispose();
        System.exit(0);
    }
}
