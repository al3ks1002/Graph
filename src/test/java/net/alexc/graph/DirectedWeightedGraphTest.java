package net.alexc.graph;

import org.junit.Test;

import static org.junit.Assert.*;

public class DirectedWeightedGraphTest {
    @Test
    public void testDirectedWeightedGraph() throws GraphException {
        DirectedWeightedGraph graph = new DirectedWeightedGraph();

        graph.addEdge(0, 1, 10);
        graph.addEdge(1, 2, 20);
        graph.addEdge(1, 3, 30);

        graph.removeEdge(1, 2);
        assertFalse(graph.isEdge(1, 2));
        assertTrue(graph.isEdge(1, 3));

        graph.addEdge(1, 2, 20);
        graph.setCost(0, 1, 100);
        assertEquals(graph.getCost(0, 1), Integer.valueOf(100));
        assertEquals(graph.getCost(1, 2), Integer.valueOf(20));

        graph.removeVertex(1);
        try {
            assertEquals(graph.getCost(0, 1), Integer.valueOf(0));
            assertFalse(true);
        } catch (GraphException e) {
            // we're gonna catch it
        }
        try {
            assertEquals(graph.getCost(1, 2), Integer.valueOf(0));
            assertFalse(true);
        } catch (GraphException e) {
            // we're gonna catch it
        }
    }
}