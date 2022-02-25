package com.jaypi4c.simon.controller.demonstration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jaypi4c.simon.controller.GameController;
import com.jaypi4c.simon.model.Model;

public class Demonstration extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(Demonstration.class);
	private Model model;

	private GameController controller;

	public Demonstration(Model model, GameController controller) {
		this.model = model;
		this.controller = controller;
	}

	@Override
	public void run() {
		logger.debug("Starting demo");
		while (!model.isDone()) {
			model.highlight();
			try {
				sleep(model.getDisplayTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			model.stopHighlight();
			try {
				sleep(model.getDelayTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			model.next();
		}
		controller.finish();
	}

}
