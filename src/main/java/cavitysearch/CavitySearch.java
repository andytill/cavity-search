package cavitysearch;

import java.util.ArrayList;
import java.util.List;

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

    private final ObservableList<Node> overlays;
    
    private final List<AutoCloseable> closables;
    
    private final SimpleStringProperty searchString;

    private Parent root;

    public CavitySearch() {
        overlays = FXCollections.observableArrayList();
        
        closables = new ArrayList<AutoCloseable>();

        searchString = new SimpleStringProperty();
        searchString.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                search();
            }});
    }
    
    public void search() {
        clearOverlays();

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
                    
                    final ChangeListener<Bounds> boundsListener = new ChangeListener<Bounds>() {
                        @Override
                        public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds bounds) {
                            setRectBounds(r, text.localToScene(text.getBoundsInLocal()));
                        }};

                    setRectBounds(r, text.localToScene(text.getBoundsInLocal()));
                        
                    text.boundsInLocalProperty().addListener(boundsListener);

                    overlays.add(r);
                    
                    closables.add(new AutoCloseable() {
                        @Override
                        public void close() throws Exception {
                            text.boundsInLocalProperty().removeListener(boundsListener);
                        }});
                }
                
                return null;
            }});
    }

    private void clearOverlays() {
        overlays.clear();
        
        for (AutoCloseable c : closables) {
            try {
                c.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isMatch(Text text) {
        return text.getText().contains(searchString.get());
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
