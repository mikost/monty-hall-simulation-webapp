package name.mikkoostlund.scrath.ui.jsf;

import javax.inject.Inject;

import name.mikkoostlund.scrath.Slsb;

public class BackingBean {

	@Inject
	Slsb slsb;
	private String helloPhrase;
	private String name;

	public String getHelloPhrase() {
		return helloPhrase;
	}

	public void setHelloPhrase(String helloPhrase) {
		this.helloPhrase = helloPhrase;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BackingBean() {
		// TODO Auto-generated constructor stub
	}

	
	public Object takeAction() {
		setHelloPhrase(slsb.sayHiTo(name));
		return null;
	}
}
