package stocktracker.client.protocol;

import java.rmi.RemoteException;
import stocktracker.api.User;
import stocktracker.api.UserApi;
import stocktracker.client.UserSession;
import stocktracker.client.protocol.AbstractProtocol.State;
import stocktracker.client.protocol.CustomException.ErrorType;

/**
 * User Protocol contains all of the actions and the flow of states between
 * the server and the client.
 */
public class UserProtocol extends AbstractProtocol
{

    // This is the menu screen first displayed when the user is logged in.
    private String menu = "1. Buy Stock    2. Sell Stock   3. Query Stock  4. Print Stock  5. Logout";
    UserSession thisSession = UserSession.getInstance();
    
    // Stock_Action is a sub-state depending on the action the user wants to take.
    public enum Stock_Action
    {
        BUY, SELL, QUERY_STOCK
    }

    public String getCurrentBalance() throws RemoteException {
        return "\nCurrent Balance: "+thisSession.getRemoteApi().getUser(thisSession.getUsername()).getBalanceString();
    }
    
    public String printMyStocks() throws RemoteException {
        return "Here is a list of all stocks you own:" + getCurrentBalance() + "\n" + thisSession.getRemoteApi().getUser(thisSession.getUsername()).printStocksOwned();
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
                thisSession.setCurrentAction(Stock_Action.BUY);
                thisSession.setCurrentState(State.SELECT_STOCK);
                break;
            case 2:
                thisSession.setCurrentAction(Stock_Action.SELL);
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
                throw new CustomException(ErrorType.INVALID_COMMAND);
        }
    }

    /**
     * getInstruction will return a question to the client based on a state.
     * @param currentState The current state the user is in.
     * @return The corresponding instruction.
     */
    @Override
    public String getInstruction() throws CustomException
    {
        switch (thisSession.getInstance().getCurrentState())
        {
            case LOGIN:
                return "Login:";
            case SELECT_COMMAND:
                return menu;
            case SELECT_STOCK:
                return "Which stock would you like to "+thisSession.getCurrentAction().toString().toLowerCase()+"? (or type \"cancel\" to go back)";
            case TRADE_STOCK_AMOUNT:
                return "How many stocks would you like to "+thisSession.getCurrentAction().toString().toLowerCase()+"? (or type \"cancel\" to go back)";
            case UPDATE_BALANCE:
                return "Current balance:";
            case QUERY:
                return "Which stock would you like to query?(or type \"cancel\" to go back)";
            case PRINT_STOCK:
                return null;
            default:
                throw new CustomException(ErrorType.INVALID_COMMAND);
        }
    }
    
    /**
     * processInput accepts input from the user and performs an action based on the
     * input and the users current state and then sends a response from the server.
     * @param input The user response.
     * @return The server response.
     * @throws RemoteException 
     */
    @Override
    public String processInput(String input) throws CustomException, RemoteException
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
                    
                    // If the user does not exist, a new user will be created.
                    if (thisSession.getRemoteApi().userExists(thisSession.getUsername()))
                    {
                        action = "signed in";
                    }
                    else
                    {
                        action = "created";
                    }
                    
                    currentUser = thisSession.getRemoteApi().getUser(thisSession.getUsername());
                    return "User " + thisSession.getUsername() + " "+action+"."+getCurrentBalance();
                case SELECT_COMMAND:
                    toggleStateByCommand(Integer.parseInt(input));
                    break;
                case SELECT_STOCK:
                    thisSession.setSelectedStockName(input);
                    String selectedStock = thisSession.getRemoteApi().selectStock(thisSession.getSelectedStockName());
                    
                    if (thisSession.getCurrentAction() != UserProtocol.Stock_Action.QUERY_STOCK)
                    {
                        // If the current state is Buy or Sell, ask for an amount.
                        thisSession.setCurrentState(State.TRADE_STOCK_AMOUNT);
                    } else {
                        // If the current state is Query, go back to the menu.
                        thisSession.setCurrentState(State.SELECT_COMMAND);
                    }
                    return selectedStock;
                    
                case TRADE_STOCK_AMOUNT:
                    
                    currentUser = thisSession.getRemoteApi().getUser(thisSession.getUsername());
                    int numStocks = Integer.parseInt(input);
                    
                    if (thisSession.getCurrentAction() == UserProtocol.Stock_Action.BUY)
                    {
                        // Buy the stock selected and return the new balance after the operation is complete.
                        double newBalance = ((UserApi)thisSession.getRemoteApi()).buyStock(thisSession.getSelectedStockName(), thisSession.getUsername(), numStocks);
                        thisSession.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                        // Print new balance.
                        return numStocks + " " + thisSession.getSelectedStockName() + " stocks purchased. \n"+printMyStocks();
                    }
                    else if (thisSession.getCurrentAction() == UserProtocol.Stock_Action.SELL)
                    {
                        // Sell the stock selected and return the new balance after the operation is complete.
                        double newBalance = ((UserApi)thisSession.getRemoteApi()).sellStock(thisSession.getSelectedStockName(), thisSession.getUsername(), numStocks);
                        thisSession.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                        
                        // Print new balance.
                        return numStocks + " " + thisSession.getSelectedStockName() + " stocks sold. \n"+printMyStocks();
                    }
                
                case UPDATE_BALANCE:
                    // Display current user's balance.
                    thisSession.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                    return currentUser.getBalanceString() + "";
                
                case PRINT_STOCK:
                    // Print the stocks owned by the user, along with an up to date balance.
                    thisSession.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                    return printMyStocks();
                default:
                    throw new CustomException(ErrorType.INVALID_COMMAND);
            }
        }

        return "";

    }
}
