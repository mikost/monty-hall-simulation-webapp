package name.mikkoostlund.montyweb.domain.montyhallsimulation;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class SimulationResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long time;

	@ManyToOne
	private User user;

	private long numberOfRuns;
	private long numberOfDoors;

	@OneToMany
	private Set<ContestantResult> contestantResults;

	protected SimulationResult() {
	}

	public SimulationResult(User user, long numberOfRuns, long numberOfDoors,
			Set<ContestantResult> contestantResults) {
		this.time = new Date().getTime();
		this.user = user;
		this.numberOfRuns = numberOfRuns;
		this.numberOfDoors = numberOfDoors;
		this.contestantResults = contestantResults;
	}

	public long getTime() {
		return time;
	}

	public long getNumberOfRuns() {
		return numberOfRuns;
	}

	public long getNumberOfDoors() {
		return numberOfDoors;
	}

	public Set<ContestantResult> getContestantResults() {
		return contestantResults;
	}
}
