package src.server;

import java.io.IOException;


public class Server {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            Reactor r  = new Reactor(PORT);
            new Thread(r).start();
            System.out.println(" === Server === Waiting for a connection...");
        } catch (IOException e) {
            System.out.println(" === Server === Error");
        }
    }
}
