package name.mikkoostlund.scrath.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import name.mikkoostlund.scrath.domain.InventoryLine;

@Stateless
public class InventorySvc {

    @PersistenceContext
    EntityManager em;

    public List<InventoryLine> getInventoryLines() {
        TypedQuery<InventoryLine> query = em.createQuery("FROM InventoryLine", InventoryLine.class);
        return query.getResultList();
    }
}
