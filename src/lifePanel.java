import java.awt.*;
import javax.swing.*;

public class lifePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public lifePanel(int cellRows, int cellCols) {

		setLayout(new GridLayout(cellRows, cellCols, 2, 2));

		setBackground (new Color(200, 200, 200));

		setPreferredSize(new Dimension(cellCols * 11, cellRows * 11));
		

		setFocusable(true);

	}

	}