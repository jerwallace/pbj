/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.api.protocol;

/**
 *
 * @author WallaceJ
 */
public class AdminProtocol extends Protocol {

    private String menu = "1. Update Stock Price"+"\t"+"2. Print Stock"+"\t"+"2. Log out";
    
    @Override
    public String getInstruction() {
        switch (currentState) {
            case LOGIN:
                return "Login:";
            case SELECT_COMMAND:
                return menu;
            case UPDATE_STOCK: 
                return "Which stock would you like to update?";
            case UPDATE_STOCK_PRICE: 
                return "What is the new price?";
            case PRINT_STOCK: 
                return "What is the new price?";
            default:
                return "Error determining state.";
        }
    }

    @Override
    public void toggleStateByCommand(int input) throws InvalidCommandException {
        switch (input) {
            case 1:
                currentState = State.UPDATE_STOCK;
                break;
            case 2:
                currentState = State.PRINT_STOCK;
                break;
            case 3:
                currentState = State.LOGIN;
                break;
            default:
                throw new InvalidCommandException();
        }
    }
   
}
