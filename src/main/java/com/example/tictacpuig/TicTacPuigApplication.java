package com.example.tictacpuig;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class TicTacPuigApplication extends Application {

    public static Stage mainStage;

    /**
     * Con este metodo iniciamos la aplicación con la escena principal sacada del main-app.fxml
     */

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacPuigApplication.class.getResource("main-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(TicTacPuigApplication.class.getResource("style.css")).toExternalForm());
        System.out.println(Objects.requireNonNull(TicTacPuigApplication.class.getResource("style.css")).toExternalForm());
        mainStage.setTitle("Tic Tac Puig");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Con este metodo haremos los cambios de escenas
     */

    public static void cambiarEscena(String fxml) throws Exception {
        Parent page = FXMLLoader.load(Objects.requireNonNull(TicTacPuigApplication.class.getResource(fxml)), null, new JavaFXBuilderFactory());
        Scene scene = TicTacPuigApplication.mainStage.getScene();
        if (scene == null) {
            scene = new Scene(page);
            TicTacPuigApplication.mainStage.setScene(scene);
        } else {
            TicTacPuigApplication.mainStage.getScene().setRoot(page);
        }
        TicTacPuigApplication.mainStage.sizeToScene();

    }
}