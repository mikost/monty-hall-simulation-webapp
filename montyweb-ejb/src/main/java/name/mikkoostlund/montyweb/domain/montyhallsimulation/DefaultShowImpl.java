package name.mikkoostlund.montyweb.domain.montyhallsimulation;

import java.util.List;

public class DefaultShowImpl implements Show {

    private static final int DEFAULT_NUMBER_OF_DOORS = 3;
    private ShowHelper showHelper;

    /**
     * Creates an instance of {@link Show} that uses 3 doors. Each time a show is run, 
     * one of the doors is picked at random to contain a car.
     */
    public DefaultShowImpl() {
        this(new DefaultShowHelper(DEFAULT_NUMBER_OF_DOORS));
    }

    /**
     * Creates an instance of {@link Show}. Each time a show is run, the supplied 
     * ShowHelper is used to generate the set of doors. How many doors there are in
     * the set and how many of them that contain cars and goats, respectively, is 
     * entirely controlled by the particular ShowHelper implementation.
     *
     * @param showHelper
     */
    public DefaultShowImpl(ShowHelper showHelper) {
        this.showHelper = showHelper;
    }

    /**
     * Runs a single show.
     * @param showContestant the contestant taking part in the show.
     * @return the result of the show - either a {@link ShowOutcome#WIN} (meaning that the 
     * contestant won a car) or a {@link ShowOutcome#LOSE} (meaning that the contestant 
     * lost - although he "won" a goat).
     */
    public ShowOutcome runWith(ShowContestant showContestant) {
        /*
         *  Prepare for show time - set up the doors! 
         */
        Doors doors = showHelper.setupDoors();

        // Let contestant choose a door.
        int index = showContestant.makeInitialDoorChoice(doors.getDoorIndexes(DoorMatcher.anyDoor()));
        Door doorChosenByContestant = doors.getDoor(index);

        //  Host opens another door (than the one chosen by the contestant), which
        //+ has a goat behind it.
        Door doorOpenedByHost = doors.getDoor(DoorMatcher.doorWithGoat()
                                                         .not(doorChosenByContestant));

        //  The remaining doors - i.e. all doors except the one chosen by the contestant 
        //+ and the one opened by the host - are eligible for the contestant to switch to.
        List<Integer> doorsThatContestantMaySwitchTo = 
                doors.getDoorIndexes(DoorMatcher.anyDoor()
                                                .not(doorChosenByContestant)
                                                .not(doorOpenedByHost));

        // Let the contestant switch to the other door, if he wants.
        int index2 = showContestant.makeSecondDoorChoice(doorChosenByContestant.index(), doorsThatContestantMaySwitchTo);
        doorChosenByContestant = doors.getDoor(index2);

        // If there is a goat behind the chosen door, the contestant lost.
        if (false == doorChosenByContestant.hasCar()) {
            return ShowOutcome.LOSE;
        }

        // The contestant won a car!
        return ShowOutcome.WIN;
    }

}
