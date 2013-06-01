/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.util.Scanner;
import stocktracker.client.protocol.AbstractProtocol;
import stocktracker.client.protocol.CustomException;

/**
 *
 * @author WallaceJ
 */
public abstract class AbstractClient {
    
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
               System.out.println("Please enter a valid postive number."); 
            } catch (CustomException ex) {
               System.out.println(ex);  
            } 
            
        }
    }
    
}
