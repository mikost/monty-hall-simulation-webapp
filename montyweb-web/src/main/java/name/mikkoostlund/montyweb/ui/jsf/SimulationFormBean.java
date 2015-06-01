package name.mikkoostlund.montyweb.ui.jsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import name.mikkoostlund.montyweb.domain.montyhallsimulation.ShowContestant;
import name.mikkoostlund.montyweb.service.MontyService;

@Named
@RequestScoped
public class SimulationFormBean {

    @Inject
    MontyService montyService;

    private List<String> options;

    private Map<String, ShowContestant> allShowContestantTypes;

    @PostConstruct
    public void populateOptions() {
        allShowContestantTypes = new HashMap<String, ShowContestant>();
        options = new ArrayList<String>();
        for (ShowContestant sc : montyService.getContestantTypes()) {
            allShowContestantTypes.put(sc.getDescription(), sc);
            options.add(sc.getDescription());
        }
    }

    public List<String> getOptions() {
        return options;
    }

    private int numberOfRuns = 1000;
    private int numberOfDoors = 3;
    private int numberOfCars = 1;
    private List<String> selections;

    public int getNumberOfRuns() {
        return numberOfRuns;
    }

    public void setNumberOfRuns(int numberOfRuns) {
        this.numberOfRuns = numberOfRuns;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    public void setNumberOfCars(int numberOfCars) {
        this.numberOfCars = numberOfCars;
    }
        
    public List<String> getSelections() {
        return selections;
    }

    public void setSelections(List<String> selections) {
        this.selections = selections;
    }

    public void runSimulation() {
        Set<ShowContestant> showContestantTypes = new HashSet<>();
        for (String selection : selections) {
            showContestantTypes.add(allShowContestantTypes.get(selection));
        }
        montyService.runSimulation(numberOfRuns, numberOfDoors, numberOfCars, showContestantTypes);
    }
}
