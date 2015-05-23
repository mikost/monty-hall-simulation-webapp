package name.mikkoostlund.montyweb.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import name.mikkoostlund.montyweb.domain.InventoryLine;

@Stateless
public class InventorySvc {

    @PersistenceContext
    EntityManager em;

    public List<InventoryLine> getInventoryLines() {
        TypedQuery<InventoryLine> query = em.createQuery("FROM InventoryLine", InventoryLine.class);
        return query.getResultList();
    }
}
