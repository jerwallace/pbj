/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client.protocol;

import java.util.ArrayList;

/**
 *
 * @author WallaceJ
 */
public abstract class AbstractProtocol
{
    
    private State currentState;

    public enum State
    {

        LOGIN, SELECT_COMMAND, UPDATE_BALANCE, PRINT_STOCK, QUERY, NUM_COMMAND,
        UPDATE_STOCK, UPDATE_STOCK_PRICE, SELECT_STOCK, TRADE_STOCK_AMOUNT
    }
    protected ArrayList<String> messages = new ArrayList<String>();

    public abstract String getInstruction(State currentState);

    public abstract void toggleStateByCommand(int input) throws InvalidCommandException;
    
     /**
     * @return the currentState
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * @param currentState the currentState to set
     */
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }   
    
    
}
