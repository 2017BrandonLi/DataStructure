/**
 * 
 * @author Brandon Li,
 *A generic 2D dynamic Array
 * Purpose: To gain a deeper understanding of 2D array relation to 1D array by making an 1D dynamic array hold 1 dynamic arrays
 */
public class Array2D<T> extends Array1D<T>{
	
	//Just use an array and hold arrays
	public Array2D(int row) {this(row,1);}
	
	public Array2D(int row, int column) {
		super(row);
		for(int i = 0; i <row;i++) this.set(i, new Array1D<T>(column));
	}
	
	//Set Method
	@SuppressWarnings("unchecked")
	public void set(int index, Array1D<T> arrayColumn) {
		if(this.isRowEmpty()) throw new ArrayIndexOutOfBoundsException("Index: "+index+" out of bounds for row "+this.getRow());
		this.array[index] = (T) arrayColumn;
	}
	@SuppressWarnings("unchecked")
	public void set(int row, int column, T data) {
		if(this.isEmpty()) 
			throw new ArrayIndexOutOfBoundsException("Row: "+row+"Column: "+column+ 
			"out of bounds for row "+this.getRow()+" "+this.getColumn(row));
		((Array1D<T>) this.array[row]).set(column, data);
	}
	//Get Method
	@SuppressWarnings("unchecked")
	public T get(int row, int column) {return ((Array1D<T>) this.array[row]).get(column);}
	
	public T get(int row) {return (this.array[row]);}
	
	public int getRow() {return(super.length());}
	
	@SuppressWarnings("unchecked")
	public int getColumn(int row) {return( ((Array1D<T>) this.array[row]).length() );}
	
	public int getRowCapacity() {return(super.capacity());}
	
	@SuppressWarnings("unchecked")
	public int getColumnCapacity(int row) {return( ((Array1D<T>)this.array[row]).capacity() );}
	
	//Conversion Method
	private Array1D<T> convert1D(T[] array) {
		int length = 0; 
		for(int row =0; row <this.getRow();row++) {
			length = length+this.getColumn(row);
		}
		
		Array1D<T> tempArray = new Array1D<T>(length);
		length=0;
		for(int row =0; row <this.getRow();row++) {
			for(int column=0; column <this.getColumn(row);column++) {
				tempArray.set(length, this.get(row,column));
				length++;
			}
		}
		return tempArray;
	}
	@SuppressWarnings("unchecked")
	private T[] convert2D(Array1D<T> array){
		T[] tempArray = (T[])new Object[this.getRow()];
		int index=0;
		for(int row=0; row < this.getRow();row++) {
			Array1D<T> tempColumn = new Array1D<T>(this.getColumn(row));
			for(int column =0;column<this.getColumn(row);column++) {
				tempColumn.set(column, array.get(index)); 
				index++;
			}
			tempArray[row]= (T) tempColumn;
		}
		return tempArray;
	}
	//IsEmpty Method
	@Override
	public boolean isEmpty() {
		return(this.isRowEmpty() || this.isColumnEmpty());
	}
	private boolean isRowEmpty() {
		return(super.isEmpty());
	}
	@SuppressWarnings("unchecked")
	private boolean isColumnEmpty() {
		boolean check = true;
		for(int row=0; row <this.getRow();row++) {
			if( ((Array1D<T>) this.array[row]).length()!=0) check = false;
		}
		return check;
	}
	//Add Methods
	@Override
	public void addAt(int row, T data) {
		super.addAt(row, data);
	}
	@SuppressWarnings("unchecked")
	public void addAt(int row, int column, T data) {
		((Array1D<T>) super.array[row]).addAt(column, data);
	}
	@Override
	public void add(T data) {
		this.addAt(this.getRow(),data);
	}
	public void add(int row, T data) {
		this.addAt(row, this.getColumn(row),data);
	}
	//Remove Methods
	@Override
	public T removeAt(int row) {
		return(super.removeAt(row));
	}
	@SuppressWarnings("unchecked")
	public T removeAt(int row,int column) {
		return((Array1D<T>) this.array[row]).removeAt(column);
	}
	@Override
	public T remove(T data) {
		for(int row = 0; row <this.getRow(); row++) {
			for(int column =0; column<this.getColumn(row);column++) {
				if(this.get(row,column)==data) 
					return this.removeAt(row,column);
			}
		}
		return null;
	}
	//Swap Method
	@Override
	public void swap(int current, int next) {
		super.swap(current,next);
	}
	@SuppressWarnings("unchecked")
	public void swap(int rowIndex, int columnIndex, int rowIndex2, int columnIndex2) {
		T temp = ((Array1D<T>) this.array[rowIndex]).get(columnIndex);
		this.set(rowIndex, columnIndex, ((Array1D<T>) this.array[rowIndex2]).get(columnIndex2));
		this.set(rowIndex, columnIndex, temp);
	}
	//Cloning methods
	@SuppressWarnings("unchecked")
	public Array2D<T> clone(Array2D<T> array) {
		Array2D<T> tempArray = new Array2D<T>(array.getRow(),1);
		for(int row=0; row < array.getRow();row++) {
			Array1D<T> tempColumn = new Array1D<T>(array.getColumn(row));
			for(int column =0;column<array.getColumn(row);column++) {
				tempColumn.set(column, array.get(row,column));
			}
			tempArray.set(row, (T)tempColumn);
		}
		return tempArray;
	}
	@SuppressWarnings("unchecked")
	public T[] clone(Array1D<T> array) {
		T[] tempArray = (T[])new Object[this.getRow()];
		int index=0;
		for(int row=0; row < this.getRow();row++) {
			Array1D<T> tempColumn = new Array1D<T>(this.getColumn(row));
			for(int column =0;column<this.getColumn(row);column++) {
				tempColumn.set(column, array.get(index)); 
				index++;
			}
			tempArray[row] = (T) tempColumn;
		}
		for(int rowI = 0; rowI<this.getRow();rowI++) {
			for(int columnI = 0; columnI < this.getColumn(rowI); columnI++ ) {
				System.out.print( "["+((Array1D<T>)tempArray[rowI]).get(columnI)+"] ");
			}
		}
		return tempArray;
	}
	
	
	//toString Method
	@Override
	public String toString() {
		String output ="";
		for(int rowI = 0; rowI<this.getRow();rowI++) {
			for(int columnI = 0; columnI < this.getColumn(rowI); columnI++ ) {
				output = output + "["+this.get(rowI,columnI)+"] ";
			}
			output = (output+"\n");
		}
		return output;
	}
	//Bubble Sorting Method
	@Override
	public void bubbleSort() {
		Array1D<T> tempArray = convert1D(this.array);
		tempArray.bubbleSort();
		this.array = convert2D(tempArray);
	}
	//Insertion Sorting
	@Override
	public void insertionSort() {
		Array1D<T> tempArray = convert1D(this.array);
		tempArray.insertionSort();
		this.array = convert2D(tempArray);
	}
	//Merge Sorting Method
	@Override
	public void mergeSort() {
		Array1D<T> tempArray = convert1D(this.array);
		tempArray.mergeSort();
		this.array = convert2D(tempArray);
	}
	//MinHeapSort Method
	@Override
	public void minHeapSort() {
		Array1D<T> tempArray = convert1D(this.array);
		tempArray.minHeapSort();
		this.array = convert2D(tempArray);
	}
	//MaxHeapSort Method
	@Override
	public void maxHeapSort() {
		Array1D<T> tempArray = convert1D(this.array);
		tempArray.maxHeapSort();
		this.array = convert2D(tempArray);
	}
}