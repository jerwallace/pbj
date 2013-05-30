package stocktracker.server;

/**
 *
 * @author WallaceJ
 */
import java.rmi.*;
import stocktracker.api.*;
import stocktracker.api.protocol.*;
import static stocktracker.api.protocol.AbstractProtocol.State;

public class UserApiImpl extends AbstractApiImpl implements UserApi
{

    private static final long serialVersionUID = 1L;

    public UserApiImpl() throws RemoteException
    {
        super();
        thisProtocol = new UserProtocol();
        
    }

    @Override
    public synchronized boolean buyStock(int numStocks) throws RemoteException
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
            if (StockList.getInstance().getStockByTickerName(currentStock.getTickerName()).getVolume()>numStocks) {
                currentUser.setBalance((currentUser.getBalance() - totalCost));
                currentUser.getStocksOwned().put(currentStock.getTickerName(), numStocksOwned + numStocks);
            } else {
                return false;
            }
            return true;
        }
    }

    @Override
    public synchronized boolean sellStock(int numStocks) throws RemoteException
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

        String output = "";

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
                        return "User " + currentUser.getUserName() + " signed in. \n Balance: $"+currentUser.getBalanceString();
                    }
                    else
                    {
                        return "User " + currentUser.getUserName() + " created.  \n Balance: $"+currentUser.getBalanceString();
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
                    currentStock = StockList.getInstance().getStockByTickerName(input);
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
                        return input + " Stock price is currently @: $" + currentStock.getPriceString() + 
                               " \n Current Balance: $"+currentUser.getBalanceString();
                    }
                case TRADE_STOCK_AMOUNT:
                    if (((UserProtocol) thisProtocol).getTradeFlag() == UserProtocol.Stock_Action.BUY_STOCK)
                    {
                        if (buyStock(Integer.parseInt(input)))
                        {
                            thisProtocol.setCurrentState(State.SELECT_COMMAND);
                            return Integer.parseInt(input) + " " + currentStock.getTickerName() + " stocks purchased. \n New Balance: $"+currentUser.getBalanceString();
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
                            return Integer.parseInt(input) + " " + currentStock.getTickerName() + " stocks sold. \n New Balance: $"+currentUser.getBalanceString();
                        }
                        else
                        {
                            return "You do not have enough stocks.";
                        }
                    }
                case UPDATE_BALANCE:
                    thisProtocol.setCurrentState(State.SELECT_COMMAND);
                    return currentUser.getBalanceString() + "";
                case PRINT_STOCK:
                    output = "Current Balance: $"+currentUser.getBalanceString()+" \n Here is a list of all stocks you own: \n";
                    thisProtocol.setCurrentState(State.SELECT_COMMAND);
                    return output + currentUser.printStocksOwned();
                default:
                    return "Error determining state.";
            }
        }

        return null;

    }
}
