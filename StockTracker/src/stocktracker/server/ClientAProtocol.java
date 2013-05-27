package stocktracker.server;

/**
 *
 * @author Bahman
 */
public class ClientAProtocol
{

    private User myUser;
    private StockList myStockList;
    private UserList myUserList;
    private String currentStockName;
    private static final int LOGIN = 0;
    private static final int SELECTCOMMAND = 1;
    private static final int BUYSTOCK = 2;
    private static final int BUYSTOCKAMOUNT = 3;
    private static final int SELLSTOCK = 4;
    private static final int SELLSTOCKAMOUNT = 5;
    private static final int UPDATEBALANCE = 6;
    private static final int PRINTSTOCK = 7;
    private static final int LOGOUT = 8;
    private static final int NUMCOMMAND = 9;
    private int state = LOGIN;
    private int currentCommand = 0;
    private String[] menu =
    {
        "1. Buy Stock    2. Sell Stock   3. Print Stock   4. Logout",
        "Enter Stock Name: (or type \"back\")",
        "Enter Stock Amount: (or type \"back\")",
        "Here is a list of stocks you own:",
        "Exit."
    };

    public ClientAProtocol(StockList stocklist, UserList userlist, User myuser)
    {
        this.myUser = myuser;
        this.myStockList = stocklist;
        this.myUserList = userlist;
    }

    public String processInput(String theInput)
    {
        String theOutput = null;
        if (state == LOGIN)
        {
            theOutput = menu[0];
            state = SELECTCOMMAND;
        }
        else if (state == SELECTCOMMAND)
        {
            if (theInput.equalsIgnoreCase("Buy Stock"))
            {
                theOutput = menu[1];
                state = BUYSTOCK;
            }
            else if (theInput.equalsIgnoreCase("Sell Stock"))
            {
                theOutput = menu[1];
                state = SELLSTOCK;
            }
            else if (theInput.equalsIgnoreCase("Print Stock"))
            {
                theOutput = menu[2];
                state = PRINTSTOCK;
            }
            else if (theInput.equalsIgnoreCase("Exit"))
            {
                theOutput = "Exit";
                state = LOGIN;
            }
            else
            {
                theOutput = "You're supposed to select a COMMAND from the menu"
                        + "Try again";
            }
        }
        else if (state == BUYSTOCK)
        {
            if (theInput.equalsIgnoreCase("Back"))
            {
                theOutput = menu[0];
                state = SELECTCOMMAND;
            }
            else if (theInput.equals("\n"))
            {
                theOutput = menu[1];
                state = SELECTCOMMAND;
            }
            else
            {
                //CHECK IF STOCK NAME IS VALID
                //IF NOT, SEND ERROR MESSAGE STAY IN SAME STATE

                //CHECK THE MOST RECENT VALUE OF THE STOCK HERE
                //AND MOVE TO NEXT STATE

                //CHECK AND SEE IF STOCK EXISTS IN STOCK LIST
                //IF NOT ADD STOCK TO STOCK LIST

                currentStockName = theInput;
                theOutput = menu[2];
                state = BUYSTOCKAMOUNT;
            }
        }
        else if (state == BUYSTOCKAMOUNT)
        {
            theOutput = "Current Stock Value: " + myStockList.getStockPrice(theInput);
            state = UPDATEBALANCE;
        }
        else if (state == UPDATEBALANCE)
        {
            if (theInput.equalsIgnoreCase("Back"))
            {
                state = BUYSTOCK;
            }
            else
            {
                try
                {
                    int numStock = Integer.valueOf(theInput);
                    if (numStock <= 0)
                    {
                        theOutput = "Enter a positive value";
                    }
                    else
                    {
                        if (myUser.getBalance() >= numStock * myStockList.getStockPrice(theInput))
                        {
                            myUser.setBalance(myUser.getBalance() - myStockList.getStockPrice(theInput));
                            theOutput = menu[3] + myUser.getStockList();
                            state = UPDATEBALANCE;
                        }

                    }
                }
                catch (NumberFormatException nfe)
                {
                    theOutput = "Enter correct numerical format";
                }
            }
        }
        else if (state == LOGOUT)
        {
            state = LOGIN;
            theOutput = "Exit";
        }
        return theOutput;
    }
}
