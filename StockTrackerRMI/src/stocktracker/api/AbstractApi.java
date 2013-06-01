/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import stocktracker.api.protocol.AbstractProtocol.State;

/**
 *
 * @author WallaceJ
 */
public interface AbstractApi extends Remote {
    
    public String processInput(String input) throws RemoteException;
     
    public String getNextInstruction() throws RemoteException;
    
    public boolean userExists(String username) throws RemoteException;
    
}
