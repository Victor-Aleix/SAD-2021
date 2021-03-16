import java.io.*;
import java.util.ArrayList;

public class Line {

    int cursor;
    ArrayList<Character> text;

    public Line() {
        this.cursor = 0;
        this.text = new ArrayList<Character>();
    }

    public void right() {
        if (cursor < text.size()) {
            cursor++;
        }
    }

    public void left() {
        if (cursor > 0) {
            cursor--;
        }
    }

    public void home() {
        cursor = 0;
    }

    public void end() {
        cursor = text.size();
    }

    public void delete() {
        if (cursor > 0) {
            cursor--;
            text.remove(cursor);
        }
    }

    public void supr() {
        if (cursor < text.size()) {
            text.remove(cursor);
        }
    }
}