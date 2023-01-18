package com.example.tictacpuig.model;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class OpenCSV {

    /**
     * Con este metodo recibo la información de las clasificaciones del csv
     */
    public static List<String[]> leerCSV(@NotNull String path) throws IOException {
        Reader reader = Files.newBufferedReader(Path.of(path));
        try (CSVReader csvReader = new CSVReader(reader)) {
            return csvReader.readAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Con este metodo escribo nueva información de las clasificaciones en el csv
     */
    public static void escribirCSV(ArrayList<String[]> list, @NotNull String path) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(path));
        writer.writeAll(list);
        writer.close();
    }
}
