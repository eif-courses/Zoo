package eif.viko.lt.zoo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.Rating;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    @FXML
    private Rating animal_rating;


    void sign_in_form() {


        //TODO igyvendinti Notifications.create().title("Weaweaweaw").text("Sekmingai prisijungeme!").darkStyle().show();


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

                        if (animals_listview.getItems().size() > 0) {
                            animals_listview.getItems().clear();
                        }


                        int countAnimals = 0;
                        int totalAnimals = 0;
                        int countTaskDone = 0;

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Animal animal = ds.getValue(Animal.class);
                            totalAnimals++;

                            if (animal.isHere()) {
                                countAnimals++;
                            }
                            if (animal.isHere() && animal.isCleaned()) {
                                countTaskDone++;
                            } else if (animal.isHere() && animal.isHungry()) {
                                countTaskDone++;
                            }
                            animals_listview.getItems().add(animal);
                        }


//
                        int temp = countAnimals * 2; // 14
//                        // 9 - 7 =
//                        int result = countAnimals * 2;

                        if (temp == countTaskDone) {

                            refUsers.child("isTasksDone").setValueAsync(true);

                        }else{
                            refUsers.child("isTasksDone").setValueAsync(false);
//                            Notifications.create()
//                                    .title("Title")
//                                    .text("TEstuojam")
//                                    .hideAfter(Duration.seconds(10))
//                                    .show();
                        }


                        animals_listview.setOnMouseClicked(e -> {
                            Animal animal = animals_listview.getSelectionModel().getSelectedItem();
                            if (animal != null) {
                                animal_name.setText(animal.getName());

                                //System.out.println(animal.getImageURL());
                                animal_image.setImage(new Image(animal.getImageURL()));
                                animal_description.setText(animal.getDescription());
                                animal_cleaned.setSelected(animal.isCleaned());
                                animal_hungry.setSelected(animal.isHungry());
                                animal_here.setSelected(animal.isHere());
                            }
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
    void enable_notifications(ActionEvent event) {
        Notifications.create()
                .title("Title")
                .text("TEstuojam")
                .hideAfter(Duration.seconds(10))
                .show();
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

        // CONTROLS FX DOKUMENTACIJA
        // https://github.com/controlsfx/controlsfx/wiki/ControlsFX-Features


        animals_listview.setCellFactory(param -> new AnimalCell());


        sign_in_form();
        profile_email.setOnMouseClicked(x -> sign_in_form());
        animal_total_results_checkbox.setDisable(true);

        Map<String, Object> userUpdates = new HashMap<>();
        animal_cleaned.setOnAction(e -> {
            Animal animal = animals_listview.getSelectionModel().getSelectedItem();

            if (animal != null) {
                animal.setCleaned(!animal.isCleaned());

                userUpdates.put(String.valueOf(animal.getIndex()), animal);
                dbref.updateChildrenAsync(userUpdates);

                if (animal.isCleaned()) {
                    Notifications.create()
                            .title(String.format("%S gyvūnas yra pilnai aprūpintas!", animal.getName()))
                            .hideAfter(Duration.seconds(10))
                            .show();
                }
            }


        });

    }
}

class AnimalCell extends ListCell<Animal> {
    private ImageView imageView = new ImageView();

    @Override
    protected void updateItem(Animal item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            imageView.setImage(null);
            setGraphic(null);
            setText(null);
        } else {

            try {
                FileInputStream fileInputStream = new FileInputStream("D:\\JAVAPROJECTS\\zoo\\src\\main\\resources\\fox.png");
                imageView.setImage(new Image(fileInputStream, 0, 64, true, true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            setText(item.getName());
            setGraphic(imageView);
        }
    }
}

