package net.alexc.graph;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class DijkstraMinimumDistanceTest {
    private DirectedWeightedGraph graph;
    private DijkstraMinimumDistance dij;

    @Test
    public void testDijkstraBig() {
        try {
            graph = new DirectedWeightedGraph("src/input/bigDijkstra.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        dij = new DijkstraMinimumDistance(graph);
        assertEquals(dij.findMinimumDistance(1, 2).getMinimumDistance(), 2196);
        assertEquals(dij.findMinimumDistance(1, 3).getMinimumDistance(), 2279);
    }
}