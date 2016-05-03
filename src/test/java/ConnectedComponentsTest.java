import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ConnectedComponentsTest {
    private UndirectedGraph graph;
    private ConnectedComponents cc;

    @Test
    public void testConnectedComponentsBig() {
        try {
            graph = new UndirectedGraph("src/input/bigCC.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        cc = new ConnectedComponents(graph);
        ArrayList<UndirectedGraph> ans = cc.getConnectedComponents();
        assertEquals(ans.size(), 97500);
    }

    @Test
    public void testConnectedComponentsSmall() throws GraphException {
        graph = mock(UndirectedGraph.class);
        when(graph.getAllVertices()).thenReturn(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6)));
        when(graph.getNeighbours(1)).thenReturn(new ArrayList<Integer>(Arrays.asList(2, 4)));
        when(graph.getNeighbours(2)).thenReturn(new ArrayList<Integer>(Arrays.asList(1)));
        when(graph.getNeighbours(4)).thenReturn(new ArrayList<Integer>(Arrays.asList(1)));
        when(graph.getNeighbours(3)).thenReturn(new ArrayList<Integer>(Arrays.asList(5)));
        when(graph.getNeighbours(5)).thenReturn(new ArrayList<Integer>(Arrays.asList(3)));
        doThrow(new GraphException()).when(graph).getNeighbours(6);

        cc = new ConnectedComponents(graph);
        ArrayList<UndirectedGraph> ans = cc.getConnectedComponents();
        assertEquals(ans.size(), 3);
    }
}