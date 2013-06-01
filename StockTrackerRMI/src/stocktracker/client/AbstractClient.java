/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import stocktracker.client.protocol.AbstractProtocol;
import stocktracker.client.protocol.CustomException;

/**
 *
 * @author WallaceJ
 */
public abstract class AbstractClient {

        protected static final String HOST = "localhost";
        protected static final int PORT = 1099;
        protected static Registry registry;
        protected static AbstractProtocol thisProtocol;
        
        public static void run() throws Exception {

        Scanner input = new Scanner(System.in);
        String inputString = "";

        while (!inputString.equalsIgnoreCase("Exit")) {
            
            String nextInstruction = thisProtocol.getInstruction(UserSession.getInstance().getCurrentState());
            
            if (nextInstruction != null) {
                System.out.println(nextInstruction);
                inputString = input.nextLine();
            }
            try {
               String serverOutput = thisProtocol.processInput(inputString);
               if (serverOutput != null) {
                    System.out.println(serverOutput);    
               }
            } catch (NumberFormatException nfex) {
               System.err.println("Please enter a valid postive number."); 
            } catch (CustomException ex) {
               System.err.println(ex);  
            } 
            
        }
    }
    
    public static void connectToServer() throws Exception {
        String validServerRegex = "^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$";
        Scanner input = new Scanner(System.in);
        String inputString = "";
        
        boolean isValidServer = false;
        
        while (!isValidServer) {
            
            System.out.println("Please enter a valid PBJ Stock Exchange server address (default: localhost):");
            inputString = input.nextLine();
            
            if (inputString.isEmpty()) {
                isValidServer = true;
            } else {
                Pattern rPattern = Pattern.compile(validServerRegex);
                Matcher matcher = rPattern.matcher(inputString);

                if (matcher.find()) {
                    System.out.println("Connecting to "+inputString+"..."); 
                    UserSession.setHost(inputString);
                    isValidServer = true;
                } else {
                    System.err.println("Invalid hostname or IP address."); 
                }
            }
        }
    }
        
}
