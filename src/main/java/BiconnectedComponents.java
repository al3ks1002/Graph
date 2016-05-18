import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class BiconnectedComponents {
    private int currentIndex;
    private UndirectedGraph graph;
    private HashMap<Integer, Integer> lowLink;
    private HashMap<Integer, Integer> index;
    private ArrayList<ArrayList<Integer>> biconnectedComponents;

    public BiconnectedComponents(UndirectedGraph graph) throws GraphException{
        this.graph = graph;
        currentIndex = 0;
        lowLink = new HashMap<>();
        index = new HashMap<>();
        biconnectedComponents = new ArrayList<>();

        ArrayList<Integer> vertices = graph.getAllVertices();
        for (Integer vertex : vertices) {
            if (!index.containsKey(vertex)) {
                Stack<Integer> currentStack = new Stack<>();
                DFS(vertex, -1, currentStack);
            }
        }
    }

    private void DFS(int vertex, int father, Stack<Integer> stack) throws GraphException {
        index.put(vertex, ++currentIndex);
        lowLink.put(vertex, index.get(vertex));

        ArrayList<Integer> neighbours = graph.getNeighbours(vertex);

        if (neighbours.size() == 0 && father == -1) {
            ArrayList<Integer> newComponent = new ArrayList<>();
            newComponent.add(vertex);
            biconnectedComponents.add(newComponent);
        }

        for (Integer son : neighbours) {
            if (son != father) {
                if (!index.containsKey(son)) {
                    stack.push(son);
                    DFS(son, vertex, stack);

                    if (lowLink.get(son) < lowLink.get(vertex)) {
                        lowLink.put(vertex, lowLink.get(son));
                    }

                    if (lowLink.get(son) >= index.get(vertex)) {
                        ArrayList<Integer> newComponent = new ArrayList<>();
                        while (!stack.peek().equals(son)) {
                            newComponent.add(stack.pop());
                        }
                        newComponent.add(stack.pop());
                        newComponent.add(vertex);
                        biconnectedComponents.add(newComponent);
                    }
                } else {
                    if (index.get(son) < lowLink.get(vertex)) {
                        lowLink.put(vertex, index.get(son));
                    }
                }
            }
        }
    }

    ArrayList<ArrayList<Integer>> getBiconnectedComponents() {
        return biconnectedComponents;
    }
}
