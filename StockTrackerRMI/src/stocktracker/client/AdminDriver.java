/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.rmi.UnknownHostException;
import stocktracker.api.AdminApi;
import stocktracker.client.protocol.AdminProtocol;

/**
 * AdminDriver class is the driver for Admin type client
 */
public class AdminDriver extends AbstractClient
{

    public static void main(String[] args) throws Exception
    {
        //Create a new Client control protocol to pass to the new connected user
        thisProtocol = new AdminProtocol();
        
        boolean isConnected = false;

        while (!isConnected)
        {
            //Attempt to connect to Server
            connectToServer();

            try
            {
                //Load the RMI registry for the user session
                AdminSession.loadRegistry();
                AdminSession.setRemoteApi((AdminApi) AdminSession.registry.lookup(AdminApi.class.getSimpleName()));
                isConnected = true;
            }
            catch (UnknownHostException uhex)
            {
                System.err.println("Server could not be found or was not running PBJ Stock Exchange.");
            }
            catch (Exception conex)
            {
                System.err.println("Error connecting. Please ensure that the server is on and running.");
            }

        }

        run();

    }
}
