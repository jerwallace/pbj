package stocktracker.server;

/**
 *
 * @author WallaceJ
 */
import stocktracker.api.protocol.UserProtocol;
import java.rmi.*;
import stocktracker.api.*;
import stocktracker.api.protocol.InvalidCommandException;

import static stocktracker.api.protocol.AbstractProtocol.State;

public class UserApiImpl extends AbstractApiImpl implements UserApi
{

    private static final long serialVersionUID = 1L;

    public UserApiImpl() throws RemoteException
    {
        super();
        thisProtocol = new UserProtocol();
        stockList.updateStock(new Stock("BBRY", 14.56, 13000000));
        stockList.updateStock(new Stock("GOOG", 120.0, 2300000));
        stockList.updateStock(new Stock("AAPL", 112.23, 9870000));
        userList.addUser("bahman").setBalance(10000);
        userList.addUser("jeremy").setBalance(5000);
        userList.addUser("peter").setBalance(0);
    }

    @Override
    public boolean buyStock(int numStocks) throws RemoteException
    {

        double totalCost = numStocks * currentStock.getPrice();
        int numStocksOwned = 0;

        try
        {
            numStocksOwned = currentUser.getStocksOwned().get(currentStock.getTickerName());
        }
        catch (NullPointerException ex)
        {
        }

        if (currentUser.getBalance() < totalCost)
        {
            return false;
        }
        else
        {
            currentUser.setBalance((currentUser.getBalance() - totalCost));
            currentUser.getStocksOwned().put(currentStock.getTickerName(), numStocksOwned + numStocks);
            return true;
        }
    }

    @Override
    public boolean sellStock(int numStocks) throws RemoteException
    {

        double totalSalePrice = numStocks * currentStock.getPrice();
        int numStocksOwned = currentUser.getStocksOwned().get(currentStock.getTickerName());

        if (numStocksOwned < numStocks)
        {
            return false;
        }
        else
        {

            currentUser.setBalance((currentUser.getBalance() + totalSalePrice));

            if (numStocksOwned == numStocks)
            {
                currentUser.getStocksOwned().remove(currentStock.getTickerName());
            }
            else
            {
                // Overwrite the current stock.
                currentUser.getStocksOwned().put(currentStock.getTickerName(), (numStocksOwned - numStocks));
            }
            return true;
        }
    }

    @Override
    public String processInput(String input) throws RemoteException
    {
        if (input.equalsIgnoreCase("cancel"))
        {
            thisProtocol.setCurrentState(State.SELECT_COMMAND);
            return null;
        }
        else
        {
            switch (thisProtocol.getCurrentState())
            {
                case LOGIN:
                    thisProtocol.setCurrentState(State.SELECT_COMMAND);
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
                case SELECT_STOCK:
                    currentStock = stockList.getStockByTickerName(input);
                    if (currentStock == null)
                    {
                        return "Not a valid stock.";
                    }
                    else
                    {
                        if (((UserProtocol) thisProtocol).getTradeFlag() != UserProtocol.Stock_Action.QUERY_STOCK)
                        {
                            thisProtocol.setCurrentState(State.TRADE_STOCK_AMOUNT);
                        }
                        else
                        {
                            thisProtocol.setCurrentState(State.SELECT_COMMAND);
                        }
                        return input + " Stock price is currently @: $" + currentStock.getPrice();
                    }
                case TRADE_STOCK_AMOUNT:
                    if (((UserProtocol) thisProtocol).getTradeFlag() == UserProtocol.Stock_Action.BUY_STOCK)
                    {
                        if (buyStock(Integer.parseInt(input)))
                        {
                            thisProtocol.setCurrentState(State.SELECT_COMMAND);
                            return Integer.parseInt(input) + " " + currentStock.getTickerName() + " stocks purchased.";
                        }
                        else
                        {
                            return "Insufficient funds to buy stocks.";
                        }
                    }
                    else if (((UserProtocol) thisProtocol).getTradeFlag() == UserProtocol.Stock_Action.SELL_STOCK)
                    {
                        if (sellStock(Integer.parseInt(input)))
                        {
                            thisProtocol.setCurrentState(State.SELECT_COMMAND);
                            return Integer.parseInt(input) + " " + currentStock.getTickerName() + " stocks sold.";
                        }
                        else
                        {
                            return "You do not have enough stocks.";
                        }
                    }
                case UPDATE_BALANCE:
                    thisProtocol.setCurrentState(State.SELECT_COMMAND);
                    return currentUser.getBalance() + "";
                case PRINT_STOCK:
                    thisProtocol.setCurrentState(State.SELECT_COMMAND);
                    return currentUser.printStocksOwned();
                default:
                    return "Error determining state.";
            }
        }

        return null;

    }
}
