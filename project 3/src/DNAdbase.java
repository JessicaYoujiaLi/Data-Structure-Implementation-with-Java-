import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

//this program reads a file from the command line and sorts DNA sequences into a hash table
//made using intellij on a mac and windows pc
//date completed: Dec 2, 2021

public class DNAdbase {


    public static void main(String [] args) throws IOException {
        Scanner fileReader = openInputFile(args[0]);
        int hashSize = Integer.parseInt(args[1]);
        if (hashSize%32 !=0){
            //check if the hash size is a multiple of 32
            System.out.println("The hash size need to be the multiple of 32");
            System.exit(0);
        }
        MemoryManager memManager = new MemoryManager(args[2],hashSize);
        ProcessFile(fileReader, memManager);
    }

    //open an input file
    static public Scanner openInputFile(String filename) {
        try {
            //open a new file
            FileInputStream myFile = new FileInputStream(filename);
            //read the file
            Scanner fileReader = new Scanner(myFile);
            return fileReader;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(-1);
        }
        return null;
    }


    //processing the commands from the file
    static public void ProcessFile(Scanner fileReader, MemoryManager memManager) throws IOException {
        //create an arraylist for the string of data
        ArrayList<String> data = new ArrayList<>();
        //read in the commands by line
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            line = line.replaceAll("\\s+", " ");
            line = line.trim();

            //if the start of the line is not r, s, i, or p, concatenate it to the end of the prev line
            if ( (!line.startsWith("r") && !line.startsWith("s") && !line.startsWith("i") && !line.startsWith("p") )&& line.length() !=0) {
                if (data.size() >0 ) {
                    data.set(data.size()-1,data.get(data.size()-1) +" " + line);
                } else {
                    data.add(line);
                }

            }
            if (!line.isEmpty()) {
                data.add(line.trim());
            }
        }
        fileReader.close();
        //split the string by space

        for (int i = 0; i < data.size(); i++) {
            String[] split = data.get(i).split(" ");

            //reading the insert commands
            if (split[0].equals("insert")) {
                int k = Integer.parseInt(split[2]);
                memManager.insert(split[1],split[3],k);
            }

            //reading the search commands
            else if (split[0].equals("search")) {
                memManager.search(split[1]);
                String search = memManager.search(split[1]);
                if (search!=null){
                    System.out.println("Sequence Found: "+search);
                }else {
                    System.out.println("SequenceID "+ split[1] + " not found");
                }
            }

            //reading the remove commands
            if (split[0].equals("remove")) {
                System.out.println("Sequence Removed "+split[1]+": ");
                String RemovedSequence = memManager.remove(split[1]);
                System.out.println(RemovedSequence);
            }

            //reading the print comments
            if (split[0].equals("print")) {
                memManager.print();
            }
        }
    }
}
