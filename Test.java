import java.io.*;

public class Test{
   public static void main(String[] args)
   {
            try
            {
            Process process = Runtime.getRuntime().exec("tput cols"); 

            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
               while ((line=reader.readLine())!=null)
               {
                System.out.println(line);
                }
             }
                catch(Exception e)
             {
                 System.out.println(e);
             }
    }
}
