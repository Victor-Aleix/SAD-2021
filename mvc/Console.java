import java.io.*;

public class Console{
	
	public static final int RIGHT = 12340;
	public static final int LEFT = 12341;
	public static final int HOME = 12342;
	public static final int END = 12343;
	public static final int INSERT = 12344;
	public static final int BACKSPACE = 12345;
	public static final int SUP = 12346;

	public Line l;
	
	public Console(Line l){
		this.l=l;
	}

	public void view(int c){
		switch (c) {
			case LEFT:
				l.left();
				System.out.print("\033[D");
				break;
			case RIGHT:
				l.right();
				if(l.cursor<l.text.size())
					System.out.print("\033[C");
				break;
			case HOME:
				System.out.print("\033["+l.cursor+"D");
				l.home();
				break;
			case END:
				System.out.print("\033["+(l.text.size()-l.cursor)+"C");
				l.end();
				break;
			case BACKSPACE:
				l.delete();
				System.out.print("\033[D");
				System.out.print("\033[P");
				break;
			case SUP:
				l.supr();
				System.out.print("\033[P");
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
					System.out.print("\033[@");
					System.out.print((char) c);
				} else {
					if (l.cursor == l.text.size()) {
						l.text.add(l.cursor, (char) c);
						l.cursor++;
						System.out.print((char) c);
					} else {
						l.text.set(l.cursor, (char) c);
						l.cursor++;
						System.out.print((char) c);
					}
				}
				break;
			}

	}

}
