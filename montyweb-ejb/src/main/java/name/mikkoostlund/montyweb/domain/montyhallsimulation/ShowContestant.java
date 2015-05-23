package name.mikkoostlund.montyweb.domain.montyhallsimulation;

import java.util.List;

/**
 * A <code>ShowContestant</code> represents a contestant who takes part in Monty Hall show.
 * @author mikko
 *
 */
public interface ShowContestant {

	int makeInitialDoorChoice(List<Integer> doorIndexes);

	int makeSecondDoorChoice(int initialDoorChoice, List<Integer> doorIndexesAllowedToChangeTo);

}
