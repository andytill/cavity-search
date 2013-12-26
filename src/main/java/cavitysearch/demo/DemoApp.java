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
        
        CavitySearch cavitySearch = new CavitySearch();
        
        // all given nodes will be searched including the search text field if 
        // you give the actual scene root
        cavitySearch.setRoot(controller.getContent());
        cavitySearch.searchStringProperty().bindBidirectional(controller.searchTextProperty());
        
        Group overlayGroup = new Group();
        
        // if the group has a "managed" layout, the overlay nodes will be given
        // the wrong coordinates
        overlayGroup.setManaged(false);
        
        // by binding the groups children to the search overlays, the overlay nodes
        // will be automatically added to the scene
        bindContentBidirectional(overlayGroup.getChildren(), cavitySearch.getOverlays());
        
        StackPane stack = new StackPane(root, overlayGroup);
        
        Scene scene;
        
        scene = new Scene(stack);
        stage.setTitle("Cavity Search Demo");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
