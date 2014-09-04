import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {

	private static int cols = 0;
	private static int rows = 0;
	private static int ticks = 0;
	static int delay = 0;
	private static JFrame lifeFrame;
	private lifePanel panel;
	private Cell[][] cell;
	private Cell[][] dummyCell;
	private JPanel[][] cellPanel;
	private static Color randomColor1;
	static Thread gameThread;
	static Timer timer;
	private static double timerTime;
	private static double timerTicks;
	private static int maxTicks = 0;

	public Game(int numRows, int numColumns, int numTicks, int delayTime) {

		cols = numColumns;
		rows = numRows;
		maxTicks = numTicks;
		delay = delayTime;
		createTimer();

		cell = new Cell[rows][cols];
		dummyCell = new Cell[rows][cols];
		cellPanel = new JPanel[rows][cols];

		randomColor1 = new Color(randyColor(), randyColor(), randyColor());

		lifeFrame = new JFrame ("Game Of Life");
		lifeFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		panel = new lifePanel(rows, cols);
		lifeFrame.getContentPane().add (panel);
		lifeFrame.pack();
		initCells();	//the cells have to be initialized before the setVisible function in the line below
		lifeFrame.setVisible(true);
		
		panel.addKeyListener(new ThingyListener());

		run();
		return;
	}

	public void initCells() {		//creates array of Cells
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				cell[y][x] = new Cell();
				cell[y][x].setAlive(randy());	//randomly makes cells alive or not
			}
		}

		draw();
	}

	public void run() {
		timer.start();
		while(ticks < maxTicks) {
			while(timer.isRunning() && ticks < maxTicks) {
				ticks++;
				next();
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {}
			}
		}
		return;
	}

	public void next() {
		judgementDay();
		redraw();
	}


	public int countNeighbours(int y, int x) {
		int neighbours = 0;
		//comparing to cell[y][x]

		if((y > 0 && y < rows-1) && (x > 0 && x < cols-1)) {

			if(cell[y-1][x].getAlive() == true) {	//1
				neighbours++;
			}
			if(cell[y-1][x+1].getAlive() == true) {	//2
				neighbours++;
			}
			if(cell[y][x+1].getAlive() == true) {	//3
				neighbours++;
			}
			if(cell[y+1][x+1].getAlive() == true) {	//4
				neighbours++;
			}
			if(cell[y+1][x].getAlive() == true) {	//5
				neighbours++;
			}
			if(cell[y+1][x-1].getAlive() == true) {	//6
				neighbours++;
			}
			if(cell[y][x-1].getAlive() == true) {	//7
				neighbours++;
			}
			if(cell[y-1][x-1].getAlive() == true) {	//8
				neighbours++;
			}
		}
		//top left
		if((y == 0) && (x == 0)) {
			if(cell[y][x+1].getAlive() == true) {	//3
				neighbours++;
			}
			if(cell[y+1][x+1].getAlive() == true) {	//4
				neighbours++;
			}
			if(cell[y+1][x].getAlive() == true) {	//5
				neighbours++;
			}
		}
		//top right
		if((y == 0) && (x == cols-1)) {
			if(cell[y+1][x].getAlive() == true) {	//5
				neighbours++;
			}
			if(cell[y+1][x-1].getAlive() == true) {	//6
				neighbours++;
			}
			if(cell[y][x-1].getAlive() == true) {	//7
				neighbours++;
			}
		}
		//bottom left
		if((y == rows-1) && (x == 0)) {
			if(cell[y-1][x].getAlive() == true) {	//1
				neighbours++;
			}
			if(cell[y-1][x+1].getAlive() == true) {	//2
				neighbours++;
			}
			if(cell[y][x+1].getAlive() == true) {	//3
				neighbours++;
			}
		}
		//bottom right
		if((y == rows-1) && (x == cols-1)) {
			if(cell[y][x-1].getAlive() == true) {	//7
				neighbours++;
			}
			if(cell[y-1][x-1].getAlive() == true) {	//8
				neighbours++;
			}
			if(cell[y-1][x].getAlive() == true) {	//1
				neighbours++;
			}
		}
		//left excluding corners
		if(x == 0 && (y > 0 && y < rows-1)) {
			if(cell[y-1][x].getAlive() == true) {	//1
				neighbours++;
			}
			if(cell[y-1][x+1].getAlive() == true) {	//2
				neighbours++;
			}
			if(cell[y][x+1].getAlive() == true) {	//3
				neighbours++;
			}
			if(cell[y+1][x+1].getAlive() == true) {	//4
				neighbours++;
			}
			if(cell[y+1][x].getAlive() == true) {	//5
				neighbours++;
			}
		}
		//top excluding corners
		if(y == 0 && (x > 0 && x < cols-1)) {
			if(cell[y][x+1].getAlive() == true) {	//3
				neighbours++;
			}
			if(cell[y+1][x+1].getAlive() == true) {	//4
				neighbours++;
			}
			if(cell[y+1][x].getAlive() == true) {	//5
				neighbours++;
			}
			if(cell[y+1][x-1].getAlive() == true) {	//6
				neighbours++;
			}
			if(cell[y][x-1].getAlive() == true) {	//7
				neighbours++;
			}
		}
		//right excluding corners
		if(x == cols-1 && (y > 0 && y < rows-1)) {
			if(cell[y+1][x].getAlive() == true) {	//5
				neighbours++;
			}
			if(cell[y+1][x-1].getAlive() == true) {	//6
				neighbours++;
			}
			if(cell[y][x-1].getAlive() == true) {	//7
				neighbours++;
			}
			if(cell[y-1][x-1].getAlive() == true) {	//8
				neighbours++;
			}
			if(cell[y-1][x].getAlive() == true) {	//1
				neighbours++;
			}
		}
		//bottom excluding corners
		if(y == rows-1 && (x > 0 && x < cols-1)) {
			if(cell[y][x-1].getAlive() == true) {	//7
				neighbours++;
			}
			if(cell[y-1][x-1].getAlive() == true) {	//8
				neighbours++;
			}
			if(cell[y-1][x].getAlive() == true) {	//1
				neighbours++;
			}
			if(cell[y-1][x+1].getAlive() == true) {	//2
				neighbours++;
			}
			if(cell[y][x+1].getAlive() == true) {	//3
				neighbours++;
			}
		}
		//return number of alive neighbours for cell[y][x]
		return neighbours;
	}

	//the rules deciding life or death!
	public void judgementDay() {	
		//create dummy cell grid so that when assigning alive or dead the changing of one cell 
		//doesn't affect the changing of its neighbours.

		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				dummyCell[y][x] = new Cell();
			}
		}

		// da rules
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				//if overcrowded, kill creature
				if(countNeighbours(y, x) > 3) {	
					dummyCell[y][x].setAlive(false);
				}
				//if alive and two or three neighbours
				if(cell[y][x].getAlive() == true && (countNeighbours(y, x) == 2 || countNeighbours(y, x) == 3)) {
					dummyCell[y][x].setAlive(true);
				}
				//if dead and two or three neighbours
				if(cell[y][x].getAlive() == false && (countNeighbours(y, x) == 2 || countNeighbours(y, x) == 3)) {
					dummyCell[y][x].setAlive(false);
				}
				//if exactly three neighbours, cell is alive
				if(countNeighbours(y, x) == 3) {
					dummyCell[y][x].setAlive(true);
				}
				if(countNeighbours(y, x) < 2) {
					dummyCell[y][x].setAlive(false);
				}
			}
		}

		//assign values of dummyCells to regular cell grid
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				cell[y][x].setAlive(dummyCell[y][x].getAlive());
			}
		}
	}

	//initial draw
	public void draw() {
		for(int j = 0; j < rows; j++) {
			for(int i = 0; i < cols; i++) {	
				cellPanel[j][i] = new JPanel();
				if(cell[j][i].getAlive() == true)
					cellPanel[j][i].setBackground(randomColor1);
				panel.add(cellPanel[j][i]);
			}
		}
	}
	//continuous redraw
	public void redraw() {
		for(int j = 0; j < rows; j++) {
			for(int i = 0; i < cols; i++) {	
				cellPanel[j][i].setBackground(new Color(230, 230, 230));
			}
		}
		for(int j = 0; j < rows; j++) {
			for(int i = 0; i < cols; i++) {	
				if(cell[j][i].getAlive() == true)
					cellPanel[j][i].setBackground(randomColor1);
			}
		}
	}

	//random boolean generator
	public boolean randy() {		//generates either true or false randomly
		Random randy = new Random();
		return randy.nextBoolean();
	}
	//random colour
	public int randyColor() {		//generates either true or false randomly
		Random randy = new Random();
		return randy.nextInt(200);
	}
	//listeners
	private class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			timerTicks++;
			timerTime = ((double) timerTicks) / 10;

		}
	}

	public void createTimer() {
		timer = new Timer(delay, new TimerListener());
		timer.start();
	}

	private class ThingyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {	
			int keyPress = e.getKeyCode();	
			if (keyPress == 27) {			//esc
				ticks = maxTicks;
				lifeFrame.setVisible(false); 
				lifeFrame.dispose();
			} else if (keyPress == 32) {	//space
				if(timer.isRunning())
					timer.stop();
				else
					timer.start();
			} else if (keyPress == 83) {	//s
				next();
			}
		}



		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

}
