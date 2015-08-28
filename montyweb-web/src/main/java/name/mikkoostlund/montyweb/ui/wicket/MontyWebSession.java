package name.mikkoostlund.montyweb.ui.wicket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;

public class MontyWebSession extends AuthenticatedWebSession {
	public MontyWebSession(Request request) {
		super(request);
	}

	@Override
	public boolean authenticate(final String username, final String password) {
		try {
			((HttpServletRequest) RequestCycle.get().getRequest()
					.getContainerRequest()).login(username, password);
			return true;
		} catch (ServletException e) {
			return false;
		}
	}

	@Override
	public Roles getRoles() {
		HttpServletRequest httpServletRequest = (HttpServletRequest) RequestCycle
				.get().getRequest().getContainerRequest();
		if (httpServletRequest.isUserInRole("uzers")) {
			return new Roles("uzers");
		}
		return new Roles();
	}
}
