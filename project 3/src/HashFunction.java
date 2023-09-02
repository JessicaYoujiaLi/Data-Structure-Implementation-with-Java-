public class HashFunction {

    private int hashSize;//size of the hash tble
    private HashObject hashtable[];//the hashtable

    //constructor
    public HashFunction(int Size){
        this.hashSize = Size;
        this.hashtable = new HashObject[hashSize];
        //initializes all slots to null
        for (int i=0; i<hashSize;i++){
            hashtable[i]=null; } }

    //print the full hashtable
    public HashObject[] print(){
        return this.hashtable;
    }

    public void insert(String ID,HashObject hash){
        int slot = (int)sfold(ID,this.hashSize);//the index in the hashtable
        int bucket = (int) slot/32;//the bucket number
        int startPos = bucket*32;//start index of the bucket in the hashtable

        if (hashtable[slot] == null || hashtable[slot].getTombstone()){
            //if the slot is open, put the hash object there
            hashtable[slot] =hash;
        }else {
            //if the slot is full, check all of the remaining slots in the bucket
            int counter = 0;
            for (int i =0; i<32;i++){
                slot++;
                counter++;
                hash.setSkip(counter);
                if (hashtable[slot] == null || hashtable[slot].getTombstone()){
                    //if it finds an empty slot, put the hash object there
                    hashtable[slot] =hash;
                    break; }
                if ((slot+1)%32==0){
                    //if it gets to the end of the bucket, loop back around to the start index
                    slot=startPos; } } } }

    public void remove(String id,int num){
        int slot = (int)sfold(id,this.hashSize);
        int bucket = (int) (slot/32);//the bucket number
        int startPos = bucket*32;//the start index of the bucket
        int endPos = startPos+31;//the end index of the bucket
        if (slot+num<=endPos){
            if (hashtable[slot] != null || !hashtable[slot].getTombstone()){
                //if the slot is not empty or there is not tombstone, then put a tombstone
                hashtable[slot+num].setTombstone(true); } }
        else {
            int wrap = slot+num-endPos;
            int wrapPos = startPos+wrap;
            if (hashtable[slot] != null || !hashtable[slot].getTombstone()){
                hashtable[wrapPos].setTombstone(true); } } }

    public HashObject search(String id, int num){
        int slot = (int)sfold(id,this.hashSize);
        int bucket = (int) (slot/32);
        int startPos = bucket*32;
        int endPos = startPos+31;

        if (slot+num<=endPos){
            if (hashtable[slot] == null || hashtable[slot].getTombstone()){
                //if the value is not found, return null
                return null; }
            else {
                //found it!!!!!!
                return hashtable[slot+num]; } }
        else {
            //start back at the beginning of the bucket
            int wrap = slot+num-endPos;
            int wrapPos = startPos+wrap;
            if (hashtable[wrapPos] == null || hashtable[wrapPos].getTombstone()){
                return null; }
            else {
                return hashtable[wrapPos]; } } }


    public long sfold(String s, int M) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char c[] = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256; } }
        char c[] = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256; }
        sum = (sum * sum) >> 8;
        return(Math.abs(sum) % M); } }

