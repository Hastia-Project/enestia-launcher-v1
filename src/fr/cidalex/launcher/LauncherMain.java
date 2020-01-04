package fr.cidalex.launcher;

import fr.trxyy.base.Configuration;
import fr.trxyy.base.Initializer;
import fr.trxyy.gamelaunch.Tweaks;
import fr.trxyy.gamelaunch.Versions;
import fr.trxyy.interfaces.LauncherApp;
import fr.trxyy.interfaces.LauncherBase;
import fr.trxyy.interfaces.components.LauncherBackground;
import fr.trxyy.interfaces.components.LauncherLogo;
import fr.trxyy.interfaces.utils.ResourceLocation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings("static-access")
public class LauncherMain extends LauncherApp {
	@SuppressWarnings("unused")
	private LauncherBackground backgroundPanel;
	private ResourceLocation resources;
	private LauncherPanel launcherPanel;
	private LauncherLogo logoPanel;

	public static void main(String[] args) {
		Initializer.registerBasics("Enestia", 880, 550);
		Initializer.registerSpecs("/resources/", "", "enestia", Versions.V1_13, Tweaks.OPTIFINE, false);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createContent());
		LauncherBase launcher = new LauncherBase(primaryStage, scene, StageStyle.UNDECORATED, true);
		LauncherBase.setIconImage(primaryStage, this.resources.loadImage("favicon.png"));
	}

	private Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize(Configuration.getWidth(), Configuration.LAUNCHER_HEIGHT);
		this.backgroundPanel = new LauncherBackground(this.resources.getMedia("background.mp4"), root);
		this.launcherPanel = new LauncherPanel(root);
		this.logoPanel = new LauncherLogo(this.resources.loadImage("logo.png"), 600, 180, root);
		this.logoPanel.setImagePos(Configuration.getWidth() / 2 - 280, 130);
		return root;
	}

}
