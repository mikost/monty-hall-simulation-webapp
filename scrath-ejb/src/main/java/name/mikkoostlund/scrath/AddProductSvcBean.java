package name.mikkoostlund.scrath;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import name.mikkoostlund.scrath.domain.Product;

@Stateless
@LocalBean
public class AddProductSvcBean implements AddProductSvcLocal, AddProductSvcRemote {

    @PersistenceContext
    EntityManager em;

    @Override
        public void add(Product product) {
        em.persist(product);
    }
}
