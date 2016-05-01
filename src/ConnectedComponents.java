import java.util.ArrayList;
import java.util.HashMap;

public class ConnectedComponents {
    private UndirectedGraph graph;
    private HashMap<Integer, Boolean> visited;
    private ArrayList<UndirectedGraph> connectedComponents;

    public ConnectedComponents(UndirectedGraph graph) {
        this.graph = graph;
        visited = new HashMap<>();
        connectedComponents = new ArrayList<>();
        ArrayList<Integer> vertices = graph.getAllVertices();

        for (Integer vertex : vertices) {
            if (!visited.containsKey(vertex)) {
                UndirectedGraph newComponent = new UndirectedGraph();
                DFS(vertex, newComponent);
                connectedComponents.add(newComponent);
            }
        }
    }

    private void DFS(final int vertex, UndirectedGraph component) {
        visited.put(vertex, true);

        ArrayList<Integer> neighbours = graph.getNeighbours(vertex);
        for (Integer neighbour : neighbours) {
            component.addEdge(vertex, neighbour);
            if (!visited.containsKey(neighbour)) {
                DFS(neighbour, component);
            }
        }
    }

    public ArrayList<UndirectedGraph> getConnectedComponents() {
        return connectedComponents;
    }
}
