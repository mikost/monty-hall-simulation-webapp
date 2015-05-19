package name.mikkoostlund.scrath.ui.jsf;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import name.mikkoostlund.scrath.domain.Product;
import name.mikkoostlund.scrath.service.AddProductSvc;

@RequestScoped
@Named
public class BackingBean {

    @Inject
    AddProductSvc addProductSvc;

    private String name;
    private List<Product> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<Product> getProducts() {
    	if (products == null) {
    		products = addProductSvc.getAll();
    	}
        return products;
    }

    public Object addNewProduct() {
        List<Product> allProducts = addProductSvc.add(new Product(name));
        this.products = allProducts;
        this.name = "";
        return null;
    }
}
