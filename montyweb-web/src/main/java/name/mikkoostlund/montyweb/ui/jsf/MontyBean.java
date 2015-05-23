package name.mikkoostlund.montyweb.ui.jsf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import name.mikkoostlund.montyweb.domain.montyhallsimulation.MontyExperiment;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.MontyExperiment.ShowStatistics;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.ShowOutcome;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.SwitchingShowContestant;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.KeepingShowContestant;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.ShowContestant;

@Named
@RequestScoped
public class MontyBean {
    private int showCount;

    private List<ContestantResult> contestantResults = new ArrayList<ContestantResult>();

    public int getShowCount() {
		return showCount;
	}

	public List<ContestantResult> getContestantResults() {
		return contestantResults;
	}

	@PostConstruct
	public void runSimulation() {
        Map<String, ShowContestant> showContestants = new HashMap<String, ShowContestant>();
        showContestants.put("switch door strategy", new SwitchingShowContestant());
        showContestants.put("keep door strategy", new KeepingShowContestant());

        MontyExperiment montyExperiment = new MontyExperiment(1000, showContestants.values());
        ShowStatistics showStatistics = montyExperiment.run();
        presentStatistics(showStatistics, showContestants);
    }

    private void presentStatistics(ShowStatistics showStatistics, Map<String, ShowContestant> showContestants) {
        showCount = showStatistics.getShowCount(anyOf(showContestants.values()));

        showContestants.entrySet().stream().forEach(entry -> {
                        int nTimesWon = showStatistics.getOutcomeCount(ShowOutcome.WIN, entry.getValue());
                        contestantResults.add(new ContestantResult(entry.getKey(), nTimesWon));
                }
        );
    }

    private <T> T anyOf(Collection<T> collection) {
        return collection.iterator().next();
    }
}
