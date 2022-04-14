
public class LinkedList<T> implements Comparable<T>{
	private int size = 0;
	private Node<T> head = null, tail = null;
	//Inner Class we do not need an LinkedList constructor since the Node is all we need
	@SuppressWarnings("hiding")
	private class Node<T>{
		T data;
		Node<T> prev, next;
		//Constructor
		public Node(T data, Node<T> prev, Node<T> next) {
			this.set(data, prev, next);
		}
		//Get Method
		public T getData() {
			return(this.data);
		}
		//Set Method
		public void setPrev(Node<T> prev) {
			this.prev = prev;
		}
		public void setNext(Node<T> next) {
			this.next = next;
		}
		public void setData(T data) {
			this.data = data;
		}
		public void set(T data, Node<T> prev, Node<T> next) {
			this.setPrev(prev);
			this.setNext(next);
			this.setData(data);
		}
	}
	//Get Method
	public int size() {
		return(this.size);
	}
	public T peekFirst() {
		return(this.head.getData());
	}
	public T peekLast() {
		return(this.tail.getData());
	}
	private Node<T> head(){
		return(this.head);
	}
	public T get(int index) {
		if(this.isEmpty()||index>=this.size()) throw new RuntimeException("Index:"+index+" is out of bounds of size: "+this.size());
		Node<T> curr = this.head; int i = 0;
		while(curr!=null&&i<index) {
			curr= curr.next;
			i++;
		}
		return (curr.getData());
	}
	private Node<T> getNode(int index){
		if(this.isEmpty()||index>=this.size()) return null;
		
		Node<T> curr = this.head; int i = 0;
		while(curr!=null && i<index) {
			curr= curr.next;
			i++;
		}
		return (curr);
	}
	//Empty Method
	public boolean isEmpty() {
		return(size()==0);
	}
	//Contain Method
	public boolean contain(T data) {
		Node<T> temp = this.head;
		for(int i=0;i<size;i++) {
			if(temp.getData().equals(data)) return (true);
			temp = temp.next;
		}
		return false;
	}
	//IndexOf Method
	public int indexOf(T data) {
		Node<T> temp = this.head;
		for(int i=0;i<size;i++) {
			if(temp.getData().equals(data)) return (i);
			temp = temp.next;
		}
		return -1;
	}
	//Swap Method
	private boolean swap(Node<T> A, Node<T> B) {
		if(isEmpty()||size()==1||A==B) {
			return false;
		}
		
		Node<T> tempNext = null,tempPrev = null;
		if(A == this.head && B==this.tail) {
			tempNext = A.next;
			tempPrev = B.prev;
			A.set(A.getData(), tempPrev, null);
			B.set(B.getData(), null, tempNext);
			A.prev.setNext(A);
			B.next.setPrev(B);
			this.head = B;
			this.tail = A;
		}else {
			A.next.setPrev(B);
			tempNext = A.next;
			tempPrev = A.prev;
			A.set(A.getData(), B.prev, B.next);
			B.set(B.getData(), tempPrev, tempNext);
			A.prev.setNext(A);

			if(this.head == A) {
				A.next.setPrev(A);
				this.head = B;
				return true;
			}else if(this.tail ==B){
				B.prev.setNext(B);
				this.tail = A;
				return true;
			}
			A.next.setPrev(A);
			B.prev.setNext(B);
		}
		return true;
	}
	//Add method
	public void addLast(T data) {
		if(isEmpty()) {
			Node<T> temp = new Node<T>(data, null, null);
			this.head = temp;
			this.tail = this.head;
		}else {
			Node<T> temp = new Node<T>(data, this.tail, null);
			this.tail.setNext(temp);
			this.tail = temp;
		}
		++this.size;
	}
	public void addFirst(T data) {
		if(isEmpty()) {
			Node<T> temp = new Node<T>(data, null, null);
			this.head = temp;
			this.tail = this.head;
		}else {
			Node<T> temp = new Node<T>(data, null, this.head);
			this.head.setPrev(temp);
			this.head = temp;
		}
		++this.size;
	}
	public void add(T data) {
		if(isEmpty()) {
			addFirst(data);
		}else {
			addLast(data);
		}
	}
	//Remove Method
	public boolean removeLast() {
		
		if(size()==1) {
			this.tail.set(null, null, null);
			this.tail = this.head = null;
		}else if(size() >1) {
			this.tail = this.tail.prev;
			this.tail.next.set(null,null,null);
			this.tail.setNext(null);
		}else {
			return false;
		}
		--this.size;
		return true;
	}
	public boolean removeFirst() {
		if(size()==1) {
			this.head.set(null, null, null);
			this.head = this.tail = null;
		}else if(size() >1) {
			this.head = this.head.next;
			this.head.prev.set(null,null,null);
			this.head.setPrev(null);
		}else {
			return false;
		}
		--this.size;
		return true;
	}
	public boolean removeAt(int index) {
		if(isEmpty()||index>=size()) throw new IllegalArgumentException("Index: "+index+" is out of bounds of size: "+size());
		
		if(index==0) return(removeFirst());
		if(index==size()-1) return(removeLast());
		
		Node<T> current = this.head;
		for(int i = 0; i != index; i++) {
			current = current.next;
		}
		current.prev.setNext(current.next);
		current.next.setPrev(current.prev);
		current.set(null, null, null);
		current = null;
		--this.size;
		return true;	
	}
	public boolean remove(T data) {
		if(!contain(data)) return false;
		removeAt(indexOf(data));
		return true;
	}
	public boolean removeAll(T data) {
		if(!contain(data)) return false;
		while(contain(data)) {
			remove(data);
		}
		return true;
	}
	//clone Method
	public LinkedList<T> clone(LinkedList<T> list){
		LinkedList<T> temp= new LinkedList<T>();
		Node<T> curr = list.head();
		
		while(curr!=null) {
			temp.add(curr.getData());
			curr = curr.next;
		}
		return(temp);
	}
	
	public void clear() {
		while(!isEmpty()) {
			this.removeLast();
		}
	}
	
	//ToString Method
	@Override
	public String toString() {
		String output ="";
		Node<T> temp = this.head;
		while(temp!=null) {
			output= (output+"("+temp.getData()+") ");
			temp = temp.next;
		}
		return(output); 
	}
	//compareTo method
	@Override
	public int compareTo(T data) {
		return(this.compareTo(data));
	}
	//MergeSort
	public Node<T> getMiddle(Node<T> head){
		if(head==null) return head;
		Node<T> curr = head,front= head;
		while(front.next!=null && front.next.next!=null) {
			curr = curr.next;
			front =front.next.next;
		}
		return curr;
	}
	@SuppressWarnings("unchecked")
	private Node<T> sortMerge(Node<T> left, Node<T> right){
		if(left==null) return right;
		if(right==null) return left;
		Node<T> sort;
		if(((Comparable<T>)left.getData()).compareTo(right.getData())<=0) {
			sort = left;
			sort.setNext(sortMerge(left.next,right));
			sort.next.setPrev(left);
		}else {
			sort = right;
			sort.setNext(sortMerge(left,right.next));
			sort.next.setPrev(right);
		}
		return sort;
	}
	private Node<T> split(Node<T> head){
		//Break Recursion
		if(head==null||head.next==null) return head;
		Node<T> left, right, middle,sortedList;
		middle = getMiddle(head);
		right = middle.next;
		middle.setNext(null);
		
		left = split(head);
		right = split(right);
		sortedList = sortMerge(left,right);
		this.head = sortedList;
		return(sortedList); 
	}
	public boolean mergeSort(){
		if(isEmpty()) return false;
		split(this.head);
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean bubbleSort() {
		if(isEmpty()) return false;
		Node<T> curr = this.head;
		for(int i=0;i<this.size();i++){
			while(curr.next!=null) {
				if( ((Comparable<T>)curr.getData()).compareTo(curr.next.getData()) >=0) {
					 this.swap(curr, curr.next);
				 }else {
					 curr= curr.next;
				 }
			}	
			curr = this.head;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean insertionSort() {
		if(isEmpty()) return false;
		Node<T> curr = this.head.next,prev = curr,placeHolder=null;
		
		while(curr!=null){
			placeHolder = curr.next;
			while(prev.prev!=null) {
				if( ((Comparable<T>)prev.prev.getData()).compareTo(prev.getData())>=0 ) {
					this.swap(prev.prev, prev);
				 }else {
					 prev =prev.prev;
				 }	
			}
			curr= placeHolder;
			prev = curr;
			
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	private Node<T> heapSort(int size, int type) {
		if(size<=0) {
			return this.head;
		}
		int minMaxIndex= (size-1)/2, placeHolder = minMaxIndex; 
		
		for(int index = placeHolder; index >=0;index--) {
			int leftNode=(2*index+1),rightNode = leftNode+1;
			if(getNode(leftNode)!=null&&leftNode<=size) {
				if( ((Comparable<T>)get(leftNode)).compareTo(get(minMaxIndex)) ==(1*type)) minMaxIndex = leftNode;
			}
			if(getNode(rightNode)!=null&&rightNode<=size) {
				if( ((Comparable<T>)get(rightNode)).compareTo(get(minMaxIndex)) ==(1*type)) minMaxIndex = rightNode;
			}
			if(minMaxIndex>index) {
				this.swap(getNode(index), getNode(minMaxIndex));
				index = minMaxIndex+1;
			}else {
				index = placeHolder;
				minMaxIndex = --placeHolder;
			}
		}
		this.swap(this.head, this.getNode(size));
		
		heapSort(size-1,type);
		return this.head;
	}
	public boolean minHeapSort() {
		if(this.isEmpty()||this.size()==1) return false;
		this.head = this.heapSort(this.size()-1,-1);
		return true;
	}
	public boolean maxHeapSort() {
		if(this.isEmpty()||this.size()==1) return false;
		this.head = this.heapSort(this.size()-1,1);
		return true;
	}
	@SuppressWarnings("unchecked")
	private Node<T> partition(int start, int end){
		if(start>=end) return this.head;
		
		int middle = (start+end)/2,left=start,right=end; T pivot = get(middle);
		while(left<=right) {
			while( ((Comparable<T>)get(left)).compareTo(pivot)==-1 ) left++;
		
			while( ((Comparable<T>)get(right)).compareTo(pivot)==1 ) right--;
		
			if(left<=right) {
				this.swap(getNode(left),getNode(right));
				left++;
				right--;
			}
			
		}
		partition(0,left-1);
		partition(left,end);
		return this.head;
	}
	public boolean quickSort() {
		if(this.isEmpty()||this.size()==1) return false;
		this.head = this.partition(0,this.size()-1);
		return true;
	}
}

