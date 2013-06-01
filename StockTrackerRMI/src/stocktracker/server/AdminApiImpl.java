/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

/**
 *
 * @author WallaceJ
 */
import stocktracker.client.protocol.AdminProtocol;
import java.rmi.*;
import stocktracker.api.*;
import stocktracker.client.protocol.InvalidCommandException;
import stocktracker.client.protocol.AbstractProtocol;
import static stocktracker.client.protocol.AbstractProtocol.State.SELECT_COMMAND;

public class AdminApiImpl extends AbstractApiImpl implements AbstractApi
{

    private static final long serialVersionUID = 1L;
    private int counter = 0;

    public AdminApiImpl() throws RemoteException
    {
        super();
        UserList.getInstance().addUser("bahman");
        UserList.getInstance().addUser("jeremy");
        UserList.getInstance().addUser("peter");
    }
    
}
