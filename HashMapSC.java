/**
 * 
 * @author Brandon Li,
 *A generic Hashmap Separation Chain using generic LinkedList
 * Purpose: To gain a deeper understanding of 1 type of Hashmap
 */
import java.util.*;

class Node<Key,Value>{
	int hash; Key key; Value value;
	public Value getValue() {return(this.value);}
	
	public Key getKey() {return(this.key);}
	
	public int getHash() {return(this.hash);}
	
	public void set(Key key, Value value) {
		this.key = key;
		this.value =value;
	}
	
	@Override
	public String toString() {
		return("("+this.getKey()+"->"+this.getValue()+")");
	}
	
	public boolean equals(Node<Key,Value> other) {
		if(this.getHash() != other.getHash()) {return (false);}
		return(this.getKey()==other.getKey());
	}
	
	
	public Node(Key key, Value value) {
		this.set(key,value);
		this.hash = key.hashCode();
	}
}
public class HashMapSC<Key,Value> {
	private static final int DEFAULT_CAPACITY=3; //Default Max Array Length
	private static final double DEFAULT_LOAD_FACTOR=0.75; //Default threshold before resizing
	private static final int MULTIPLIER = 2; //Multiplier to capacity for size limit
	
	private double loadFactor=0; private int capacity=0,threshold=0,size=0;
	LinkedList<Node<Key,Value>>[] table;
	
	public double loadFactor() {return(this.loadFactor);}
	
	public int size() {return(this.size);}
	
	public int threshold() {return(this.threshold);}
	
	public int capacity() {return(this.capacity);}
	
	public void set(int capacity, double loadFactor) {
		this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
		this.loadFactor = loadFactor;
		this.threshold = (int)(this.capacity*loadFactor*MULTIPLIER);
	}
	
	public boolean isEmpty() {return(this.size()==0);}
	
	
	private int hashIndex(int hash) {return(Math.abs(hash)%this.capacity());} //Index < capacity
	
	private Node<Key,Value> getEntry(int index, Key key) {
		if(key==null) {return null;}
		if(table[index]==null) {return null;}
		LinkedList<Node<Key,Value>> bucket = table[index];
		Node<Key,Value> entry = null;
		for(int entryIndex=0;entryIndex<bucket.size();entryIndex++) {
			entry = ((Node<Key,Value>)bucket.get(entryIndex));
			if( entry.getKey().equals(key) ) {return entry;}
		}
		return null;
	}
	
	
	public boolean hasEntry(int index,Key key) {return(getEntry(index,key)!=null);}
	
	public void clear() {
		Arrays.fill(table,null);
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<Node<Key,Value>>[] resizeTable() {
		LinkedList<Node<Key, Value>>[] tempTable = new LinkedList[this.capacity*2];
		this.set(this.capacity*2,this.loadFactor);
		
		int newIndex;
		
		for(int index=0;index<table.length;index++) {
			if(table[index]!=null) {				
				for(int entryIndex=0;entryIndex<table[index].size();entryIndex++) {
					newIndex = hashIndex(table[index].get(entryIndex).getHash());
					if(tempTable[newIndex]==null) {tempTable[newIndex] = new LinkedList<Node<Key,Value>>();}
					tempTable[newIndex].add(table[index].get(entryIndex));
				}
			}
		}
		this.clear();
		table = tempTable;
		
		return table;
	}
	
	public String add(Key key, Value value) {
		if(key==null) {throw new IllegalArgumentException("Null Key");}
		
		Node<Key,Value> entry = new Node<Key,Value>(key,value);
		int index = hashIndex(entry.getHash());
		LinkedList<Node<Key,Value>> bucket = table[index];
		if(bucket==null) {table[index] = bucket = new LinkedList<Node<Key,Value>>();}
		
		if(hasEntry(index,entry.getKey())) {
			this.getEntry(index,entry.getKey()).set(entry.getKey(), value);; 
			return("Update Entry: "+entry+" "+entry.getHash());
		}else {
			bucket.add(entry);
			if(++size>=this.threshold()){resizeTable();}
			return("Added Entry: "+entry+" "+entry.getHash());
		}
	}
	public String remove(Key key) {
		if(key==null) { throw new IllegalArgumentException("Null Key");}
		if(!hasEntry(hashIndex(key.hashCode()),key)) {return"Not Found";}
		System.out.println(getEntry(hashIndex(key.hashCode()),key));
		int index = hashIndex(key.hashCode());	
		Node<Key,Value> entry = getEntry(index,key);
		String output =("Removed: "+ entry); 
		table[index].remove(entry);
		return(output);

	}
	public String toStringValues() {
		String output =("");
		
		for(int index=0;index<table.length;index++) {
			output=(output+"[ ");
			if(table[index]!=null) {
				for(int entryIndex=0;entryIndex<table[index].size();entryIndex++) {
					output=(output+table[index].get(entryIndex).getValue()+" ");
				}
			}
			output=(output+"]\n");
		}
		return(output);
	}
	public String toStringKeys() {
		String output =("");
		
		for(int index=0;index<table.length;index++) {
			output=(output+"[ ");
			if(table[index]!=null) {
				for(int entryIndex=0;entryIndex<table[index].size();entryIndex++) {
					output=(output+table[index].get(entryIndex).getKey()+" ");
				}
			}
			output=(output+"]\n");
		}
		return(output);
	}
	public String toString() {
		String output =("");
		
		for(int index=0;index<table.length;index++) {
			output=(output+"[ ");
			if(table[index]!=null) {
				for(int entryIndex=0;entryIndex<table[index].size();entryIndex++) {
					output=(output+table[index].get(entryIndex)+" ");
				}
			}
			output=(output+"]\n");
		}
		return(output);
	}
	
	public HashMapSC() {this(DEFAULT_CAPACITY,DEFAULT_LOAD_FACTOR);}
	
	public HashMapSC(int capacity) {this(capacity,DEFAULT_LOAD_FACTOR);}
	
	@SuppressWarnings("unchecked")
	public HashMapSC(int capacity, double loadFactor) {
		if(capacity <0) {throw new IllegalArgumentException("Illegal Capacity: "+capacity);}
		if(loadFactor<=0||Double.isNaN(loadFactor)||Double.isInfinite(loadFactor)) {throw new IllegalArgumentException("Illegal Load Factor: "+loadFactor);}
		
		this.set(capacity,loadFactor);
		table = new LinkedList[this.capacity];
	}
}
