package eif.viko.lt.zoo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.*;



public class HelloController implements Initializable {


    private final Image IMAGE_RUBY  = new Image("https://upload.wikimedia.org/wikipedia/commons/f/f1/Ruby_logo_64x64.png");
    private final Image IMAGE_APPLE  = new Image("http://findicons.com/files/icons/832/social_and_web/64/apple.png");
    private final Image IMAGE_VISTA  = new Image("http://antaki.ca/bloom/img/windows_64x64.png");
    private final Image IMAGE_TWITTER = new Image("http://files.softicons.com/download/social-media-icons/fresh-social-media-icons-by-creative-nerds/png/64x64/twitter-bird.png");

    private Image[] listOfImages = {IMAGE_RUBY, IMAGE_APPLE, IMAGE_VISTA, IMAGE_TWITTER};




    FirebaseAuth auth = null;
    DatabaseReference refUsers = FirebaseDatabase.getInstance().getReference("/users");

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
            } catch (FirebaseAuthException e) {
                e.printStackTrace();
            }
            if (userByEmail != null) {

                Image image = new Image(userByEmail.getPhotoUrl());
                profile_image.setImage(image);
                //profile_description.setText(userByEmail.getDisplayName());
                profile_email.setText(userByEmail.getEmail());

                DatabaseReference dbref = refUsers.child(userByEmail.getUid() + "/animals");

                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        animals_listview.getItems().clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Animal animal = ds.getValue(Animal.class);
                            animals_listview.getItems().add(animal);
                            System.out.println(animal.getName());
                        }
                        animals_listview.setOnMouseClicked(e->{
                            Animal animal = animals_listview.getSelectionModel().getSelectedItem();
                            System.out.println(animal.getName());
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

    }
}