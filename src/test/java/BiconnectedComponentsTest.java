import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BiconnectedComponentsTest {
    private UndirectedGraph graph;
    private BiconnectedComponents bcc;

    @Test
    public void testBiconnectedComponentsBig() throws GraphException {
        try {
            graph = new UndirectedGraph("src/input/bigBCC.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bcc = new BiconnectedComponents(graph);
        ArrayList<ArrayList<Integer>> ans = bcc.getBiconnectedComponents();
        assertEquals(ans.size(), 3611);
    }
}