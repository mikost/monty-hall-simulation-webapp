package name.mikkoostlund.montyweb.domain.montyhallsimulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DefaultShowHelper implements ShowHelper {

    Random r = new Random();

    private int numberOfDoors;

    public DefaultShowHelper(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;

        if (numberOfDoors < 3) {
            throw new IllegalArgumentException(excMsg("there must be at least three doors"));
        }
    }

    public Doors setupDoors() {
    	int indexOfDoorWithCar = r.nextInt(numberOfDoors);

        List<Door> doors = new ArrayList<>();
        for (int doorIndex = 0; doorIndex < numberOfDoors; doorIndex++) {
            doors.add(new DoorImpl(doorIndex, indexOfDoorWithCar==doorIndex));
        }

        return new Doors(doors);
    }

    private String excMsg(String string) {
        return "numberOfDoors = "+ numberOfDoors +"; " + string;
    }
}
