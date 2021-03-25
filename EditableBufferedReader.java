import java.io.*;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

public class EditableBufferedReader extends BufferedReader {

	public static final int RIGHT = 12340;
	public static final int LEFT = 12341;
	public static final int HOME = 12342;
	public static final int END = 12343;
	public static final int INSERT = 12344;
	public static final int BACKSPACE = 12345;

	public EditableBufferedReader(InputStreamReader in) {
		super(in);
		setRaw();
	}

	public static void setRaw() {
		try {
			Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "stty -echo raw </dev/tty" });
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void unsetRaw() {
		try {
			Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "stty echo cooked </dev/tty" });
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public int read() {
		int c = 0;
		try {
			c = super.read();
			if (c == 27) {
				super.read();
				switch (c = super.read()) {
				case 'D':
					c = LEFT;
					break;
				case 'C':
					c = RIGHT;
					break;
				case 'H':
					c = HOME;
					break;
				case 'F':
					c = END;
					break;
				case '2':
					super.read();
					c = INSERT;
					break;
				}
			} else if (c == 127)
				c = BACKSPACE;
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			return c;
		}
	}

	public String readLine() throws IOException {
		Line l = new Line();
		StringBuilder s = new StringBuilder();
		int c = this.read();
		while (c != '\r') {
			switch (c) {
			case LEFT:
				l.left();
				System.out.print("\033[D");
				break;
			case RIGHT:
				l.right();
				System.out.print("\033[C");
				break;
			case HOME:
				l.home();
				System.out.print("\033["+l.cursor +"D");
				break;
			case END:
				l.end();
				System.out.print("\033["+(l.text.size()-l.cursor)+"C");
				break;
			case BACKSPACE:
				l.delete();
				System.out.print("\010\040\010");
				break;
			case INSERT:
				if (l.isInsert())
					l.setInsert(false);
				else
					l.setInsert(true);
				break;
			default:
				if (l.isInsert()) {
					l.text.add(l.cursor, (char) c);
					l.cursor++;
					System.out.print((char) c);
				} else {
					if (l.cursor == l.text.size()) {
						l.text.add(l.cursor, (char) c);
						l.cursor++;
						System.out.print((char) c);
					} else {
						l.text.set(l.cursor, (char) c);
						l.cursor++;
						System.out.print("\033[@");
						System.out.print((char) c);
					}
				}
				break;
			}
			c = this.read();
		}
		
		unsetRaw();
		return l.toString();

	}
}
