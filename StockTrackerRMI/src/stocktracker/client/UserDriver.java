/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.rmi.UnknownHostException;
import stocktracker.api.UserApi;
import static stocktracker.client.AbstractClient.connectToServer;
import stocktracker.client.protocol.UserProtocol;

/**
 * UserDriver class is the driver for User type client
 */
public class UserDriver extends AbstractClient
{

    public static void main(String[] args) throws Exception
    {
        //Create a new Client control protocol to pass to the new connected user
        thisProtocol = new UserProtocol();

        boolean isConnected = false;

        while (!isConnected)
        {
            //Attempt to connect to Server
            connectToServer();
            try
            {
                //Load the RMI registry for the user session
                UserSession.loadRegistry();
                UserSession.setRemoteApi((UserApi) UserSession.registry.lookup(UserApi.class.getSimpleName()));
                System.out.println("Welcome to PBJ Stock Exchange @ " + UserSession.getHost());
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

        //Runs the protocol
        run();
    }
}
