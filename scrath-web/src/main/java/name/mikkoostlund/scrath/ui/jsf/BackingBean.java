package name.mikkoostlund.scrath.ui.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import name.mikkoostlund.scrath.domain.Product;
import name.mikkoostlund.scrath.service.AddProductSvc;

public class BackingBean {

    @Inject
    AddProductSvc addProductSvc;
    private String helloPhrase;
    private String name;
    private List<Product> products = new ArrayList<Product>();

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
    
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Object takeAction() {
        List<Product> allProducts = addProductSvc.add(new Product(name));
        setProducts(allProducts);
        setName("");
        return null;
    }
}
