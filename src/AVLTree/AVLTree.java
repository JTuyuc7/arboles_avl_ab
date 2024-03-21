package AVLTree;

import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;

// AVL Tree class
public class AVLTree {
    AVLNode root;

    // Get height of a node
    int height(AVLNode N) {
        if (N == null)
            return 0;
//        System.out.println("altura" + N.height);
        return N.height;
    }

    // Get balance factor of a node
    int getBalance(AVLNode N) {
        if (N == null)
            return 0;
        int balance = height(N.left) - height(N.right);
        //! Mostrar que el balance puede ser 1,0 o -1 para que este balanceado
//        System.out.println("balance" + balance);
        return height(N.left) - height(N.right);
    }

    // Right rotate subtree rooted with y
    AVLNode rightRotate(AVLNode y) {
//        System.out.printf("Y.left--" +  height(y.right));
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotate subtree rooted with x
    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Insert a node
    AVLNode insert(AVLNode node, int key, JSONObject obj) {
        if (node == null)
            return (new AVLNode(key, obj));

        if (key < node.key)
            node.left = insert(node.left, key, obj);
        else if (key > node.key)
            node.right = insert(node.right, key, obj);
        else // Duplicate keys not allowed
            return node;

        node.height = 1 + Math.max(height(node.left),
                height(node.right));

        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Inorder traversal of the tree
    void inorder(AVLNode node, BufferedWriter writer) throws IOException {
        if (node != null) {
            inorder(node.left, writer);
            writer.write(node.data.toJSONString() + "\n");
            inorder(node.right, writer);
        }
    }

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
