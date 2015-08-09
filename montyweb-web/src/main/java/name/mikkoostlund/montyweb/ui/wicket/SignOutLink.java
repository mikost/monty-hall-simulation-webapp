package name.mikkoostlund.montyweb.ui.wicket;

import org.apache.wicket.authroles.authentication.pages.SignOutPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

public class SignOutLink extends Panel {

    public SignOutLink(String id) {
        super(id);
        add(new BookmarkablePageLink<Void>("logoutlink", SignOutPage.class));
    }
}
