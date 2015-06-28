package name.mikkoostlund.montyweb.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import name.mikkoostlund.montyweb.domain.montyhallsimulation.ContestantResult;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.DefaultShowImpl;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.MontyExperiment;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.MontyExperiment.ShowStatistics;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.ShowContestant;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.ShowOutcome;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.SimulationResult;

/**
 * Session Bean implementation class MontyService
 */
@Stateless
@LocalBean
public class MontyService {

    @PersistenceContext
    EntityManager em;

    public List<ShowContestant> getContestantTypes() {
        return em.createQuery("FROM ShowContestant", ShowContestant.class).getResultList();
    }

    public SimulationResult runSimulation(int numberOfRuns, int numberOfDoors, int numberOfCars, Set<ShowContestant> showContestants) {
        MontyExperiment montyExperiment = new MontyExperiment(new DefaultShowImpl(numberOfDoors), numberOfRuns, showContestants);
        ShowStatistics showStatistics = montyExperiment.run();
        Set<ContestantResult> contestantResults = new HashSet<>();
        for (ShowContestant showContestant : showContestants) {
            ContestantResult contestantResult = new ContestantResult(showContestant, showStatistics.getOutcomeCount(ShowOutcome.WIN, showContestant));
            em.persist(contestantResult);
            contestantResults.add(contestantResult);
        }
        SimulationResult simulationResult = new SimulationResult(numberOfRuns, numberOfDoors, numberOfCars, contestantResults);
        em.persist(simulationResult);
        return simulationResult;
    }
}
