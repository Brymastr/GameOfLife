import java.util.Scanner;

public class playlife {

	private static Game game;
	private static String defaults;
	private static int rows;
	private static int columns;
	private static int maxTicks;
	private static int delay;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		p("Play game with defaults? (y/n): ");
		defaults = scan.next();
		if(defaults.substring(0, 1).equals("y")) {
			rows = 30; columns = 50; maxTicks = 200; delay = 100;
		} else {

			p("Input the size of the board(min 10, max 50). ");
			p("columns: ");
			columns = scan.nextInt();
			while(columns < 10 || columns > 50) {
				p("Please enter an int between 10 and 50: ");
				columns = scan.nextInt();
			}
			p("rows: ");
			rows = scan.nextInt();
			while(rows < 10 || rows > 50) {
				p("Please enter an int between 10 and 50: ");
				rows = scan.nextInt();
			}
			p("ticks (larger is longer): ");
			maxTicks = scan.nextInt();
			while(maxTicks < 0) {
				p("Please enter an int above 0: ");
				maxTicks = scan.nextInt();
			}
			p("speed (smaller is faster): ");
			delay = scan.nextInt();
			while(delay < 0) {
				p("Please enter an int: ");
				maxTicks = scan.nextInt();
			}
		}
		p("Controls: \n\tSpace = pause/play\n\tS Key = next tick\n\tEsc Key = Exit Game");
		p("\nCreating game...");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {}
		p("Play Life!");
		//initialize game
		setGame(new Game(rows, columns, maxTicks, delay));
		return;

	}
	public static void p(String s) {
		System.out.print("\n" + s);
	}
	public static Game getGame() {
		return game;
	}
	public static void setGame(Game game) {
		playlife.game = game;
	}
}
