/**                                                                   
 *                                                                    
 * @author Brandon Li,                                                
 *A generic pQueue using ArrayList and HashMap         
 * Purpose: To gain a deeper understanding of 1 type of Queue (MinHeapSort)    
 * Note: HashMap is only useful when you are doing multiple add and polls
 * 		 HashMap is used to count frequency
 */                                                                   
import java.util.*;
public class pQueue<T extends Comparable<T>>{
	private static final int HEAPIFY_INDEX = 1; //1=least->greatest, -1=greatest->least 
	List<T> queue;
	HashMap<T,Integer> map = new HashMap<>();
	
	public T get(int index) {return(queue.get(index));}
	
	public int size() {return(queue.size());}
	
	public boolean isEmpty() {return(size()==0);}
	
	public boolean contains(T elem) {
		if(elem==null) {return false;}
		return(map.containsKey(elem));
	}
	
	public void clear() {
		if(!isEmpty()) {
			for(int index=size()-1;index>-1;index--) {
				queue.set(index,null); 
				queue.remove(index);
			}
				map.clear();
			}
	}
	
	public T peek() {
		if(isEmpty()) {return(null);}
		return(queue.get(0));
	}
	
	private void swap(int A,int B) {
		T temp = queue.get(A);
		queue.set(A, queue.get(B));
		queue.set(B, temp);
	}
	
	private List<T> heapify(List<T> queue,int length){
		if(length==0) {return (queue);}
		int left,right,heapIndex=((length-1)/2),placeHolder=heapIndex;
		
		for(int headIndex=heapIndex;headIndex>-1;headIndex--) {
			left = (headIndex*2)+1; right = left+1;
			if(left<length&&queue.get(left).compareTo(queue.get(heapIndex))==(HEAPIFY_INDEX*1)) {
				heapIndex = left;
			}
			if(right<length&&queue.get(right).compareTo(queue.get(heapIndex))==(HEAPIFY_INDEX*1)) {
				heapIndex = right;
			}
			if(heapIndex>headIndex) {
				swap(heapIndex,headIndex);
				headIndex = heapIndex+1;
			}else {
				headIndex=placeHolder;
				heapIndex=--placeHolder;
			}
		}
		swap(0,length-1);
		return(heapify(queue,length-1));
	}

	public T poll() {
		if(isEmpty()) {return(null);}
		if(map.getOrDefault(queue.get(0),1)==1) {map.remove(queue.get(0));}
		else {map.put(queue.get(0), map.getOrDefault(queue.get(0), 0)-1);}
		return(queue.remove(0));
	}
	
	public void add(T elem) {
		if(elem==null) {throw new IllegalArgumentException("Element null");}
		queue.add(elem);
		map.put(elem, map.getOrDefault(elem, 0)+1);
		heapify(queue,queue.size());
	}
	
	@Override
	public String toString() {
		String output=("");
		
		 //Note: Print queue in tree format that only works for a single letter
		int depth=(int)(Math.log(queue.size())/Math.log(2)),count=0,newLine=0;
		for(int index=0; index<queue.size();index++) {
			if(index>=newLine) {
				newLine=(int)Math.pow(2,++count)-1;
				output=(output+"\n");
				for(int s=0;s<Math.pow(2, depth)-1;s++) {output=(output+" ");}
				--depth;
			} 
			output=(output+queue.get(index));
			for(int i=0;i<Math.pow(2, depth+2)-1;i++) {output=(output+" ");}
		}
		/*
		
		for(T elem:queue) {output=(output+elem+" ");}
		*/
		return (output);
	}
	
	public pQueue(T[] array) {
		queue = new ArrayList<T>(array.length);
		for(T elem:array) {
			queue.add(elem);
			map.put(elem, map.getOrDefault(elem, 0)+1);
			heapify(queue,queue.size());
		}
	}
	
	public pQueue() {
		queue = new ArrayList<T>();
	}
}
