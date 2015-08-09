package name.mikkoostlund.montyweb.ui.wicket;

import org.apache.wicket.authroles.authentication.pages.SignInPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

public class SignInLink extends Panel {

    public SignInLink(String id) {
        super(id);
        add(new BookmarkablePageLink<Void>("loginlink", SignInPage.class));
    }
}
