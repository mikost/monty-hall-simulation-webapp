package name.mikkoostlund.montyweb.domain.montyhallsimulation;


/**
 * A <code>DoorMatcher</code> instance either matches or doesn't match a <code>Door</code>
 * passed to its {@link #matches(Door) matches} method. Instances of <code>DoorMatcher</code>
 * are used to retrieve a <code>Door</code> from a <code>Doors</code> instance,
 * by means of the {@link Doors#getDoor(DoorMatcher)} method.
 * @author mikko
 *
 */
abstract class DoorMatcher {
	/**
	 * @return a DoorMatcher that matches any <code>Door</code>.
	 */
	static DoorMatcher anyDoor() {
		return new DoorMatcher() {
			@Override
			boolean matches(Door door) {
				return true;
			}
		};
	}

	/**
	 * @return a DoorMatcher that matches a <code>Door</code> that has a goat behind it.
	 */
	static DoorMatcher doorWithGoat() {
		return new DoorMatcher() {
			@Override
			public boolean matches(Door door) {
				return !door.hasCar();
			}
		};
	}

	/**
	 * @return a DoorMatcher that matches a <code>Door</code> that has a car behind it.
	 */
	static DoorMatcher doorWithCar() {
		return new DoorMatcher() {
			@Override
			public boolean matches(Door door) {
				return door.hasCar();
			}
		};
	}

	/**
	 * @return a <code>DoorMatcher</code> that has the same matching criterion as this 
	 * <code>DoorMatcher</code> <i>except</i> that it does <i>not</i> match the instance 
	 * referred to by parameter <code>unwantedDoor</code>.
	 * 
	 */
	DoorMatcher not(final Door unwantedDoor) {
		final DoorMatcher prev = this;
		return new DoorMatcher() {

			@Override
			boolean matches(Door door) {
				return door != unwantedDoor && prev.matches(door);
			}
			
		};
	}

	/**
	 * @param door
	 * @return <code>true</code>, if and only if, the supplied <code>door</code> is 
	 * matched by this <code>DoorMatcher</code>.
	 */
	abstract boolean matches(Door door);
}
