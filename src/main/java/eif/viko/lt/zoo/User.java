package eif.viko.lt.zoo;

import java.util.*;

public class User {
    private String email = "user@example.com";
    private String password = "secretPassword";
    private String phone = "+11234567890";
    private String photoURL = "https://www.colourbox.com/preview/12863178-profile-icon-male-avatar-portrait-casual-person.jpg";
    private String displayName = "John Doe";
    private final boolean disabled = false;
    private final boolean emailVerified = false;
    private List<Animal> animals;

    public User() {
        animals = new ArrayList<>();
        loadRandomAnimals();
    }

    public void loadRandomAnimals() {

        List<String> names = new ArrayList<>(Arrays.asList("aardvark",
                "albatross",
                "alligator",
                "alpaca",
                "ant",
                "anteater",
                "antelope",
                "ape",
                "armadillo",
                "herd",
                "baboon",
                "badger",
                "barracuda",
                "bat",
                "bear",
                "beaver",
                "bee",
                "bison",
                "boar",
                "galago",
                "butterfly",
                "camel",
                "caribou",
                "cat",
                "caterpillar",
                "cattle",
                "chamois",
                "cheetah",
                "chicken"));

        for (int i = 0; i < 5; i++) {
            Collections.shuffle(names);
            Animal randAnimal = new Animal(
                    names.get(0),
                    "No description",
                    "https://apilist.fun/out/randomcat",
                    new Random().nextBoolean(),
                    new Random().nextBoolean(),
                    new Random().nextBoolean()
            );

            animals.add(randAnimal);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", displayName='" + displayName + '\'' +
                ", disabled=" + disabled +
                ", emailVerified=" + emailVerified +
                ", animals=" + animals +
                '}';
    }
}
