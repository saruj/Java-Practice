package com.tree;

public class PrintBinaryTreeLeaf {
	public static void main(String[] args) throws Exception {

	    // let's create a binary tree
	    TreeNode d = new TreeNode("d");
	    TreeNode e = new TreeNode("e");
	    TreeNode g = new TreeNode("g");
	    TreeNode k = new TreeNode("k");

	    TreeNode c = new TreeNode("c", d, null);
	    TreeNode h = new TreeNode("h", k, null);

	    TreeNode b = new TreeNode("b", c, e);
	    TreeNode f = new TreeNode("f", g, h);

	    TreeNode root = new TreeNode("a", b, f);

	    // print all leaf nodes of binary tree using recursion
	    
	    TreeNode t = new TreeNode();
	    
	    System.out
	        .println("Printing all leaf nodes of binary tree in Java (recursively)");
	    t.printLeaves(root);

	  }
}
