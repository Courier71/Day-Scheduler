// --== CS400 File Header Information ==--
// Name: Samuel Erickson
// Email: swerickson2@wisc.edu
// Team: LF
// Role: Front End Developer
// TA: Divyanshu
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.LinkedList;

/**
 * Binary Search Tree implementation with a Node inner class for representing
 * the nodes within a binary search tree.  You can use this class' insert
 * method to build a binary search tree, and its toString method to display
 * the level order (breadth first) traversal of values in that tree.
 */
public class RedBlackTree<Task extends Comparable<Task>> {
    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always be maintained.
     */
    protected static class Node<Task> {
        public Task data;
        public Node<Task> parent; // null for root node
        public Node<Task> leftChild;
        public Node<Task> rightChild;
        public boolean isBlack;

        public Node(Task data) {
            this.data = data;
            this.isBlack = false;
        }

        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }

        /**
         * This method performs a level order traversal of the tree rooted
         * at the current node.  The string representations of each data value
         * within this tree are assembled into a comma separated string within
         * brackets (similar to many implementations of java.util.Collection).
         *
         * @return string containing the values of this tree in level order
         */
        @Override
        public String toString() { // display subtree in order traversal
            String output = "[";
            LinkedList<Node<Task>> q = new LinkedList<>();
            q.add(this);
            while (!q.isEmpty()) {
                Node<Task> next = q.removeFirst();
                if (next.leftChild != null) q.add(next.leftChild);
                if (next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString();
                if (!q.isEmpty()) output += ", ";
            }
            return output + "]";
        }
    }

    protected Node<Task> root; // reference to root node of tree, null when empty

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree.  After
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     *
     * @param data to be added into this binary search tree
     * @throws NullPointerException     when the provided data argument is null
     * @throws IllegalArgumentException when the tree already contains data
     */
    public void insert(Task data) throws NullPointerException,
            IllegalArgumentException {
        // null references cannot be stored within this tree
        if (data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");
        Node<Task> newNode = new Node<Task>(data);
        if (root == null) {
            root = newNode;
        } // add first node to an empty tree
        else insertHelper(newNode, root); // recursively insert into subtree
        root.isBlack = true; //set the root to be black
    }

    private void enforceRBTreePropertiesAfterInsert(Node<Task> currentNode){
        boolean siblingIsBlack;
        if (!currentNode.parent.isBlack)
        {
            //check the color of the sibling of new node's parent
            if (currentNode.parent.isLeftChild())
            {
                if (currentNode.parent.parent.rightChild == null) {
                    siblingIsBlack = true;
                }
                else {
                    siblingIsBlack = currentNode.parent.parent.rightChild.isBlack;
                }
            }
            else
            {
                if (currentNode.parent.parent.leftChild == null) {
                    siblingIsBlack = true;
                }
                else {
                    siblingIsBlack = currentNode.parent.parent.leftChild.isBlack;
                }
            }
            //parent's sibling is red
            if (!siblingIsBlack){
                //make parent and parent's sibling to black
                if (currentNode.parent.parent.leftChild != null){
                    currentNode.parent.parent.leftChild.isBlack = true;
                }
                if (currentNode.parent.parent.rightChild != null){
                    currentNode.parent.parent.rightChild.isBlack = true;
                }
                currentNode.parent.parent.isBlack = false;
                //recursion triggered when grandparent has parent and that parent is red
                if (currentNode.parent.parent.parent != null && !currentNode.parent.parent.parent.isBlack){
                    enforceRBTreePropertiesAfterInsert(currentNode.parent.parent);
                }
            }
            //parent's sibling is black or empty
            else
            {
                if (currentNode.parent.isLeftChild())
                {
                    //maintain property when the insert node is in the opposite side of parent's sibling
                    if (currentNode.isLeftChild())
                    {
                        currentNode.parent.isBlack = true;
                        currentNode.parent.parent.isBlack = false;
                        rotate(currentNode.parent, currentNode.parent.parent);
                    }
                    //maintain property when the insert node is in the same side of parent's sibling
                    else{
                        currentNode.isBlack = true;
                        currentNode.parent.parent.isBlack = false;
                        rotate(currentNode, currentNode.parent);
                        rotate(currentNode, currentNode.parent);
                    }
                }
                else
                {
                    //maintain property when the insert node is in the same side of parent's sibling
                    if (currentNode.isLeftChild())
                    {
                        currentNode.isBlack = true;
                        currentNode.parent.parent.isBlack = false;
                        rotate(currentNode, currentNode.parent);
                        rotate(currentNode, currentNode.parent);
                    }
                    //maintain property when the insert node is in the opposite side of parent's sibling
                    else{
                        currentNode.parent.isBlack = true;
                        currentNode.parent.parent.isBlack = false;
                        rotate(currentNode.parent, currentNode.parent.parent);
                    }
                }


            }

        }
    }

    /**
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     *
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the
     *                newNode should be inserted as a descenedent beneath
     * @throws IllegalArgumentException when the newNode and subtree contain
     *                                  equal data references (as defined by Comparable.compareTo())
     */
    private void insertHelper(Node<Task> newNode, Node<Task> subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if (compare == 0) throw new IllegalArgumentException(
                "This RedBlackTree already contains that value.");
            // store newNode within left subtree of subtree
        else if (compare < 0) {
            if (subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                //maintain property of the red black tree
                enforceRBTreePropertiesAfterInsert(newNode);
                // otherwise continue recursive search for location to insert
            } else insertHelper(newNode, subtree.leftChild);

        }
        // store newNode within the right subtree of subtree
        else {
            if (subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                //maintain property of the red black tree
                enforceRBTreePropertiesAfterInsert(newNode);
                // otherwise continue recursive search for location to insert
            } else insertHelper(newNode, subtree.rightChild);

        }
    }

    /**
     * This method performs a level order traversal of the tree. The string
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     *
     * @return string containing the values of this tree in level order
     */
    @Override
    public String toString() {
        return root.toString();
    }

    /**
     * Performs the rotation operation on the provided nodes within this BST.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation (sometimes called a left-right
     * rotation).  When the provided child is a rightChild of the provided
     * parent, this method will perform a left rotation (sometimes called a
     * right-left rotation).  When the provided nodes are not related in one
     * of these ways, this method will throw an IllegalArgumentException.
     *
     * @param child  is the node being rotated from child to parent position
     *               (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *               (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *                                  <p>
     *                                  node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<Task> child, Node<Task> parent)
            throws IllegalArgumentException {
        if (child.parent == null || child.parent != parent ) {
            throw new IllegalArgumentException();
        } else {
            if (child.isLeftChild()) {

                if (parent.parent != null) {

                    if (parent.isLeftChild()) {
                        parent.parent.leftChild = child;
                        child.parent = parent.parent;
                        parent.parent = child;
                        parent.leftChild = child.rightChild;
                        child.rightChild = parent;
                    } else {
                        parent.parent.rightChild = child;
                        child.parent = parent.parent;
                        parent.parent = child;
                        parent.leftChild = child.rightChild;
                        child.rightChild = parent;
                    }
                }
                else{
                    root = child;
                    child.parent = parent.parent;
                    parent.parent = child;
                    parent.leftChild = child.rightChild;
                    child.rightChild = parent;
                }
            } else {
                if (parent.parent != null) {
                    if (parent.isLeftChild()) {
                        parent.parent.leftChild = child;
                        child.parent = parent.parent;
                        parent.parent = child;
                        parent.rightChild = child.leftChild;
                        child.leftChild = parent;
                    } else {
                        parent.parent.rightChild = child;
                        child.parent = parent.parent;
                        parent.parent = child;
                        parent.rightChild = child.leftChild;
                        child.leftChild = parent;
                    }
                }
                else{
                    root = child;
                    child.parent = parent.parent;
                    parent.parent = child;
                    parent.rightChild = child.leftChild;
                    child.leftChild = parent;
                }
            }
        }
    }

//    public static void main(String[] args)
//    {
//    }
}


