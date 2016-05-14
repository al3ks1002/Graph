import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class UndirectedGraph {
    private int vertices;
    private int edges;
    private HashMap<Integer, ArrayList<Integer>> neighbours;

    public UndirectedGraph() {
        vertices = 0;
        edges = 0;
        neighbours = new HashMap<>();
    }

    public UndirectedGraph(UndirectedGraph that) {
        vertices = that.vertices;
        edges = that.edges;
        neighbours = that.neighbours;
    }

    public UndirectedGraph(String file) throws FileNotFoundException {
        vertices = 0;
        edges = 0;
        neighbours = new HashMap<>();

        Scanner scanner = new Scanner(new File(file));
        int n = scanner.nextInt();

        for (int i = 1; i <= n; i++)
            addVertex(i);

        int m = scanner.nextInt();

        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            addEdge(from, to);
        }
    }

    public int getVertices() {
        return vertices;
    }

    public int getEdges() {
        return edges;
    }

    public void addVertex(int vertex) {
        if (isVertex(vertex)) {
            return;
        }

        neighbours.put(vertex, new ArrayList<>());
        vertices++;
    }

    public void addEdge(int from, int to) {
        if (!isVertex(from)) {
            addVertex(from);
        }

        if (!isVertex(to)) {
            addVertex(to);
        }

        if (!neighbours.get(from).contains(to)) {
            neighbours.get(to).add(from);
            neighbours.get(from).add(to);
            edges++;
        }
    }

    public int degree(int vertex) throws GraphException {
        if (!isVertex(vertex)) {
            throw new GraphException();
        }

        return neighbours.get(vertex).size();
    }

    public void removeVertex(int vertex) {
        if (!isVertex(vertex)) {
            return;
        }

        vertices--;
        ArrayList<Integer> aux = new ArrayList<>();

        aux.addAll(neighbours.get(vertex));
        for (Integer neighbour : aux) {
            removeEdge(neighbour, vertex);
        }

        neighbours.remove(vertex);
    }

    public void removeEdge(int from, int to) {
        if (!isEdge(from, to)) {
            return;
        }

        edges--;

        for (Integer vertex : neighbours.get(from)) {
            if (vertex == to) {
                neighbours.get(from).remove(vertex);
                break;
            }
        }

        for (Integer vertex : neighbours.get(to)) {
            if (vertex == from) {
                neighbours.get(to).remove(vertex);
                break;
            }
        }
    }

    public boolean isVertex(int vertex) {
        return neighbours.containsKey(vertex);
    }

    public boolean isEdge(int to, int from) {
        if (!isVertex(to)) {
            return false;
        }

        for (Integer vertex : neighbours.get(to)) {
            if (vertex == from) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Integer> getNeighbours(int vertex) throws GraphException {
        if (!isVertex(vertex)) {
            throw new GraphException();
        }
        return neighbours.get(vertex);
    }

    public ArrayList<Integer> getAllVertices() {
        ArrayList<Integer> vertices = new ArrayList<>();
        vertices.addAll(neighbours.keySet());
        return vertices;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long time1 = System.currentTimeMillis();

        long time2 = System.currentTimeMillis();
        long elapsedTime = time2 - time1;
        System.out.printf("Time to read: %f\n", elapsedTime / 1000.0);

        long time3 = System.currentTimeMillis();
        elapsedTime = time3 - time2;
        System.out.printf("Time to find connected components: %f\n", elapsedTime / 1000.0);

        long time4 = System.currentTimeMillis();
        elapsedTime = time4 - time3;
        System.out.printf("Time to find biconnected components: %f\n", elapsedTime / 1000.0);
    }
}
