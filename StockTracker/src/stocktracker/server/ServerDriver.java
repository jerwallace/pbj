package stocktracker.server;

import java.io.*;

/**
 *
 * @author Bahman
 */
public class ServerDriver
{

    public static void main(String[] args) throws IOException
    {
        UserList myUserList = new UserList();
        StockList myStockList = new StockList();
        myUserList.addUser("bahman");

        Thread stockTrackerThread = new Thread(new StockTracker(myStockList));
        Thread ClientA = new Thread(new ClientAServer(myStockList, myUserList));
        //Thread ClientB = new Thread(new ClientAServer(myStockList, myUserList));
        ClientA.start();
        stockTrackerThread.start();
        //ClientB.start();

        try
        {
            ClientA.join();
            stockTrackerThread.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("join ClientA failed");
            e.printStackTrace();
        }

        while (true)
        {
        }
    }
}
