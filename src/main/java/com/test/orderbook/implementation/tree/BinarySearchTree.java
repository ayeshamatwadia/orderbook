package com.test.orderbook.implementation.tree;


import com.test.orderbook.implementation.domain.Order;

/*
* This implementation of a Binary Tree comes from (making changes for my use case):
* https://www.softwaretestinghelp.com/binary-search-tree-in-java/
* */

public class BinarySearchTree {
    public class Node {
        Order order;
        Node left, right;

        public Node(Order data){
            order = data;
            left = right = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "order=" + order +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }
    /*The root node*/
    public Node root;

    /*initialise and empty tree*/
    public BinarySearchTree() {
        root = null;
    }

    /*delete the order from the Binary Tree*/
    public void deleteOrder(Order order) {
        root = delete_Recursive(root, order);
    }

    /*Recursive Delete*/
    public Node delete_Recursive(Node root, Order order)  {
        if (root == null)  return root;

        //traverse the tree
        if (order.compareTo(root.order) < 0)     //traverse left subtree
            root.left = delete_Recursive(root.left, order);
        else if (order.compareTo(root.order) > 0)  //traverse right subtree
            root.right = delete_Recursive(root.right, order);
        else  {
            /*node contains only 1 child*/
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            /*node has 2 children, get inorder successor (min value in the right subtree)*/
            root.order = minValue(root.right);

           /*Delete the in order successor*/
            root.right = delete_Recursive(root.right, root.order);
        }
        return root;
    }

    public Order minValue(Node root)  {
        /*initially minval = root*/
        Order minval = root.order;
        /*find min value*/
        while (root.left != null)  {
            minval = root.left.order;
            root = root.left;
        }
        return minval;
    }

    // insert a node in BST
    public void insert(Order order)  {
        root = insert_Recursive(root, order);
    }

    //recursive insert function
    public Node insert_Recursive(Node root, Order order) {
        //tree is empty
        if (root == null) {
            root = new Node(order);
            return root;
        }
        //traverse the tree
        if (order.compareTo(root.order) < 0)     //insert in the left subtree
            root.left = insert_Recursive(root.left, order);
        else if (order.compareTo(root.order) > 0)    //insert in the right subtree
            root.right = insert_Recursive(root.right, order);
        // return pointer
        return root;
    }

    // method for inorder traversal of BST
    public void inorder() {
        inorder_Recursive(root);
    }

    // recursively traverse the BST
    public void inorder_Recursive(Node root) {
        if (root != null) {
            inorder_Recursive(root.left);
            System.out.print(root.order + " ");
            inorder_Recursive(root.right);
        }
    }

    public boolean search(Order order)  {
        root = search_Recursive(root, order);
        if (root!= null)
            return true;
        else
            return false;
    }

    //recursive insert function
    public Node search_Recursive(Node root, Order order)  {
        // Base Cases: root is null or key is present at root
        if (root==null || root.order.equals(order))
            return root;
        // val is greater than root's key
        if (root.order.compareTo(order) > 0)
            return search_Recursive(root.left, order);
        // val is less than root's key
        return search_Recursive(root.right, order);
    }

    @Override
    public String toString() {
        return "BinarySearchTree{" +
                "root=" + root +
                '}';
    }
}
