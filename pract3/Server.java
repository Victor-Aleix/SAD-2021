package src.server;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    private static final int PORT = 1234;
    private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static void main(String[] args) {
        try {
            Reactor r  = new Reactor(PORT);
            new Thread(r).start();
            System.out.println(dateFormat.format(new Date())+" === Server === Waiting for a connection...");
        } catch (IOException e) {
            System.out.println(dateFormat.format(new Date())+" === Server === Error");
        }
    }
}
