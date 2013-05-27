package stocktracker.server;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientAServer implements Runnable
{

    private User myUser;
    private StockList myStockList;
    private UserList myUserList;

    public ClientAServer(StockList stocklist, UserList userlist)
    {
        this.myUser = new User("someUser", 1000);
        this.myStockList = stocklist;
        this.myUserList = userlist;
    }

    @Override
    public void run()
    {

        ServerSocket serverSocket = null;
        try
        {
            serverSocket = new ServerSocket(4444);
            serverSocket.setReuseAddress(true);
            if (serverSocket.isClosed())
            {
                System.out.println("Server socket is closed");
            }
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
        try
        {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        }
        catch (IOException e)
        {
            System.err.println("ServerA cannot create PrintWriter");
            e.printStackTrace();
        }
        BufferedReader in = null;
        try
        {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (IOException e)
        {
            System.err.println("ServerA cannot create BufferReader");
            e.printStackTrace();
        }
        String inputLine, outputLine;
        String userName = "";

        out.println("Enter User Name:");
        try
        {
            userName = in.readLine();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ClientAServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (myUserList.isUserVailable(userName))
        {
            myUser = myUserList.getUser(userName);
            out.println("Logged in.\t"
                    + "1. Buy Stock    2. Sell Stock   3. Print Stock   4. Logout");
        }
        else
        {
            User newUser = new User(userName, 1000);
            myUserList.addUser(newUser);
            out.println("User: " + userName + " created.\n");
        }
        ClientAProtocol stp = new ClientAProtocol(myStockList, myUserList, myUser);
        outputLine = stp.processInput(null);
        try
        {
            while ((inputLine = in.readLine()) != null)
            {
                outputLine = stp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Exit"))
                {
                    break;
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("ServerA cannot transfer to protocol");
            e.printStackTrace();
        }
        out.close();
        try
        {
            in.close();
        }
        catch (IOException e)
        {
            System.err.println("ServerA cannot close input chanel");
            e.printStackTrace();
        }
        try
        {
            clientSocket.close();
        }
        catch (IOException e)
        {
            System.err.println("ServerA cannot close clientSocket");
            e.printStackTrace();
        }
        try
        {
            serverSocket.close();
        }
        catch (IOException e)
        {
            System.err.println("ServerA cannot close serverSocket");
            e.printStackTrace();
        }
    }
}
