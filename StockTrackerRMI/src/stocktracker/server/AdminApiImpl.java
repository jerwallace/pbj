/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

/**
 *
 * @author WallaceJ
 */
import stocktracker.api.protocol.AdminProtocol;
import java.rmi.*;
import stocktracker.api.*;
import stocktracker.api.protocol.InvalidCommandException;
import stocktracker.api.protocol.AbstractProtocol;

public class AdminApiImpl extends AbstractApiImpl implements AbstractApi
{

    private static final long serialVersionUID = 1L;
    private int counter = 0;

    public AdminApiImpl() throws RemoteException
    {
        super();
        thisProtocol = new AdminProtocol();
    }

    @Override
    public String processInput(String input) throws RemoteException
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
                currentStock = stockList.getStockByTickerName(input);
                if (currentStock == null)
                {
                }
                break;
            case UPDATE_STOCK_PRICE:
                thisProtocol.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                currentStock.setPrice(Double.parseDouble(input));
                stockList.updateStock(currentStock);
                return currentStock.getTickerName() + " price has changed to: $" + Double.parseDouble(input) + ".";
            case PRINT_STOCK:
                thisProtocol.setCurrentState(AbstractProtocol.State.SELECT_COMMAND);
                return stockList + "";
            default:
                return "Error determining state.";
        }

        return null;

    }
}