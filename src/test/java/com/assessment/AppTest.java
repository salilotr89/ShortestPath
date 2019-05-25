package com.assessment;

import com.assessment.constant.CoreConstants;
import com.assessment.pojo.Box;
import com.assessment.pojo.Graph;
import com.assessment.util.DijkstraUtil;
import com.assessment.util.VolumeUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.assessment.util.CSVUtil.fetchReader;
import static com.assessment.util.CSVUtil.readAllLines;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static List<Path> listOfFiles = new ArrayList<>();

    @BeforeAll
    public static void setup() {
        try {
            listOfFiles = Files.walk(Paths.get(CoreConstants.TEST_FOLDER_PATH))
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".csv"))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShortestPath() throws Exception {

        for (Path path : listOfFiles) {
            generateGraphFromCSV(path);
        }

    }


    private Graph generateGraphFromCSV(Path path) throws Exception {

        List<String[]> csvContentList = readAllLines(fetchReader(path));
        Graph graph = DijkstraUtil.createGraphFromCSV(csvContentList);
        DijkstraUtil.calculateShortestPathFromSource(graph, graph.getNodes().get(CoreConstants.SOURCE_TAG));

        for (String[] data : csvContentList) {
            assertShortestPath(data, graph);
        }
        return null;
    }

    private void assertShortestPath(String[] data, Graph graph) {
        String name = data[1];
        Box box = new Box(data[2].split(CoreConstants.CSV_MULTIPLY_TOKEN));

        if (data[3].equals(CoreConstants.CSV_INFINITY_TOKEN))
            assertEquals(graph.getNodes().get(name).getDistance(), Integer.MAX_VALUE);
        else {
            double volume = VolumeUtil.calculateShippingCost(graph.getNodes().get(name), box);
            assertEquals(volume, Double.valueOf(data[3]));
        }

    }

}
