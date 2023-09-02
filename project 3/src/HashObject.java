public class HashObject {
    private MemoryHandleHolder ID;
    private MemoryHandleHolder Seq;
    int skip;
    boolean tombstone = false;

    //constructors
    public HashObject(MemoryHandleHolder id, MemoryHandleHolder seq, int skipValue){
        ID = id;
        Seq = seq;
        skip = skipValue;
    }
    public HashObject(MemoryHandleHolder id, MemoryHandleHolder seq){
        ID =id;
        Seq = seq;
    }
    public HashObject(HashObject hash){
        ID =hash.getId();
        Seq = hash.getFull();
    }

    //getters
    public MemoryHandleHolder getId() {
        return ID;
    }
    public MemoryHandleHolder getFull() {
        return Seq;
    }
    public int getSkip(){return skip;}
    public boolean getTombstone() {
        return tombstone;
    }

    //setters
    public void setID(MemoryHandleHolder ID) {
        this.ID = ID;
    }
    public void setFull(MemoryHandleHolder seq) {
        Seq = seq;
    }
    public void setSkip(int skip) {
        this.skip = skip;
    }
    public void setTombstone(boolean tombstone) {
        this.tombstone = tombstone;
    }
}
