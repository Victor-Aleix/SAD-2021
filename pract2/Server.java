import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server{
	public static final int PORT = 12345;
	public static final ReentrantReadWriteLock readwrite = new ReentrantReadWriteLock();
	public static final Lock readl = readwrite.readLock();
	public static final Lock writel = readwrite.writeLock();
	public static Map<String, MySocket> clients = new TreeMap<String, MySocket>();
	
	public static void main(String[] args){
		MyServerSocket listenserver = new MyServerSocket(PORT);
		System.out.println("Waiting...");
		while(true){
			MySocket client = listenserver.accept();
			Thread clientCustomer = new Thread(() -> {
				String myusername = client.readStr();
				int i = isUsername(myusername);
				if(i!=0){
					myusername = myusername+1;
				}
				client.setUsername(myusername);
				System.out.println(myusername+" connected");
				putClient(myusername, client);
				String line = client.readStr();
				while(line!=null){
					notifiedall(line,myusername);
					System.out.println(">"+myusername+": "+line);
					line = client.readStr();
				}
				System.out.println(myusername+"left");
				removeClient(myusername);
				client.close();
			});
			clientCustomer.start();
		}
	}

	public static int isUsername(String myusername){
		writel.lock();
		try{
			int i = 0;
			for(Map.Entry<String,MySocket> entry : clients.entrySet()){
				if(entry.getKey().equals(myusername)){
					i++;
				}
			}
			return i;
		}
		finally{
			writel.unlock();
		}
	}

	public static void putClient(String username, MySocket client){
		writel.lock();
		try{
			clients.put(username, client);
		}
		finally{
			writel.unlock();
		}
	}

	public static void removeClient(String username){
		writel.lock();
		try{
			clients.remove(username);
		}
		finally{
			writel.unlock();
		}
	}

	public static void notifiedall(String line, String myusername){
		readl.lock();
		try{
			for(Map.Entry<String,MySocket> entry : clients.entrySet()){
				if(entry.getKey()!=myusername){
					entry.getValue().writeStr(">"+myusername+": " + line);
				}
			}
		}
		finally{
			readl.unlock();
		}
	}
}

