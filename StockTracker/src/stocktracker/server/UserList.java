/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Bahman
 */
public class UserList
{

    private Map<String, User> userNameList;

    public UserList()
    {
    }

    public boolean isUserVailable(String userName)
    {
        if (this.userNameList.containsKey(userName))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void addUser(String userName)
    {
        User newUser = new User(userName, 1000);
        userNameList.put(userName, newUser);
    }

    public void removeUser(String userName)
    {
        userNameList.remove(userName);
    }
}
