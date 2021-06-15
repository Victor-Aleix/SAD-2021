import java.io.*;
import java.util.*;
import java.net.*;

public class MySocket{
	private Socket client;
	private String username;
	private PrintWriter output;
	private BufferedReader input; 

	
	public MySocket(String host, int port, String username){
		try{
			this.username = username;
			client = new Socket(host, port);
			inicializeStream();
			writeStr(username);

		}catch(IOException e){
			System.out.println("Error booting the socket");
		}
	}

	public MySocket(Socket s){
		client = s;
		inicializeStream();
	}

	public void inicializeStream(){
		try{
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new PrintWriter(client.getOutputStream(),true);
		}catch (IOException e){
			System.out.println("Error booting the stream");
		}
	}


	public String readStr(){
		try{
			return input.readLine();
		}catch (IOException e){
			return null;
		}
	}

	public int readInt(){
		return Integer.parseInt(readStr());
	}

	public boolean readBoolean(){
		return Boolean.parseBoolean(readStr());
	}

	public void writeStr(String s){
		output.println(s);
	}

	public void writeInt(int i){
		writeStr(Integer.toString(i));
	}

	public String getUsername(){
		return username;
	}

	public void setUsername(String username){
		this.username=username;
	}

	public void close(){
		try{
			input.close();
			output.close();
			client.close();
		}catch(IOException e){
			System.out.println("Error closing the socket");
		}
	}

}


	
