package name.mikkoostlund.montyweb.ui.wicket;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wicket.Page;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.protocol.http.WebApplication;
 
public class MontyWicketApplication extends WebApplication {

    @Override  
    public void init() {
        super.init();  
        BeanManager manager = null;
        try {
            manager = (BeanManager) new InitialContext().lookup("java:comp/BeanManager");  
        } catch (NamingException e) {  
        }  
        new CdiConfiguration(manager).configure(this);

        this.mountPage("introduction", MontyMain.class);
        this.mountPage("simulation", MontySim.class);
    }  

    @Override
    public Class<? extends Page> getHomePage() {
        return MontyMain.class;
    }
}

