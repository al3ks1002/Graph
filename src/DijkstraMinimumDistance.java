import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.IntBinaryOperator;

public class DijkstraMinimumDistance {
    private class Pair implements Comparable<Pair> {
        private int node;
        private int dist;

        public Pair(int node, int dist) {
            this.node = node;
            this.dist = dist;
        }

        public int getNode() {
            return this.node;
        }

        @Override
        public int compareTo(Pair that) {
            return this.dist - that.dist;
        }
    }

    private DirectedWeightedGraph<Integer> graph;
    private HashMap<Integer, Integer> distance;
    private PriorityQueue<Pair> queue;
    private HashMap<Integer, Boolean> visited;
    private HashMap<Integer, Integer> father;

    public DijkstraMinimumDistance(DirectedWeightedGraph graph) {
        this.graph = graph;
    }

    public ArrayList<Integer> findMinimumDistance(int source, int destination) {
        this.distance = new HashMap<>();
        this.queue = new PriorityQueue<>();
        this.visited = new HashMap<>();
        this.father = new HashMap<>();

        distance.put(source, 0);
        queue.add(new Pair(source, 0));

        while (!queue.isEmpty()) {
            int vertex = queue.poll().getNode();

            if (visited.containsKey(vertex))
                continue;

            visited.put(vertex, true);
            int currentDistance = distance.get(vertex);

            if (vertex == destination) {
                ArrayList<Integer> answer = new ArrayList<>();

                int x = destination;
                while (x != source) {
                    answer.add(x);
                    x = father.get(x);
                }
                answer.add(source);
                Collections.reverse(answer);

                answer.add(currentDistance);
                return answer;
            }

            for (Integer neighbour : graph.iterableOut(vertex)) {
                int cost = graph.getCost(vertex, neighbour);

                if (!distance.containsKey(neighbour) || currentDistance + cost < distance.get(neighbour)) {
                    distance.put(neighbour, currentDistance + cost);
                    queue.add(new Pair(neighbour, distance.get(neighbour)));
                    father.put(neighbour, vertex);
                }
            }
        }

        return new ArrayList<>();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("graph1k.txt"));
        DirectedWeightedGraph myGraph = new DirectedWeightedGraph();

        int vertices = scanner.nextInt();
        int edges = scanner.nextInt();
        for (int i = 0; i < edges; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int cost = scanner.nextInt();
            myGraph.addEdge(from, to, cost);
        }

        DijkstraMinimumDistance dijkstra = new DijkstraMinimumDistance(myGraph);

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.printf("--- Minimum distance between vertices x and y ---\n");

            System.out.printf("Enter x: ");
            int x = in.nextInt();

            System.out.printf("Enter y: ");
            int y = in.nextInt();

            ArrayList<Integer> answer = dijkstra.findMinimumDistance(x, y);
            if (answer.isEmpty())
                System.out.println("No path between x and y!\n");
            else {
                int n = answer.size();
                System.out.println(answer.get(n - 1));
                for (int i = 0; i < n - 1; i++)
                    System.out.printf("%d ", answer.get(i));
                System.out.println("\n");
            }
        }
    }
}
