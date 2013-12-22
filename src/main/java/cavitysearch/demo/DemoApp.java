package cavitysearch.demo;

import static javafx.beans.binding.Bindings.bindContentBidirectional;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import cavitysearch.CavitySearch;

public class DemoApp extends javafx.application.Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL resource = getClass().getResource("/cavitysearch/demo/cavity-search-app.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent root = (Parent) fxmlLoader.load();
        DemoAppView controller = fxmlLoader.getController();
        
        CavitySearch cavitySearch;
        
        cavitySearch = new CavitySearch();
        cavitySearch.setRoot(controller.getContent());
        cavitySearch.searchStringProperty().bindBidirectional(controller.searchTextProperty());
        
        Group overlayGroup;
        
        overlayGroup = new Group();
        overlayGroup.setManaged(false);
        bindContentBidirectional(overlayGroup.getChildren(), cavitySearch.getOverlays());
        
        StackPane stack;
        
        stack = new StackPane(root, overlayGroup);
        
        Scene scene;
        
        scene = new Scene(stack);
        stage.setTitle("Flow List View Demo");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
