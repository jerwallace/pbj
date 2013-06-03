/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client.protocol;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author WallaceJ
 */
public abstract class AbstractProtocol
{
    
    public enum State
    {

        LOGIN, SELECT_COMMAND, UPDATE_BALANCE, PRINT_STOCK, QUERY, NUM_COMMAND,
        UPDATE, UPDATE_STOCK_PRICE, SELECT_STOCK, TRADE_STOCK_AMOUNT
    }
    protected ArrayList<String> messages = new ArrayList<String>();

    public abstract String getInstruction(State currentState) throws CustomException;

    public abstract void toggleStateByCommand(int input) throws CustomException;
    
    public abstract String processInput(String input) throws CustomException, RemoteException;
    
}
