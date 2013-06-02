/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client.protocol;

import java.rmi.RemoteException;

/**
 *
 * @author WallaceJ
 */
public class CustomException extends RemoteException {
    
    public enum ErrorType
    {
        NO_STOCK_FOUND, NEGATIVE_VOLUME, INSUFFICIENT_FUNDS, UNAVAILABLE_STOCK_VOLUME, 
        STOCKS_NOT_AVAILABLE, BAD_PRICE_VALUE, INVALID_COMMAND
    }
    
    public CustomException (ErrorType type) {
        super(getMessage(type));
    }
    
    public static String getMessage(ErrorType type) {
        
        switch (type) {
            case NO_STOCK_FOUND: 
                return "Not a valid tickername.";
            case NEGATIVE_VOLUME:
                return "Not enough stocks available.";
            case INSUFFICIENT_FUNDS:
                return "Insufficient funds.";
            case UNAVAILABLE_STOCK_VOLUME:
                return "Not enough stock volume is available for this trade.";
            case STOCKS_NOT_AVAILABLE:
                return "You have specified more stocks than you own.";
            case BAD_PRICE_VALUE:
                return "New price must be greater than zero.";
            case INVALID_COMMAND:
                return "Please enter a valid command.";
            default:
                return "Error.";
        }
        
    }
    
}
