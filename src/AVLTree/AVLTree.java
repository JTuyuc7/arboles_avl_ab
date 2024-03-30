package AVLTree;

import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * AVL Tree class for storing key-value pairs in a balanced binary search tree.
 */
public class AVLTree {
    AVLNode root;

    /**
     * Method to get the height of a node.
     *
     * @param N The node whose height is to be determined.
     * @return The height of the node. Returns 0 if the node is null.
     */
    int height(AVLNode N) {
        if (N == null)
            return 0;
        return N.height;
    }

    /**
     * Method to get the balance factor of a node.
     *
     * @param N The node for which balance factor is to be calculated.
     * @return The balance factor of the node. Returns 0 if the node is null.
     */
    int getBalance(AVLNode N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    /**
     * Right rotate subtree rooted with y.
     *
     * @param y The root of the subtree to be rotated.
     * @return The new root after rotation.
     */
    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    /**
     * Left rotate subtree rooted with x.
     *
     * @param x The root of the subtree to be rotated.
     * @return The new root after rotation.
     */
    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    /**
     * Method to insert a node into the AVL Tree.
     *
     * @param node The root of the subtree where insertion is to be done.
     * @param key  The key of the node to be inserted.
     * @param obj  The JSON object associated with the key to be inserted.
     * @return The root of the modified subtree after insertion.
     */
    AVLNode insert(AVLNode node, String key, JSONObject obj) {
        if (node == null)
            return (new AVLNode(key, obj));

        int compareResult = key.compareTo(node.key); // Compare keys as strings
        if (compareResult < 0)
            node.left = insert(node.left, key, obj);
        else if (compareResult > 0)
            node.right = insert(node.right, key, obj);
        else // Duplicate keys not allowed
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && key.compareTo(node.left.key) < 0)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key.compareTo(node.right.key) > 0)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Inorder traversal of the AVL Tree.
     *
     * @param node   The root of the subtree to be traversed.
     * @param writer The BufferedWriter object to write the traversal results.
     * @throws IOException If an I/O error occurs while writing.
     */
    void inorder(AVLNode node, BufferedWriter writer) throws IOException {
        if (node != null) {
            inorder(node.left, writer);
            writer.write(node.data.toJSONString() + "\n");
            inorder(node.right, writer);
        }
    }

    /**
     * Method to write JSON objects in the AVL Tree to a BufferedWriter in a sorted order.
     *
     * @param node   The root of the subtree to be traversed.
     * @param writer The BufferedWriter object to write the JSON objects.
     * @throws IOException If an I/O error occurs while writing.
     */
    void writeJSON(AVLNode node, BufferedWriter writer) throws IOException {
        if (node != null) {
            writeJSON(node.left, writer);
            writer.write(node.data.toJSONString());
            if (node.right != null || node != root) {
                writer.write(",");
            }
            writer.write("\n");
            writeJSON(node.right, writer);
        }
    }
}
