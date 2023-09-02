// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
public class ExternalSorting {
    public static void main(String[] arg) {

        //initialze the input and output buffers
        Record[] InputBuffer = new Record[512];
        Record[] OutputBuffer = new Record[512];

        //intialize the size of the working memory
        int WorkMemSize = 8192*8;

        File file = new File(arg[0]);
        //read the entire file into an array called bytes
        byte[] bytes = readContentIntoByteArray(file);

        //print the length of  the bytes array
        //System.out.println(bytes.length);

        //create the working memory
        RecordMinHeap mHeap = new RecordMinHeap();


        //fill the working memory for the first time from the file
        for (int i = 0; i < WorkMemSize; i = i + 16) {
            byte[] temp = Arrays.copyOfRange(bytes, i, i + 16);
            Record re = new Record(temp);
            mHeap.insert(re);
        }

        //fill the remainingBytes array with the remaining bytes
        // that were not put into the working mem for the first time
        byte[] remainingBytes = Arrays.copyOfRange(bytes, WorkMemSize, bytes.length);

        int runCounter = 0;
        //replacement selection until there is nothing left in the input file
        while (remainingBytes.length > 0) {
            //write what is in the output buffer to the file until the available heap size is 0
            while (mHeap.getAvaliableHeapSize() > 0) {
                //fill the input buffer from the remaining integers array
                if (remainingBytes.length ==0) {
                    break;
                }
                int p = 0; // temp pointer for the input buffer
                int numberofRecords = 0;

                for (int i = 0; i < 8192; i = i + 16) {
                    byte[] temp = Arrays.copyOfRange(remainingBytes, i, i + 16);
                    Record re = new Record(temp);
                    //fill the input buffer
                    InputBuffer[p] = re;
                    numberofRecords++;
                    p++;
                }
                //update remaining bytes
                remainingBytes = Arrays.copyOfRange(remainingBytes, InputBuffer.length*16, remainingBytes.length);


                //fill the output buffer from the working mem and inputbuffer
                //do the thing

                for (int i=0; i < OutputBuffer.length;i++){
                    OutputBuffer[i] = (Record) mHeap.GetminVal();
                    //removed
                    if (InputBuffer[i].getKey() < (OutputBuffer[i].getKey()) ) {
                        mHeap.insertSwap(InputBuffer[i]);
                        //swapped
                    }else {
                        mHeap.insertNew(InputBuffer[i]);
                        //inserted
                    }
                    if (mHeap.getAvaliableHeapSize() <= 0) {
                        //reset the heap and the hidden object back
                        break;
                    }
                }

                //the output buffer is now full

                //write what's in the output buffer into the file
                for (int i = 0; i < OutputBuffer.length; i++) {
                    try {
                        // create a writer
                        FileOutputStream fos = new FileOutputStream("output.bin", true);
                        // write OutputBuffer[i] to the output file
                        if (OutputBuffer[i].getCompleteRecord() != null) {
                            //write OutputBuffer[k] to the output file
                            fos.write(OutputBuffer[i].getCompleteRecord());
                        }
                        // close the writer
                        fos.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                //clear the output buffer by setting its elements to null
                for (int i = 0; i < OutputBuffer.length; i++) {
                    Record temp = new Record(null);
                    OutputBuffer[i] = temp;
                }
            }

            //the available min heap is now 0
            runCounter++;

            //percolate up to sort any values into the heap that aren't part of the availableminheap
            ////////////////////for (int i = 0; i < mHeap.getHeapSize()- mHeap.getAvaliableHeapSize(); i++) {
            for (int i = mHeap.getHeapSize()-1; i > -1; i--) {
                mHeap.percolateUp(i);
            }

            //reset the available minheap size
            mHeap.setAvaliableHeapSize(512*8);

        }


        //this is wrong
        /*   //percolate up to sort any values into the heap that aren't part of the availableminheap
        for (int i = mHeap.getHeapSize()-1; i > -1; i--) {
            mHeap.percolateUp(i);
        }*/
        int outputPointer = 0;
        int HiddenObjects = mHeap.getHeapSize()- mHeap.getAvaliableHeapSize();
        //write what is in the available min heap to the file
        while (mHeap.getAvaliableHeapSize()>0) {

            //write from the output buffer to the file if it gets full

            if (outputPointer == (OutputBuffer.length-1)) {
                //write the output buffer to the file
                try {
                    // create a writer
                    FileOutputStream fos = new FileOutputStream("output.bin", true);
                    // write data to file

                    for (int k = 0; k < OutputBuffer.length; k++) {
                        if (OutputBuffer[k].getCompleteRecord() != null) {
                            //write OutputBuffer[k] to the output file
                            fos.write(OutputBuffer[k].getCompleteRecord());
                        }
                    }
                    // close the writer
                    fos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //clear the output buffer by setting its elements to null
                for (int o = 0; o < OutputBuffer.length; o++) {
                    Record temp = new Record(null);
                    OutputBuffer[o] = temp;
                }
                outputPointer = 0;
            }
            //keep adding records from the min heap to the output buffer
            OutputBuffer[outputPointer] = (Record) mHeap.GetminVal();
            outputPointer++;
        }
        //write anything remaining in the output buffer to the file
        try {
            // create a writer
            FileOutputStream fos = new FileOutputStream("output.bin", true);
            // write data to file

            for (int k = 0; k < OutputBuffer.length; k++) {
                if (OutputBuffer[k].getCompleteRecord() != null) {
                    //write OutputBuffer[k] to the output file
                    fos.write(OutputBuffer[k].getCompleteRecord());
                }
            }
            // close the writer
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //clear the output buffer by setting its elements to null
        for (int o = 0; o < OutputBuffer.length; o++) {
            Record temp = new Record(null);
            OutputBuffer[o] = temp;
        }

        //insert all the hidden records into a new minheap
        RecordMinHeap newHeap = new RecordMinHeap();
        for (int i  = HiddenObjects-1; i > -1; i--) {
            newHeap.insert(mHeap.getInedex(i));
        }


        //write what is in the working memory to the file
        while (newHeap.getHeapSize()>0){
            //clear the output buffer by setting its element to null
            for (int i = 0; i < OutputBuffer.length; i++) {
                Record temp = new Record(null);
                OutputBuffer[i] = temp;
            }
            for (int j =0; j< OutputBuffer.length; j++){
                if (newHeap.getHeapSize() <= 0) {
                    break;
                }
                //set the output buffer to the values removed from the working memory
                OutputBuffer[j] = (Record) newHeap.GetminVal();
            }
            try {
                // create a writer
                FileOutputStream fos = new FileOutputStream("output.bin", true);
                // write data to file

                for (int k = 0; k < OutputBuffer.length; k++) {
                    if (OutputBuffer[k] != null) {
                        //write OutputBuffer[k] to the output file
                        fos.write(OutputBuffer[k].getCompleteRecord());
                    }
                }
                // close the writer
                fos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        runCounter++;
    }
    //method for reading the input file
    private static byte[] readContentIntoByteArray(File file) {
        FileInputStream fileInputStream = null;
        //byte[] b = new byte[(int) file.length()];
        byte[] b = new byte[(int) file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
}