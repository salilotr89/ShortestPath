package com.assessment;

import com.assessment.constant.CoreConstants;
import com.assessment.pojo.Box;
import com.assessment.pojo.Graph;
import com.assessment.util.DijkstraUtil;
import com.assessment.util.VolumeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.assessment.util.CSVUtil.fetchReader;
import static com.assessment.util.CSVUtil.readAllLines;


public class AppTest {

    private static Logger LOGGER = Logger.getLogger(AppTest.class.getName());

    public static List<Path> setup() throws IOException {
        String folderPath = System.getProperty(CoreConstants.TEST_FOLDER_VARIABLE);
        LOGGER.info("Folder for test case:" + folderPath);
        return Files.walk(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .filter(p -> p.getFileName().toString().endsWith(".csv"))
                .collect(Collectors.toList());

    }

    @ParameterizedTest//Used to pass parameter to the test.
    @MethodSource("setup")//Calling the method mentioned to fetch a list and then repeat the test for each index
    public void testShortestPath(Path path) throws Exception {
        LOGGER.info("Executing test for filename: " + path.getFileName());
        generateGraphFromCSV(path);
        LOGGER.info("Execution completed for filename: " + path.getFileName());
    }


    /**
     * Takes a path, read the file and create a graph.
     * @param path CSV path
     * @throws Exception
     */
    private void generateGraphFromCSV(Path path) throws Exception {

        List<String[]> csvContentList = readAllLines(fetchReader(path));
        Graph graph = DijkstraUtil.createGraphFromCSV(csvContentList);
        DijkstraUtil.calculateShortestPathFromSource(graph, graph.getNodes().get(CoreConstants.SOURCE_TAG));

        for (String[] data : csvContentList) {
            assertShortestPath(data, graph);
        }
    }

    /**
     * Takes a graph and data from csv to run assertions
     * @param data data inside csv
     * @param  graph generated from csv
     */
    private void assertShortestPath(String[] data, Graph graph) {
        String name = data[1];
        LOGGER.info("Running assertion for:" + name);

        Box box = new Box(data[2].split(CoreConstants.CSV_MULTIPLY_TOKEN));

        if (data[3].equals(CoreConstants.CSV_INFINITY_TOKEN))
            Assertions.assertEquals(graph.getNodes().get(name).getDistance(), Integer.MAX_VALUE);
        else {
            double volume = VolumeUtil.calculateShippingCost(graph.getNodes().get(name), box);
            Assertions.assertEquals(volume, Double.valueOf(data[3]));
        }

    }

}
