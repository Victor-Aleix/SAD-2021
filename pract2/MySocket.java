import java.io.*;
import java.util.*;
import java.net.*;

publi class MySocket extends Socket{
    
    public MySocket(InetAddress ipAddr, int port){
        super();
        InetSocketAddress add = new InetSocketAddress(ipAddr,port)
        try{
            this.connect(add);
        }catch(IOException e){
            System.out.print(e);
        }
    }

    @Override
    public InputStream getInputStream() throws IOException{
        return super.getInputStream();
    }
    
    @Override
    public OutputStream getOutputStream() throws IOException{
        return super.getOutputStream();
    }

    public BufferedReader getBufferedReadr(){
        BufferedReader br = null;
        try{
            br = new BufferedReader(new InputStreamReader(this.getInputStream()));
        }catch(IOException e){
            System.out.println(e);
        }
        return br;
    }

    public PrintWriter getPrintWriter(){
        PrintWriter pw = null;
        try{
            p = new PrintWriter(new OutputStreamWriter(this.getOutputStream()));
        }catch(IOException e){
            System.out.println(e);
        }
        return pw;
    }
}

