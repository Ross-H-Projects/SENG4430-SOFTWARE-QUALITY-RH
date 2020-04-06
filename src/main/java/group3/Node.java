package group3;

import java.util.HashMap;

public class Node {
    private Node ancestor;
    private HashMap<String, Node> children;
    private String className;

    Node (String className) {
        this.className = className;
        this.children = new HashMap<String, Node>();
    }

    public String getClassName () {
        return this.className;
    }

    public Node getAncestor() { return this.ancestor; }

    public HashMap<String, Node> getChildren() {
        return this.children;
    }

    public void setAncestor (Node nodeAncestor) {
        this.ancestor = nodeAncestor;
    }

    public void addChild (Node child) {
        children.put(child.getClassName(), child);
    }
}
