/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client.protocol;

/**
 *
 * @author WallaceJ
 */
public class AdminProtocol extends AbstractProtocol
{

    private String menu = "1. Update Stock Price" + "\t" + "2. Print Stock" + "\t" + "2. Log out";

    @Override
    public String getInstruction(State currentState)
    {
        switch (currentState)
        {
            case LOGIN:
                return "Login:";
            case SELECT_COMMAND:
                return menu;
            case UPDATE_STOCK:
                return "Which stock would you like to update? (or type \"cancel\" to go back)";
            case UPDATE_STOCK_PRICE:
                return "Enter new stock value: (or type \"cancel\" to go back)";
            case PRINT_STOCK:
                return null;
            default:
                return "Error determining state.";
        }
    }

    @Override
    public void toggleStateByCommand(int input) throws InvalidCommandException
    {
        switch (input)
        {
            case 1:
                setCurrentState(State.UPDATE_STOCK);
                break;
            case 2:
                setCurrentState(State.PRINT_STOCK);
                break;
            case 3:
                setCurrentState(State.LOGIN);
                break;
            default:
                throw new InvalidCommandException();
        }
    }
}
