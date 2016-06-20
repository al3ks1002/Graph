package net.alexc.graph;

import org.junit.Test;

import static org.junit.Assert.*;

public class UndirectedGraphTest {
    @Test
    public void testUndirectedGraph() {
        UndirectedGraph graph = new UndirectedGraph();

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);

        assertEquals(graph.getVertices(), 4);
        assertEquals(graph.getEdges(), 3);
        assertTrue(graph.isEdge(2, 1));
        assertTrue(graph.isEdge(1, 2));
        assertFalse(graph.isEdge(3, 2));
        assertTrue(graph.isVertex(1));
        assertFalse(graph.isVertex(4));
        try {
            assertEquals(graph.degree(1), 3);
        } catch (GraphException e) {
            // we're gonna catch it
        }
        try {
            assertEquals(graph.degree(2), 1);
        } catch (GraphException e) {
            // we're gonna catch it
        }

        graph.removeEdge(1, 2);
        assertFalse(graph.isEdge(1, 2));
        try {
            assertEquals(graph.degree(2), 0);
        } catch (GraphException e) {
            // we're gonna catch it
        }

        graph.addEdge(1, 2);
        graph.removeVertex(1);
        assertEquals(graph.getEdges(), 0);
        assertEquals(graph.getVertices(), 3);
        assertFalse(graph.isEdge(1, 2));
        assertFalse(graph.isEdge(1, 3));
        assertFalse(graph.isEdge(0, 1));
        assertFalse(graph.isVertex(1));
    }
}