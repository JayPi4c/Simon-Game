package com.jaypi4c.simon;

import java.net.URISyntaxException;

import com.jaypi4c.simon.controller.GameController;
import com.jaypi4c.simon.model.Model;
import com.jaypi4c.simon.util.Observable;
import com.jaypi4c.simon.util.Observer;
import com.jaypi4c.simon.view.GamePanel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class App extends Application implements Observer {

	private MenuItem menuItem1;
	private MenuItem menuItem2;
	private Menu menu;
	private MenuBar menuBar;

	private Button start;
	private ToolBar toolbar;

	private Model model;

	private GameController gameController;

	private GamePanel gamePanel;

	private Scene scene;

	private Label messageLabel;

	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Simon Game");
		model = new Model();

		menuItem1 = new MenuItem("item 1");
		menuItem2 = new MenuItem("item 2");
		menu = new Menu("menu", null, menuItem1, menuItem2);
		menuBar = new MenuBar(menu);

		start = new Button("start", null);
		toolbar = new ToolBar(start);

		gamePanel = new GamePanel(this);

		messageLabel = new Label("Hello World!");
		model.addObserver(this);

		VBox vbox = new VBox(menuBar, toolbar, gamePanel, messageLabel);
		scene = new Scene(vbox);

		primaryStage.setScene(scene);

		primaryStage.show();
		primaryStage.setResizable(false);
	}

	@Override
	public void update(Observable observable) {
		Platform.runLater(() -> {
			if (model.isGameOver()) {
				messageLabel.setText("Game Over");
			} else if (model.isWon()) {
				messageLabel.setText("Game won! Click start to start the next round");
			} else {
				messageLabel.setText("Current Level: " + model.getLevel());
			}
		});
	}

	@Override
	public void init() throws Exception {
		super.init();
		try {
			Model.initialize();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.out.println("could not load sounds");
		}
	}

}
