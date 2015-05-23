package name.mikkoostlund.montyweb.ui.jsf;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import name.mikkoostlund.montyweb.domain.InventoryLine;
import name.mikkoostlund.montyweb.service.InventorySvc;

import javax.enterprise.context.RequestScoped;

@RequestScoped
@Named
public class InventoryBean {

    @Inject
    InventorySvc inventorySvc;

    private List<InventoryLine> inventoryLines;

    public List<InventoryLine> getInventoryLines() {
    	if (inventoryLines == null) {
    		inventoryLines = inventorySvc.getInventoryLines();
    	}
    	return inventoryLines; 
    }
}
