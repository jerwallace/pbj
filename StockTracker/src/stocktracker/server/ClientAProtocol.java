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
    private static final int TRADESTOCK = 2;
    private static final int GIVESTOCKPRICE = 3;
    private static final int TRADESTOCKAMOUNT = 4;
    private static final int PRINTSTOCK = 5;
    private static final int LOGOUT = 6;
    private static boolean TRADECALL = true;
    private int state = LOGIN;
    private String[] menu =
    {
        "1. Buy Stock    2. Sell Stock   3. Print Stock   4. Log Out",
        "Enter Stock Name: (or type \"back\")",
        "Enter Stock Amount: (or type \"back\")",
        "Here is a list of stocks you own (tickerName, numStock): ",
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
                //TRADECALL=true means we are buying stock
                TRADECALL = true;
                state = TRADESTOCK;
            }
            else if (theInput.equalsIgnoreCase("Sell Stock"))
            {
                theOutput = menu[1];
                //TRADECALL=false means we are selling stock
                TRADECALL = false;
                state = TRADESTOCK;
            }
            else if (theInput.equalsIgnoreCase("Print Stock"))
            {
                theOutput = menu[4];
                state = PRINTSTOCK;
            }
            else if (theInput.equalsIgnoreCase("log out"))
            {
                theOutput = "Exit";
                state = LOGIN;
            }
            else if (theInput.equalsIgnoreCase("\n"))
            {
                theOutput = menu[0];
                state = SELECTCOMMAND;
            }
            else
            {
                theOutput = "You're supposed to select a COMMAND from the menu"
                        + "Try again";
            }
        }
        else if (state == TRADESTOCK)
        {
            if (theInput.equalsIgnoreCase("Back"))
            {
                theOutput = menu[0];
                state = SELECTCOMMAND;
            }
            else if (theInput.equals("\n"))
            {
                theOutput = menu[1];
                state = TRADESTOCK;
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
                theOutput = "Current Stock Value: " + myStockList.getStockPrice(theInput) + " proceed (yes/no)?";
                state = GIVESTOCKPRICE;
            }
        }
        else if (state == GIVESTOCKPRICE)
        {
            if (theInput.equalsIgnoreCase("yes"))
            {
                theOutput = menu[2];
                state = TRADESTOCKAMOUNT;
            }
            else if (theInput.equalsIgnoreCase("no"))
            {
                theOutput = menu[0];
                state = SELECTCOMMAND;
            }
            else
            {
                theOutput = "Say YES to proceed or NO to return to main menu.";
                state = GIVESTOCKPRICE;
            }
        }
        else if (state == TRADESTOCKAMOUNT)
        {
            if (theInput.equalsIgnoreCase("Back"))
            {
                theOutput = menu[1];
                state = TRADESTOCK;
            }
            else
            {
                try
                {
                    int numStock = Integer.valueOf(theInput);
                    if (numStock <= 0)
                    {
                        theOutput = "Enter a positive value.";
                    }
                    else
                    {
                        //BUYING STOCK - UPDATE BALANCE
                        if (TRADECALL == true)
                        {
                            if (myUser.getBalance() >= numStock * myStockList.getStockPrice(currentStockName))
                            {
                                myUser.setBalance(myUser.getBalance() - myStockList.getStockPrice(currentStockName));
                                myUser.addStock(myStockList.getStockObject(currentStockName), numStock);
                                theOutput = menu[3] + myUser.getStockList();
                                state = PRINTSTOCK;
                            }
                            else
                            {
                                theOutput = "Your balance is not enough for this trade. Re-enter value or type \"back\"";
                                state = TRADESTOCKAMOUNT;
                            }
                        }
                        //SELLING STOCK - UPDATE BALANCE
                        else if (TRADECALL == false)
                        {
                            myUser.setBalance(myUser.getBalance() + myStockList.getStockPrice(currentStockName));
                            myUser.removeStock(myStockList.getStockObject(currentStockName), numStock);
                            theOutput = menu[3] + myUser.getStockList();
                            state = PRINTSTOCK;
                        }

                    }

                }
                catch (NumberFormatException nfe)
                {
                    theOutput = "Enter correct numerical format.";
                    state = TRADESTOCKAMOUNT;
                }
            }
        }
        else if (state == PRINTSTOCK)
        {
            theOutput = menu[0];
            state = SELECTCOMMAND;
        }
        else if (state == LOGOUT)
        {
            state = LOGIN;
            theOutput = "Exit";
        }

        return theOutput;
    }
}
