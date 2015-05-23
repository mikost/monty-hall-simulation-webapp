package name.mikkoostlund.montyweb.service;

import java.util.List;

import name.mikkoostlund.montyweb.domain.Product;

public interface AddProductSvc {
    List<Product> add(Product product);

    List<Product> getAll();
}
