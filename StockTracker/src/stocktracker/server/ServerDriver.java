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
        User Jeremy = new User("jeremy", 100000);
        User Bahman = new User("bahman", 10000);
        User Peter = new User("peter", 5000);
        Stock AAPL = new Stock("AAPL", 445.50);
        Stock GOOG = new Stock("GOOG", 873.00);
        Stock BBRY = new Stock("BBRY", 15.50);
        UserList myUserList = new UserList();
        StockList myStockList = new StockList();
        myUserList.addUser(Bahman);
        myUserList.addUser(Jeremy);
        myUserList.addUser(Peter);
        myStockList.addNewStock("bbry", BBRY);
        myStockList.addNewStock("aapl", BBRY);
        myStockList.addNewStock("bbry", BBRY);
        Bahman.addStock(AAPL, 10);
        Bahman.addStock(GOOG, 5);
        Bahman.addStock(BBRY, 20);
		String[] stockArr = {"GOOG"};

        Thread stockTrackerThread = new Thread(new StockTracker(myStockList));
		Thread ClientA = new Thread(new ClientAServer(myStockList, myUserList));
//        Thread ClientA = new Thread(new GetStockPrice(stockArr));
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
