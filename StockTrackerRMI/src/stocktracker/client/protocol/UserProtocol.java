/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client.protocol;

import java.rmi.RemoteException;
import stocktracker.api.User;
import stocktracker.api.UserApi;
import stocktracker.client.UserSession;
import stocktracker.client.protocol.AbstractProtocol.State;

/**
 *
 * @author WallaceJ
 */
public class UserProtocol extends AbstractProtocol
{

    private String menu = "1. Buy Stock    2. Sell Stock   3. Query Stock  4. Print Stock  5. Logout";
    UserSession thisSession = UserSession.getInstance();
    
    public enum Stock_Action
    {

        BUY_STOCK, SELL_STOCK, QUERY_STOCK
    }

    @Override
    public void toggleStateByCommand(int input) throws InvalidCommandException
    {
        switch (input)
        {
            case 1:
                thisSession.setCurrentAction(Stock_Action.BUY_STOCK);
                thisSession.setCurrentState(State.SELECT_STOCK);
                break;
            case 2:
                thisSession.setCurrentAction(Stock_Action.SELL_STOCK);
                thisSession.setCurrentState(State.SELECT_STOCK);
                break;
            case 3:
                thisSession.setCurrentAction(Stock_Action.QUERY_STOCK);
                thisSession.setCurrentState(State.SELECT_STOCK);
                break;
            case 4:
                thisSession.setCurrentState(State.PRINT_STOCK);
                break;
            case 5:
                thisSession.setCurrentState(State.LOGIN);
                break;
            default:
                throw new InvalidCommandException();
        }
    }

    @Override
    public String getInstruction(State currentState)
    {
        switch (currentState)
        {
            case LOGIN:
                return "Login:";
            case SELECT_COMMAND:
                return menu;
            case SELECT_STOCK:
                return "Which stock would you like to trade? (or type \"cancel\" to go back)";
            case TRADE_STOCK_AMOUNT:
                return "How many stocks would you like to trade? (or type \"cancel\" to go back)";
            case UPDATE_BALANCE:
                return "Current balance:";
            case QUERY:
                return "Which stock would you like to query?(or type \"cancel\" to go back)";
            case PRINT_STOCK:
                return null;
            default:
                return "Error determining state.";
        }
    }
    
    @Override
    public String processInput(String input) throws RemoteException
    {
        User currentUser = thisSession.getRemoteApi().getUser(thisSession.getUsername());
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
                    thisSession.setUsername(input);
                    String action = "";
                    if (thisSession.getRemoteApi().userExists(thisSession.getUsername()))
                    {
                        action = "signed in";
                    }
                    else
                    {
                        action = "created";
                    }
                    
                    currentUser = thisSession.getRemoteApi().getUser(thisSession.getUsername());
                    return "User " + thisSession.getUsername() + " "+action+". \n Balance: $"+currentUser.getBalanceString();
                    
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
                case SELECT_STOCK:
                    thisSession.setSelectedStockName(input);
                    if (thisSession.getCurrentAction() != UserProtocol.Stock_Action.QUERY_STOCK)
                    {
                        thisSession.setCurrentState(State.TRADE_STOCK_AMOUNT);
                    }
                    return thisSession.getRemoteApi().selectStock(thisSession.getSelectedStockName())+" \nCurrent Balance: "+thisSession.getRemoteApi().getUser(thisSession.getUsername()).getBalanceString();
                case TRADE_STOCK_AMOUNT:
                    currentUser = thisSession.getRemoteApi().getUser(thisSession.getUsername());
                    int numStocks = Integer.parseInt(input);

                    if (thisSession.getCurrentAction() == UserProtocol.Stock_Action.BUY_STOCK)
                    {
                        ((UserApi)thisSession.getRemoteApi()).buyStock(thisSession.getSelectedStockName(), thisSession.getUsername(), numStocks);
                        thisSession.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                        return numStocks + " " + thisSession.getSelectedStockName() + " stocks purchased. \n New Balance: $"+currentUser.getBalanceString();
                    }
                    else if (thisSession.getCurrentAction() == UserProtocol.Stock_Action.SELL_STOCK)
                    {
                        ((UserApi)thisSession.getRemoteApi()).sellStock(thisSession.getSelectedStockName(), thisSession.getUsername(), numStocks);
                        thisSession.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                        return numStocks + " " + thisSession.getSelectedStockName() + " stocks sold. \n New Balance: $"+currentUser.getBalanceString();
                    }
                case UPDATE_BALANCE:
                    thisSession.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                    return currentUser.getBalanceString() + "";
                case PRINT_STOCK:
                    output = "Current Balance: $"+currentUser.getBalanceString()+" \n Here is a list of all stocks you own: \n";
                    thisSession.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                    return output + currentUser.printStocksOwned();
                default:
                    return "Error determining state.";
            }
        }

        return null;

    }
}
