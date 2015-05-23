package name.mikkoostlund.montyweb.domain.montyhallsimulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A Doors instance abstracts the set of doors in a Monty Hall show.
 * A Doors instance contains instances of {@link Door}, where 
 * exactly one Door "has a car" (its {@link Door#hasCar() hasCar} method returns <code>true</code>) 
 * and each of the remaining ones "has a goat" (its {@link Door#hasCar() hasCar} method 
 * returns <code>false</code>).
 * @author mikko
 *
 */
public class Doors  {

	private final List<Door> doors = new ArrayList<>();

	/**
	 * @param indexOfDoorWithCar the index of the door that has a car behind it. Must be non-negative and less than {@code numberOfDoors}.
	 * @param numberOfDoors the total number of doors. Must be non-negative.
	 */
	public Doors(final int indexOfDoorWithCar, int numberOfDoors) {
		validate(numberOfDoors, indexOfDoorWithCar);

		for (int i = 0; i < numberOfDoors; i++) {
			final int doorIndex = i;
			doors.add(new Door() {
				
				@Override
				public int index() {
					return doorIndex;
				}
				
				@Override
				public boolean hasCar() {
					return doorIndex == indexOfDoorWithCar;
				}
			});
		}

		assertInvariant();
	}

	private void validate(int numberOfDoors, final int indexOfCarDoor) {
		if (indexOfCarDoor < 0 || indexOfCarDoor >= numberOfDoors) {
			throw new IllegalArgumentException("indexOfCarDoor = "+ indexOfCarDoor +", numberOfDoors = "+ numberOfDoors +"; required: 0 <= indexOfCarDoor < numberOfDoors");
		}
	}

	private void assertInvariant() {
		assertNoNullDoors();
		assertDistinct();
		assertExactlyOneHasCar();
	}

	private void assertNoNullDoors() {
		for (Door door: doors) {
			assert door != null; 
		}
	}

	private void assertDistinct() {
		Set<Door> set = new HashSet<>();
		set.addAll(doors);
		assert set.size() == doors.size();
	}

	private void assertExactlyOneHasCar() {
		int cars = 0;
		for (Door door : doors) {
			if (door.hasCar()) {
				cars++;
			}
		}
		assert cars == 1;
	}

	/**
	 * Returns the lowest-indexed <code>Door</code> instance within this <code>Doors</code>, 
	 * which is matched by the specified <code>DoorMatcher</code>. If there is no instance 
	 * matched by the specified <code>DoorMatcher</code>, a <code>NoSuchElementException</code> 
	 * is thrown.  
	 * @param matcher a <code>DoorMatcher</code> which specifies the <code>Door</code> within 
	 * this <code>Doors</code> to retrieve. 
	 * @return a <code>Door</code> instance, which is contained in this <code>Doors</code> 
	 * and which is matched by the specified <code>DoorMatcher<code>.
	 * @throws NoSuchElementException if no <code>Door</code> instance within
	 * this <code>Doors</code> instance is matched by the specified <code>DoorMatcher</code>.
	 */
	Door getDoor(DoorMatcher matcher) {
		for (Door door: doors) {
			if (matcher.matches(door)) {
				return door;
			}
		}
		throw new NoSuchElementException();
	}

	/**
	 * @return the number of {@link Door} instances contained by this <code>Doors</code> instance.
	 */
	public int numberOfDoors() {
		return doors.size();
	}

	/**
	 * @param index the index of the <code>Door</code> to retrieve.
	 * @return the <code>Door</code> instance at index <code>index</code> within this 
	 * <code>Doors</code> instance.
	 */
	public Door getDoor(int index) {
		return doors.get(index);
	}

	public List<Integer> getDoorIndexes(DoorMatcher doorMatcher) {
		List <Integer> doorIndexes = new ArrayList<>();
		for (int i = 0; i < doors.size(); i++) {
			if (doorMatcher.matches(doors.get(i))) {
				doorIndexes.add(i);
			}
		}
		return doorIndexes;
	}
}
