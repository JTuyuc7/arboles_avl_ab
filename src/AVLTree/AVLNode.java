package AVLTree;

import org.json.simple.JSONObject;

/**
 * Node class for the AVL tree.
 */
public class AVLNode {
    int key; // The key of the node
    JSONObject data; // The data associated with the key
    int height; // The height of the node in the AVL tree
    AVLNode left, right; // References to the left and right child nodes

    /**
     * Constructor to initialize an AVLNode with a key and associated JSON object.
     * @param d The key of the node.
     * @param obj The JSON object associated with the key.
     */
    AVLNode(int d, JSONObject obj) {
        key = d;
        data = obj;
        height = 1; // Initially, the height of the node is set to 1
    }
}
