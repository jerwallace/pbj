/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WallaceJ
 */
public class StockTracker implements Runnable {

	/**
	 * initialize a thread to update StockList every 20000 ms
	 */
    @Override
    public void run() {
        while (true) {
            try {
                OnlineStockInfo.updateStocks();
                Thread.sleep(20000);
            } catch (InterruptedException ex) {
                Logger.getLogger(StockTracker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}