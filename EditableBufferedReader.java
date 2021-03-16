import java.io.*;
import java.util.Scanner;

public class EditableBufferedReader extends BufferedReader{

public static final int RIGHT = 12340;
public static final int LEFT = 12341;
public static final int HOME = 12342;
public static final int END = 12343;
public static final int INSERT = 12344;
public static final int BACKSPACE = 12345;

	public EditableBufferedReader(InputStreamReader in){
		super(in);
	}
	public static void setRaw(){
		try
            	{
           		Runtime.getRuntime().exec(new String[]{"/bin/sh","-c","stty -echo raw </dev/tty"}); 
		} catch(Exception e){
                 	System.out.println(e);             
                }    
	}
	public static void unsetRaw(){
                try
                {
                        Runtime.getRuntime().exec(new String[]{"/bin/sh","-c","stty echo cooked </dev/tty"});
                } catch(Exception e){
                        System.out.println(e);             
                }    
        }
	public int read(){
        	int c = 0;
		try{
			c = super.read();
			if(c ==27){
				super.read();
				switch(c = super.read()){
					case 'D' :
						c = LEFT;				
					break;
					case 'C' :
                                        	c = RIGHT;                                
					break;
					case 'H' :
                                        	 c = HOME;                                
					break;
					case 'F' :
                                        	c = END;                                
					break;
					case '2' :
						super.read();
                                        	c = INSERT;
					break;
				}
			}
			else if( c == 127 ) 
				c = BACKSPACE;	
		}catch(IOException e){
			System.out.println(e);
		}finally {
			return c;
		}
	}
}

