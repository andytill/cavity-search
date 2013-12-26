package cavitysearch.demo;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;


public class DemoAppView implements Initializable {
    @FXML
    private Parent content;
    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL url, ResourceBundle r) {
        assert searchField != null;
    }
    
    public StringProperty searchTextProperty() {
        return searchField.textProperty();
    }

    public Parent getContent() {
        return content;
    }

    public void setContent(Parent content) {
        this.content = content;
    }
}
