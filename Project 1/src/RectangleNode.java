import java.awt.*;

class RectangleNode implements Comparable<RectangleNode>{
    private String name; //name of the rectangle
    private Rectangle rec; //rectangle

    //constructor
    public RectangleNode(String name, int x, int y, int w, int h) {
        this.rec = new Rectangle(x,y,w,h);
        this.name = name;
    }

    //getters
    public String getName() {
        return name;
    }
    public int getX() {
        return rec.x;
    }
    public int getY() {
        return rec.y;
    }
    public int getW() {
        return rec.width;
    }
    public int getH() {
        return rec.height;
    }

    //return a string with the details of the rectangle
    @Override
    public String toString() {
        return "(" + name + ", " +
                rec.x +", "+
                rec.y +", "+
                rec.width +", "+
                rec.height +")";
    }

    //compare the names of two rectangles
    @Override
    public int compareTo(RectangleNode o) {
        return name.compareTo(o.getName());
    }
}
