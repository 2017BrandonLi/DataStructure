/**
 * 
 * @author Brandon Li,
 *A generic 1D dynamic Array
 * Purpose: To gain a deeper understanding of 1D array
 */
public class Array1D<T> implements Comparable<T>{
	protected T[] array; //array is protected since2D array extends to 1D array
	private int capacity; int length;
	
	@SuppressWarnings("unchecked")
	public Array1D(int length) {
		if(length<0) {throw new IllegalArgumentException("Illegal Capacity: "+length);}
		
		setLength(length);
		setCapacity(length*2);
		array = (T[]) new Object[capacity]; //Casting a Generic Object[] into Generic Data Array 
	}
	
	//Get Methods
	public T get(int index) {return(this.array[index]);}
	
	public int length() {return(this.length);}
	
	protected int capacity() {return(this.capacity);}
	
	//Set Method
	public void set(int index, T data) {
		if(index>=length()) {throw new ArrayIndexOutOfBoundsException("Index: "+index+" out of bounds for length "+this.length);}
		this.array[index] = data;
	}

	private void setLength(int length) {this.length=length;}

	public void setCapacity(int capacity) {this.capacity=capacity;}
	
	//IsEmpty Method
	public boolean isEmpty() {return(this.length()==0);}
	
	//toString Method
		@Override
		public String toString() {
			if(isEmpty()) {return("");}
			String output="[ ";
			
			
			for(int index=0;index<length();index++)
				output=output+get(index)+" ";

			return (output+"]");
		}
	//Clear Method
	public T[] clear() {
		if(isEmpty()) {return array;}
		for(int i =0; i <this.length();i++)
			this.set(i, null);
		
		return array;
	}
	
	//Contains Method
		public boolean contain(T data) {return(indexOf(data)!=-1);}
	
	//IndexOf Method
	@SuppressWarnings("unchecked")
	public int indexOf(T data) {
		int index=0;
		for(T elem:array) {
			if(((Comparable<T>) elem).compareTo(data)==0) {return index;}
			++index;
		}
		return -1;
	}
	
	
	//Remove Method
	public T removeAt(int index) {
		if(index>=length()) {throw new ArrayIndexOutOfBoundsException("Index: "+index+" out of bounds for length "+this.length);}
		T removeValue =get(index);
		
		for(int i=index;i<length()-1;i++) {set(i,get(i+1));}//Instead of making a new array we replace array[] values to use less memory
		set(length()-1,null); //loops doesn't reach the last index for array so we manually replace it with null
		setLength(length()-1);

		return removeValue;
	}

	public T remove(T data) {
		if(contain(data)) {return(removeAt(indexOf(data)));}
		return (null);
	}
	
	//Add Method
	@SuppressWarnings("unchecked")
	public void addAt(int index, T data) {
		//Requires more memory
		if(index>=capacity) {
			setLength(index+1);
			setCapacity(length()*2);
			
			T[] temp = (T[]) new Object[capacity()];
			int tempIndex=0;
			for(T elem:array) {
				temp[tempIndex]=elem;
				++tempIndex;
			}
			array=temp;
		}else if(index>=length()) {
			setLength(length+1);
		}
		set(index,data);
	}

	public void add(T data) {this.addAt(this.length(), data);}
	
	//Swap Method
	public void swap(int current, int next) {
		T data = get(next);
		set(next, get(current));
		set(current, data);
	}
	
	//Clone Method
	@SuppressWarnings("unchecked")
	//Array[] Cloning
	public T[] clone(Object[] array) {	
		T[] tempArray = (T[]) new Object[array.length];
		for(int i=0; i < array.length;i++) {
			tempArray[i] = (T) array[i];
		}
		return this.array=tempArray;
	}
	
	@SuppressWarnings("unchecked")
	public T[] clone(Array1D<T> array) {
		T[] tempArray = (T[]) new Object[array.length];
		setLength(array.length);
		setCapacity(array.length*2);
		
		for(int i=0; i < array.length;i++) {
			tempArray[i] = array.get(i);
		}
		return this.array = tempArray;
	}
	@SuppressWarnings("unchecked")
	public void merge(Array1D<T> array) {
		T[] tempArray = (T[]) new Object[this.length()+array.length];
		int i =0;
		while(i < this.length()) {
			tempArray[i] = this.get(i);
		}
		while( (i-this.length()) < array.length()) {
			tempArray[i] = ((T) array.get(i-this.length()));
		}
		this.array = tempArray;
	}
	//CompareTo Method
	public int compareTo(T data) {
		return (this.compareTo(data));
	}
	//BubbleSort Method
	@SuppressWarnings("unchecked")
	public void bubbleSort()  {
		for(int count = 0; count <this.length();count++) {
			for(int index = 1; index < this.length()-count;index++) {
				if( ((Comparable<T>)this.get(index-1)).compareTo(this.get(index))>0 ) {
					this.swap(index-1,index);
				}
			}
			
		}
	}
	//InsertionSort Method
	@SuppressWarnings("unchecked")
	public void insertionSort() {
		T data;
		for(int index=1;index <this.length();index++) {
			data = this.get(index);
			int prev = index-1;
			
			while(prev>=0&& ((Comparable<T>) this.get(prev)).compareTo(data) >= 0 ) {
				if( ((Comparable<T>) this.get(prev)).compareTo(data) >= 0 ) {
					this.set(prev+1, this.get(prev));
				}
				prev--;
			}
			this.set(prev+1, data);
		}
	}
	//MergeSort Method
	public void mergeSort() {
		this.array = splitArray(this.array,this.length());
	}
	@SuppressWarnings({ "unchecked" })
	private T[] splitArray(T[] array,int length) {
		//Break Recursion
		if(length<=1) {
			return array;
		}
		
		int midPoint = length/2;
		T[] leftSide = (T[]) new Object[midPoint];
		T[] rightSide = (T[]) new Object[length-midPoint];
		
		for(int i = 0; i<midPoint; i++) {
			leftSide[i]=array[i];
		}
		for(int i = midPoint; i<length; i++) {
			rightSide[i-midPoint]=array[i];
		}
		
		leftSide = splitArray(leftSide,midPoint);
		rightSide = splitArray(rightSide,length-midPoint);
		//Merging and Sorting
		T[] temp = (T[])new Object[leftSide.length + rightSide.length];
		temp = combine(leftSide,rightSide);
		
		return (temp);		
				
	}

	@SuppressWarnings("unchecked")
	private T[] combine(T[] leftSide, T[] rightSide) {
		//Merge
		int leftPointer=0, rightPointer=0, tempPointer = 0;
		T[] temp = (T[])new Object[leftSide.length + rightSide.length]; 
		
		while(leftPointer <leftSide.length && rightPointer < rightSide.length) {
			if(((Comparable<T>)leftSide[leftPointer]).compareTo(rightSide[rightPointer]) < 0) {
				temp[tempPointer] = leftSide[leftPointer];
				leftPointer++;
			}else {
				temp[tempPointer] = rightSide[rightPointer];
				rightPointer++;
			}
			tempPointer++;
		}
		while(leftPointer <leftSide.length) {
			temp[tempPointer] = leftSide[leftPointer];
			leftPointer++;
			tempPointer++;
		}
		while(rightPointer <rightSide.length) {
			temp[tempPointer] = rightSide[rightPointer];
			rightPointer++;
			tempPointer++;
		}
		return(temp);
	}
	//MinHeapSort Method
	public void minHeapSort() {
		this.array = heapSort(this.array,this.length(),-1);
	}
	//MaxHeapSort Method
	public void maxHeapSort() {
		this.array = heapSort(this.array,this.length(),1);
	}
	//heapSort
	@SuppressWarnings("unchecked")
	private T[] heapSort(T[] array, int length, int type) {
		//Break Recursion
		if(length<=1) {
			return array;
		}
		//Heap Sorting
		int indexLargest=length/2, indexPlaceHolder=length/2;
		
		for(int index = length/2; index >=0; index--) {
			
			int left = (index*2+1), right = (index*2+2);
			//Head has one children: Left node
			if(left<length) {
				if( ((Comparable<T>) this.get(left)).compareTo(this.get(indexLargest)) == (1*type) ){
					indexLargest = left;
				}
				//Head has two children Right node
				if(right<length&& ((Comparable<T>) this.get(right)).compareTo(this.get(indexLargest)) == (1*type)) {
					indexLargest = right;
				}
			}
			
			if(indexLargest>index) {
				this.swap(indexLargest,index);
				index = indexLargest+1;
			}else { 
				index = indexPlaceHolder;
				indexLargest= indexPlaceHolder-1;
				indexPlaceHolder = indexLargest;
			}
			
		}
		//Remove Max/Min from Heap
		this.swap(0,length-1);
		
		array=heapSort(this.array,length-1,type);
		
		return array;
	}
	//QuickSort
	public void quickSort() {
		this.array = partition(this.array,0,this.length()-1);
	}
	@SuppressWarnings("unchecked")
	private T[] partition(T[] array, int start, int end) {
		//Break Recursion
		if(start>=end) {
			return array;
		}
		//Partition
		int pivot = (start+end)/2, left= start, right=end;
		T pivotValue = array[pivot], temp;
		
		while(left<=right) {
			//Finding left value greater than pivot
			while(((Comparable<T>) array[left]).compareTo(pivotValue)==-1) {
				left++;
			}
			//Finding right value less than pivot
			while( ((Comparable<T>) array[right]).compareTo(pivotValue)==1 ) {
				right--;
			}
			if(left<=right) {
				temp = array[left];
				array[left] = array[right];
				array[right] = temp;
				left++;
				right--;
			}
		}
		
		//sort left side
		array = partition(array,start,left-1);
		//sort right side
		array = partition(array,left,end);
		
		return array;
	}
}
