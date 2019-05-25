package com.assessment.util;

import com.opencsv.CSVReader;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    public static List<String[]> readAllLines(Reader reader) throws Exception {
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list;
        list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list;
    }

    public static Reader fetchReader(Path path) throws Exception {
        Reader reader = Files.newBufferedReader(path);
        return reader;
    }

}
