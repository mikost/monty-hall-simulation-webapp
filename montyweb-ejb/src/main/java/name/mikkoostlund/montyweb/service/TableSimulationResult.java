package name.mikkoostlund.montyweb.service;

public class TableSimulationResult {

	private long time;
	private long numberOfRuns;
	private int keepingWins;
	private int switchingWins;
	private long numberOfDoors;

	public long getTime() {
		return time;
	}

	public long getNumberOfRuns() {
		return numberOfRuns;
	}

	public int getKeepingWins() {
		return keepingWins;
	}

	public int getSwitchingWins() {
		return switchingWins;
	}

	public long getNumberOfDoors() {
		return numberOfDoors;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setNumberOfRuns(long numberOfRuns) {
		this.numberOfRuns = numberOfRuns;
	}

	public void setKeepingWins(int keepingWins) {
		this.keepingWins = keepingWins;
	}

	public void setSwitchingWins(int switchingWins) {
		this.switchingWins = switchingWins;
	}

	public void setNumberOfDoors(long numberOfDoors) {
		this.numberOfDoors = numberOfDoors;
	}
}
