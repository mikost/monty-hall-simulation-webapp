package name.mikkoostlund.montyweb.domain.montyhallsimulation;

import java.util.Random;

public class DefaultShowHelper implements ShowHelper {

	Random r = new Random();
	public Doors setupDoors(int numberOfDoors) {
		int indexOfDoorWithCar = r.nextInt(numberOfDoors);

		return new Doors(indexOfDoorWithCar, numberOfDoors);
	}

}
