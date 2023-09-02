import java.nio.ByteBuffer;

/**
 * Holds a single record
 * 
 * @author XXX
 * @version XX-XX-XXXX
 */
public class Record implements Comparable<Record> {

    private byte[] completeRecord;
    private int flag; // only needed for grading


    /**
     * The constructor for the Record class
     * 
     * @param record
     *            The byte for this object
     */
    public Record(byte[] record) {
        completeRecord = record;
        this.flag = 0;
    }


    /**
     * The constructor to tak a byte array and flag
     * 
     * @param record
     *            the array
     * @param flagOfHeap
     *            location of heap
     */
    public Record(byte[] record, int flagOfHeap) {
        completeRecord = record;
        flag = flagOfHeap;
    }


    /**
     * Returns the object's ID
     * 
     * @return the ID
     */
    public long getID() {
        ByteBuffer buff = ByteBuffer.wrap(completeRecord);
        return buff.getLong();
    }


    /**
     * Returns the object's key
     * 
     * @return the key
     */
    public double getKey() {
        ByteBuffer buff = ByteBuffer.wrap(completeRecord);
        return buff.getDouble(8);
    }


    /**
     * returns the complete record
     * 
     * @return complete record
     */
    public byte[] getCompleteRecord() {
        return completeRecord;
    }


    /**
     * Returns the flag
     * 
     * @return the flag
     */
    public int getFlag() {
        return this.flag;
    }


    /**
     * Sets the flag
     * 
     * @param flagToSet
     *            is the flag to set
     */
    public void setFlag(int flagToSet) {
        this.flag = flagToSet;
    }


    /**
     * the array of bytes to set
     * 
     * @param newByteArray
     *            array of bytes
     */
    public void setCompleteRecord(byte[] newByteArray) {
        this.completeRecord = newByteArray;
    }


    /**
     * Compare Two Records based on their keys
     * 
     * @param o
     *            - The Record to be compared.
     * @return A negative integer, zero, or a positive integer as this employee
     *         is less than, equal to, or greater than the supplied record
     *         object.
     */
    @Override
    public int compareTo(Record o) {
        if (Double.isNaN(o.getKey())) {
            return 1;
        } else if (Double.isNaN(this.getKey())) {
            return -1;
        } else {
            return Double.compare(this.getKey(), o.getKey());
        }
    }

    /**
     * Outputs the record as a String
     * 
     * @return a string of what the record contains
     */
    public String toString() {
        return "" + this.getKey();
    }
}