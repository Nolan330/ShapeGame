package application;

import javafx.application.Application;
import javafx.stage.Stage;

import com.cs279.ShapeWorld.GameEngine;
import com.cs279.ShapeWorld.ShapeEngine;

public class GameLoop extends Application {
	GameEngine gEngine = new ShapeEngine(60, "Hello World");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		gEngine.initialize(primaryStage);
		gEngine.beginGameLoop();
		primaryStage.show();

	}

}

// package application;
//
// import java.io.InputStream;
// import java.net.URL;
//
// import javafx.animation.Animation;
// import javafx.application.Application;
// import javafx.geometry.Rectangle2D;
// import javafx.scene.Group;
// import javafx.scene.Scene;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.paint.Color;
// import javafx.stage.Stage;
// import javafx.util.Duration;
//
// import com.cs279.ShapeWorld.SpriteAnimation;

//
// public class Main extends Application {
// static InputStream io =
// Main.class.getClassLoader().getResourceAsStream("main-walk.png");
// private static final Image IMAGE = new Image(io);
//
// private static final int COLUMNS = 4;
// private static final int COUNT = 8;
// private static final int OFFSET_X = 0;
// private static final int OFFSET_Y = 0;
// private static final int WIDTH = 525;
// private static final int HEIGHT = 525;
// @Override
// public void start(Stage primaryStage) {
// primaryStage.setTitle("The Horse in Motion");
//
// final ImageView imageView = new ImageView(IMAGE);
// imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
//
// final Animation animation = new SpriteAnimation(
// imageView,
// Duration.millis(750),
// COUNT, COLUMNS,
// OFFSET_X, OFFSET_Y,
// WIDTH, HEIGHT
// );
// animation.setCycleCount(Animation.INDEFINITE);
// animation.play();
//
// Scene s = new Scene(new Group(imageView));
// s.setFill(Color.AZURE);
// primaryStage.setScene(s);
// primaryStage.show();
// }
//
// public static void main(String[] args) {
// launch(args);
// }
// }

