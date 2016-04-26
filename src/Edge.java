public class Edge implements Comparable<Edge> {
    private final int from;
    private final int to;

    public Edge(final int from, final int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public final int compareTo(final Edge that) {
        if (from == that.from) {
            return to - that.to;
        }
        return from - that.from;
    }
}
