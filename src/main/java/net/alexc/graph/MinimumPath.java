package net.alexc.graph;

import java.util.ArrayList;

public class MinimumPath {
    private final int minimumDistance;
    private final ArrayList<Integer> path;

    public MinimumPath() {
        minimumDistance = 0;
        path = new ArrayList<>();
    }

    public MinimumPath(int minimumDistance, ArrayList<Integer> path) {
        this.minimumDistance = minimumDistance;
        this.path = path;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    public int getMinimumDistance() {
        return minimumDistance;
    }
}
