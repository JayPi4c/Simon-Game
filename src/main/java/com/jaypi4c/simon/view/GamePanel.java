package com.jaypi4c.simon.view;

import com.jaypi4c.simon.App;
import com.jaypi4c.simon.controller.GameController;
import com.jaypi4c.simon.model.Field;
import com.jaypi4c.simon.model.Model;
import com.jaypi4c.simon.util.Observable;
import com.jaypi4c.simon.util.Observer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GamePanel extends Canvas implements Observer {

	// private static final Logger logger =
	// LoggerFactory.getLogger(GamePanel.class);
	private Model model;

	public GamePanel(App app) {
		GameController gameController = new GameController(app);
		app.getModel().addObserver(this);
		this.model = app.getModel();
		setOnMouseClicked(gameController);
		setWidth(500);
		setHeight(500);
		paintPanel();

	}

	private void paintPanel() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, 250, 250);
		gc.setFill(Color.RED);
		gc.fillRect(250, 0, 500, 250);
		gc.setFill(Color.BLUE);
		gc.fillRect(250, 250, 500, 500);
		gc.setFill(Color.YELLOW);
		gc.fillRect(0, 250, 250, 500);
	}

	@Override
	public void update(Observable observable) {
		if (model.isHighlight()) {
			paintPanelHighlight(model.getCurrentHighlight());
		} else {
			paintPanel();
			GraphicsContext gc = getGraphicsContext2D();
			gc.setFill(Color.BLACK);
			gc.setFont(new Font(32));
			if (model.isGameOver()) {
				gc.fillText("Game Over", 250 - 100, 250);
			} else if (model.isWon()) {
				gc.fillText("Game Won", 250 - 100, 250);
			}
		}
	}

	private void paintPanelHighlight(Field highlight) {
		GraphicsContext gc = getGraphicsContext2D();
		if (Field.GREEN.equals(highlight))
			gc.setFill(Color.LIGHTGREEN);
		else
			gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, 250, 250);
		if (Field.RED.equals(highlight))
			gc.setFill(Color.ORANGERED);
		else
			gc.setFill(Color.RED);
		gc.fillRect(250, 0, 500, 250);
		if (Field.BLUE.equals(highlight))
			gc.setFill(Color.LIGHTBLUE);
		else
			gc.setFill(Color.BLUE);
		gc.fillRect(250, 250, 500, 500);
		if (Field.YELLOW.equals(highlight))
			gc.setFill(Color.LIGHTYELLOW);
		else
			gc.setFill(Color.YELLOW);
		gc.fillRect(0, 250, 250, 500);
	}
}
