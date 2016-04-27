import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.ArrayList;

public class DirectedGraph {
    protected int vertices;
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

    public int inDegree(final int vertex) {
        if (!isVertex(vertex)) {
            return 0;
        }

        return in.get(vertex).size();
    }

    public int outDegree(final int vertex) {
        if (!isVertex(vertex)) {
            return 0;
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

    public ArrayList<Integer> iterableIn(final int vertex) {
        if (!isVertex(vertex))
            return new ArrayList<>();
        return in.get(vertex);
    }

    public ArrayList<Integer> iterableOut(final int vertex) {
        if (!isVertex(vertex))
            return new ArrayList<>();
        return out.get(vertex);
    }

    private static void runTests() {
        DirectedGraph myDirectedGraph = new DirectedGraph();

        myDirectedGraph.addVertex(0);
        myDirectedGraph.addVertex(1);
        myDirectedGraph.addVertex(2);
        myDirectedGraph.addVertex(3);
        myDirectedGraph.addEdge(0, 1);
        myDirectedGraph.addEdge(1, 2);
        myDirectedGraph.addEdge(1, 3);

        assert myDirectedGraph.getVertices() == 4;
        assert myDirectedGraph.getEdges() == 3;
        assert !myDirectedGraph.isEdge(2, 1);
        assert myDirectedGraph.isEdge(1, 2);
        assert myDirectedGraph.isVertex(1);
        assert !myDirectedGraph.isVertex(4);
        assert myDirectedGraph.outDegree(1) == 2;
        assert myDirectedGraph.inDegree(2) == 1;

        myDirectedGraph.removeEdge(1, 2);
        assert !myDirectedGraph.isEdge(1, 2);
        assert myDirectedGraph.inDegree(2) == 0;

        myDirectedGraph.addEdge(1, 2);
        myDirectedGraph.removeVertex(1);
        assert myDirectedGraph.getEdges() == 0;
        assert myDirectedGraph.getVertices() == 3;
        assert !myDirectedGraph.isEdge(1, 2);
        assert !myDirectedGraph.isEdge(1, 3);
        assert !myDirectedGraph.isEdge(0, 1);
        assert !myDirectedGraph.isVertex(1);
    }

    public static void main(final String[] args) throws FileNotFoundException {
        DirectedGraph.runTests();
    }
}
