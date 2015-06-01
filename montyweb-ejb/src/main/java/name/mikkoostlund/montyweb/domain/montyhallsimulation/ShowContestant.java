package name.mikkoostlund.montyweb.domain.montyhallsimulation;

import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import org.hibernate.annotations.ForceDiscriminator;

/**
 * A <code>ShowContestant</code> represents a contestant who takes part in Monty Hall show.
 * @author mikko
 *
 */
@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name="CONTESTANT_TYPE", discriminatorType = STRING)
@ForceDiscriminator
public abstract class ShowContestant {

    @Id
    private long id;

    public abstract int makeInitialDoorChoice(List<Integer> doorIndexes);

    public abstract int makeSecondDoorChoice(int initialDoorChoice, List<Integer> doorIndexesAllowedToChangeTo);

    public abstract String getDescription();

}
