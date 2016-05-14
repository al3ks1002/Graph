import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DijkstraMinimumDistance {
    private class Pair implements Comparable<Pair> {
        private int vertex;
        private int distance;

        Pair(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        int getVertex() {
            return vertex;
        }

        @Override
        public int compareTo(Pair that) {
            return distance - that.distance;
        }
    }

    private DirectedWeightedGraph graph;

    public DijkstraMinimumDistance(DirectedWeightedGraph graph) {
        this.graph = graph;
    }

    public MinimumPath findMinimumDistance(int source, int destination) {
        if (!graph.isVertex(source))
            return new MinimumPath();

        HashMap<Integer, Integer> distance = new HashMap<>();
        PriorityQueue<Pair> queue = new PriorityQueue<>();
        HashMap<Integer, Boolean> visited = new HashMap<>();
        HashMap<Integer, Integer> father = new HashMap<>();

        distance.put(source, 0);
        queue.add(new Pair(source, 0));

        while (!queue.isEmpty()) {
            int vertex = queue.poll().getVertex();

            if (visited.containsKey(vertex))
                continue;

            visited.put(vertex, true);
            int currentDistance = distance.get(vertex);

            if (vertex == destination) {
                ArrayList<Integer> path = new ArrayList<>();

                int x = destination;
                while (x != source) {
                    path.add(x);
                    x = father.get(x);
                }
                path.add(source);
                Collections.reverse(path);

                return new MinimumPath(currentDistance, path);
            }

            try {
                ArrayList<Integer> neighbours = graph.iterableOut(vertex);
                for (Integer neighbour : neighbours) {
                    int cost = graph.getCost(vertex, neighbour);

                    if (!distance.containsKey(neighbour) || currentDistance + cost < distance.get(neighbour)) {
                        distance.put(neighbour, currentDistance + cost);
                        queue.add(new Pair(neighbour, distance.get(neighbour)));
                        father.put(neighbour, vertex);
                    }
                }
            } catch (GraphException e) {
                // never getting here
                e.printStackTrace();
            }

        }

        return new MinimumPath();
    }

    public static void main(String[] args) throws FileNotFoundException {
        DirectedWeightedGraph myGraph = new DirectedWeightedGraph("src/input/dijgraph.txt");
        DijkstraMinimumDistance dijkstra = new DijkstraMinimumDistance(myGraph);

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.printf("--- Minimum distance between vertices x and y ---\n");

            System.out.printf("Enter x: ");
            int x = in.nextInt();

            System.out.printf("Enter y: ");
            int y = in.nextInt();

            MinimumPath answer = dijkstra.findMinimumDistance(x, y);
            ArrayList<Integer> path = answer.getPath();
            if (path.isEmpty())
                System.out.println("No path between x and y!\n");
            else {
                System.out.println(answer.getMinimumDistance());
                for (Integer vertex : path) {
                    System.out.printf("%d ", vertex);
                }
                System.out.println("\n");
            }
        }
    }
}
