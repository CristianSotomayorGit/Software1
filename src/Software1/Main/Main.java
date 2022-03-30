package Software1.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Cristian Sotomayor
 * Software1.Software1.Main.Main class
 * See JavaDOCS in *\Software1Project-master-master\JavaDOC
 * FUTURE ENHANCEMENT The ability of adding user comments/descriptions to the products and/or products.
 */
public class Main extends Application {
    /**
     * Software1.Software1.Main.Main method
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Software1/View/mainform.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("Hello!");
        mainStage.setScene(scene);
        mainStage.show();

    }

}
