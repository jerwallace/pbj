/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.api.protocol;

import static stocktracker.api.protocol.Protocol.State;

/**
 *
 * @author WallaceJ
 */
public class UserProtocol extends Protocol {
    
    private String menu = "1. Buy Stock    2. Sell Stock   3. Print Stock   4. Logout";
    
    @Override
    public void toggleStateByCommand(int input) throws InvalidCommandException {
        switch (input) {
            case 1:
                currentState = State.BUY_STOCK;
                break;
            case 2:
                currentState = State.SELL_STOCK;
                break;
            case 3:
                currentState = State.PRINT_STOCK;
                break;
            case 4:
                currentState = State.LOGIN;
                break;
            default:
                throw new InvalidCommandException();
        }
    }
    
    @Override
    public String getInstruction() {
        switch (currentState) {
            case LOGIN:
                return "Login:";
            case SELECT_COMMAND:
                return menu;
            case BUY_STOCK: 
                return "Which stock would you like to buy?";
            case BUY_STOCK_AMOUNT: 
                return "How much stock would you like to buy?";
            case SELL_STOCK: 
                return "Which stock would you like to sell?";
            case SELL_STOCK_AMOUNT: 
                return "How much stock would you like to sell?";
            case UPDATE_BALANCE: 
                return "Current balance:"; 
            case PRINT_STOCK: 
                return menu;
            default:
                return "Error determining state.";
        }
    }

}
