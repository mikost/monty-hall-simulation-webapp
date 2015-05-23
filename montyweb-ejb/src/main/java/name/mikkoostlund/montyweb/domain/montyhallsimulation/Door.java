package name.mikkoostlund.montyweb.domain.montyhallsimulation;

public interface Door {

	/**
	 * @return the index of the {@code IDoor} within its {@code Doors} container.
	 */
	int index();

	/**
	 * @return <code>true</code> if, and only if, this <code>Door</code> "has a car behind it".
	 */
	boolean hasCar();

}