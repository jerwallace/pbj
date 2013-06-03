package stocktracker.client.protocol;

import java.rmi.RemoteException;
import stocktracker.api.AdminApi;
import stocktracker.api.StockList;
import stocktracker.api.User;
import stocktracker.client.AdminSession;
import stocktracker.client.protocol.CustomException.ErrorType;

/**
 * Admin Protocol contains all of the actions and the flow of states between
 * the server and the client.
 */
public class AdminProtocol extends AbstractProtocol
{

    private String menu = "1. Update Stock Price" + "\t" + "2. Print Stock" + "\t" + "2. Log out";
    AdminSession thisSession = AdminSession.getInstance();
    
    /**
     * getInstruction will return a question to the client based on a state.
     * @param currentState The current state the user is in.
     * @return The corresponding instruction.
     */    
    @Override
    public String getInstruction(State currentState) throws CustomException
    {
        switch (currentState)
        {
            case LOGIN:
                return "Login:";
            case SELECT_COMMAND:
                return menu;
            case UPDATE:
                return "Which stock would you like to update? (or type \"cancel\" to go back)";
            case UPDATE_STOCK_PRICE:
                return "Enter new stock value: (or type \"cancel\" to go back)";
            case PRINT_STOCK:
                return null;
            default:
                throw new CustomException(ErrorType.INVALID_COMMAND);
        }
    }

    /**
     * This method accepts a number and issues a state change.
     * @param input The number that corresponds to the menu.
     * @throws InvalidCommandException When an invalid number is selected.
     */
    @Override
    public void toggleStateByCommand(int input) throws CustomException
    {
        switch (input)
        {
            case 1:
                thisSession.setCurrentState(State.UPDATE);
                break;
            case 2:
                thisSession.setCurrentState(State.PRINT_STOCK);
                break;
            case 3:
                thisSession.setCurrentState(State.LOGIN);
                break;
            default:
                throw new CustomException(ErrorType.INVALID_COMMAND);
        }
    }
    
    @Override
    public String processInput(String input) throws CustomException, RemoteException
    {
        User currentUser = thisSession.getRemoteApi().getUser(thisSession.getUsername());
        
        System.out.println(thisSession.getUsername()+" - testing."+" ("+input+") - "+thisSession.getCurrentState().toString());
        String output = "";

        if (input.equalsIgnoreCase("cancel"))
        {
            thisSession.setCurrentState(State.SELECT_COMMAND);
            return null;
        }
        else
        {
            switch (thisSession.getCurrentState())
            {
                case LOGIN:
                    thisSession.setCurrentState(State.SELECT_COMMAND);
                    System.out.println("login function.");
                    // If the user does not exist, a new user will be created.
                    if (thisSession.getRemoteApi().userExists(input))
                    {
                        return "User " + thisSession.getUsername() + " signed in.";
                    }
                    else
                    {
                        return "User " + thisSession.getUsername() + " created.";
                    }
                case SELECT_COMMAND:
                    toggleStateByCommand(Integer.parseInt(input));
                    break;
                case UPDATE:
                    thisSession.setCurrentState(State.UPDATE_STOCK_PRICE);
                    thisSession.setSelectedStockName(thisSession.getRemoteApi().selectStock(thisSession.getSelectedStockName()));
                    return thisSession.getSelectedStockName()+" has been selected.";
                case UPDATE_STOCK_PRICE:
                    thisSession.setCurrentState(State.SELECT_COMMAND);
                    // Accept the new price and update the stock.
                    double newPrice = Double.parseDouble(input);
                    ((AdminApi)thisSession.getRemoteApi()).updateStock(thisSession.getSelectedStockName(),newPrice);
                    return "Stock Updated: "+((AdminApi)thisSession.getRemoteApi()).selectStock(thisSession.getSelectedStockName());
                case PRINT_STOCK:
                    output = "Here is a list of all stocks you have updated:\n";
                    thisSession.setCurrentState(State.SELECT_COMMAND);
                    return output + StockList.getInstance();
                default:
                    throw new CustomException(ErrorType.INVALID_COMMAND);
            }
        }
        return null;
    }
}
