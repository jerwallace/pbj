/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.rmi.RemoteException;
import stocktracker.api.AbstractApi;
import stocktracker.api.StockList;
import stocktracker.client.protocol.AbstractProtocol;
import static stocktracker.client.protocol.AbstractProtocol.State;
import stocktracker.client.protocol.InvalidCommandException;

/**
 *
 * @author WallaceJ
 */
public class AdminDriver extends AbstractClient
{

    public static void main(String[] args) throws Exception
    {
        loadRegistry();
        remoteApi = (AbstractApi) registry.lookup(AbstractApi.class.getSimpleName());
        run();
    }
    
       @Override
    public String processInput(String input) throws RemoteException
    {
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
                    if (userExists(input))
                    {
                        return "User " + currentUser.getUserName() + " signed in.";
                    }
                    else
                    {
                        return "User " + currentUser.getUserName() + " created.";
                    }
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
                case UPDATE_STOCK:
                    thisProtocol.setCurrentState(AbstractProtocol.State.UPDATE_STOCK_PRICE);
                    currentStock = StockList.getInstance().getStockByTickerName(input);
                    currentUser.getStocksOwned().put(currentStock.getTickerName(), 1);
                    if (currentStock == null)
                    {
                        StockList.getInstance().updateStock(currentStock);
                    }
                    break;
                case UPDATE_STOCK_PRICE:
                    thisProtocol.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                    currentStock.setPrice(Double.parseDouble(input));
                    StockList.getInstance().updateStock(currentStock);
                    return currentStock.getTickerName() + " price has changed to: $" + Double.parseDouble(input) + ".";
                case PRINT_STOCK:
                    output = "Here is a list of all stocks you have updated:\n";
                    thisProtocol.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                    return output + StockList.getInstance();
                default:
                    return "Error determining state.";
            }
        }
        return null;
    }
}
