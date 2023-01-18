package com.example.tictacpuig.controller;

import com.example.tictacpuig.TicTacPuigApplication;
import com.example.tictacpuig.model.OpenCSV;
import com.example.tictacpuig.model.Clasificacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.example.tictacpuig.controller.ClasificacionController.clasificaciones;

public class MainController implements Initializable {
    String turn = "";
    String winnerString;
    boolean winner = false;
    int Computer = 0;
    boolean bComputer;
    String[][] tableroString;
    @FXML
    GridPane tablero;
    @FXML
    Button btnStart;
    @FXML
    Text turnString;
    @FXML
    ToggleGroup modoDeJuego;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ClasificacionController.leerClasificaciones();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Con este metodo al pulsar el boton start
     * iniciaremos el juego depende el modo que hayamos escogido
     */

    @FXML
    protected void onClickStartBtn() {
        //Reads the stats file to have it updated
        RadioButton radioButton = (RadioButton) modoDeJuego.getSelectedToggle();
        Computer = 0;
        bComputer = true;

        if (radioButton == null) {
            turnString.setText("Escoge un modo!");
        } else {
            restartTablero("primer");

            tableroString = new String[tablero.getColumnCount()][tablero.getRowCount()];
            turn = "X";
            btnStart.setDisable(true);
            turnString.setText("Empieza el jugador " + turn);
            switch (radioButton.getId()) {
                case "compvscomp" -> {
                    Computer = 2;
                    turnComputer();
                }
                case "plavscomp" -> Computer = 1;
            }
        }
    }

    /**
     * Cuando se de clic en una celda se ejecutara este codigo,
     * se comprobará si hay un ganador y si no lo hay le dara paso al siguiente jugador
     */
    @FXML
    protected void onClickCell(@NotNull ActionEvent event) {
        Button btn = (Button) event.getSource();
        btn.setText(turn);
        btn.setDisable(true);
        updateTablero();
        winnerString = comprobarVictoria();
        if (winnerString != null) {
            winStage(winnerString);
            turnString.setText("Empezar nueva partida");
        } else cambiarPlayer();
    }

    /**
     * Al clicar en about aparece información sobre la aplicación
     */
    @FXML
    protected void onClickAboutBtnMenu() {
        Stage stage = new Stage();
        Label label = new Label("""
                Tic Tac Puig\s
                by Eric Fernández Luna""");
        label.setTextAlignment(TextAlignment.CENTER);
        VBox vBox = new VBox(label);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20, 40, 20, 40));
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setTitle("About");
        stage.show();
    }

    /**
     * Cambia la escena a la de clasificaciones
     */
    @FXML
    protected void onClickClasificacionesBtn() {
        try {
            TicTacPuigApplication.cambiarEscena("clasificaciones.fxml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Cierra la aplicación
     */
    @FXML
    private void onClickCloseBtn(){
        TicTacPuigApplication.mainStage.close();
    }

    /**
     * Vacia las celdas para jugar una nueva partida
     */
    protected void restartTablero(String from) {
        for (Node node : tablero.getChildren()) {
            Button btnCell = (Button) node;
            if (from.equals("primer")) {
                btnCell.setDisable(false);
                btnCell.setText("");
            } else {
                btnCell.setDisable(true);
                btnStart.setDisable(false);
            }
        }
    }

    /**
     * Cambia el tablero cada vez que alguien hace algun movimiento
     */
    protected void updateTablero() {
        List<Node> nodes = tablero.getChildren();
        int celda = 0;
        for (int i = 0; i < tablero.getColumnCount(); i++) {
            for (int j = 0; j < tablero.getRowCount(); j++) {
                Button button = (Button) nodes.get(celda);
                tableroString[i][j] = button.getText();
                celda++;
            }
        }
    }

    /**
     * Metodo para detectar si le toca a un jugador o a la máquina
     */
    protected void cambiarPlayer() {
        if (turn.equals("X")) turn = "O";
        else turn = "X";
        turnString.setText("Le toca al jugador " + turn);

        if (Computer == 1) {
            if (bComputer) {
                turnComputer();
            } else {
                bComputer = true;
            }
        } else if (Computer == 2) {
            turnComputer();
        }
    }

    /**
     * La máquina elige casillas aleatorias y si estan vacias haze clic en ellas
     */
    protected void turnComputer() {
        //Randomize the position
        List<Node> nodes = tablero.getChildren();
        boolean bClick;
        int randomCelda;
        bComputer = false;

        do {
            bClick = false;
            randomCelda = (int) (Math.random() * 9);
            Button celda = (Button) nodes.get(randomCelda);
            if (!celda.isDisabled()) {
                celda.fire();
            } else {
                bClick = true;
            }

        } while (bClick);


    }

    /**
     * Detecta si hay tres en raya
     */
    protected String comprobarVictoria() {
        String player;


        // Verticales
        for (int j = 0; j < tablero.getRowCount(); j++) {
            winner = true;
            player = tableroString[0][j];

            if (!player.equals("")) {
                for (int i = 1; i < tablero.getColumnCount(); i++) {
                    if (!player.equals(tableroString[i][j])) {
                        winner = false;
                    }
                }
                if (winner) {
                    return player;
                }
            }
        }

        // Horizontales
        for (int i = 0; i < tablero.getColumnCount(); i++) {
            winner = true;
            player = tableroString[i][0];

            if (!player.equals("")) {
                for (int j = 1; j < tablero.getRowCount(); j++) {
                    if (!player.equals(tableroString[i][j])) {
                        winner = false;
                    }
                }
                if (winner) {
                    return player;
                }
            }
        }


        // Diagonales
        if ((Objects.equals(tableroString[0][0], tableroString[1][1]) && Objects.equals(tableroString[1][1], tableroString[2][2])) && !tableroString[0][0].equals("")) {
            return tableroString[0][0];
        } else if ((Objects.equals(tableroString[0][2], tableroString[1][1]) && Objects.equals(tableroString[1][1], tableroString[2][0])) && !tableroString[0][2].equals("")) {
            return tableroString[0][2];
        }

        int blanks = 0;
        for (String[] s : tableroString) {
            if (!Arrays.stream(s).toList().contains("")) blanks++;
        }

        if (blanks == 3) return "noBlanks";
        return null;

    }

    /**
     * Si un jugador gana se ejecuta esta escena para pasar los datos al metodo submitStats
     */
    protected void winStage(String player) {
        Insets indvPadding = new Insets(10, 10, 10, 10);
        Stage winStage = new Stage();

        Text winnerText = new Text();
        HBox hBox = new HBox(winnerText);
        hBox.setPadding(indvPadding);
        hBox.setSpacing(35);
        hBox.setAlignment(Pos.CENTER);

        Label playerXLabel = new Label("Player X name:");
        TextField playerXName = new TextField();
        playerXName.setPrefHeight(10);
        playerXName.setPrefWidth(145);
        HBox hBox1 = new HBox(playerXLabel, playerXName);
        hBox1.setPadding(indvPadding);
        hBox1.setPrefHeight(30);
        hBox1.setSpacing(10);
        hBox1.setAlignment(Pos.CENTER);

        Label playerOLabel = new Label("Player O name:");
        TextField playerOName = new TextField();
        playerOName.setPrefHeight(15);
        playerOName.setPrefWidth(145);
        HBox hBox2 = new HBox(playerOLabel, playerOName);
        hBox2.setPadding(indvPadding);
        hBox2.setSpacing(10);
        hBox2.setAlignment(Pos.CENTER);

        Button submitBtn = new Button("Submit");
        submitBtn.setPrefWidth(60);
        submitBtn.onActionProperty().set(e -> {
            if (player.equals("X")) {
                submitStats(playerXName.getText().trim(), "win");
                submitStats(playerOName.getText().trim(), "lose");
            } else if (player.equals("O")) {
                submitStats(playerXName.getText().trim(), "lose");
                submitStats(playerOName.getText().trim(), "win");
            } else {
                submitStats(playerXName.getText().trim(), "draw");
                submitStats(playerOName.getText().trim(), "draw");
            }
            winStage.close();
        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.onActionProperty().set(e -> winStage.close());
        cancelBtn.setPrefWidth(60);
        HBox buttonsBox = new HBox(submitBtn, cancelBtn);
        buttonsBox.setSpacing(10);
        buttonsBox.setPadding(indvPadding);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        if (Computer >= 1) {
            playerOName.setDisable(true);
            if (Computer == 2) {
                playerXName.setDisable(true);
                submitBtn.setDisable(true);
            }
        }

        VBox vBox = new VBox(hBox, hBox1, hBox2, buttonsBox);
        Scene scene = new Scene(vBox);
        winStage.setTitle("Game ended");
        winStage.setScene(scene);
        winStage.show();

        if (player.equals("noBlanks")) {
            winnerText.setText("Draw");
        } else {
            winnerText.setText("The player " + player + " win!");
        }


        restartTablero("none");
    }

    /**
     * Se envian los datos al csv
     */
    @FXML
    protected void submitStats(String playerName, String stat) {
        if (!Objects.equals(playerName, "")) {
            if (clasificaciones.get(playerName) != null) {
                switch (stat) {
                    case "win" -> clasificaciones.get(playerName).plusWin();
                    case "lose" -> clasificaciones.get(playerName).plusLoses();
                    case "draw" -> clasificaciones.get(playerName).plusDraws();
                }
            } else {
                switch (stat) {
                    case "win" -> clasificaciones.put(playerName, new Clasificacion(playerName, 1, 0, 0));
                    case "lose" -> clasificaciones.put(playerName, new Clasificacion(playerName, 0, 1, 0));
                    case "draw" -> clasificaciones.put(playerName, new Clasificacion(playerName, 0, 0, 1));
                }
            }
            ArrayList<String[]> list = new ArrayList<>();
            for (Clasificacion clasificacion : clasificaciones.values()) {
                String[] statsPlayer = new String[4];
                statsPlayer[0] = clasificacion.getPlayer();
                statsPlayer[1] = clasificacion.getWins() + "";
                statsPlayer[2] = clasificacion.getLoses() + "";
                statsPlayer[3] = clasificacion.getDraws() + "";
                list.add(statsPlayer);
            }
            try {
                OpenCSV.escribirCSV(list, "src/main/resources/data/clasificacion.csv");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
