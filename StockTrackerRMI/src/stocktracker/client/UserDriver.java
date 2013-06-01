/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.rmi.RemoteException;
import stocktracker.api.StockList;
import stocktracker.api.User;
import stocktracker.api.UserApi;
import static stocktracker.client.AbstractClient.remoteApi;
import stocktracker.client.protocol.AbstractProtocol;
import stocktracker.client.protocol.InvalidCommandException;
import stocktracker.client.protocol.UserProtocol;
import stocktracker.client.protocol.AbstractProtocol.State;
import stocktracker.client.protocol.CustomException;

/**
 *
 * @author WallaceJ
 */
public class UserDriver extends AbstractClient {
    
    
    public static void main(String[] args) throws Exception  {
        thisProtocol = new UserProtocol();
        loadRegistry();
        remoteApi = (UserApi) registry.lookup(UserApi.class.getSimpleName());
        run();
    }
    
    @Override
    public static String processInput(String input) throws RemoteException
    {
        User currentUser = remoteApi.getUser(username);
        
        String output = "";

        if (input.equalsIgnoreCase("cancel"))
        {
            thisProtocol.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
            return null;
        }
        else
        {
            switch (thisProtocol.getCurrentState())
            {
                case LOGIN:
                    thisProtocol.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                    username = input;
                    String action = "";
                    if (remoteApi.userExists(username))
                    {
                        action = "signed in";
                    }
                    else
                    {
                        action = "created";
                    }
                    
                    currentUser = remoteApi.getUser(username);
                    return "User " + username + " "+action+". \n Balance: $"+currentUser.getBalanceString();
                    
                case SELECT_COMMAND:
                    try
                    {
                        thisProtocol.toggleStateByCommand(Integer.parseInt(input));
                    }
                    catch (InvalidCommandException ex)
                    {
                        return "Please enter a valid command.";
                    }
                    break;
                case SELECT_STOCK:
                    this.selectedStockName = input;
                    return remoteApi.selectStock(this.selectedStockName)+" \nCurrent Balance: "+remoteApi.getUser(username).getBalanceString();
                case TRADE_STOCK_AMOUNT:
                    currentUser = remoteApi.getUser(username);
                    int numStocks = Integer.parseInt(input);

                    if (((UserProtocol)thisProtocol).getTradeFlag() == UserProtocol.Stock_Action.BUY_STOCK)
                    {
                        ((UserApi)remoteApi).buyStock(selectedStockName, username, numStocks);
                        thisProtocol.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                        return numStocks + " " + this.selectedStockName + " stocks purchased. \n New Balance: $"+currentUser.getBalanceString();
                    }
                    else if (((UserProtocol) thisProtocol).getTradeFlag() == UserProtocol.Stock_Action.SELL_STOCK)
                    {
                        ((UserApi)remoteApi).sellStock(selectedStockName, username, numStocks);
                        thisProtocol.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                        return numStocks + " " + this.selectedStockName + " stocks sold. \n New Balance: $"+currentUser.getBalanceString();
                    }
                case UPDATE_BALANCE:
                    thisProtocol.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                    return currentUser.getBalanceString() + "";
                case PRINT_STOCK:
                    output = "Current Balance: $"+currentUser.getBalanceString()+" \n Here is a list of all stocks you own: \n";
                    thisProtocol.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                    return output + currentUser.printStocksOwned();
                default:
                    return "Error determining state.";
            }
        }

        return null;

    }
}
