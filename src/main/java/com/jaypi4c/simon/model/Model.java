package com.jaypi4c.simon.model;

import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jaypi4c.simon.util.Observable;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lombok.Getter;

@Getter
public class Model extends Observable {

	private static final Logger logger = LoggerFactory.getLogger(Model.class);

	private int level;

	private int delayTime = 1250;
	private int displayTime = 500;

	private Field[] fields;

	private volatile int currentIndex = 0;

	private boolean highlight = false;

	private boolean demoDone = false;

	private boolean gameOver = false;

	private boolean won = false;

	private static MediaPlayer playerRed = null;
	private static MediaPlayer playerGreen = null;
	private static MediaPlayer playerYellow = null;
	private static MediaPlayer playerBlue = null;

	public static void initialize() throws URISyntaxException {
		// https://stackoverflow.com/a/60599091/13670629
		if (playerRed == null) {
			Media sound = new Media(Model.class.getResource("/sounds/toneRED.mp3").toURI().toString());
			playerRed = new MediaPlayer(sound);
			playerRed.setCycleCount(MediaPlayer.INDEFINITE);
		}
		if (playerGreen == null) {
			Media sound = new Media(Model.class.getResource("/sounds/toneGREEN.mp3").toURI().toString());
			playerGreen = new MediaPlayer(sound);
			playerGreen.setCycleCount(MediaPlayer.INDEFINITE);
		}
		if (playerYellow == null) {
			Media sound = new Media(Model.class.getResource("/sounds/toneYELLOW.mp3").toURI().toString());
			playerYellow = new MediaPlayer(sound);
			playerYellow.setCycleCount(MediaPlayer.INDEFINITE);
		}
		if (playerBlue == null) {
			Media sound = new Media(Model.class.getResource("/sounds/toneBLUE.mp3").toURI().toString());
			playerBlue = new MediaPlayer(sound);
			playerBlue.setCycleCount(MediaPlayer.INDEFINITE);
		}
	}

	public Model() {
		level = 3;
	}

	public void createOrder() {
		demoDone = false;
		gameOver = false;
		won = false;
		logger.debug("creating order for level {}", level);
		fields = new Field[level];
		for (int i = 0; i < level; i++) {
			fields[i] = Field.randomField();
		}
		currentIndex = 0;
		highlight = true;
	}

	public void levelUp() {
		level++;
	}

	public synchronized boolean isDone() {
		return currentIndex >= fields.length;
	}

	public synchronized void next() {
		highlight = true;
		currentIndex++;

	}

	public synchronized void highlight() {
		switch (getCurrentHighlight()) {
		case GREEN:
			playerGreen.play();
			break;
		case RED:
			playerRed.play();
			break;
		case BLUE:
			playerBlue.play();
			break;
		case YELLOW:
			playerYellow.play();
			break;
		default:
			break;
		}
		setChanged();
		notifyAllObservers();
	}

	public synchronized void stopHighlight() {
		highlight = false;
		switch (getCurrentHighlight()) {
		case GREEN:
			playerGreen.stop();
			break;
		case RED:
			playerRed.stop();
			break;
		case BLUE:
			playerBlue.stop();
			break;
		case YELLOW:
			playerYellow.stop();
			break;
		default:
			break;
		}
		setChanged();
		notifyAllObservers();
	}

	public void setDemoDone(boolean flag) {
		this.demoDone = flag;
		highlight = false;
		currentIndex = 0;
	}

	public boolean isReady() {
		return demoDone;
	}

	public synchronized Field getCurrentHighlight() {
		if (currentIndex < fields.length)
			return fields[currentIndex];
		return null;
	}

	public void click(Field color) {
		if (fields.length > currentIndex && fields[currentIndex].equals(color)) {
			currentIndex++;
			if (currentIndex == level) {
				won = true;
				level++;
				setChanged();
				notifyAllObservers();

			}
		} else {
			gameOver = true;
			logger.debug("User clicked wrong field");
			setChanged();
			notifyAllObservers();
		}
	}

}
