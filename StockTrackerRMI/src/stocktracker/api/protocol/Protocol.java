/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.api.protocol;

import java.util.ArrayList;

/**
 *
 * @author WallaceJ
 */
public abstract class Protocol {
    
    public enum State {
        LOGIN,SELECT_COMMAND,BUY_STOCK,BUY_STOCK_AMOUNT,SELL_STOCK,SELL_STOCK_AMOUNT,
        UPDATE_BALANCE, PRINT_STOCK, NUM_COMMAND, UPDATE_STOCK, UPDATE_STOCK_PRICE
    }
    
    protected State currentState = State.LOGIN;
    
    protected ArrayList<String> messages = new ArrayList<String>();
    
    public abstract String getInstruction();
    
    public abstract void toggleStateByCommand(int input) throws InvalidCommandException;
    
    public void setCurrentState(State newState) {
        this.currentState = newState;
    }
    
    public State getCurrentState() {
        return currentState;
    }

}
