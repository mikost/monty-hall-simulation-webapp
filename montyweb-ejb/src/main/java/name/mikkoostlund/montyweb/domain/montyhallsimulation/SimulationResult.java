package name.mikkoostlund.montyweb.domain.montyhallsimulation;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class SimulationResult {
        
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private long id;

    private long numberOfRuns;
    private long numberOfDoors;
    private long numberOfCars;

    @OneToMany
    private Set<ContestantResult> contestantResults;

    protected SimulationResult() {
    }

    public SimulationResult(long numberOfRuns, long numberOfDoors, long numberOfCars, Set<ContestantResult> contestantResults) {
        this.numberOfRuns = numberOfRuns;
        this.numberOfDoors = numberOfDoors;
        this.numberOfCars = numberOfCars;
        this.contestantResults = contestantResults;
    }

    public long getNumberOfRuns() {
        return numberOfRuns;
    }

    public long getNumberOfDoors() {
        return numberOfDoors;
    }

    public long getNumberOfCars() {
        return numberOfCars;
    }

    public Set<ContestantResult> getContestantResults() {
        return contestantResults;
    }
}
