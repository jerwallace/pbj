/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import stocktracker.api.AbstractApi;
import stocktracker.api.StockList;
import stocktracker.client.protocol.AbstractProtocol;
import stocktracker.client.protocol.AbstractProtocol.State;
import static stocktracker.client.protocol.AbstractProtocol.State.SELECT_COMMAND;
import stocktracker.client.protocol.CustomException;
import stocktracker.client.protocol.InvalidCommandException;

/**
 *
 * @author WallaceJ
 */
public abstract class AbstractClient {

        protected static final String HOST = "localhost";
        protected static final int PORT = 1099;
        protected static Registry registry;
        protected static AbstractApi remoteApi;
        protected static AbstractProtocol thisProtocol;
        protected String username;
        protected String selectedStockName;
        
        public static void loadRegistry() throws RemoteException {
            registry = LocateRegistry.getRegistry(HOST, PORT);
        }
        
        public static void run() throws Exception {
        
        Scanner input = new Scanner(System.in);
        String inputString = "";
        
        while (!inputString.equalsIgnoreCase("Exit")) {
            
            String nextInstruction = remoteApi.getNextInstruction(currentState);
            
            if (nextInstruction != null) {
                System.out.println(nextInstruction);
                inputString = input.nextLine();
            }
            try {
               String serverOutput = processInput(inputString);
               if (serverOutput != null) {
                    System.out.println(serverOutput);    
               }
            } catch (NumberFormatException nfex) {
               System.out.println("Please enter a valid postive number."); 
            } catch (CustomException ex) {
               System.out.println(ex);  
            } 
            
        }
    }

    public abstract String processInput(String input) throws RemoteException;
    
}
