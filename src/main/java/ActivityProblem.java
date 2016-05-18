import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ActivityProblem {
    private DirectedGraph graph;
    private ArrayList<Integer> topologicalSorting;
    private HashMap<Integer, Integer> time;
    private HashMap<Integer, Integer> earliestStart, earliestEnd;
    private HashMap<Integer, Integer> latestStart, latestEnd;
    private boolean isDAG;

    public ActivityProblem(String file) throws FileNotFoundException, GraphException {
        topologicalSorting = new ArrayList<>();
        earliestStart = new HashMap<>();
        earliestEnd = new HashMap<>();
        latestStart = new HashMap<>();
        latestEnd = new HashMap<>();
        time = new HashMap<>();
        isDAG = true;
        this.graph = new DirectedGraph();

        Scanner scanner = new Scanner(new File(file));
        int n = scanner.nextInt();

        for (int i = 1; i <= n; i++) {
            int activity = scanner.nextInt();
            graph.addVertex(activity);

            int t = scanner.nextInt();
            time.put(activity, t);

            int predecessors = scanner.nextInt();
            for (int j = 1; j <= predecessors; j++) {
                int pred = scanner.nextInt();
                graph.addEdge(pred, activity);
            }
        }

        int source = Integer.MIN_VALUE;
        graph.addVertex(source);
        time.put(source, 0);
        for (Integer activity : graph.getAllVertices()) {
            if (activity != source && graph.iterableIn(activity).size() == 0) {
                graph.addEdge(source, activity);
            }
        }

        int sink = Integer.MAX_VALUE;
        graph.addVertex(sink);
        time.put(sink, 0);
        for (Integer activity : graph.getAllVertices()) {
            if (activity != sink && graph.iterableOut(activity).size() == 0) {
                graph.addEdge(activity, sink);
            }
        }

        StronglyConnectedComponents scc = new StronglyConnectedComponents(graph);
        ArrayList<ArrayList<Integer>> components = scc.getStronglyConnectedComponents();

        if (components.size() < n) {
            isDAG = false;
            return;
        }

        for (ArrayList<Integer> comp : components) {
            int activity = comp.get(0);
            topologicalSorting.add(activity);
        }

        Collections.reverse(topologicalSorting);

        earliestStart.put(source, 0);
        earliestEnd.put(source, 0);
        for (int i = 1; i < topologicalSorting.size(); i++) {
            int current = topologicalSorting.get(i);
            int best = 0;
            for (Integer pred : graph.iterableIn(current)) {
                best = Math.max(best, earliestEnd.get(pred));
            }
            earliestStart.put(current, best);
            earliestEnd.put(current, best + time.get(current));
        }

        latestEnd.put(sink, earliestEnd.get(sink));
        latestStart.put(sink, latestEnd.get(sink));
        for (int i = topologicalSorting.size() - 2; i >= 0; i--) {
            int current = topologicalSorting.get(i);
            int best = Integer.MAX_VALUE;
            for (Integer after : graph.iterableOut(current)) {
                best = Math.min(best, latestStart.get(after));
            }
            latestEnd.put(current, best);
            latestStart.put(current, best - time.get(current));
        }

        System.out.printf("Total time: %d\n\n", latestEnd.get(sink));

        for (int i = 1; i < topologicalSorting.size() - 1; i++) {
            int current = topologicalSorting.get(i);
            System.out.printf("Activity %d\n", current);
            System.out.printf("Earliest starting time: %d\n", earliestStart.get(current));
            System.out.printf("Latest starting time: %d\n", latestStart.get(current));
            if (earliestStart.get(current).equals(latestStart.get(current))) {
                System.out.printf("Critical activity!\n");
            }
            System.out.printf("\n");
        }
    }

    public static void main(String[] args) throws FileNotFoundException, GraphException {
        ActivityProblem mlc = new ActivityProblem("src/input/activity.txt");
    }
}
