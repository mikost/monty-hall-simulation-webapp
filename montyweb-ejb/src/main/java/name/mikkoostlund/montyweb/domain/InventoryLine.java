package name.mikkoostlund.montyweb.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class InventoryLine {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private Long id;

    @OneToOne
    private Product product;

    private Long productCount;

    public InventoryLine() {
    }

    public InventoryLine(Product product, long productCount) {
        this.product = product;
        this.setProductCount(productCount);
    }

    public Product getProduct() {
        return product;
    }

    public Long getProductCount() {
        return productCount;
    }

    public void setProductCount(Long productCount) {
        this.productCount = productCount;
    }
}
