package com.example.tictacpuig.controller;

import com.example.tictacpuig.TicTacPuigApplication;
import com.example.tictacpuig.model.Clasificacion;
import com.example.tictacpuig.model.OpenCSV;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.layout.GridPane.*;

public class ClasificacionController implements Initializable {
    @FXML
    GridPane clasificacionesGrid;
    protected static HashMap<String, Clasificacion> clasificaciones = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridConstructor();
    }

    /**
     * Este metodo genera el tablero (grid) de clasificaciones cogiendo la lista
     * donde se guardan los datos de clasificaciones y usando sus valores
     * en el orden indicado
     */
    @FXML
    public void gridConstructor() {
        int rows = 1;
        for (Clasificacion clasificacion : clasificaciones.values()) {
            Label name = new Label(clasificacion.getPlayer());
            Label wins = new Label(clasificacion.getWins() + "");
            Label loses = new Label(clasificacion.getLoses() + "");
            Label tied = new Label(clasificacion.getDraws() + "");

            clasificacionesGrid.add(name, 0, rows);
            clasificacionesGrid.add(wins, 1, rows);
            clasificacionesGrid.add(loses, 2, rows);
            clasificacionesGrid.add(tied, 3, rows);

            setHalignment(name, HPos.CENTER);
            setHalignment(wins, HPos.CENTER);
            setHalignment(loses, HPos.CENTER);
            setHalignment(tied, HPos.CENTER);
            rows++;
        }
    }


    /**
     * Lee las clasificaciones desde el csv
     */
    protected static void leerClasificaciones() throws IOException {
        List<String[]> listStatsReaded;

        listStatsReaded = OpenCSV.leerCSV("src/main/resources/data/clasificacion.csv");
        listStatsReaded.forEach(strings -> clasificaciones.put(strings[0], new Clasificacion(strings[0], Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]))));

    }

    /**
     * Boton para ir hacia atras, desde la escena de clasificaciones hacia la inicial
     */
    @FXML
    protected void botonAtras() {
        try {
            TicTacPuigApplication.cambiarEscena("main-app.fxml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
