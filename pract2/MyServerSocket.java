import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket{

	public ServerSocket serversocket;

	public MyServerSocket(int port){
		try{
			serversocket = new ServerSocket(port);
		}catch (IOException e){
		}
	}

	public MySocket accept(){
		try{
			Socket client = serversocket.accept();
			MySocket c = new MySocket(client);
			return c; 
		}catch(IOException e){
			return null;
		}
	}
}