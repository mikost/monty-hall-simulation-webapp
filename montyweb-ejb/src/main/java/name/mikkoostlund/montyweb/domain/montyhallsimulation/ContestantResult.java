package name.mikkoostlund.montyweb.domain.montyhallsimulation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ContestantResult {
        
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private long id;

    @ManyToOne
    @JoinColumn(nullable=false)
    private ShowContestant showContestant;

    private int timesWon;

    public ContestantResult() {
                
    }

    public ContestantResult(ShowContestant showContestant, int timesWon) {
        this.showContestant = showContestant;
        this.timesWon = timesWon;
    }

    public int getTimesWon() {
        return timesWon;
    }

    public ShowContestant getShowContestant() {
        return showContestant;
    }
}
