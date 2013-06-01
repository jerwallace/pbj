/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import stocktracker.api.UserApi;
import stocktracker.client.protocol.UserProtocol;

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
