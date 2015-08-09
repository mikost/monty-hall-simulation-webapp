package name.mikkoostlund.montyweb.ui.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;

public class SignInWidget extends Panel {

    private static final Component SIGN_OUT_LINK = new SignOutLink("content");
    private static final Component SIGN_IN_LINK = new SignInLink("content");

    public SignInWidget(String id) {
        super(id);
    }

    @Override
    protected void onConfigure() {
        if (MontyWebSession.get().isSignedIn()) {
            addOrReplace(SIGN_OUT_LINK);
        } else {
            addOrReplace(SIGN_IN_LINK);
        }
    }
}
