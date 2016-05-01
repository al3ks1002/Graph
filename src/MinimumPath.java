import java.util.ArrayList;

public class MinimumPath {
    private int minimumDistance;
    ArrayList<Integer> path;

    public MinimumPath() {
        this.minimumDistance = 0;
        this.path = new ArrayList<>();
    }

    public MinimumPath(int minimumDistance, ArrayList<Integer> path) {
        this.minimumDistance = minimumDistance;
        this.path = path;
    }

    public ArrayList<Integer> getPath() {
        return this.path;
    }

    public int getMinimumDistance() {
        return this.minimumDistance;
    }
}
