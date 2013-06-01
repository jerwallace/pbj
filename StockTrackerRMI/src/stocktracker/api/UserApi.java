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

public interface UserApi extends AbstractApi {
    
    public void buyStock(String tickerName, String username, int numStocks) throws RemoteException;

    public void sellStock(String tickerName, String username, int numStocks) throws RemoteException;

}
