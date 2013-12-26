package cavitysearch;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class CavitySearch {
    
    private final SimpleStringProperty searchString;

    private final ObservableList<Node> overlays;

    private Parent root;

    public CavitySearch() {
        overlays = FXCollections.observableArrayList();

        searchString = new SimpleStringProperty();
        searchString.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                search();
            }});
    }
    
    public void search() {
        overlays.clear();

        final String search = searchString.get();
        
        if("".equals(search))
            return;
        
        NodeIterator.iterate(root, new Callback<Node, Void>() {
            @Override
            public Void call(Node n) {
                
                if(!(n instanceof Text)) 
                    return null;
                
                final Text text = ((Text) n);
                
                if(isMatch(text)) {
                    final Rectangle r = provideOverlayNode();
                    setRectBounds(r, text.localToScene(text.getBoundsInLocal()));
                    
                    text.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {
                        @Override
                        public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds bounds) {
                            setRectBounds(r, text.localToScene(text.getBoundsInLocal()));
                        }});

                    overlays.add(r);
                }
                
                return null;
            }

            private boolean isMatch(final Text text) {
                return text.getText().contains(searchString.get());
            }});
    }

    private Rectangle provideOverlayNode() {
        final Rectangle r;
        
        r = new Rectangle();
        r.setFill(Color.YELLOW);
        r.setOpacity(0.35d);
        
        // make sure the overlays do not consume mouse events!
        r.setMouseTransparent(true);
        
        return r;
    }

    private void setRectBounds(final Rectangle r, Bounds bounds) {
        r.setLayoutX(bounds.getMinX());
        r.setLayoutY(bounds.getMinY());
        r.setWidth(bounds.getWidth());
        r.setHeight(bounds.getHeight());
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public SimpleStringProperty searchStringProperty() {
        return searchString;
    }
    
    public ObservableList<Node> getOverlays() {
        return overlays;
    }
}
