package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("SettingWindow.fxml"));
        primaryStage.setTitle("Rainbow Six Siege Game Settings");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/resources/R6MenuLogo.png"));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
