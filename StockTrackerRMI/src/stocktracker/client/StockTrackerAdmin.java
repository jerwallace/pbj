/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import stocktracker.api.StockTrackerApi;
import stocktracker.api.StockTrackerUserApi;

/**
 *
 * @author WallaceJ
 */
public class StockTrackerAdmin {
    
    private static final String HOST = "localhost";
    private static final int PORT = 1100;
    private static Registry registry;

    public static void main(String[] args) throws Exception {
        
        registry = LocateRegistry.getRegistry(HOST, PORT);
        StockTrackerApi remoteApi = (StockTrackerApi) registry.lookup(StockTrackerApi.class.getSimpleName());
        
        Scanner input = new Scanner(System.in);
        String inputString = "";
        
        while (!inputString.equalsIgnoreCase("Exit")) {
            
            String nextInstruction = remoteApi.getNextInstruction();
            
            if (nextInstruction != null) {
                System.out.println(nextInstruction);
            }
            
            inputString = input.nextLine();
            String serverOutput = remoteApi.processInput(inputString);
            
            if (serverOutput != null) {
                System.out.println(serverOutput);    
            }
            
        }
        
    }
}
