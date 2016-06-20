package net.alexc.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class DirectedWeightedGraph extends DirectedGraph {
    private final TreeMap<Edge, Integer> costs;

    public DirectedWeightedGraph() {
        super();
        costs = new TreeMap<>();
    }

    public DirectedWeightedGraph(DirectedWeightedGraph that) {
        super();
        costs = that.costs;
    }

    public DirectedWeightedGraph(String file) throws FileNotFoundException {
        in = new HashMap<>();
        out = new HashMap<>();
        costs = new TreeMap<>();

        Scanner scanner = new Scanner(new File(file));
        int n = scanner.nextInt();

        for (int i = 1; i <= n; i++)
            addVertex(i);

        int m = scanner.nextInt();

        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int cost = scanner.nextInt();
            addEdge(from, to, cost);
        }
    }

    public void addEdge(int from, int to) {
    }

    public void addEdge(int from, int to, int cost) {
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

    public void removeEdge(int from, int to) {
        super.removeEdge(from, to);
        costs.remove(new Edge(from, to));
    }

    public Integer getCost(int from, int to) throws GraphException {
        if (!isEdge(from, to)) {
            throw new GraphException();
        }

        return costs.get(new Edge(from, to));
    }

    public void setCost(int from, int to, int cost) throws GraphException {
        if (!isEdge(from, to)) {
            throw new GraphException();
        }

        costs.put(new Edge(from, to), cost);
    }

    public static void main(String[] args) throws FileNotFoundException {
        DirectedWeightedGraph myGraph = new DirectedWeightedGraph();

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
                try {
                    System.out.println(myGraph.inDegree(vertex));
                } catch (GraphException e) {
                    System.out.println("The vertex doesn't exist!");
                    e.printStackTrace();
                }
            } else if (command == 8) {
                System.out.println("Enter a vertex:");
                int vertex = in.nextInt();
                try {
                    System.out.println(myGraph.outDegree(vertex));
                } catch (GraphException e) {
                    System.out.println("The vertex doesn't exist!");
                    e.printStackTrace();
                }
            } else if (command == 9) {
                System.out.println("Enter x: ");
                int x = in.nextInt();
                System.out.println("Enter y: ");
                int y = in.nextInt();
                try {
                    System.out.println(myGraph.getCost(x, y));
                } catch (GraphException e) {
                    System.out.println("The edge doesn't exist!");
                    e.printStackTrace();
                }
            } else if (command == 10) {
                System.out.println("Enter x: ");
                int x = in.nextInt();
                System.out.println("Enter y: ");
                int y = in.nextInt();
                System.out.println("Enter cost: ");
                int cost = in.nextInt();
                try {
                    myGraph.setCost(x, y, cost);
                } catch (GraphException e) {
                    System.out.println("The edge doesn't exist!");
                    e.printStackTrace();
                }
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
