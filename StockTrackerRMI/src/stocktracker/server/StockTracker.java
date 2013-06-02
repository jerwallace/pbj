/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Runnable class that controls the OnlineStockInfo object by calling it to
 * update all Stock information every 2 minutes
 */
public class StockTracker implements Runnable
{

    /**
     * initialize a thread to update StockList every 120000 ms (2 minutes)
     */
    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                OnlineStockInfo.updateStocks();
                Thread.sleep(120000);
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(StockTracker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
