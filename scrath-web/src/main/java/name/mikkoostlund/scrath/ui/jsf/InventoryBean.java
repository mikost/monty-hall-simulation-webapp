package name.mikkoostlund.scrath.ui.jsf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import name.mikkoostlund.scrath.domain.InventoryLine;
import name.mikkoostlund.scrath.service.InventorySvc;

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
