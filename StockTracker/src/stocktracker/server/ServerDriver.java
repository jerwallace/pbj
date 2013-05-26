import java.io.*;
//import java.lang.*;

/**
 *
 * @author Bahman
 */
public class ServerDriver
{

    public static void main(String[] args) throws IOException
    {
    	Thread ClientA = new Thread(new ClientAServer());
    	//Thread ClientB = new Thread(new ClientBControl());
    	ClientA.start();
    	//ClientB.start();
    	
    	try {
			ClientA.join();
		} catch (InterruptedException e) {
			System.out.println("join ClientA failed");
			e.printStackTrace();
		}
    	
    	while (true){}
    }
}
