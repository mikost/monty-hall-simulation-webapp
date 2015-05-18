package name.mikkoostlund.scrath.service;

import java.util.List;

import name.mikkoostlund.scrath.domain.Product;

public interface AddProductSvc {
    List<Product> add(Product product);
}
