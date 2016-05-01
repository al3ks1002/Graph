import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class BiconnectedComponents {
    private UndirectedGraph graph;
    private HashMap<Integer, Integer> lowLink;
    private HashMap<Integer, Integer> count;
    private ArrayList<ArrayList<Integer>> biconnectedComponents;

    public BiconnectedComponents(UndirectedGraph graph) {
        this.graph = graph;
        lowLink = new HashMap<>();
        count = new HashMap<>();
        biconnectedComponents = new ArrayList<>();

        ArrayList<Integer> vertices = graph.getAllVertices();
        count.put(-1, 0);
        for (Integer vertex : vertices) {
            if (!count.containsKey(vertex)) {
                Stack<Integer> currentStack = new Stack<>();
                DFS(vertex, -1, currentStack);
            }
        }
    }

    private void DFS(final int vertex, final int father, final Stack<Integer> stack) {
        count.put(vertex, count.get(father) + 1);
        lowLink.put(vertex, count.get(father) + 1);

        ArrayList<Integer> neighbours = graph.getNeighbours(vertex);

        if (neighbours.size() == 0 && father == -1) {
            ArrayList<Integer> newComponent = new ArrayList<>();
            newComponent.add(vertex);
            biconnectedComponents.add(newComponent);
        }

        for (Integer son : neighbours) {
            if (son != father) {
                if (!count.containsKey(son)) {
                    stack.push(son);
                    DFS(son, vertex, stack);

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

    ArrayList<ArrayList<Integer>> getBiconnectedComponents() {
        return biconnectedComponents;
    }
}
