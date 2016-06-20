package net.alexc.graph;

import java.util.ArrayList;
import java.util.HashMap;

public class ConnectedComponents {
    private final UndirectedGraph graph;
    private final HashMap<Integer, Boolean> visited;
    private final ArrayList<UndirectedGraph> connectedComponents;

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

    private void DFS(int vertex, UndirectedGraph component) {
        visited.put(vertex, true);

        try {
            ArrayList<Integer> neighbours = graph.getNeighbours(vertex);
            for (Integer neighbour : neighbours) {
                component.addEdge(vertex, neighbour);
                if (!visited.containsKey(neighbour)) {
                    DFS(neighbour, component);
                }
            }
        } catch (GraphException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<UndirectedGraph> getConnectedComponents() {
        return connectedComponents;
    }
}
