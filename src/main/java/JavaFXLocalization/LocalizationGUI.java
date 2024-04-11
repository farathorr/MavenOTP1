package JavaFXLocalization;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationGUI extends Application {

    private Locale locale = new Locale("en", "GB");

    @Override
    public void start(Stage stage) {
        GridPane root = new GridPane();
        Label firstName = new Label(ResourceBundle.getBundle("localization", locale).getString("firstName"));
        Label lastName = new Label(ResourceBundle.getBundle("localization", locale).getString("lastName"));
        Label email = new Label(ResourceBundle.getBundle("localization", locale).getString("email"));
        Label language = new Label(ResourceBundle.getBundle("localization", locale).getString("language"));

        TextField fNameField = new TextField();
        TextField lNameField = new TextField();
        TextField emailField = new TextField();

        Button saveButton = new Button("Save");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("EN", "FA", "JA");
        choiceBox.setValue("EN");

        firstName.setPadding(new Insets(10));
        lastName.setPadding(new Insets(10));
        email.setPadding(new Insets(10));
        language.setPadding(new Insets(10));

        root.add(language, 0, 0);
        root.add(choiceBox, 1, 0);

        root.add(firstName, 0, 1);
        root.add(fNameField, 1, 1);

        root.add(lastName, 0, 2);
        root.add(lNameField, 1, 2);

        root.add(email, 0, 3);
        root.add(emailField, 1, 3);

        root.add(saveButton, 1, 4);

        choiceBox.setOnAction(e -> {
            switch (choiceBox.getValue()) {
                case "EN":
                    locale = new Locale("en", "GB");
                    break;
                case "FA":
                    locale = new Locale("fa", "IR");
                    break;
                case "JA":
                    locale = new Locale("ja", "JP");
                    break;
            }
            System.out.println("Locale changed to: " + locale.getLanguage() + "_" + locale.getCountry());
            firstName.setText(ResourceBundle.getBundle("localization", locale).getString("firstName"));
            lastName.setText(ResourceBundle.getBundle("localization", locale).getString("lastName"));
            email.setText(ResourceBundle.getBundle("localization", locale).getString("email"));
            language.setText(ResourceBundle.getBundle("localization", locale).getString("language"));
            saveButton.setText(ResourceBundle.getBundle("localization", locale).getString("save"));
        });

        saveButton.setOnAction(e -> {
            EmployeeDao employeeDao = new EmployeeDao();
            Employee employee = new Employee(fNameField.getText(), lNameField.getText(), emailField.getText(), choiceBox.getValue());
            employeeDao.persist(employee);
            System.out.println("Employee saved: " + employee);
        });

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("LocalizationTest");
        stage.show();
    }
}
