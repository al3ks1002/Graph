import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

public class DirectedWeightedGraph<T> extends DirectedGraph {
    private final TreeMap<Edge, T> costs;

    public DirectedWeightedGraph() {
        super();
        costs = new TreeMap<>();
    }

    public DirectedWeightedGraph(final DirectedWeightedGraph<T> that) {
        super();
        costs = that.costs;
    }

    public void addEdge(final int from, final int to) {
    }

    public void addEdge(final int from, final int to, final T cost) {
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
            costs.put(new Edge(from, to), cost);
        }
    }

    public void removeEdge(final int from, final int to) {
        super.removeEdge(from, to);
        costs.remove(new Edge(from, to));
    }

    public T getCost(final int from, final int to) {
        if (!isEdge(from, to)) {
            Object ret = 0;
            return (T) ret;
        }

        return costs.get(new Edge(from, to));
    }

    public void setCost(final int from, final int to, final T cost) {
        if (!isEdge(from, to)) {
            return;
        }

        costs.put(new Edge(from, to), cost);
    }

    private static void runTests() {
        DirectedWeightedGraph<Integer> myDirectedWeightedGraph = new DirectedWeightedGraph<>();
        myDirectedWeightedGraph.addEdge(0, 1, 10);
        myDirectedWeightedGraph.addEdge(1, 2, 20);
        myDirectedWeightedGraph.addEdge(1, 3, 30);

        myDirectedWeightedGraph.removeEdge(1, 2);
        assert !myDirectedWeightedGraph.isEdge(1, 2);
        assert myDirectedWeightedGraph.isEdge(1, 3);

        myDirectedWeightedGraph.addEdge(1, 2, 20);
        myDirectedWeightedGraph.setCost(0, 1, 100);
        assert myDirectedWeightedGraph.getCost(0, 1) == 100;
        assert myDirectedWeightedGraph.getCost(1, 2) == 20;

        myDirectedWeightedGraph.removeVertex(1);
        assert myDirectedWeightedGraph.getCost(0, 1) == 0;
        assert myDirectedWeightedGraph.getCost(1, 2) == 0;
    }

    public static void main(final String[] args) throws FileNotFoundException {
        DirectedWeightedGraph.runTests();

        Scanner scanner = new Scanner(new File("graph100k.txt"));
        DirectedWeightedGraph<Integer> myGraph = new DirectedWeightedGraph<>();

        int vertices = scanner.nextInt();
        for (int i = 0; i < vertices; i++) {
            myGraph.addVertex(i);
        }

        int edges = scanner.nextInt();

        for (int i = 0; i < edges; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int cost = scanner.nextInt();
            myGraph.addEdge(from, to, cost);
        }

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add a vertex.");
            System.out.println("2. Remove a vertex.");
            System.out.println("3. Add an edge.");
            System.out.println("4. Remove an edge.");
            System.out.println("5. Check if it vertex.");
            System.out.println("6. Check if it edge.");
            System.out.println("7. Print the in degree.");
            System.out.println("8. Print the out degree.");
            System.out.println("9. Get cost of an edge.");
            System.out.println("10. Set cost of an edge.");
            System.out.println("11. Print number of vertices.");
            System.out.println("12. Print number of edges.");
            System.out.println("0. Exit.");

            int command = in.nextInt();

            if (command == 1) {
                System.out.println("Enter a vertex:");
                int vertex = in.nextInt();
                myGraph.addVertex(vertex);
            } else if (command == 2) {
                System.out.println("Enter a vertex: ");
                int vertex = in.nextInt();
                myGraph.removeVertex(vertex);
            } else if (command == 3) {
                System.out.println("Enter x: ");
                int x = in.nextInt();
                System.out.println("Enter y: ");
                int y = in.nextInt();
                System.out.println("Enter cost: ");
                int cost = in.nextInt();
                myGraph.addEdge(x, y, cost);
            } else if (command == 4) {
                System.out.println("Enter x: ");
                int x = in.nextInt();
                System.out.println("Enter y: ");
                int y = in.nextInt();
                myGraph.removeEdge(x, y);
            } else if (command == 5) {
                System.out.println("Enter a vertex: ");
                int vertex = in.nextInt();
                System.out.println(myGraph.isVertex(vertex));
            } else if (command == 6) {
                System.out.println("Enter x: ");
                int x = in.nextInt();
                System.out.println("Enter y: ");
                int y = in.nextInt();
                System.out.println(myGraph.isEdge(x, y));
            } else if (command == 7) {
                System.out.println("Enter a vertex:");
                int vertex = in.nextInt();
                System.out.println(myGraph.inDegree(vertex));
            } else if (command == 8) {
                System.out.println("Enter a vertex:");
                int vertex = in.nextInt();
                System.out.println(myGraph.outDegree(vertex));
            } else if (command == 9) {
                System.out.println("Enter x: ");
                int x = in.nextInt();
                System.out.println("Enter y: ");
                int y = in.nextInt();
                System.out.println(myGraph.getCost(x, y));
            } else if (command == 10) {
                System.out.println("Enter x: ");
                int x = in.nextInt();
                System.out.println("Enter y: ");
                int y = in.nextInt();
                System.out.println("Enter cost: ");
                int cost = in.nextInt();
                myGraph.setCost(x, y, cost);
            } else if (command == 11) {
                System.out.println(myGraph.getVertices());
            } else if (command == 12) {
                System.out.println(myGraph.getEdges());
            } else {
                break;
            }
        }
    }
}
