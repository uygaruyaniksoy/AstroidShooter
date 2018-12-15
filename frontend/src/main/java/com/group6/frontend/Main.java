package com.group6.frontend;

import com.group6.frontend.model.enums.GameScreen;
import com.group6.frontend.view.GameView;
import com.group6.frontend.view.MainMenuView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@SpringBootApplication
public class Main extends Application {

	private ConfigurableApplicationContext context;
	private Parent rootNode;

	private static Map<GameScreen, Scene> scenes = new HashMap<>();
	private static Stack<GameScreen> history = new Stack<>();

	@Override
	public void init() throws Exception {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
		context = builder.run(getParameters().getRaw().toArray(new String[0]));
		rootNode = new AnchorPane();
	}

	@Override
	public void start(Stage stage) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
		System.out.println(quote.toString());

		// Create and store all scenes up front
		scenes.put(GameScreen.MAIN_MENU, new MainMenuView(stage).getScene());
		scenes.put(GameScreen.GAME, new GameView(stage).getScene());

		// Start with the main scene
		stage.setScene(scenes.get(GameScreen.MAIN_MENU));
//		stage.setScene(scenes.get(GameScreen.GAME));
		stage.show();
	}

		/** Returns a Map of the scenes by {@link GameScreen} */
	public static Map<GameScreen, Scene> getScenes() {
		return scenes;
	}

//

	@Override
	public void stop() throws Exception {
		context.close();
	}
}
