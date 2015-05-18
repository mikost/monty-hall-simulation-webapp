package name.mikkoostlund.scrath;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import name.mikkoostlund.scrath.domain.Product;

import org.hamcrest.CoreMatchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AddProductTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                         .addClass(AddProductSvcBean.class)
                         .addClass(AddProductSvcRemote.class)
                         .addClass(AddProductSvcLocal.class)
                         .addClass(AddProductSvc.class)
                         .addClass(Product.class)
                         .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                         .addAsResource("persistence.xml", "META-INF/persistence.xml");
    }

    @PersistenceContext
    EntityManager em;

    @Inject AddProductSvc addProductSvc;
	
    @Test
    public final void testThatSavingANewProductPersistsItInTheDatabase() {
    	Query query = em.createQuery("FROM Product");
    	List<Product> resultList = (List<Product>)query.getResultList();
    	assertFalse(resultList.stream().anyMatch(p -> p.getName().equals("Book")));
    	Product product = new Product("Book");
        addProductSvc.add(product);
        resultList = (List<Product>)query.getResultList();
    	assertTrue(resultList.stream().anyMatch(p -> p.getName().equals("Book")));	 
    }
}
