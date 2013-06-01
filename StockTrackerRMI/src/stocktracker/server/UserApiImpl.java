package stocktracker.server;

/**
 *
 * @author WallaceJ
 */
import java.rmi.*;
import stocktracker.api.*;
import stocktracker.client.protocol.CustomException;
import stocktracker.client.protocol.CustomException.ErrorType;

/**
 *
 * @author Bahman
 */
public class UserApiImpl extends AbstractApiImpl implements UserApi
{

    private static final long serialVersionUID = 1L;

    /**
     *
     * @throws RemoteException
     */
    public UserApiImpl() throws RemoteException
    {
        super();
//        stockList.updateStock(new Stock("BBRY", 14.56, 13000000));
//        stockList.updateStock(new Stock("GOOG", 120.0, 2300000));
//        stockList.updateStock(new Stock("AAPL", 112.23, 9870000));
        UserList.getInstance().addUser("bahman").setBalance(10000);
        UserList.getInstance().addUser("jeremy").setBalance(500000);
        UserList.getInstance().addUser("peter").setBalance(0);
    }

    /**
     *
     * @param tickerName
     * @param username
     * @param numStocks
     * <p/>
     * @throws RemoteException
     */
    @Override
    public synchronized void buyStock(String tickerName, String username, int numStocks) throws RemoteException
    {
        Stock currentStock = StockList.getInstance().getStockByTickerName(tickerName);
        User currentUser = UserList.getInstance().getUser(username);

        double totalCost = numStocks * currentStock.getPrice();
        int numStocksOwned = currentUser.numStocksOwned(tickerName);

        if (currentUser.getBalance() < totalCost)
        {
            throw new CustomException(ErrorType.INSUFFICIENT_FUNDS);
        }
        else if (currentStock.getVolume() < numStocks)
        {
            throw new CustomException(ErrorType.UNAVAILABLE_STOCK_VOLUME);
        }
        else
        {
            UserList.getInstance().getUser(username).setBalance((currentUser.getBalance() - totalCost));
            UserList.getInstance().getUser(username).updateUserStock(currentStock.getTickerName(), numStocksOwned + numStocks);
            StockList.getInstance().getStockByTickerName(currentStock.getTickerName()).decreaseVolume(numStocks);
        }
    }

    /**
     *
     * @param tickerName
     * @param username
     * @param numStocks
     * <p/>
     * @throws RemoteException
     */
    @Override
    public synchronized void sellStock(String tickerName, String username, int numStocks) throws RemoteException
    {
        Stock currentStock = StockList.getInstance().getStockByTickerName(tickerName);
        User currentUser = UserList.getInstance().getUser(username);

        double totalSalePrice = numStocks * currentStock.getPrice();
        int numStocksOwned = currentUser.getStocksOwned().get(currentStock.getTickerName());

        if (numStocksOwned < numStocks)
        {
            throw new CustomException(ErrorType.STOCKS_NOT_AVAILABLE);
        }
        else
        {
            
            StockList.getInstance().getStockByTickerName(currentStock.getTickerName()).increaseVolume(numStocks);
            UserList.getInstance().getUser(username).setBalance((currentUser.getBalance() + totalSalePrice));

            if (numStocksOwned == numStocks)
            {
                UserList.getInstance().getUser(username).removeUserStock(currentStock.getTickerName());
            }
            else
            {
                // Overwrite the current stock.
                UserList.getInstance().getUser(username).updateUserStock(currentStock.getTickerName(), (numStocksOwned - numStocks));
            }
        }
    }
}
