public class RecordMinHeap <T extends Comparable>{
    private T[] heapArray;
    private int heapSize;
    private int avaliableHeapSize;

    public RecordMinHeap() {
        heapArray = (T[])new Comparable[2];
        heapSize = 0;
        avaliableHeapSize = 0;
    }

    //build the heap by passing an unsorted array
    public void buildMinHeap(T[] array) {
        heapArray = (T[])new Comparable[2];//why 2
        heapSize = 0;
        avaliableHeapSize = 0;
        for (int i = 0; i < array.length; i++) {
            insert(array[i]);
        }
    }

    public void resizeArray() {
        int newLength = heapArray.length * 2;
        T[] newArray = (T[])new Comparable[newLength];
        if (newArray != null) {
            // Copy from existing array to new array
            for (int i = 0; i < heapArray.length; i++) {
                newArray[i] = heapArray[i];
            }
            // Set the reference to the new array
            heapArray = newArray;
        }
    }

    public void percolateUp(int nodeIndex) {
        while (nodeIndex > 0) {
            // get the parent node's index
            int parentIndex = (nodeIndex - 1) / 2;
            // Check for a violation of the min heap property
            if (heapArray[nodeIndex].compareTo(heapArray[parentIndex]) == 1) {
                // No violation, so percolate up is done
                return;
            } else {
                // Swap heapArray[nodeIndex] and heapArray[parentIndex]
                T temp = heapArray[nodeIndex];
                heapArray[nodeIndex] = heapArray[parentIndex];
                heapArray[parentIndex] = temp;

                // Continue the loop from the parent node
                nodeIndex = parentIndex;
            }
        }
    }

    public void percolateDown(int nodeIndex) {
        int childIndex = 2 * nodeIndex + 1;
        T value = heapArray[nodeIndex];
        while (childIndex < heapSize) {
            // Find the min among the node and all the node's children
            T minValue = value;
            int minIndex = -1;
            int i = 0;
            while (i < 2 && i + childIndex < heapSize) {
                if (heapArray[i + childIndex].compareTo(minValue) == -1) {
                    minValue = heapArray[i + childIndex];
                    minIndex = i + childIndex;
                }
                i++;
            }
            // Check for a violation of the min heap property
            if (minValue == value) {
                return;
            } else {
                // Swap heapArray[nodeIndex] and heapArray[minIndex]
                T temp = heapArray[nodeIndex];
                heapArray[nodeIndex] = heapArray[minIndex];
                heapArray[minIndex] = temp;

                // Continue loop from the min index node
                nodeIndex = minIndex;
                childIndex = 2 * nodeIndex + 1;
            }
        }
    }

    private void SwapPercolateDown(int nodeIndex) {
        int childIndex = 2 * nodeIndex + 1;
        T value = heapArray[nodeIndex];
        while (childIndex < avaliableHeapSize) {
            // Find the min among the node and all the node's children
            T minValue = value;
            int minIndex = -1;
            int i = 0;
            while (i < 2 && i + childIndex < avaliableHeapSize) {
                if (heapArray[i + childIndex].compareTo(minValue) == -1) {
                    minValue = heapArray[i + childIndex];
                    minIndex = i + childIndex;
                }
                i++;
            }
            // Check for a violation of the min heap property
            if (minValue == value) {
                return;
            } else {
                // Swap heapArray[nodeIndex] and heapArray[minIndex]
                T temp = heapArray[nodeIndex];
                heapArray[nodeIndex] = heapArray[minIndex];
                heapArray[minIndex] = temp;

                // Continue loop from the min index node
                nodeIndex = minIndex;
                childIndex = 2 * nodeIndex + 1;
            }
        }
    }

    //insert a value into the heap
    public void insert(T value) {
        // Resize if needed
        if (heapSize == heapArray.length) {
            resizeArray();
        }
        // Add the new value to the end of the array
        heapArray[heapSize] = value;
        heapSize++;
        avaliableHeapSize++;
        // Percolate up from the last index to restore heap property.
        percolateUp(heapSize - 1);
    }


    public void insertNew(T value){
        heapArray[0] = value;
        heapSize++;
        avaliableHeapSize++;
        // Percolate down to restore heap property.
        /////////////////percolateDown( 0);
        SwapPercolateDown( 0);
    }


    public void insertSwap(T value) {
        // Swap with the last element in array
        //////////////////////////////this got deleted here: heapArray[0] = value;
        T temp = heapArray[avaliableHeapSize];
        heapArray[avaliableHeapSize] = value;
        heapArray[0] = temp;
        heapSize++;
        // Percolate up from the last index to restore heap property.
        SwapPercolateDown(0);

    }

    //remove the smallest from the heap
    public T remove() {

        // Save the min value from the root of the heap.
        T minValue = heapArray[0];

        // Move the last item in the array into index 0.
        T replaceValue = heapArray[heapSize - 1];
        heapSize--;
        if (heapSize > 0) {
            heapArray[0] = replaceValue;
            // Percolate down to restorxe min heap property.
            percolateDown(0);
        }
        // Return the min value
        return minValue;
    }

    public T GetminVal() {
        // Save the min value from the root of the heap.
        T minValue = heapArray[0];
        heapSize--;
        avaliableHeapSize--;
        // Return the min value
        return minValue;
    }


    //print a string with the heap array contents
    public void print() {
        System.out.print("The Min Heap is: [");
        for (int i =0; i<heapSize;i++){
            System.out.print(heapArray[i].toString() + " ");
        }
        System.out.println("]");
    }

    public T getInedex(int index) {
        return heapArray[index];
    }

    //get the avaliable size of the heap
    public int getAvaliableHeapSize() {
        return avaliableHeapSize;
    }
    //set the available size of the heap
    public void setAvaliableHeapSize(int num){
        avaliableHeapSize = num;
    }
    //get the size of the heap
    public int getHeapSize() {
        return heapSize;
    }
}