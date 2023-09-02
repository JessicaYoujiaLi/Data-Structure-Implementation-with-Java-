import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.FileInputStream;
import java.util.Scanner;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.ForLoopTree;
import org.w3c.dom.css.Rect;

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


//Operating systems used: MacOS and Windows 10
//Compiler: IntelliJ
//Date completed: Sept 27, 2021

//This program creates a Binary Search Tree to store and sort a collection of nodes. Rectangles of various sizes and
//positions can be inserted and removed. It can also search a region for rectangles and determine what rectangles intersect.
public class Rectangle1 {
    //open an input file
    static public Scanner openInputFile(String[] args) {
        try {
            //open a new file
            FileInputStream myFile = new
            FileInputStream(args[0]);
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
    static public void ProcessFile(Scanner fileReader, World world) {
        //create an arraylist for the string of data
        ArrayList<String> data = new ArrayList<>();
        //read in the commands by line
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            line = line.replaceAll("\\s+", " ");
            line = line.trim();
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
                if (split.length!=6) {
                    System.out.println("Invalid Insert Parameter Length: " + split.length);
                } else {
                    String name = split[1];
                    int x = Integer.parseInt(split[2]);
                    int y = Integer.parseInt(split[3]);
                    int w = Integer.parseInt(split[4]);
                    int h = Integer.parseInt(split[5]);
                    world.insert(name, x, y, w, h);
                }
            }
            //reading the dump commands
            else if (split[0].equals("dump")) {
                world.dump();
            }
            //reading the search commands
            else if (split[0].equals("search")) {
                if (split.length!=2) {
                    System.out.println("Invalid Search Parameter Length: " + split.length);
                } else {
                    String name = split[1];
                    world.search(name);
                }

            }
            //reading the intersection commands
            else if (split[0].equals("intersections")) {
                world.intersections();
            }
            //reading the region search commands
            else if (split[0].equals("regionsearch")) {
                if (split.length!=5) {
                    System.out.println("Invalid Region Search Parameter Length: " + split.length);
                } else {
                    int x = Integer.parseInt(split[1]);
                    int y = Integer.parseInt(split[2]);
                    int w = Integer.parseInt(split[3]);
                    int h = Integer.parseInt(split[4]);
                    world.RegionSearch(x, y, w, h);
                }
            }
            //reading the remove commands
            else if (split[0].equals("remove")) {
                //remove by x y w h
                if ( !((split.length ==5) || (split.length==2)) ) {
                    System.out.println("Invalid Remove Parameter Length: " + split.length);
                } else if (isNumeric(split[1])) {
                    int x = Integer.parseInt(split[1]);
                    int y = Integer.parseInt(split[2]);
                    int w = Integer.parseInt(split[3]);
                    int h = Integer.parseInt(split[4]);
                    world.remove(x, y, w, h);
                }
                //remove by name
                else {
                    world.remove(split[1]);
                }
            } else {
                System.out.println("Invalid Command. Please Check Again");
            }
        }
    }
    //check if a string is a number
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {

            return false;
        }
        return true;
    }
    //main operations
    public static void main(String[] args) {
        World world = new World(); //create the world
        Scanner fileReader = openInputFile(args); //create the scanner to read the file
        ProcessFile(fileReader, world); //process the commands from the file
    }
}