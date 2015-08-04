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

    private final List<Door> doors;

    /**
     * @param doors a java.util.List of Door instances that fulfill the following requirements:
     */
    public Doors(List<Door> doors) {
        this.doors = doors;
        validate();
    }

    private void validate() {
        validateThatAllDoorsAreNonNull();
        validateThatAllDoorIndexesAreDistinct();
        validateThatAllDoorsAreDistinct();
        validateThatAtLeastTwoDoorsHaveGoats();
        validateThatAtLeastOneDoorHasCar();
    }


    private void validateThatAllDoorsAreNonNull() {
        assert doors != null;
        for (Door door: doors) {
            assert door != null; 
        }
    }

    private void validateThatAllDoorIndexesAreDistinct() {
        Set<Integer> indexSet = new HashSet<>();
        for (Door door : doors) {
            indexSet.add(door.index());
        }
        assert indexSet.size() == doors.size();
    }

    private void validateThatAllDoorsAreDistinct() {
        Set<Door> doorSet = new HashSet<>();
        doorSet.addAll(doors);
        assert doorSet.size() == doors.size();
    }

    private void validateThatAtLeastTwoDoorsHaveGoats() {
        long numberOfGoats = doors.stream().filter(door -> !door.hasCar()).count();
        assert numberOfGoats >= 2;
    }

    private void validateThatAtLeastOneDoorHasCar() {
        long numberOfCars = doors.stream().filter(door -> door.hasCar()).count();
        assert numberOfCars >= 1;
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
