import org.junit.Test;

import static org.junit.Assert.*;

public class DirectedWeightedGraphTest {
    private DirectedWeightedGraph graph;

    @Test
    public void testDirectedWeightedGraph() {
        graph = new DirectedWeightedGraph();
        graph.addEdge(0, 1, 10);
        graph.addEdge(1, 2, 20);
        graph.addEdge(1, 3, 30);

        graph.removeEdge(1, 2);
        assertFalse(graph.isEdge(1, 2));
        assertTrue(graph.isEdge(1, 3));

        graph.addEdge(1, 2, 20);
        try{
            graph.setCost(0, 1, 100);
        } catch (GraphException e) {}
        try{
            assertEquals(graph.getCost(0, 1), Integer.valueOf(100));
        } catch (GraphException e) {}
        try{
            assertEquals(graph.getCost(1, 2), Integer.valueOf(20));
        } catch (GraphException e) {}

        graph.removeVertex(1);
        try{
            assertEquals(graph.getCost(0, 1), Integer.valueOf(0));
        } catch (GraphException e) {}
        try{
            assertEquals(graph.getCost(1, 2), Integer.valueOf(0));
        } catch (GraphException e) {}
    }
}