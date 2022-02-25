package com.jaypi4c.simon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jaypi4c.simon.App;
import com.jaypi4c.simon.controller.demonstration.Demonstration;
import com.jaypi4c.simon.model.Field;
import com.jaypi4c.simon.model.Model;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class GameController implements EventHandler<MouseEvent> {

	private static final Logger logger = LoggerFactory.getLogger(GameController.class);

	private Model model;

	private Demonstration demonstration;
	private App app;

	public GameController(App app) {
		this.model = app.getModel();
		this.app = app;
		app.getStart().setOnAction(event -> start());
	}

	public void start() {
		logger.info("start");
		Platform.runLater(() -> {
			app.getStart().setDisable(true);
		});
		model.createOrder();
		demonstration = new Demonstration(model, this);
		demonstration.setDaemon(true);
		demonstration.start();
	}

	@Override
	public void handle(MouseEvent event) {
		if (!model.isReady())
			return;

		if (event.getX() <= 250 && event.getY() <= 250) {
			model.click(Field.GREEN);
		} else if (event.getX() <= 250 && event.getY() <= 500) {
			logger.info("YELLOW");
			model.click(Field.YELLOW);
		} else if (event.getX() <= 500 && event.getY() <= 250) {
			logger.info("RED");
			model.click(Field.RED);
		} else {
			logger.info("BLUE");
			model.click(Field.BLUE);
		}
	}

	public void finish() {
		Platform.runLater(() -> {
			app.getStart().setDisable(false);
		});
		model.setDemoDone(true);
	}

}
