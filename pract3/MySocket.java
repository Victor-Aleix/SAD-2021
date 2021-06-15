package src.client;

import java.io.*;
import java.net.Socket;

public class MySocket {
    private Socket mySocket;
    private BufferedReader input;
    private PrintWriter output;

    //creates a socket to stablish a connection with the server
    public MySocket(String nickname, String host, int port) {
        try {
            mySocket = new Socket(host, port);
            iniStreams();
            writeString(nickname);

        } catch (IOException e) {
            System.out.println("Socket couldn't be created!");
            e.printStackTrace();
        }
    }

    //cereates a client.MySocket through another socket
    public MySocket(Socket socket) {
        mySocket = socket;
        iniStreams();
    }

    //closes the socket
    public void close() {
        try {
            writeString("EXIT");
            input.close();
            output.close();
            mySocket.close();
        } catch (IOException e) {
            System.out.println("Socket couldn't be closed!");
        }
    }

    private void iniStreams() {
        try {
            input = new BufferedReader(new InputStreamReader(
                    mySocket.getInputStream()
            ));
            output = new PrintWriter(mySocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Streams couldn't be initialised!");
        }
    }

    //if there is a boolean, it reads it, else return null
    public boolean readBoolean() {
        return Boolean.parseBoolean(readString());
    }

    //if there is an int, it reads it, else return null
    public int readInt() {
        return Integer.parseInt(readString());
    }

    //if there is a string, it reads it, else return null
    public String readString() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    //writes the int in the socket
    public void writeInt(int i) {
        writeString(Integer.toString(i));
    }

    //writes the string in the socket
    public void writeString(String s) {
        output.println(s);
    }
}