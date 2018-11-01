package com.tree;

public class TreeNode {
	String value;
    TreeNode left;
    TreeNode right;

    TreeNode() {
      }
    
    TreeNode(String value) {
      this.value = value;
    }

    TreeNode(String data, TreeNode left, TreeNode right) {
      this.value = data;
      this.left = left;
      this.right = right;
    }

    boolean isLeaf() {
      return left == null ? right == null : false;
    }
    
    public void printLeaves(TreeNode node) {
        // base case
        if (node == null) {
          return;
        }

        if (node.isLeaf()) {
          System.out.printf("%s ", node.value);
        }

        printLeaves(node.left);
        printLeaves(node.right);

      }
}
