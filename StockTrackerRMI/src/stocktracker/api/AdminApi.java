/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.api;

import java.rmi.RemoteException;

/**
 *
 * @author WallaceJ
 */
public interface AdminApi extends AbstractApi {
    
    public void updateStock(String tickerName, double newPrice) throws RemoteException;
    
}
