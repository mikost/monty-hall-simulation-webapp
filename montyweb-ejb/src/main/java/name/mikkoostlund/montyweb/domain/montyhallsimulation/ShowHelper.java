package name.mikkoostlund.montyweb.domain.montyhallsimulation;

/**
 * A ShowHelper is responsible for setting up a set of doors for a Monty Hall show.
 * It has a single method, {@link #setupDoors}.
 * @author mikko
 *
 */
public interface ShowHelper {

    /**
     * @return an instance of type {@link Doors}</a>
     */
    Doors setupDoors();

}
