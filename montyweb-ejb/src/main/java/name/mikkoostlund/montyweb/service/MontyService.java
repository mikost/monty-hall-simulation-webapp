package name.mikkoostlund.montyweb.service;

import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import name.mikkoostlund.montyweb.domain.montyhallsimulation.ContestantResult;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.DefaultShowHelper;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.DefaultShowImpl;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.KeepingShowContestant;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.MontyExperiment;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.MontyExperiment.ShowStatistics;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.ShowContestant;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.ShowOutcome;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.SimulationResult;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.SwitchingShowContestant;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.User;

import org.slf4j.Logger;

/**
 * Session Bean implementation class MontyService
 */

@Stateless
@LocalBean
public class MontyService {

	@PersistenceContext
	EntityManager em;

	@Resource
	SessionContext sc;

	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(MontyService.class);

	public List<ShowContestant> getContestantTypes() {
		return em.createQuery("FROM ShowContestant", ShowContestant.class)
				.getResultList();
	}

	public SimulationResult runSimulation(int numberOfRuns, int numberOfDoors,
			Set<ShowContestant> showContestants) {
		log.debug("runSimulation({},{},{})", numberOfRuns, numberOfDoors,
				showContestants);
		boolean storeResult = sc.isCallerInRole("uzers");
		User user = null;
		if (storeResult) {
			user = em
					.createQuery("FROM User u WHERE u.name LIKE :userName",
							User.class)
					.setParameter("userName", sc.getCallerPrincipal().getName())
					.getSingleResult();
		}

		MontyExperiment montyExperiment = new MontyExperiment(
				new DefaultShowImpl(new DefaultShowHelper(numberOfDoors)),
				numberOfRuns, showContestants);
		ShowStatistics showStatistics = montyExperiment.run();
		Set<ContestantResult> contestantResults = new HashSet<>();
		for (ShowContestant showContestant : showContestants) {
			ContestantResult contestantResult = new ContestantResult(
					showContestant, showStatistics.getOutcomeCount(
							ShowOutcome.WIN, showContestant));
			if (storeResult) {
				em.persist(contestantResult);
			}
			contestantResults.add(contestantResult);
		}
		SimulationResult simulationResult = new SimulationResult(user,
				numberOfRuns, numberOfDoors, contestantResults);
		if (storeResult) {
			em.persist(simulationResult);
		}
		return simulationResult;
	}

	@RolesAllowed("uzers")
	public List<TableSimulationResult> getSimulationResults() {
		User user = em
				.createQuery("FROM User u WHERE u.name LIKE :userName",
						User.class)
				.setParameter("userName", sc.getCallerPrincipal().getName())
				.getSingleResult();
		List<SimulationResult> resultList = em
				.createQuery(
						"FROM SimulationResult sr WHERE sr.user.name LIKE :userName",
						SimulationResult.class)
				.setParameter("userName", user.getName()).getResultList();
		List<TableSimulationResult> tableResultList = resultList
				.stream()
				.map(simulationResult -> {
					TableSimulationResult tsr = new TableSimulationResult();
					tsr.setTime(simulationResult.getTime());
					tsr.setNumberOfRuns(simulationResult.getNumberOfRuns());
					tsr.setNumberOfDoors(simulationResult.getNumberOfDoors());
					tsr.setKeepingWins(getWins(simulationResult,
							KeepingShowContestant.class));
					tsr.setSwitchingWins(getWins(simulationResult,
							SwitchingShowContestant.class));
					return tsr;
				}).collect(toList());

		return tableResultList;
	}

	private int getWins(SimulationResult simulationResult,
			Class<? extends ShowContestant> clazz) {
		for (ContestantResult cr : simulationResult.getContestantResults()) {
			if (cr.getShowContestant().getClass() == clazz) {
				return cr.getTimesWon();
			}
		}
		return 0;
	}
}
