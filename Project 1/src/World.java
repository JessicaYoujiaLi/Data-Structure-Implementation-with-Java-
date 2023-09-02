import java.util.Iterator;

public class World {
    public BST<RectangleNode> tree; //create the BST
    public World() {
        tree = new BST<>();
    }

    //insert a rectangle by its dimensions
    public void insert(String name, int x, int y, int w, int h) {
        RectangleNode rec = new RectangleNode(name, x, y, w, h);
        if (x < 0 || y < 0) { //if rectangle has a negative position, reject it
            System.out.println("Rectangle rejected: " + rec.toString());
        } else if (w <= 0 || h <= 0) { //check that width and height are not 0
            System.out.println("Rectangle rejected: " + rec.toString());
        } else if (x + w > 1024 || y + h > 1024) { //make sure rectangle is within the boundaries
            System.out.println("Rectangle rejected: " + rec.toString());
        } else { //accept the rectangle
            System.out.println("Rectangle accepted: " + rec.toString());
            tree.insert(rec);
        }

    }

    //remove a rectangle with the specified name
    public void remove(String name) {
        Iterator<RectangleNode> iter = tree.iterator();
        boolean removed = false; //boolean to check if the rectangle has been removed
        while (iter.hasNext()) {
            RectangleNode rec = iter.next();
            if (rec.getName().compareTo(name)==0) {
                removed = true;
                tree.remove(rec);
            }
        }
        //if the rectangle is not found to remove, output a reject message
        if (!removed) {
            System.out.println("Rectangle rejected: " + name);
        }
    }

    //remove a rectangle with the specified dimensions
    public void remove(int x, int y, int w, int h) {
        Iterator<RectangleNode> iter = tree.iterator();
        boolean removed = false; //boolean to check if the rectangle has been removed
        while (iter.hasNext()) {
            RectangleNode rec = iter.next();
            if ( (rec.getX() == x) && (rec.getY() == y) && (rec.getH() == h) && (rec.getW() == w) ) {
                removed = true;
                tree.remove(rec);
                break; //break the loop once the rectangle is removed
            }
        }
        //if the rectangle is not found to remove, output a message
        if (!removed) {
            System.out.println("Rectangle rejected: (" + x + ", " + y + ", " + w + ", " + h + ") ");
        }
    }

    //search a region for rectangles
    public void RegionSearch(int x, int y, int w, int h) {
        if (w == 0 || h==0) { //make the rectangle is not a line
            System.out.println("Rectangle rejected: (" + x + ", " + y + ", " + w + ", " + h + ")");
        } else {
            System.out.println("Rectangles intersecting region (" + x + ", " + y + ", " + w + ", " + h + "): ");
            Iterator<RectangleNode> iter = tree.iterator();
            while (iter.hasNext()) {
                boolean intersect = true;//boolean to check if there is an intersection
                RectangleNode rec = iter.next();
                if (x > rec.getX()+rec.getW() || rec.getX() > x+w) {//if the rectangles' x-axis don't overlap, no intersection
                    intersect = false;
                }
                if (y-h > rec.getY() || rec.getY()-rec.getH() > y) {//if the rectangles' y-axis don't overlap, no intersection
                    intersect = false;
                }
                if (intersect) {//they intersect
                    System.out.println(rec.toString());
                }
            }
        }
    }

    //check for any intersections of the inserted rectangles
    public void intersections() {
        System.out.println("Intersection Pairs:");
        Iterator<RectangleNode> iter = tree.iterator();
        while (iter.hasNext()) {
            Iterator<RectangleNode> itera = tree.iterator();
            RectangleNode rec = iter.next();
            while (itera.hasNext()) {
                RectangleNode rect = itera.next();
                boolean intersect = true;///boolean to check if there is an intersection
                if (rect.getX() > rec.getX()+rec.getW() || rec.getX() > rect.getX()+rect.getW()) {//if the rectangles' x-axis don't overlap, no intersection
                    intersect = false;
                }
                if (rect.getY()-rect.getH() > rec.getY() || rec.getY()-rec.getH() > rect.getY()) {//if the rectangles' y-axis don't overlap, no intersection
                    intersect = false;
                }
                if (intersect && ( (rec.getX() != rect.getX()) && (rec.getY() != rect.getY()) && (rec.getH() != rect.getH()) && (rec.getW() != rect.getW())) ){//they intersect
                    System.out.println(rec.toString() + " : " + rect.toString());

                }
            }
        }
    }

    //search for a rectangle with the specified name
    public void search(String name){
        Iterator<RectangleNode> iter = tree.iterator();
        boolean found = false;//boolean to check if the rectangle with that name has been found
        while (iter.hasNext()) {
            RectangleNode rec = iter.next();
            if (rec.getName().compareTo(name)==0){
                found = true;
                System.out.println("Rectangle found: "+rec.toString());
            }
        }
        //if the rectangle is not found, output a message
        if (!found){
            System.out.println("Rectangle not found: " + name);
        }

    }

    //print everything in the BST
    public void dump() {
        Iterator<RectangleNode> iter = tree.iterator();
        System.out.println("BST dump: ");
        if (tree.size()==0) {
            System.out.println("Node has depth 0, Value (null)");
        }
        while (iter.hasNext()) {
            RectangleNode rec = iter.next();
            System.out.println("Node has depth " + tree.getDepth(tree.getRoot(), rec) + ", Value " + rec.toString());
        }
        System.out.println("BST size: " + tree.size());
    }
}