package name.mikkoostlund.scrath.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import name.mikkoostlund.scrath.domain.InventoryLine;
import name.mikkoostlund.scrath.domain.Product;

@Stateless
@LocalBean
public class AddProductSvcBean implements AddProductSvcLocal, AddProductSvcRemote {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Product> add(Product product) {
        em.persist(product);
        em.persist(new InventoryLine(product, 1));
        return getAll();
    }

    @Override
    public List<Product> getAll() {
        return em.createQuery("FROM Product", Product.class).getResultList();
    }
}
