/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import stocktracker.api.StockTrackerUserApi;
import java.util.Scanner;
/**
 *
 * @author WallaceJ
 */
public class StockTrackerUser {
    
    private static final String HOST = "localhost";
    private static final int PORT = 1099;
    private static Registry registry;

    public static void main(String[] args) throws Exception {
        
        registry = LocateRegistry.getRegistry(HOST, PORT);
        StockTrackerUserApi remoteApi = (StockTrackerUserApi) registry.lookup(StockTrackerUserApi.class.getSimpleName());
        
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
