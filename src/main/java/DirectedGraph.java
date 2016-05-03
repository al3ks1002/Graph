import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.ArrayList;

public class DirectedGraph {
    private int vertices;
    protected int edges;
    protected final HashMap<Integer, ArrayList<Integer>> out;
    protected final HashMap<Integer, ArrayList<Integer>> in;

    public DirectedGraph() {
        vertices = 0;
        edges = 0;
        in = new HashMap<>();
        out = new HashMap<>();
    }

    public DirectedGraph(final DirectedGraph that) {
        vertices = that.vertices;
        edges = that.edges;
        in = that.in;
        out = that.out;
    }

    public final int getVertices() {
        return vertices;
    }

    public final int getEdges() {
        return edges;
    }

    public void addVertex(final int vertex) {
        if (isVertex(vertex)) {
            return;
        }

        in.put(vertex, new ArrayList<>());
        out.put(vertex, new ArrayList<>());
        vertices++;
    }

    public void addEdge(final int from, final int to) {
        if (!isVertex(from)) {
            addVertex(from);
        }

        if (!isVertex(to)) {
            addVertex(to);
        }

        if (!out.get(from).contains(to)) {
            out.get(from).add(to);
            in.get(to).add(from);
            edges++;
        }
    }

    public int inDegree(final int vertex) throws GraphException {
        if (!isVertex(vertex)) {
            throw new GraphException();
        }

        return in.get(vertex).size();
    }

    public int outDegree(final int vertex) throws GraphException {
        if (!isVertex(vertex)) {
            throw new GraphException();
        }

        return out.get(vertex).size();
    }

    public void removeVertex(final int vertex) {
        if (!isVertex(vertex)) {
            return;
        }

        vertices--;
        ArrayList<Integer> aux = new ArrayList<>();

        aux.addAll(in.get(vertex));
        for (Integer neighbour : aux) {
            removeEdge(neighbour, vertex);
        }

        aux.clear();
        aux.addAll(out.get(vertex));
        for (Integer neighbour : aux) {
            removeEdge(vertex, neighbour);
        }

        in.remove(vertex);
        out.remove(vertex);
    }

    public void removeEdge(final int from, final int to) {
        if (!isEdge(from, to)) {
            return;
        }

        edges--;

        for (Integer vertex : out.get(from)) {
            if (vertex == to) {
                out.get(from).remove(vertex);
                break;
            }
        }

        for (Integer vertex : in.get(to)) {
            if (vertex == from) {
                in.get(to).remove(vertex);
                break;
            }
        }
    }

    public boolean isVertex(final int vertex) {
        return in.containsKey(vertex);
    }

    public boolean isEdge(final int to, final int from) {
        if (!isVertex(to)) {
            return false;
        }

        for (Integer vertex : out.get(to)) {
            if (vertex == from) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Integer> iterableIn(final int vertex) throws GraphException {
        if (!isVertex(vertex)) {
            throw new GraphException();
        }

        return in.get(vertex);
    }

    public ArrayList<Integer> iterableOut(final int vertex) throws GraphException {
        if (!isVertex(vertex)) {
            throw new GraphException();
        }

        return out.get(vertex);
    }
}
