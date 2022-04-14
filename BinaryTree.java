import java.util.*;
/**
 * 
 * @author Brandon Li,
 *A generic Binary Search Tree 
 * Purpose: To gain a deeper understanding of 1 type of Tree
 * Note: I program this tree to not support duplicate values 
 */
public class BinaryTree<T extends Comparable<T>> {
	private class Node {
		T elem; Node left, right;
		public Node(T elem, Node left, Node right) {
			set(elem,left,right);
		}
		
		public void set(T elem, Node left, Node right) {
			this.elem=elem;
			this.left=left;
			this.right=right;
		}
	}
	private Node root=null;
	private int nodeCount=0;
	
	
	public int size() {return(this.nodeCount);}
	
	public boolean isEmpty() {return(size()==0);}
	
	public T root() {
		if(isEmpty()) { return(null);}
			return(this.root.elem);
	}
	
	private Node contains(Node parent,T elem) {
		if(parent==null) {return null;}
		if(parent.elem.equals(elem)) {return parent;}
		
		if(parent.elem.compareTo(elem)==1) {return(contains(parent.left,elem));}
		else {return(contains(parent.right,elem));}
	}
	
	public boolean contains(T elem) {return(contains(root,elem)!=null);}
	
	private Node getMin(Node node) {
		if(node.left==null) {return(node);}
		return(getMin(node.left));
	}
	
	private Node remove(Node oldNode,T elem) {
		if(oldNode==null) {return null;}
		
		Node tempNode=null;
		
		if(oldNode.elem.compareTo(elem)==1) {
			remove(oldNode.left,elem);
		}else if(oldNode.elem.compareTo(elem)==1) {
			remove(oldNode.right,elem);
		}else {
			if(oldNode.left==null&&oldNode.right==null) {
				return(oldNode = null);
			}else if(oldNode.left==null) {
				tempNode = oldNode.right;
				oldNode.set(tempNode.elem,tempNode.left,tempNode.right);
				tempNode.elem = null; 
				tempNode = null;
				return(oldNode);
			}else if(oldNode.right==null) {
				tempNode = oldNode.left;
				oldNode.set(tempNode.elem,tempNode.left,tempNode.right);
				tempNode.elem = null; 
				tempNode = null;
				return(oldNode);
			}else {
				tempNode = getMin(oldNode.right);
				oldNode.set(tempNode.elem, oldNode.left, oldNode.right);
				return(remove(tempNode,elem));
			}
		}
		nodeCount--;
		return(oldNode);
	}
	
	public Node remove(T elem) {
		if(isEmpty()) {return(null);}
		Node oldNode = contains(root,elem);
		if(oldNode!=null) {
			return(remove(root,elem));}else {return (null);}
	}
	
	private Node addNode(Node current, T elem) {
		if(current==null) {return(current = new Node(elem,null,null));}
		if(current.elem.compareTo(elem)==1) {
			current.left = addNode(current.left,elem);
		}else {
			current.right = addNode(current.right,elem);
		}
		return(current);
	}
	
	public Node add(T elem) {
		if(elem==null) {throw new IllegalArgumentException("Null Element");}
		if(contains(elem)) {return null;}
		if(root==null) {return(root = new Node(elem,null,null));}
		return(addNode(root,elem));
	}
	
	private int depth(Node node) {
		if(node==null) {return(0);}
		return(Math.max(depth(node.left),depth(node.right))+1);
	}
	
	public int depth() {
		return(depth(root));
	}
	private ArrayList<T> list = new ArrayList<T>();
	
	private String inOrder(Node node) {
		if(node==null) {return null;}
		inOrder(node.left);
		list.add(node.elem);
		inOrder(node.right);
		return("");
	}
	
	private String postOrder(Node node) {
		if(node==null) {return null;}
		postOrder(node.right);
		list.add(node.elem);
		postOrder(node.left);
		return(null);
	}
	
	public String print(String order) {
		 list.clear();
		switch (order.toLowerCase()) {
	      case ("inorder"):
	    	inOrder(root);
	      	return(list.toString());
	      case ("postorder"):
	        postOrder(root);
	      	return(list.toString());
	      default:
	        return ("Print options are inOrder or postOrder. You entered: "+order);
	    }

	}
	
}








