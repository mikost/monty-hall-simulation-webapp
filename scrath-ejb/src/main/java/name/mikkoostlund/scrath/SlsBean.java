package name.mikkoostlund.scrath;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class SlsBean implements SlsbLocal, SlsbRemote {

	@Override
	public String sayHiTo(String name) {
		return "Hi "+ name;
	}
}
