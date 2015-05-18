package name.mikkoostlund.scrath;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import name.mikkoostlund.scrath.domain.Myentity;

@Stateless
@LocalBean
public class SlsBean implements SlsbLocal, SlsbRemote {

    @PersistenceContext
    EntityManager em;

    @Override
    public String sayHiTo(String name) {
        Myentity entity = new Myentity(name);
        em.persist(entity);
        return "Hi "+ name;
    }
}
