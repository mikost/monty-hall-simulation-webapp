package name.mikkoostlund.montyweb.domain.montyhallsimulation;

class DoorImpl implements Door {

    private final int doorIndex;
    private final boolean hasCar;

    public DoorImpl(int doorIndex, boolean hasCar) {
        this.doorIndex = doorIndex;
        this.hasCar = hasCar;
    }

    @Override
    public final int index() {
        return doorIndex;
    }

    @Override
    public boolean hasCar() {
        return hasCar;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + doorIndex;
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DoorImpl)) {
            return false;
        }
        DoorImpl other = (DoorImpl) obj;
        if (doorIndex != other.doorIndex) {
            return false;
        }
        return true;
    }
}
