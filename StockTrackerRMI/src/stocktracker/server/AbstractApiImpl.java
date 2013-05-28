/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

import java.rmi.*;
import java.rmi.server.*;
import stocktracker.api.*;
import stocktracker.api.protocol.AbstractProtocol;

/**
 *
 * @author WallaceJ
 */
public abstract class AbstractApiImpl extends UnicastRemoteObject implements AbstractApi
{

    protected AbstractProtocol thisProtocol;
    protected User currentUser = null;
    protected Stock currentStock = null;
    protected UserList userList = new UserList();
    protected StockList stockList = new StockList();

    public AbstractApiImpl() throws RemoteException
    {
        super();
    }

    @Override
    public abstract String processInput(String input) throws RemoteException;

    @Override
    public String getNextInstruction() throws RemoteException
    {
        return thisProtocol.getInstruction();
    }

    @Override
    public boolean userExists(String username) throws RemoteException
    {

        currentUser = userList.getUser(username);

        if (currentUser == null)
        {
            currentUser = userList.addUser(username);
            return false;
        }
        else
        {
            return true;
        }

    }
}
