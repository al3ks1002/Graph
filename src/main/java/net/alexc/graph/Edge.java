package net.alexc.graph;

public class Edge implements Comparable<Edge> {
    private final int from;
    private final int to;

    public Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public int compareTo(Edge that) {
        if (from == that.from) {
            return to - that.to;
        }
        return from - that.from;
    }
}
