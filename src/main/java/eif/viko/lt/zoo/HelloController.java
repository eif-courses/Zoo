package eif.viko.lt.zoo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;


public class HelloController implements Initializable {


    FirebaseAuth auth = null;
    DatabaseReference refUsers = FirebaseDatabase.getInstance().getReference("/users");
    DatabaseReference dbref = null;
    // Dialogo lango Objektai

    TextField textField_username = null;
    TextField textField_password = null;
    Label label_username = null;
    Label label_password = null;
    TextInputDialog textInputDialog = null;

    //-------------------------------------

    @FXML
    private ImageView profile_image;
    @FXML
    private Text profile_email;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField name;
    @FXML
    private Text statusas;
    @FXML
    private ListView<Animal> animals_listview;
    @FXML
    private Text animal_name;
    @FXML
    private ImageView animal_image;
    @FXML
    private Text animal_description;
    @FXML
    private CheckBox animal_cleaned;
    @FXML
    private CheckBox animal_hungry;
    @FXML
    private CheckBox animal_here;
    @FXML
    private CheckBox animal_total_results_checkbox;

    void sign_in_form() {

        textInputDialog = new TextInputDialog();

        DialogPane dialog = textInputDialog.getDialogPane();

        label_username = new Label("Username");
        label_password = new Label("Password");
        textField_username = new TextField();
        textField_password = new TextField();

        Button sign_in_button = new Button("Sign in");
        GridPane root = new GridPane();
        root.addRow(0, label_username, textField_username);
        root.addRow(1, label_password, textField_password);
        root.addRow(2, sign_in_button);

        dialog.setContent(root);
        dialog.getButtonTypes().removeAll(ButtonType.OK);

        // IGNORUOTI KIEKVIENA KARTA SUVESTI PRISIJUNGIMO DUOMENIS

        textField_username.setText("m.gzegozevskis@gmail.com");

        sign_in_button.setOnAction(actionEvent -> {
            auth = FirebaseAuth.getInstance();

            UserRecord userByEmail = null;
            try {
                userByEmail = auth.getUserByEmail(textField_username.getText());
                dbref = refUsers.child(userByEmail.getUid() + "/animals");
            } catch (FirebaseAuthException e) {
                e.printStackTrace();
            }
            if (userByEmail != null) {
                Image image = new Image(userByEmail.getPhotoUrl());
                profile_image.setImage(image);
                profile_email.setText(userByEmail.getEmail());

                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        animals_listview.getItems().clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Animal animal = ds.getValue(Animal.class);
                            animals_listview.getItems().add(animal);
                        }
                        animals_listview.setOnMouseClicked(e -> {
                           Animal animal = animals_listview.getSelectionModel().getSelectedItem();
                            animal_name.setText(animal.getName());
                            animal_image.setImage(new Image(animal.getImageURL()));
                            animal_description.setText(animal.getDescription());
                            animal_cleaned.setSelected(animal.isCleaned());
                            animal_hungry.setSelected(animal.isHungry());
                            animal_here.setSelected(animal.isHere());
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println(databaseError.getMessage());
                    }
                });
                textInputDialog.close();
            } else {
                statusas.setText("Offline");
            }

        });

        label_username.setOnMouseClicked(mouseEvent -> label_username.setStyle("-fx-background-color: red"));
        textInputDialog.showAndWait();

    }

    @FXML
    private Text information_text;

    @FXML
    void createUser(ActionEvent event) {

        User testUser = new User();

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email.getText())
                .setEmailVerified(testUser.isEmailVerified())
                .setPassword(password.getText())
                .setDisplayName(name.getText())
                .setPhotoUrl(testUser.getPhotoURL())
                .setDisabled(testUser.isDisabled());


        testUser.setEmail(email.getText());
        testUser.setPassword(password.getText());
        testUser.setDisplayName(name.getText());

        UserRecord userRecord = null;

        try {
            userRecord = FirebaseAuth.getInstance().createUser(request);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        } finally {
            assert userRecord != null;
            refUsers.child(userRecord.getUid()).setValueAsync(testUser);
        }

        information_text.setText(userRecord.getUid());
        System.out.println("Successfully created new user: " + userRecord.getUid());
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        sign_in_form();
        profile_email.setOnMouseClicked(x -> sign_in_form());
        animal_total_results_checkbox.setDisable(true);

        Map<String, Object> userUpdates = new HashMap<>();
        animal_cleaned.setOnAction(e->{
            Animal animal = animals_listview.getSelectionModel().getSelectedItem();
            animal.setCleaned(!animal.isCleaned());
            userUpdates.put(String.valueOf(animal.getIndex()), animal);
            dbref.updateChildrenAsync(userUpdates);
        });

    }
}