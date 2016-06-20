package net.alexc.graph;

import org.junit.*;

import static org.junit.Assert.*;

public class DirectedGraphTest {
    @Test
    public void testDirectedGraph() throws GraphException {
        DirectedGraph graph = new DirectedGraph();

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);

        assertEquals(graph.getVertices(), 4);
        assertEquals(graph.getEdges(), 3);
        assertFalse(graph.isEdge(2, 1));
        assertTrue(graph.isEdge(1, 2));
        assertTrue(graph.isVertex(1));
        assertFalse(graph.isVertex(4));

        assertEquals(graph.outDegree(1), 2);
        assertEquals(graph.inDegree(2), 1);

        graph.removeEdge(1, 2);

        assertFalse(graph.isEdge(1, 2));
        assertEquals(graph.inDegree(2), 0);

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