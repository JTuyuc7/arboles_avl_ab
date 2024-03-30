package BinaryTree;
import org.json.simple.JSONObject;
public class TreeNode {
    String key; // The key of the node
    JSONObject data; // The data associated with the key
    TreeNode left, right; // References to the left and right child nodes

    /**
     * Constructor to initialize a TreeNode with a key and associated JSON object.
     * @param key The key of the node.
     * @param obj The JSON object associated with the key.
     */
    TreeNode(String key, JSONObject obj) {
        this.key = key;
        data = obj;
        left = null;
        right = null;
    }
}
