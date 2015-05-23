package name.mikkoostlund.montyweb.domain.montyhallsimulation;

import java.util.List;
import java.util.Random;

public class KeepingShowContestant implements ShowContestant {

	Random random = new Random();

	@Override
	public int makeInitialDoorChoice(List<Integer> doorIndexes) {
		return randomPickFrom(doorIndexes);
	}

	@Override
	public int makeSecondDoorChoice(int initialDoorChoice, List<Integer> doorIndexesAllowedToChangeTo) {
		return initialDoorChoice;
	}
	
	private Integer randomPickFrom(List<Integer> list) {
		return list.get(random.nextInt(list.size()));
	}
}
