package net.alexc.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

public class StronglyConnectedComponents {
    private int currentIndex;
    private final DirectedGraph graph;
    private final HashMap<Integer, Integer> lowLink;
    private final HashMap<Integer, Integer> index;
    private final HashMap<Integer, Boolean> inStack;
    private final ArrayList<ArrayList<Integer>> stronglyConnectedComponents;

    public StronglyConnectedComponents(DirectedGraph graph) throws GraphException {
        this.graph = graph;

        currentIndex = 0;
        lowLink = new HashMap<>();
        index = new HashMap<>();
        inStack = new HashMap<>();
        stronglyConnectedComponents = new ArrayList<>();

        ArrayList<Integer> vertices = graph.getAllVertices();
        for (Integer vertex : vertices) {
            if (!index.containsKey(vertex)) {
                Stack<Integer> currentStack = new Stack<>();
                DFS(vertex, currentStack);
            }
        }
    }

    private void DFS(int vertex, Stack<Integer> stack) throws GraphException {
        index.put(vertex, ++currentIndex);
        inStack.put(vertex, true);
        lowLink.put(vertex, index.get(vertex));
        stack.push(vertex);

        ArrayList<Integer> neighbours = graph.iterableOut(vertex);

        if (neighbours.size() == 0 && graph.iterableIn(vertex).size() == 0) {
            ArrayList<Integer> newComponent = new ArrayList<>();
            newComponent.add(vertex);
            stronglyConnectedComponents.add(newComponent);
            return;
        }

        for (Integer son : neighbours) {
            if (!index.containsKey(son)) {
                DFS(son, stack);

                if (lowLink.get(son) < lowLink.get(vertex)) {
                    lowLink.put(vertex, lowLink.get(son));
                }
            } else if (inStack.containsKey(son) && inStack.get(son)) {
                if (index.get(son) < lowLink.get(vertex)) {
                    lowLink.put(vertex, index.get(son));
                }
            }
        }

        if (Objects.equals(lowLink.get(vertex), index.get(vertex))) {
            ArrayList<Integer> newComponent = new ArrayList<>();

            while (!stack.peek().equals(vertex)) {
                int v = stack.pop();
                newComponent.add(v);
                inStack.put(v, false);
            }

            newComponent.add(stack.pop());
            inStack.put(vertex, false);
            stronglyConnectedComponents.add(newComponent);
        }
    }

    ArrayList<ArrayList<Integer>> getStronglyConnectedComponents() {
        return stronglyConnectedComponents;
    }
}
