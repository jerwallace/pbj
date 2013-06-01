package stocktracker.client;

import java.io.Serializable;

/**
 * Class definition for the Singleton Admin type client session
 */
public class AdminSession extends Session implements Serializable
{

    private static AdminSession adminSession = null;

    protected AdminSession()
    {
    }

    /**
     * Default constructor for the singleton instance of the UserSession
     */
    public static AdminSession getInstance()
    {

        if (adminSession == null)
        {

            synchronized (UserSession.class)
            {

                AdminSession inst = adminSession;

                if (inst == null)
                {

                    synchronized (AdminSession.class)
                    {
                        adminSession = new AdminSession();
                    }
                }
            }
        }

        return adminSession;
    }
}
