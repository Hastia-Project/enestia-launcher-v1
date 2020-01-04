package fr.cidalex.launcher;

import java.text.DecimalFormat;

import fr.trxyy.account.GameSaver;
import fr.trxyy.authentication.GameAuth;
import fr.trxyy.base.Configuration;
import fr.trxyy.interfaces.components.LauncherButton;
import fr.trxyy.interfaces.components.LauncherLabel;
import fr.trxyy.interfaces.components.LauncherPasswordField;
import fr.trxyy.interfaces.components.LauncherProgressBar;
import fr.trxyy.interfaces.components.LauncherRectangle;
import fr.trxyy.interfaces.components.LauncherTextField;
import fr.trxyy.interfaces.utils.FontLoader;
import fr.trxyy.interfaces.utils.OSUtil;
import fr.trxyy.interfaces.utils.ResourceLocation;
import fr.trxyy.updater.GameParser;
import fr.trxyy.updater.GameUpdater;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LauncherPanel {
	
	private LauncherTextField usernamefield;
	private LauncherPasswordField passwordField;
	private LauncherButton loginButton;
	private ResourceLocation resources;
	private LauncherButton closeButton;
	private LauncherButton minimizeButton;
	private LauncherRectangle topRectangle;
	private LauncherLabel titleLabel;
	private GameSaver saver = new GameSaver(OSUtil.getDirectory());
	private LauncherLabel downloadLabel;
	private LauncherProgressBar progressBar;
	private GameUpdater gameupdater = new GameUpdater();
	private GameParser gameParsec = new GameParser();
	private static DecimalFormat df2 = new DecimalFormat(".##");

	public LauncherPanel(Pane root) {
		
		this.topRectangle = new LauncherRectangle(root, 0, 0, Configuration.getWidth(), 24);
		this.topRectangle.setOpacity(0.7);
		
		this.titleLabel = new LauncherLabel(root);
		this.titleLabel.setText("Enestia");
		this.titleLabel.setPosition(4, 0);
		this.titleLabel.setFont(FontLoader.loadFont("Sabado-Regular.ttf", "Sabado", 20F));
		this.titleLabel.setTextFill(Color.WHITE);
		
		this.downloadLabel = new LauncherLabel(root);
		this.downloadLabel.setPosition(170, 0);
		this.downloadLabel.setSize(250, 20);
		this.downloadLabel.setFont(FontLoader.loadFont("Sabado-Regular.ttf", "Sabado", 20F));
		this.downloadLabel.setOpacity(0.5);
		this.downloadLabel.setTextFill(Color.WHITE);	
		
		this.usernamefield = new LauncherTextField(root);
		this.usernamefield.setText(saver.getUsername(""));
		this.usernamefield.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.usernamefield.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Lt", 18F));
		this.usernamefield.setPosition(5, Configuration.getHeight() - 40);
		this.usernamefield.setSize(200, 35);
		
		this.passwordField = new LauncherPasswordField(root);
		this.passwordField.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.passwordField.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Lt", 18F));
		this.passwordField.setPosition(210, Configuration.getHeight() - 40);
		this.passwordField.setSize(200, 35);
		
		this.loginButton = new LauncherButton(root);
		this.loginButton.setText("Se connecter");
		this.loginButton.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.loginButton.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Lt", 18F));
		this.loginButton.setPosition(415, Configuration.getHeight() - 40);
		this.loginButton.setSize(130, 35);
		this.loginButton.setAction(e -> {
			saver.saveUsername(usernamefield.getText());
			//Action
			GameAuth auth = new GameAuth(usernamefield.getText(), passwordField.getText(), true);
			auth.tryLogin();
			if (auth.isLogged()) {
                this.usernamefield.setDisable(true);
                this.passwordField.setDisable(true);
                this.saver.saveUsername(usernamefield.getText());

                if (!gameupdater.isUpdating()) {
                    Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0), dr -> downloadLabel.setText("" + df2.format(gameupdater.getProgressBar().getProgress() * 100) + "%")), new KeyFrame(Duration.seconds(0.1)));
                    tl.setCycleCount(Animation.INDEFINITE);
                    tl.play();
                    this.gameupdater.checkForUpdate(gameParsec, progressBar);

                }
			}
        });

		
		this.closeButton = new LauncherButton(root);
		this.closeButton.setSize(38, 22);
		this.closeButton.setPosition(Configuration.getWidth() - 38, 0);
		this.closeButton.setInvisible();
		this.closeButton.setStyle("-fx-padding: 0;");
		ImageView closeIcon = new ImageView(resources.loadImage("close.png"));
		closeIcon.setFitWidth(this.closeButton.getPrefWidth());
		closeIcon.setFitHeight(this.closeButton.getPrefHeight());
		this.closeButton.setGraphic(closeIcon);
		this.closeButton.setAction(e -> {
			System.exit(0);
		});
		
		this.minimizeButton = new LauncherButton(root);
		this.minimizeButton.setSize(38, 22);
		this.minimizeButton.setPosition(Configuration.getWidth() - 76, 0);
		this.minimizeButton.setInvisible();
		this.minimizeButton.setStyle("-fx-padding: 0;");
		ImageView minimizeIcon = new ImageView(resources.loadImage("minimize.png"));
		minimizeIcon.setFitWidth(this.minimizeButton.getPrefWidth());
		minimizeIcon.setFitHeight(this.minimizeButton.getPrefHeight());
		this.minimizeButton.setGraphic(minimizeIcon);
		this.minimizeButton.setAction(e -> {
			Stage stage = (Stage)((LauncherButton)e.getSource()).getScene().getWindow();
			stage.setIconified(true);
		});
		
		this.progressBar = new LauncherProgressBar(root);
		this.progressBar.setPosition(Configuration.getWidth() - 290, Configuration.getHeight() - 20);
		this.progressBar.setSize(250, 20);	
	}

}
