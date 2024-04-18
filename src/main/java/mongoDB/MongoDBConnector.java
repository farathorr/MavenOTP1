package mongoDB;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import javafx.application.Application;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MongoDBConnector extends Application {

    private MongoCollection<Document> collection;

    @Override
    public void start(Stage primaryStage) {
        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("DemoDB");
            collection = database.getCollection("DemoCollection");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        primaryStage.setTitle("MongoDB JavaFX App");

        TextField nameInput = new TextField();
        TextField ageInput = new TextField();
        TextField idInput = new TextField();
        TextField cityInput = new TextField();
        Button addButton = new Button("Add");
        Button retrieveButton = new Button("Retrieve");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");

        addButton.setOnAction(e -> {
            if (nameInput.getText().isEmpty() || ageInput.getText().isEmpty() || cityInput.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty fields");
                alert.setContentText("Please fill all fields apart from ID");
                alert.showAndWait();
                return;
            }
            addDocument(nameInput.getText(), Integer.parseInt(ageInput.getText()), cityInput.getText());
            nameInput.clear();
            ageInput.clear();
            idInput.clear();
            cityInput.clear();
        });

        retrieveButton.setOnAction(e -> {
            if (idInput.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty ID field");
                alert.setContentText("Please fill in the ID field to retrieve a document");
                alert.showAndWait();
                return;
            }
            retrieveDocument(new ObjectId(idInput.getText()));
            idInput.clear();
        });

        updateButton.setOnAction(e -> {
            if (idInput.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty ID field");
                alert.setContentText("Please fill in the ID field to update a document");
                alert.showAndWait();
                return;
            }
            updateDocument(nameInput.getText(), ageInput.getText(), cityInput.getText(), new ObjectId(idInput.getText()));
            nameInput.clear();
            ageInput.clear();
            idInput.clear();
            cityInput.clear();
        });

        deleteButton.setOnAction(e -> {
            if (idInput.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty ID field");
                alert.setContentText("Please fill in the ID field to delete a document");
                alert.showAndWait();
                return;
            }
            deleteDocument(new ObjectId(idInput.getText()));
            nameInput.clear();
        });

        HBox lowerBox = new HBox(10);
        lowerBox.getChildren().addAll(updateButton, deleteButton);

        HBox higherBox = new HBox(10);
        higherBox.getChildren().addAll(addButton, retrieveButton);

        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(higherBox, lowerBox);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setSpacing(10);

        VBox fieldsBox = new VBox(10);
        fieldsBox.getChildren().addAll(new Label("ID"), idInput, new Label("Name"), nameInput, new Label("Age"), ageInput, new Label("City"), cityInput);

        BorderPane root = new BorderPane();
        root.setCenter(fieldsBox);
        root.setBottom(buttonBox);
        root.setPadding(new Insets(15));

        primaryStage.setScene(new Scene(root, 300, 350));
        primaryStage.show();
    }

    private void addDocument(String name, int age, String city) {
        Person person = new Person(name, age, city);
        collection.insertOne(person.toDocument());
        System.out.println("Document inserted: " + person);
    }

    private void retrieveDocument(ObjectId id) {
        Document foundDoc = collection.find(Filters.eq("_id", id)).first();
        System.out.println("Document found: " + foundDoc);
    }

    private void updateDocument(String name, String age, String city, ObjectId id) {
        HashMap<String, String> content = new HashMap<>();
        content.put("name", name);
        content.put("age", age);
        content.put("city", city);
        List<Bson> updateOperations = new ArrayList<>();

        for (String field : content.keySet()) {
            if (!content.get(field).isEmpty()) {
                if (field.equals("age")) {
                    updateOperations.add(Updates.set(field, Integer.parseInt(content.get(field))));
                } else {
                    updateOperations.add(Updates.set(field, content.get(field)));
                }
            }
        }
        UpdateResult updateResult = collection.updateOne(
                Filters.eq("_id", id),
                Updates.combine(updateOperations)
        );
        System.out.println("Document updated: " + updateResult);
    }

    private void deleteDocument(ObjectId id) {
        DeleteResult deleteResult = collection.deleteOne(Filters.eq("_id", id));
        System.out.println("Document deleted: " + deleteResult);
    }
}
