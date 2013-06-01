/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.rmi.RemoteException;
import stocktracker.api.StockList;
import stocktracker.api.User;
import stocktracker.api.UserApi;
import stocktracker.client.protocol.AbstractProtocol;
import stocktracker.client.protocol.InvalidCommandException;
import stocktracker.client.protocol.UserProtocol;
import stocktracker.client.protocol.AbstractProtocol.State;
import stocktracker.client.protocol.CustomException;

/**
 *
 * @author WallaceJ
 */
public class UserDriver extends AbstractClient {
    
    
    public static void main(String[] args) throws Exception  {
        thisProtocol = new UserProtocol();
        UserSession.loadRegistry();
        UserSession.setRemoteApi((UserApi) UserSession.registry.lookup(UserApi.class.getSimpleName()));
        run();
    }
   
}
