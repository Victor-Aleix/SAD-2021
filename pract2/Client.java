import java.io.*;

public class Client{
    private static final String HOST = "localhost";
    private static final int PORT = 12345;
    private static String username;

    public static void main(String [] args){
        //para leer la linea
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader input = new BufferedReader (isr);

        System.out.println("Please introduce your username");
	
	try{
        	username = input.readLine();
        	System.out.println("Welcome to the live chat "+username);
	
	}catch (IOException e){
		System.out.println("Error introducing username");
	}
        
        MySocket client = new MySocket(HOST, PORT, username);

        Thread inputTh = new Thread(() -> {
            try{
                String line;
                while((line=input.readLine())!=null){
                    client.writeStr(line);
                }
            }catch (IOException e){
		System.out.println("Impossible to read");
	    }
        });
        inputTh.start();

        Thread outputTh = new Thread(() -> {
            String line = client.readStr();
	    while(line!=null){
		System.out.println(line);
		line = client.readStr();
	    }
        });
	outputTh.start();
    }
}
