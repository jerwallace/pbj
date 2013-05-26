import java.net.*;
import java.io.*;


public class ClientAServer implements Runnable {

	@Override
	public void run() {
		
		ServerSocket serverSocket = null;
        try
        {
            serverSocket = new ServerSocket(4446);
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port: 4444");
            System.exit(1);
        }
        Socket clientSocket = null;
        try
        {
            clientSocket = serverSocket.accept();
        }
        catch (IOException e)
        {
            System.err.println("Accept A failed.");
            System.exit(1);
        }
        PrintWriter out = null;
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.err.println("ServerA cannot create PrintWriter");
			e.printStackTrace();
		}
        BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			System.err.println("ServerA cannot create BufferReader");
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
			System.err.println("ServerA canno transfer to protocol");
			e.printStackTrace();
		}
        out.close();
        try {
			in.close();
		} catch (IOException e) {
			System.err.println("ServerA cannot close input chanel");
			e.printStackTrace();
		}
        try {
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("ServerA cannot close clientSocket");
			e.printStackTrace();
		}
        try {
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("ServerA cannot close serverSocket");
			e.printStackTrace();
		}
	}

}
