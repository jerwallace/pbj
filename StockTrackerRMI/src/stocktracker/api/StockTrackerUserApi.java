/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.api;

import java.rmi.*;

/**
 *
 * @author WallaceJ
 */

public interface StockTrackerUserApi extends StockTrackerApi {
    
    public boolean buyStock(int numStocks) throws RemoteException;

    public boolean sellStock(int numStocks) throws RemoteException;

}
