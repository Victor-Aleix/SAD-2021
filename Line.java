import java.io.*;
import java.util.ArrayList;

public class Line {

    int cursor;
    ArrayList<Character> text;
    boolean insertMode = true;

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

    public boolean isInsert(){
        return insertMode;
    }

    public void setInsert(boolean b){
        insertMode = b;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Character c : text)
            s.append(c);
        return s.toString();
    }
}