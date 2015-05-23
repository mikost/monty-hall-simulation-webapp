package name.mikkoostlund.montyweb.domain.montyhallsimulation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MontyExperiment {

	public interface ShowStatistics {

		int getOutcomeCount(ShowOutcome showOutcome, ShowContestant showContestant);

		int getShowCount(ShowContestant showContestant);

	}

	private final Show show;
	private final int times;
	private final Collection<? extends ShowContestant> showContestants;

	public MontyExperiment(Show show, int times, Collection<? extends ShowContestant> showContestants) {
		this.show = show;
		this.times = times;
		this.showContestants = showContestants;
	}

	public MontyExperiment(int timesToRunShow, Collection<? extends ShowContestant> showContestants) {
		this(new DefaultShowImpl(), timesToRunShow, showContestants);
	}

	public ShowStatistics run() {
		Registrator registrator = new Registrator();

		for (ShowContestant showContestant : showContestants) {	
			for (int time = 0; time < times; time++) {
				ShowOutcome showOutcome = show.runWith(showContestant);
				registrator.registerOutcome(showContestant, showOutcome);
			}
		}
		
		return registrator.getShowStatistics();
	}

	private static class Registrator {
		private final Map<ShowContestant, ContestantStatistics> statistics = new HashMap<>();

		public void registerOutcome(ShowContestant showContestant, ShowOutcome showOutcome) {
			ContestantStatistics contestantStatistics = statistics.get(showContestant);
			if (contestantStatistics == null) {
				contestantStatistics = new ContestantStatistics();
				statistics.put(showContestant, contestantStatistics);
			}
			contestantStatistics.registerOutcome(showOutcome);
		}

		public ShowStatistics getShowStatistics() {
			return new ShowStatistics() {

				@Override
				public int getOutcomeCount(ShowOutcome showOutcome, ShowContestant showContestant) {
					ContestantStatistics contestantStatistics = statistics.get(showContestant);
					if (contestantStatistics == null) {
						return 0;
					}
					return contestantStatistics.getOutcomeCount(showOutcome);
				}

				@Override
				public int getShowCount(ShowContestant showContestant) {
					ContestantStatistics contestantStatistics = statistics.get(showContestant);
					if (contestantStatistics == null) {
						return 0;
					}
					return contestantStatistics.getShowCount();
				}
				
			};
		}

		private static class ContestantStatistics {

			private final HashMap<ShowOutcome, Integer> outcomes = new HashMap<>();

			public int getShowCount() {
				int showCount = 0;
				for (ShowOutcome showOutcome : outcomes.keySet()) {
					showCount += outcomes.get(showOutcome).intValue();
				}
				return showCount;
			}

			public int getOutcomeCount(ShowOutcome showOutcome) {
				if (showOutcome == null) throw new NullPointerException();

				Integer count = outcomes.get(showOutcome);
				return count == null ? 0 : count.intValue();
			}

			public void registerOutcome(ShowOutcome showOutcome) {
				if (showOutcome == null) throw new NullPointerException();

				Integer count = outcomes.get(showOutcome);
				if (count == null) {
					outcomes.put(showOutcome, 1);
				} else {
					outcomes.put(showOutcome, count + 1);
				}
			}

		}
	}
}
