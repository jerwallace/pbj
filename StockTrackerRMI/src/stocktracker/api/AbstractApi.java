/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import stocktracker.client.protocol.AbstractProtocol.State;

/**
 *
 * @author WallaceJ
 */
public interface AbstractApi extends Remote {
    
    public boolean userExists(String username) throws RemoteException;
    
    public User getUser(String username) throws RemoteException;
    
    public String selectStock(String tickerName) throws RemoteException;
    
}
