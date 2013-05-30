/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.api.protocol;

import static stocktracker.api.protocol.AbstractProtocol.State;

/**
 *
 * @author WallaceJ
 */
public class UserProtocol extends AbstractProtocol
{

    private String menu = "1. Buy Stock    2. Sell Stock   3. Query Stock  4. Print Stock  5. Logout";

    public enum Stock_Action
    {

        BUY_STOCK, SELL_STOCK, QUERY_STOCK
    }
    private Stock_Action currentAction = Stock_Action.QUERY_STOCK;

    @Override
    public void toggleStateByCommand(int input) throws InvalidCommandException
    {
        switch (input)
        {
            case 1:
                currentAction = Stock_Action.BUY_STOCK;
                currentState = State.SELECT_STOCK;
                break;
            case 2:
                currentAction = Stock_Action.SELL_STOCK;
                currentState = State.SELECT_STOCK;
                break;
            case 3:
                currentAction = Stock_Action.QUERY_STOCK;
                currentState = State.SELECT_STOCK;
                break;
            case 4:
                currentState = State.PRINT_STOCK;
                break;
            case 5:
                currentState = State.LOGIN;
                break;
            default:
                throw new InvalidCommandException();
        }
    }

    @Override
    public String getInstruction()
    {
        switch (currentState)
        {
            case LOGIN:
                return "Login:";
            case SELECT_COMMAND:
                return menu;
            case SELECT_STOCK:
                return "Which stock would you like to trade? (or type \"cancel\" to go back)";
            case TRADE_STOCK_AMOUNT:
                return "How many stocks would you like to trade? (or type \"cancel\" to go back)";
            case UPDATE_BALANCE:
                return "Current balance:";
            case QUERY:
                return "Which stock would you like to query?(or type \"cancel\" to go back)";
            case PRINT_STOCK:
                return null;
            default:
                return "Error determining state.";
        }
    }

    public Stock_Action getTradeFlag()
    {
        return currentAction;
    }
}
