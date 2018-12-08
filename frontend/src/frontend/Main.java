package frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import frontend.model.entities.GameScreen;
import frontend.view.GameView;
import frontend.view.MainMenuView;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class Main extends Application {
	private static Map<GameScreen, Scene> scenes = new HashMap<>();
	private static Stack<GameScreen> history = new Stack<>();

	@Override
	public void start(Stage stage) {

		// Create and store all scenes up front
		scenes.put(GameScreen.MAIN_MENU, new MainMenuView(stage).getScene());
		scenes.put(GameScreen.GAME, new GameView(stage).getScene());

		// Start with the main scene
//		stage.setScene(scenes.get(GameScreen.MAIN_MENU));
		stage.setScene(scenes.get(GameScreen.GAME));
		stage.show();
	}

	/** Returns a Map of the scenes by {@link GameScreen} */
	public static Map<GameScreen, Scene> getScenes() {
		return scenes;
	}

    public static void main(String[] args) {
        launch(args);
    }
}
