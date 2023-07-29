import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

public class DatabaseManagerJavaFxGui extends Application {

    private Button registrierenButton;

    private Label outputLabel;

    private TextField passwordTextField;

    private TextField usernameTextField;

    @Override
    public void start(Stage primaryStage) {

        //==========================================================================
        //Header welcomeTitle
        //=========================================================================
        HBox hboxWelcomeTitle = createHeader();

        //=========================================================================
        //Username TEXT BOX Section
        //=========================================================================
        HBox hboxUsername = creatHboxUsernameBody();

        //=========================================================================
        //Password TEXT BOX Section
        //=========================================================================
        HBox hboxPassword = createhBoxPasswordBody();

        //=========================================================================
        //Password Registrieren Einloggen Button Section
        //=========================================================================

        HBox hboxRegistrierenEinloggenButton = createHboxRegistrierenEinloggenButtonBody();

        //=========================================================================
        //Output Section
        //=========================================================================
        HBox hboxOutputLabel = createHboxOutputLabelBody();

        //=========================================================================
        //Combine Sections
        //=========================================================================
        VBox vBox = new VBox(hboxWelcomeTitle, hboxUsername, hboxPassword, hboxRegistrierenEinloggenButton, hboxOutputLabel);

        Scene scene = new Scene(vBox);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(1000);
        primaryStage.setHeight(800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private HBox createHboxOutputLabelBody() {
        this.outputLabel = new Label();
        outputLabel.setStyle("-fx-font: normal 25px 'serif' ; -fx-border-style: solid ; -fx-border-width: 2px ; -fx-border-radius: 5px; -fx-border-color: grey; -fx-background-color: white ; ");
        outputLabel.setMinSize(600, 250);

        HBox hboxOutputLabel = new HBox(outputLabel);
        hboxOutputLabel.setPadding(new Insets(40, 0, 20, 0));
        hboxOutputLabel.setAlignment(Pos.CENTER);

        return hboxOutputLabel;
    }

    private HBox createHboxRegistrierenEinloggenButtonBody() {
        Button registrierenButton = new Button("Register");
        registrierenButton.setMinWidth(250);
        registrierenButton.setMaxWidth(250);
        registrierenButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold; ");

        Button EinloggenButton = new Button("Login");
        EinloggenButton.setMinWidth(250);
        EinloggenButton.setMaxWidth(250);
        EinloggenButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold; ");

        HBox hboxRegistrierenEinloggenButton = new HBox(registrierenButton, EinloggenButton);
        hboxRegistrierenEinloggenButton.setPadding(new Insets(30, 0, 30, 0));
        hboxRegistrierenEinloggenButton.setAlignment(Pos.CENTER);
        hboxRegistrierenEinloggenButton.setSpacing(100);
        hboxRegistrierenEinloggenButton.setStyle("-fx-background-color: #d9d9d9; ");

        registrierenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String usernameTextFieldTextValue = usernameTextField.getText();
                String passwordTextFieldTextValue = passwordTextField.getText();

                Connector connectorXampp = new Connector("com.mysql.jdbc.Driver",
                        "jdbc:mysql://localhost:3306/Versand",
                        "root",
                        "");

                int result = connectorXampp.createDatabaseUser(usernameTextFieldTextValue, passwordTextFieldTextValue);

                if (result == 0) {
                    showCreationSuccessResultInOutputLabel(usernameTextFieldTextValue, passwordTextFieldTextValue, result);

                } else {
                    showCreationFailResultInOutputLabel(result);
                }
            }

        });

        EinloggenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String usernameTextFieldTextValue = usernameTextField.getText();
                String passwordTextFieldTextValue = passwordTextField.getText();

                Connector connectorXampp = new Connector("com.mysql.jdbc.Driver",
                        "jdbc:mysql://localhost:3306/Versand",
                        usernameTextFieldTextValue,
                        passwordTextFieldTextValue);

                String result = connectorXampp.validateDatabaseUser(usernameTextFieldTextValue, passwordTextFieldTextValue);
                showLoginSuccessResultInOutputLabel(result);

                if (Objects.equals(result, "False")) {
                    showCreationFailResultInOutputLabel(3);
                }
            }
        });


        return hboxRegistrierenEinloggenButton;
    }

    private void showCreationFailResultInOutputLabel(int result) {
        outputLabel.setText(Connector.getErrorMessage(result) + "\n"
                + "Failed: User could not be created");
        outputLabel.setTextFill(Color.RED);
        outputLabel.setStyle("-fx-font: normal 25px 'serif' ; -fx-border-style: solid ; -fx-border-width: 2px ; -fx-border-radius: 5px; -fx-border-color: grey; -fx-background-color: white ; ");
        ;
    }

    private void showCreationSuccessResultInOutputLabel(String usernameTextFieldTextValue, String passwordTextFieldTextValue, int result) {
        outputLabel.setText(Connector.getErrorMessage(result) + "\n"
                + "username: " + usernameTextFieldTextValue
                + " and password: " + passwordTextFieldTextValue
                + " was succussfuly added");

        outputLabel.setStyle("-fx-font: normal 25px 'serif' ; -fx-border-style: solid ; -fx-border-width: 2px ; -fx-border-radius: 5px; -fx-border-color: grey; -fx-background-color: white ; ");
        ;
        outputLabel.setTextFill(Color.GREEN);
    }

    private void showLoginSuccessResultInOutputLabel(String result) {
        outputLabel.setText(result + "\n"
                + "User logged in and SELECT privilege granted successfully!");

        outputLabel.setStyle("-fx-font: normal 25px 'serif' ; -fx-border-style: solid ; -fx-border-width: 2px ; -fx-border-radius: 5px; -fx-border-color: grey; -fx-background-color: white ; ");
        ;
        outputLabel.setTextFill(Color.GREEN);
    }

    private HBox createhBoxPasswordBody() {
        Text PasswordText = new Text("Password");
        PasswordText.setStyle("-fx-font: normal bold 25px 'serif' ;");

        this.passwordTextField = new TextField();
        passwordTextField.setStyle("-fx-font: normal 25px 'serif' ;");
        passwordTextField.setMinSize(400, 30);

        HBox hboxPassword = new HBox(PasswordText, passwordTextField);
        hboxPassword.setPadding(new Insets(40, 0, 20, 10));
        hboxPassword.setAlignment(Pos.CENTER);
        hboxPassword.setSpacing(50);

        return hboxPassword;
    }

    private HBox creatHboxUsernameBody() {
        Text UsernameText = new Text("Username");
        UsernameText.setStyle("-fx-font: normal bold 25px 'serif' ;");

        this.usernameTextField = new TextField();
        usernameTextField.setMinSize(400, 30);
        usernameTextField.setStyle("-fx-font: normal 25px 'serif' ;");

        HBox hboxUsername = new HBox(UsernameText, usernameTextField);
        hboxUsername.setPadding(new Insets(40, 0, 20, 0));
        hboxUsername.setAlignment(Pos.CENTER);
        hboxUsername.setSpacing(50);

        return hboxUsername;
    }

    private HBox createHeader() {
        Text welcomeTitle = new Text("Database Manager".toUpperCase());
        //   welcomeTitle.setFont(Font.font("Julius Sans One", FontWeight.BOLD, FontPosture.REGULAR, 40));
        welcomeTitle.setFill(Color.BLACK);
        welcomeTitle.setStyle("-fx-font: normal bold 30px 'serif' ;");

        HBox hboxWelcomeTitle = new HBox(welcomeTitle);
        hboxWelcomeTitle.setAlignment(Pos.CENTER);
        hboxWelcomeTitle.setPadding(new Insets(20, 0, 20, 0));
        hboxWelcomeTitle.setStyle("-fx-background-color: BEIGE;");

        return hboxWelcomeTitle;
    }

    public static void main(String args[]) {
        launch(args);
    }
}