package com.group6.frontend;

import com.group6.frontend.model.enums.GameScreen;
import com.group6.frontend.view.FormsTabView;
import com.group6.frontend.view.GameView;
import com.group6.frontend.view.LeaderBoardTabView;
import com.group6.frontend.view.MainMenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;

@SpringBootApplication
public class Main extends Application {

    public static String TOKEN = null;

	private ConfigurableApplicationContext context;

	private static Map<GameScreen, Scene> scenes = new HashMap<>();
	private static Stack<GameScreen> history = new Stack<>();
	private static Stage stage;

	public static void resetGameView() {
		scenes.remove(GameScreen.GAME);
		scenes.put(GameScreen.GAME, new GameView(stage).getScene());
	}

	@Override
	public void init() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
		context = builder.run(getParameters().getRaw().toArray(new String[0]));
	}

	@Override
	public void start(Stage stage) {
		Main.stage = stage;

		// Create and store all scenes up front
		scenes.put(GameScreen.MAIN_MENU, new MainMenuView(stage).getScene());
		scenes.put(GameScreen.GAME, new GameView(stage).getScene());
		scenes.put(GameScreen.FORM, new FormsTabView(stage).getScene());
		scenes.put(GameScreen.LEADERBOARD, new LeaderBoardTabView(stage).getScene());
		// Start with the main scene
		stage.setScene(scenes.get(GameScreen.FORM));

		stage.show();
	}

		/** Returns a Map of the scenes by {@link GameScreen} */
	public static Map<GameScreen, Scene> getScenes() {
		return scenes;
	}


	@Override
	public void stop() throws Exception {
		context.close();
	}

}
