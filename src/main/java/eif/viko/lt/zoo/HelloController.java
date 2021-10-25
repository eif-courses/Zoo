package eif.viko.lt.zoo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.Future;


public class HelloController implements Initializable {


    DatabaseReference refUsers = FirebaseDatabase.getInstance().getReference("/users");

    DatabaseReference refGyvunai = FirebaseDatabase.getInstance().getReference("/gyvunai");


    List<Gyvunas> gyvunai = new ArrayList<>();


    ObservableList<String> items = FXCollections.observableArrayList(
            "Single", "Double", "Suite", "Family App");


    @FXML
    private Button mygtukas;

    @FXML
    private ListView<Gyvunas> sarasas;

    @FXML
    private ImageView paveikslelis;

    @FXML
    private Label informacija;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private TextField name;


    @FXML
    private TextField username_login;

    @FXML
    private TextField password_login;

    @FXML
    private Text statusas;


    // PRSIJUNGIMAS PRIE SISTEMOS
    FirebaseAuth auth = null;
    TextField textField_username = null;
    TextField textField_password = null;
    Label label_username = null;
    Label label_password = null;

    TextInputDialog textInputDialog = null;

    void sign_in_form() {
        textInputDialog = new TextInputDialog();

        DialogPane dialog = textInputDialog.getDialogPane();

        label_username = new Label("Enter Username");
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

        sign_in_button.setOnAction( actionEvent -> {
            auth = FirebaseAuth.getInstance();

            UserRecord userByEmail = null;
            try {
                userByEmail = auth.getUserByEmail(textField_username.getText());
            } catch (FirebaseAuthException e) {
                e.printStackTrace();
            }
            if (userByEmail != null) {
                statusas.setText("Online, user id:" + userByEmail.getUid());
            } else {
                statusas.setText("Offline");
            }

        });

        label_username.setOnMouseClicked(mouseEvent -> label_username.setStyle("-fx-background-color: red"));

        textInputDialog.showAndWait();



    }


    @FXML
    void sign_in(ActionEvent event) {
        sign_in_form();
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
        }finally {
            assert userRecord != null;
            refUsers.child(userRecord.getUid()).setValueAsync(testUser);
        }

        information_text.setText(userRecord.getUid());
        System.out.println("Successfully created new user: " + userRecord.getUid());
    }


    @FXML
    void mygtukas_paspaustas(ActionEvent event) {
        mygtukas.setStyle("-fx-background-color:#" + new Random().nextInt(999999));


        addToDB(new Gyvunas("aw", "aqeawea", "waewarfaw"));// IKELIMAS I DB


    }

    public void gyvunuSarasoSudarymas() {

        // https://zoo-vilnius-default-rtdb.firebaseio.com/gyvunai.json

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://zoo-vilnius-default-rtdb.firebaseio.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GyvunasAPI service = retrofit.create(GyvunasAPI.class);

        Call<List<Gyvunas>> repos = service.getGyvunai();


        repos.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NotNull Call<List<Gyvunas>> call, @NotNull Response<List<Gyvunas>> response) {
                assert response.body() != null;
                gyvunai.addAll(response.body());

                sarasas.getItems().clear();

                for (Gyvunas it : gyvunai) {
                    sarasas.getItems().add(it);
                }
                //sarasas.setItems(gyvunai);


            }

            @Override
            public void onFailure(Call<List<Gyvunas>> call, Throwable throwable) {
                System.out.println("DEJA NEPAVYKO GAUTI DUOMENU");
            }
        });


//        Gyvunas tigras = new Gyvunas(
//                "Baltasis Tigras",
//                "Gyvena Sibiro stepėse, dažnai pastebimas ir Kauno zoo.",
//                "https://www.schemuskrynele.lt/wp-content/uploads/2021/04/Originalas-23.jpg");
//
//
//        Gyvunas lape = new Gyvunas(
//                "Lapė",
//                "Gyvena Sibiro stepėse, dažnai pastebimas ir Kauno zoo.",
//                "https://www.lrt.lt/img/2021/06/01/981468-288531-756x425.jpg");
//
//        gyvunai.add(tigras);
//        gyvunai.add(lape);

    }

    public void addToDB(Gyvunas g) {
        Gyvunas gyvunas = new Gyvunas(g.getPavadinimas(), g.getAprasymas(), g.getPaveikslelis());
        refGyvunai.push().setValueAsync(gyvunas);
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {


        //Map<String, Gyvunas> list = new HashMap<>();

        // -------------------------------

        refGyvunai.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                sarasas.getItems().clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Gyvunas g = ds.getValue(Gyvunas.class);
                    sarasas.getItems().add(g);
                    System.out.println(g.getPavadinimas());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });


//
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String res = (String) dataSnapshot.getValue();
//                System.out.println(res);
//                pavadinimas.setText("pav: "+res);
//                randosm.setText(res);
//                informacija.setText(res);
//                pav.setText(res);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                pavadinimas.setText(databaseError.getDetails());
//            }
//        });


        //gyvunuSarasoSudarymas();

        sarasas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Gyvunas>() {
            @Override
            public void changed(ObservableValue<? extends Gyvunas> observableValue, Gyvunas gyvunas, Gyvunas t1) {
                informacija.setText(t1.getAprasymas());
                paveikslelis.setImage(new Image(t1.getPaveikslelis(), 100, 100, false, false));
            }
        });


    }
}