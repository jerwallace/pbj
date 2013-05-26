import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ClientBServre implements Runnable{

	@Override
	public void run() {
		
		ServerSocket serverSocket = null;
        try
        {
            serverSocket = new ServerSocket(4445);
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port: 4445.");
            System.exit(1);
        }
        
        Socket clientSocket = null;
        try
        {
            clientSocket = serverSocket.accept();
        }
        catch (IOException e)
        {
            System.err.println("Accept B failed.");
            System.exit(1);
        }
        PrintWriter out = null;
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BufferedReader in = null;
		try {
			in = new BufferedReader(
			        new InputStreamReader(
			        clientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String inputLine, outputLine;
		
		StockTrackerProtocolA stp = new StockTrackerProtocolA();

        outputLine = stp.processInput(null);
        out.println(outputLine);

        try {
			while ((inputLine = in.readLine()) != null)
			{
			    outputLine = stp.processInput(inputLine);
			    out.println(outputLine);
			    if (outputLine.equals("Exit"))
			    {
			        break;
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        out.close();
        try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
