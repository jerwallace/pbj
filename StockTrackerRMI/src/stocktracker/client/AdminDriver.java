/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.rmi.UnknownHostException;
import stocktracker.api.AdminApi;

/**
 *
 * @author WallaceJ
 */
public class AdminDriver extends AbstractClient
{

    public static void main(String[] args) throws Exception
    {
        boolean isConnected = false;

        while (!isConnected) {
            
                connectToServer();
                
                try {
                    AdminSession.loadRegistry();
                    AdminSession.setRemoteApi((AdminApi) AdminSession.registry.lookup(AdminApi.class.getSimpleName()));
                    isConnected = true;
                } catch (UnknownHostException uhex) {
                    System.err.println("Server could not be found or was not running PBJ Stock Exchange.");
                }
            
        }
        
        run();
    
    }
    
    
    
}
