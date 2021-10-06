package eif.viko.lt.zoo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;




public class HelloController implements Initializable {



    DatabaseReference ref = FirebaseDatabase
            .getInstance()
            .getReference("/pavadinimas");

    List<Gyvunas> gyvunai  = new ArrayList<>();


    ObservableList<String> items = FXCollections.observableArrayList (
            "Single", "Double", "Suite", "Family App");

    @FXML
    private TextField pav;
    @FXML
    private Label randosm;
    @FXML
    private Label pavadinimas;

    @FXML
    private Button mygtukas;

    @FXML
    private ListView<Gyvunas> sarasas;

    @FXML
    private ImageView paveikslelis;

    @FXML
    private Label informacija;

    @FXML
    void mygtukas_paspaustas(ActionEvent event) {
        mygtukas.setStyle("-fx-background-color:#" + new Random().nextInt(999999));
    }

    public void gyvunuSarasoSudarymas(){

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

                for(Gyvunas it: gyvunai) {
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


    public void initialize(URL url, ResourceBundle resourceBundle) {



        pavadinimas.setText("LABA DIENA");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String res = (String) dataSnapshot.getValue();
                System.out.println(res);
                pavadinimas.setText("pav: "+res);
                randosm.setText(res);
                informacija.setText(res);
                pav.setText(res);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pavadinimas.setText(databaseError.getDetails());
            }
        });





        gyvunuSarasoSudarymas();

        sarasas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Gyvunas>() {
            @Override
            public void changed(ObservableValue<? extends Gyvunas> observableValue, Gyvunas gyvunas, Gyvunas t1) {
                informacija.setText(t1.getAprasymas());
                paveikslelis.setImage(new Image(t1.getPaveikslelis(),  100, 100, false, false));
            }
        });


    }
}