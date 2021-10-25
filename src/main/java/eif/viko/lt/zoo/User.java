package eif.viko.lt.zoo;

public class User {
    private String email = "user@example.com";
    private String password = "secretPassword";
    private String phone = "+11234567890";
    private String photoURL = "http://www.example.com/12345678/photo.png";
    private String displayName = "John Doe";
    private boolean isDisabled = false;
    private boolean isEmailVerified = false;

    public User() {}

    public User(String email, String password, String phone, String photoURL, String displayName, boolean isDisabled, boolean isEmailVerified) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.photoURL = photoURL;
        this.displayName = displayName;
        this.isDisabled = isDisabled;
        this.isEmailVerified = isEmailVerified;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", displayName='" + displayName + '\'' +
                ", isDisabled=" + isDisabled +
                ", isEmailVerified=" + isEmailVerified +
                '}';
    }
}
