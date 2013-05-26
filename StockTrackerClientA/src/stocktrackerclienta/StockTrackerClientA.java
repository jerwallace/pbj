/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StockTrackerClientA;

/**
 *
 * @author RazmpaB
 */
import java.io.*;
import java.net.*;

public class StockTrackerClientA
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        Socket stSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try
        {
            stSocket = new Socket("localhost", 4444);
            stSocket.setReuseAddress(true);
            if (stSocket.isClosed())
            {
                System.out.println("Client socket is closed");
            }
            if (stSocket.isInputShutdown())
            {
                System.out.println("Client socket Input half is closed");
            }
            if (stSocket.isOutputShutdown())
            {
                System.out.println("Client socket Output half is closed");
            }
            out = new PrintWriter(stSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(stSocket.getInputStream()));
        }
        catch (UnknownHostException e)
        {
            System.err.println("Don't know about the host");
            System.exit(1);
        }
        catch (IOException e)
        {
            System.err.println("Couldn't get I/O for the connection to: taranis.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;
        while ((fromServer = in.readLine()) != null)
        {
            System.out.println("Server: " + fromServer);
            if (fromServer.equals("Exit"))
            {
                break;
            }

            fromUser = stdIn.readLine();
            if (fromUser != null)
            {
                System.out.println("ClientA: " + fromUser);
                out.println(fromUser);
            }
        }
        out.close();
        in.close();
        stdIn.close();
        stSocket.close();
    }
}
