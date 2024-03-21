package AVLTree;

import org.json.simple.JSONObject;

// Node class for AVL tree
public class AVLNode {
    int key;
    JSONObject data;
    int height;
    AVLNode left, right;

    AVLNode(int d, JSONObject obj) {
        key = d;
        data = obj;
        height = 1;
    }
}
