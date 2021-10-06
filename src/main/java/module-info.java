module eif.viko.lt.zoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires retrofit2;
    requires com.google.gson;
    requires retrofit2.converter.gson;
    requires annotations;
    requires firebase.admin;
    requires com.google.auth;
    requires com.google.auth.oauth2;

    opens eif.viko.lt.zoo to javafx.fxml, com.google.gson;
    exports eif.viko.lt.zoo;
}