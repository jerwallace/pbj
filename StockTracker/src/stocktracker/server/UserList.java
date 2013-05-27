/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

import java.util.HashMap;
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
        this.userNameList = new HashMap<String, User>();
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

    public void addUser(User newUser)
    {
        userNameList.put(newUser.getUserName(), newUser);
    }

    public void removeUser(String userName)
    {
        userNameList.remove(userName);
    }

    public User getUser(String userName)
    {
        if (this.userNameList.containsKey(userName))
        {
            return this.userNameList.get(userName);
        }
        else
        {
            return null;
        }
    }
}
