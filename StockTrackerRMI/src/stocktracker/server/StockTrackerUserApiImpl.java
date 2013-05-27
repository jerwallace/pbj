
package stocktracker.server;

/**
 *
 * @author WallaceJ
 */
import stocktracker.api.protocol.UserProtocol;
import java.rmi.*;
import stocktracker.api.*;
import stocktracker.api.protocol.InvalidCommandException;

import static stocktracker.api.protocol.Protocol.State;

public class StockTrackerUserApiImpl extends StockTrackerApiImpl implements StockTrackerUserApi {
    
    private static final long serialVersionUID = 1L;

    public StockTrackerUserApiImpl() throws RemoteException {
        super();
        thisProtocol = new UserProtocol();
    }
    
    @Override
    public boolean buyStock(int numStocks) throws RemoteException {
        double totalCost = numStocks*currentStock.getPrice();
        if (currentUser.getBalance()<totalCost) {
            return false;
        } else {
            currentUser.setBalance((currentUser.getBalance()-totalCost));
            currentUser.getStocksOwned().addStock(currentStock, numStocks);
            return true;
        }
    }

    @Override
    public boolean sellStock(int numStocks) throws RemoteException {
        
        double totalSalePrice = numStocks*currentStock.getPrice();
        int numStocksOwned = currentUser.getStocksOwned().getNumStocks(currentStock.getTickerName());
        
        if (numStocksOwned<numStocks) {
            return false;
        } else {
            
            currentUser.setBalance((currentUser.getBalance()+totalSalePrice));
            
            if (numStocksOwned==numStocks) {
                currentUser.getStocksOwned().removeStock(currentStock);
            } else {
                // Overwrite the current stock.
                currentUser.getStocksOwned().addStock(currentStock, (numStocksOwned-numStocks));
            }
            return true;
        }
    }
    
    @Override
    public String processInput(String input) throws RemoteException {
        
        switch (thisProtocol.getCurrentState()) {
            case LOGIN:
                thisProtocol.setCurrentState(State.SELECT_COMMAND);
                if (userExists(input)) {
                    return "User "+currentUser.getUserName()+" signed in.";
                } else {
                    return "User "+currentUser.getUserName()+" created.";
                }
            case SELECT_COMMAND:
                try {
                    thisProtocol.toggleStateByCommand(Integer.parseInt(input));
                } catch (InvalidCommandException ex) {
                    return "Please enter a valid command.";
                }
                break;
            case BUY_STOCK:
                currentStock = stockList.getStockByTickerName(input);
                if (currentStock == null) {
                    return "No stock could be found.";
                } else {
                    thisProtocol.setCurrentState(State.BUY_STOCK_AMOUNT);
                }
                break;
            case SELL_STOCK: 
                currentStock = stockList.getStockByTickerName(input);
                if (currentStock == null) {
                    return "No stock could be found.";
                } else {
                    thisProtocol.setCurrentState(State.SELL_STOCK_AMOUNT);
                }
                break;
            case BUY_STOCK_AMOUNT: 
                if (buyStock(Integer.parseInt(input))) {
                    thisProtocol.setCurrentState(State.SELECT_COMMAND);
                    return Integer.parseInt(input)+" "+currentStock.getTickerName()+" stocks purchased.";
                } else {
                    return "Insufficient funds to buy stocks.";
                }
            case SELL_STOCK_AMOUNT: 
                if (sellStock(Integer.parseInt(input))) {
                    thisProtocol.setCurrentState(State.SELECT_COMMAND);
                    return Integer.parseInt(input)+" "+currentStock.getTickerName()+" stocks sold.";
                } else {
                    return "You do not have enough stocks.";
                }
            case UPDATE_BALANCE:
                thisProtocol.setCurrentState(State.SELECT_COMMAND);
                return currentUser.getBalance()+"";
            case PRINT_STOCK:
                thisProtocol.setCurrentState(State.SELECT_COMMAND);
                return currentUser.getStocksOwned()+"";
            default:
                return "Error determining state.";
        }
        
        return null;

    }

}
