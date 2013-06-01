/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client.protocol;

import java.rmi.RemoteException;
import stocktracker.api.AdminApi;
import stocktracker.api.StockList;
import stocktracker.api.UserApi;
import stocktracker.client.AdminSession;

/**
 *
 * @author WallaceJ
 */
public class AdminProtocol extends AbstractProtocol
{

    private String menu = "1. Update Stock Price" + "\t" + "2. Print Stock" + "\t" + "2. Log out";
    AdminSession thisSession = AdminSession.getInstance();
    
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
                thisSession.setCurrentState(State.UPDATE_STOCK);
                break;
            case 2:
                thisSession.setCurrentState(State.PRINT_STOCK);
                break;
            case 3:
                thisSession.setCurrentState(State.LOGIN);
                break;
            default:
                throw new InvalidCommandException();
        }
    }
    
    @Override
    public String processInput(String input) throws RemoteException
    {
        String output = "";

        if (input.equalsIgnoreCase("cancel"))
        {
            thisSession.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
            return null;
        }
        else
        {
            switch (thisSession.getCurrentState())
            {
                case LOGIN:
                    thisSession.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                    if (thisSession.getRemoteApi().userExists(input))
                    {
                        return "User " + thisSession.getUsername() + " signed in.";
                    }
                    else
                    {
                        return "User " + thisSession.getUsername() + " created.";
                    }
                case SELECT_COMMAND:
                    try
                    {
                        toggleStateByCommand(Integer.parseInt(input));
                    }
                    catch (InvalidCommandException ex)
                    {
                        return "Please enter a valid command.";
                    }
                    break;
                case UPDATE_STOCK:
                    thisSession.setCurrentState(AbstractProtocol.State.UPDATE_STOCK_PRICE);
                    thisSession.setSelectedStockName(thisSession.getRemoteApi().selectStock(thisSession.getSelectedStockName()));
                    return thisSession.getSelectedStockName()+" has been selected.";
                case UPDATE_STOCK_PRICE:
                    thisSession.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                    double newPrice = Double.parseDouble(input);
                    ((AdminApi)thisSession.getRemoteApi()).updateStock(thisSession.getSelectedStockName(),newPrice);
                    return "Stock Updated: "+((AdminApi)thisSession.getRemoteApi()).selectStock(thisSession.getSelectedStockName());
                case PRINT_STOCK:
                    output = "Here is a list of all stocks you have updated:\n";
                    thisSession.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                    return output + StockList.getInstance();
                default:
                    return "Error determining state.";
            }
        }
        return null;
    }
}
