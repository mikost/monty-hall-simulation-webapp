package name.mikkoostlund.scrath.ui.jsf;

import javax.inject.Inject;

import name.mikkoostlund.scrath.domain.Product;
import name.mikkoostlund.scrath.service.AddProductSvc;

public class BackingBean {

	@Inject
	AddProductSvc addProductSvc;
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
	
	public Object takeAction() {
		addProductSvc.add(new Product(name));
		return null;
	}
}
