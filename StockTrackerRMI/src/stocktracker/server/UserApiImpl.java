package stocktracker.server;

import java.rmi.*;
import stocktracker.api.*;
import stocktracker.client.protocol.CustomException;
import stocktracker.client.protocol.CustomException.ErrorType;

/**
 * Extends the AbstractApiImpl by adding Server side remotely invoked methods
 * that are used by User type Client
 */
public class UserApiImpl extends AbstractApiImpl implements UserApi
{

    private static final long serialVersionUID = 1L;

    /**
     * Public default constructor overriding the AbstractApiImpl superclass
     * <p/>
     * @throws RemoteException
     */
    public UserApiImpl() throws RemoteException
    {
        super();

    }

    /**
     * Public method that adds a numStock number of a Stock object given its
     * tickerName to a User object with the given userName
     * <p/>
     * @param tickerName
     * @param username
     * @param numStocks  - Number of Stocks purchased
     * <p/>
     * @throws RemoteException
     */
    @Override
    public synchronized double buyStock(String tickerName, String username, int numStocks) throws CustomException, RemoteException
    {
        //If number of stocks requested is a negative number throw error
        if (numStocks < 0)
        {
            throw new CustomException(ErrorType.NEGATIVE_NUMBER);
        }
        else
        {
            //Get the selected Stock object including its current price from the StockList
            Stock currentStock = StockList.getInstance().getStockByTickerName(tickerName);

            //Get current User object from the UserList
            User currentUser = UserList.getInstance().getUser(username);

            //Calculate the total purchase cost of the selected trade
            double totalCost = numStocks * currentStock.getPrice();

            //Get the current number of same selected stock already owned by currentUser
            int numStocksOwned = currentUser.numStocksOwned(tickerName);

            //If user does not have enough balance for this trade
            if (currentUser.getBalance() < totalCost)
            {
                //Throw error
                throw new CustomException(ErrorType.INSUFFICIENT_FUNDS);
            }
            //Else, add if the requested number of stocks are not available in the market
            else if (currentStock.getVolume() < numStocks)
            {
                //Throw error
                throw new CustomException(ErrorType.UNAVAILABLE_STOCK_VOLUME);
            }
            //Else make the tarnsaction happen and update the UserList, StockList and return new Balance
            else
            {
                //Update User's Balance
                UserList.getInstance().getUser(username).setBalance((currentUser.getBalance() - totalCost));

                //Update number of Stocks user owns of the selected Stock
                UserList.getInstance().getUser(username).updateUserStock(currentStock.getTickerName(), numStocksOwned + numStocks);

                //Decrease the overall available volume of selected stock in the Stock Market by deducting them from the StockList
                StockList.getInstance().getStockByTickerName(currentStock.getTickerName()).decreaseVolume(numStocks);
                return (UserList.getInstance().getUser(username).getBalance());
            }
        }
    }

    /**
     * Public method that sells a numStock number of a Stock object given its
     * tickerName from a User object with the given userName
     * <p/>
     * @param tickerName
     * @param username
     * @param numStocks  - Number of stocks to trade
     * <p/>
     * @throws RemoteException
     */
    @Override
    public synchronized double sellStock(String tickerName, String username, int numStocks) throws CustomException, RemoteException
    {
        //If number of stocks requested is a negative number throw error
        if (numStocks < 0)
        {
            throw new CustomException(ErrorType.NEGATIVE_NUMBER);
        }
        else
        {
            //Get the selected Stock object including its current price from the StockList
            Stock currentStock = StockList.getInstance().getStockByTickerName(tickerName);

            //Get current User object from the UserList
            User currentUser = UserList.getInstance().getUser(username);

            //Calculate the total amount of the selected trade
            double totalSalePrice = numStocks * currentStock.getPrice();

            //Get the current number of same selected stock already owned by currentUser
            int numStocksOwned = currentUser.getStocksOwned().get(currentStock.getTickerName());

            //If User has less available stocks to trade as selected throw error
            if (numStocksOwned < numStocks)
            {
                throw new CustomException(ErrorType.STOCKS_NOT_AVAILABLE);
            }
            //Else make the tarnsaction happen and update the UserList, StockList and return new Balance
            else
            {

                //Increase the overall available volume of selected stock in the Stock Market by adding them back to StockList
                StockList.getInstance().getStockByTickerName(currentStock.getTickerName()).increaseVolume(numStocks);
                
                UserList.getInstance().getUser(username).setBalance((currentUser.getBalance() + totalSalePrice));

                //If User has sold all of their Stocks of the selected kind, remove that tickerName from User's StocksOwned
                if (numStocksOwned == numStocks)
                {
                    UserList.getInstance().getUser(username).removeUserStock(currentStock.getTickerName());
                }
                else
                {
                    // Overwrite the current stock.
                    UserList.getInstance().getUser(username).updateUserStock(currentStock.getTickerName(), (numStocksOwned - numStocks));
                }
                return (UserList.getInstance().getUser(username).getBalance());
            }
        }
    }
}
