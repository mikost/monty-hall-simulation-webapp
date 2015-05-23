package name.mikkoostlund.montyweb.ui.jsf;

public class ContestantResult {

	private String name;
	private int timesWon;

	public ContestantResult(String name, int nTimesWon) {
		this.setName(name);
		this.setTimesWon(nTimesWon);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTimesWon() {
		return timesWon;
	}

	public void setTimesWon(int timesWon) {
		this.timesWon = timesWon;
	}

}
