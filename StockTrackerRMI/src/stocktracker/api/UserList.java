/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.api;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bahman
 */
public class UserList
{

    private Map<String, User> userList;

    public UserList()
    {
        this.userList = new HashMap<>();
    }

    public User getUser(String username)
    {
        return userList.get(username);
    }

    public User addUser(String userName)
    {
        User newUser = new User(userName, 1000);
        userList.put(userName, newUser);
        return newUser;
    }

    public void removeUser(String userName)
    {
        userList.remove(userName);
    }
    
}
