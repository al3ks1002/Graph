import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class UndirectedGraph {
    private int vertices;
    private int edges;
    private final HashMap<Integer, ArrayList<Integer>> neighbours;

    public UndirectedGraph() {
        vertices = 0;
        edges = 0;
        neighbours = new HashMap<>();
    }

    public UndirectedGraph(final UndirectedGraph that) {
        vertices = that.vertices;
        edges = that.edges;
        neighbours = that.neighbours;
    }

    public int getVertices() {
        return vertices;
    }

    public int getEdges() {
        return edges;
    }

    public void addVertex(final int vertex) {
        if (isVertex(vertex)) {
            return;
        }

        neighbours.put(vertex, new ArrayList<>());
        vertices++;
    }

    public void addEdge(final int from, final int to) {
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

    public int degree(final int vertex) {
        if (!isVertex(vertex)) {
            return 0;
        }

        return neighbours.get(vertex).size();
    }

    public void removeVertex(final int vertex) {
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

    public void removeEdge(final int from, final int to) {
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

    public boolean isVertex(final int vertex) {
        return neighbours.containsKey(vertex);
    }

    public boolean isEdge(final int to, final int from) {
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

    public Iterable<Integer> iterable(final int vertex) {
        return neighbours.get(vertex);
    }


    private void connectedComponentsDFS(final int vertex, final HashMap<Integer, Boolean> visited, final
    ArrayList<Integer> component) {
        visited.put(vertex, true);
        component.add(vertex);

        for (Integer neighbour : neighbours.get(vertex)) {
            if (!visited.containsKey(neighbour)) {
                connectedComponentsDFS(neighbour, visited, component);
            }
        }
    }

    private void biconnectedComponentsDFS(final int vertex, final int father, final HashMap<Integer, Integer> lowLink,
                                          final HashMap<Integer, Integer> count, final Stack<Integer> stack,
                                          final ArrayList<ArrayList<Integer>> biconnectedComponents) {
        count.put(vertex, count.get(father) + 1);
        lowLink.put(vertex, count.get(father) + 1);

        if (neighbours.get(vertex).size() == 0 && father == -1) {
            ArrayList<Integer> newComponent = new ArrayList<>();
            newComponent.add(vertex);
            biconnectedComponents.add(newComponent);
        }

        for (Integer son : neighbours.get(vertex)) {
            if (son != father) {
                if (!count.containsKey(son)) {
                    stack.push(son);
                    biconnectedComponentsDFS(son, vertex, lowLink, count, stack, biconnectedComponents);

                    if (lowLink.get(son) < lowLink.get(vertex)) {
                        lowLink.put(vertex, lowLink.get(son));
                    }

                    if (lowLink.get(son) >= count.get(vertex)) {
                        ArrayList<Integer> newComponent = new ArrayList<>();
                        while (!stack.peek().equals(son)) {
                            newComponent.add(stack.pop());
                        }
                        newComponent.add(stack.pop());
                        newComponent.add(vertex);
                        biconnectedComponents.add(newComponent);
                    }
                } else {
                    if (count.get(son) < lowLink.get(vertex)) {
                        lowLink.put(vertex, count.get(son));
                    }
                }
            }
        }
    }

    public ArrayList<ArrayList<Integer>> findConnectedComponents() {
        HashMap<Integer, Boolean> visited = new HashMap<>();
        ArrayList<ArrayList<Integer>> connectedComponents = new ArrayList<>();

        for (Integer vertex : neighbours.keySet()) {
            if (!visited.containsKey(vertex)) {
                ArrayList<Integer> newComponent = new ArrayList<>();
                connectedComponentsDFS(vertex, visited, newComponent);
                connectedComponents.add(newComponent);
            }
        }

        return connectedComponents;
    }

    public ArrayList<ArrayList<Integer>> findBiconnectedComponents() {
        HashMap<Integer, Integer> lowLink = new HashMap<>();
        HashMap<Integer, Integer> count = new HashMap<>();
        ArrayList<ArrayList<Integer>> biconnectedComponents = new ArrayList<>();
        count.put(-1, 0);

        for (Integer vertex : neighbours.keySet()) {
            if (!count.containsKey(vertex)) {
                Stack<Integer> stack = new Stack<>();
                biconnectedComponentsDFS(vertex, -1, lowLink, count, stack, biconnectedComponents);
            }
        }

        return biconnectedComponents;
    }

    private static void runTests() {
        UndirectedGraph myUndirectedGraph = new UndirectedGraph();

        myUndirectedGraph.addVertex(0);
        myUndirectedGraph.addVertex(1);
        myUndirectedGraph.addVertex(2);
        myUndirectedGraph.addVertex(3);
        myUndirectedGraph.addEdge(0, 1);
        myUndirectedGraph.addEdge(1, 2);
        myUndirectedGraph.addEdge(1, 3);

        assert myUndirectedGraph.getVertices() == 4;
        assert myUndirectedGraph.getEdges() == 3;
        assert myUndirectedGraph.isEdge(2, 1);
        assert myUndirectedGraph.isEdge(1, 2);
        assert !myUndirectedGraph.isEdge(3, 2);
        assert myUndirectedGraph.isVertex(1);
        assert !myUndirectedGraph.isVertex(4);
        assert myUndirectedGraph.degree(1) == 3;

        assert myUndirectedGraph.degree(2) == 1;
        myUndirectedGraph.removeEdge(1, 2);
        assert !myUndirectedGraph.isEdge(1, 2);
        assert myUndirectedGraph.degree(2) == 0;

        myUndirectedGraph.addEdge(1, 2);
        myUndirectedGraph.removeVertex(1);
        assert myUndirectedGraph.getEdges() == 0;
        assert myUndirectedGraph.getVertices() == 3;
        assert !myUndirectedGraph.isEdge(1, 2);
        assert !myUndirectedGraph.isEdge(1, 3);
        assert !myUndirectedGraph.isEdge(0, 1);
        assert !myUndirectedGraph.isVertex(1);
    }

    public static void main(final String[] args) throws FileNotFoundException {
        UndirectedGraph.runTests();

        long time1 = System.currentTimeMillis();

        Scanner scanner = new Scanner(new File("/home/alex/IdeaProjects/Graph/graph1m.txt"));
        UndirectedGraph myGraph = new UndirectedGraph();

        int vertices = scanner.nextInt();
        int edges = scanner.nextInt();

        for (int i = 0; i < vertices; i++) {
            myGraph.addVertex(i);
        }

        for (int i = 0; i < edges; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            //int cost = scanner.nextInt();
            myGraph.addEdge(from, to);
        }

        long time2 = System.currentTimeMillis();
        long elapsedTime = time2 - time1;
        System.out.printf("Time to read: %f\n", elapsedTime / 1000.0);

        ArrayList<ArrayList<Integer>> cc = myGraph.findConnectedComponents();
        System.out.printf("Number of connected components: %d\n", cc.size());
/*        for (ArrayList<Integer> component : cc) {
            for (Integer vertex : component)
                System.out.format("%d ", vertex);
            System.out.printf("\n");
        }*/

        long time3 = System.currentTimeMillis();
        elapsedTime = time3 - time2;
        System.out.printf("Time to find connected components: %f\n", elapsedTime / 1000.0);

        ArrayList<ArrayList<Integer>> bc = myGraph.findBiconnectedComponents();
        System.out.printf("Number of biconnected components: %d\n", bc.size());
        for (ArrayList<Integer> component : bc) {
            for (Integer vertex : component)
                System.out.format("%d ", vertex);
            System.out.printf("\n");
        }

        long time4 = System.currentTimeMillis();
        elapsedTime = time4 - time3;
        System.out.printf("Time to find biconnected components: %f\n", elapsedTime / 1000.0);
    }
}
