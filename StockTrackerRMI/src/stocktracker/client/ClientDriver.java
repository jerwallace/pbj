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

/**
 *
 * @author WallaceJ
 */
public abstract class ClientDriver {

        protected static final String HOST = "localhost";
        protected static final int PORT = 1099;
        protected static Registry registry;
        protected static AbstractApi remoteApi;
    
        public static void loadRegistry() throws RemoteException {
            registry = LocateRegistry.getRegistry(HOST, PORT);
        }
        
        public static void run() throws Exception {
        
        Scanner input = new Scanner(System.in);
        String inputString = "";
        
        while (!inputString.equalsIgnoreCase("Exit")) {
            
            String nextInstruction = remoteApi.getNextInstruction();
            
            if (nextInstruction != null) {
                System.out.println(nextInstruction);
                inputString = input.nextLine();
            }
            try {
               String serverOutput = remoteApi.processInput(inputString);
               if (serverOutput != null) {
                    System.out.println(serverOutput);    
               }
            } catch (NumberFormatException ex) {
               System.out.println("Please enter a valid number.");  
            } 
            
        }
    }
    
}
