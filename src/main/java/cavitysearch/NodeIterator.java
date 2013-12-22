package cavitysearch;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.util.Callback;

class NodeIterator {
    public static void iterate(final Node rootNode, Callback<Node, Void> nodeCallback) {        
        nodeCallback.call(rootNode);
        
        if(rootNode instanceof Parent) {
            for (Node n : ((Parent) rootNode).getChildrenUnmodifiable()) {
                iterate(n, nodeCallback);
            }
        }
        
  /*      if(rootNode instanceof Control) {
            Skin<?> skin = ((Control) rootNode).getSkin();
            
            if(skin != null && skin instanceof SkinBase) {
                for (Node n : skinChildren(skin)) {
                    iterate(n, nodeCallback);
                }
            }
        }*/
    }

/*    @SuppressWarnings("unchecked")
    private static ObservableList<Node> skinChildren(Skin<?> skin) {
        return ((SkinBase<Control>)skin).getChildren();
    }*/
}
