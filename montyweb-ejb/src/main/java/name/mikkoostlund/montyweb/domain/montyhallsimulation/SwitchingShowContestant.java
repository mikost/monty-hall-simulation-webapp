package name.mikkoostlund.montyweb.domain.montyhallsimulation;

import java.util.List;
import java.util.Random;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("Switching")
public class SwitchingShowContestant extends ShowContestant {

    @Transient
    Random random = new Random();

    @Override
    public int makeInitialDoorChoice(List<Integer> doorIndexes) {
        return randomPickFrom(doorIndexes);
    }

    private Integer randomPickFrom(List<Integer> list) {
        return list.get(random.nextInt(list.size()));
    }

    @Override
    public int makeSecondDoorChoice(int initialDoorChoice, List<Integer> doorIndexesAllowedToChangeTo) {
        return randomPickFrom(doorIndexesAllowedToChangeTo);
    }

    @Override
    public String getDescription() {
        return "Contestant that always changes to another door";
    }
}
