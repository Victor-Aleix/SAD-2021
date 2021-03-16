import java.io.*;
import java.util.ArrayList;

public class Line {

    int cursor;
    ArrayList<Character> text;

    public Line() {

        cursor = 0;
        ArrayList<Character> text = new ArrayList<Character>();

    }

    public void right() {
        if (cursor < text.size()) {
            cursor++;
            // System.out.print("\033[C");
        }
    }

    public void left() {
        if (cursor > 0) {
            cursor--;
            // System.out.print("\033[D");
        }
    }

    public void home() {
        // int aux = cursor;
        cursor = 0;
        // System.out.print("\033[" + aux + "D");
    }

    public void end() {
        // int aux = line.size() - cursor;
        cursor = text.size();
        // System.out.print("\033[" + aux + "C");
    }

    public void delete() {
        if (cursor > 0) {
            cursor--;
            text.remove(cursor);
            // System.out.print("\033[D");
            // System.out.print("\033[P"); // redefine el comportamiento de la letra
        }
    }

    public void supr() {
        if (cursor < text.size()) {
            text.remove(cursor);
            // System.out.print("\033[P");
        }
    }

}