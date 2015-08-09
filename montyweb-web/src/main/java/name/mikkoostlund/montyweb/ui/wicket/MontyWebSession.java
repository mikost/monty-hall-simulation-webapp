package name.mikkoostlund.montyweb.ui.wicket;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
 
public class MontyWebSession extends AuthenticatedWebSession
{
    public MontyWebSession(Request request)
    {
        super(request);
    }

    @Override
    public boolean authenticate(final String username, final String password) {
        final String WICKET = "monty";
        return WICKET.equals(username) && WICKET.equals(password);
    }

    @Override
    public Roles getRoles()
    {
        if (isSignedIn())
        {
             
         // assign the role ( modify the code to add dynamic roles from database)
            return new Roles(Roles.USER);
        }
        return null;
    }
}
