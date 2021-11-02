package eif.viko.lt.zoo;

import java.util.*;

public class User {
    private String email = "user@example.com";
    private String password = "secretPassword";
    private String phone = "+11234567890";
    private String photoURL = "https://www.colourbox.com/preview/12863178-profile-icon-male-avatar-portrait-casual-person.jpg";
    private String displayName = "John Doe";
    private final boolean isDisabled = false;
    private final boolean isEmailVerified = false;
    private final List<Animal> animals = new ArrayList<>();

    public User() {
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
                    "https://picsum.photos/200/300",
                    false,
                    false,
                    false
            );

            animals.add(randAnimal);
        }
    }


    public List<Animal> getAnimals() {
        return animals;
    }

    public void setEmail (String email){
            this.email = email;
        }

        public void setPassword (String password){
            this.password = password;
        }

        public void setDisplayName (String displayName){
            this.displayName = displayName;
        }

        public String getEmail () {
            return email;
        }

        public String getPassword () {
            return password;
        }

        public String getPhone () {
            return phone;
        }

        public String getPhotoURL () {
            return photoURL;
        }

        public String getDisplayName () {
            return displayName;
        }

        public boolean isDisabled () {
            return isDisabled;
        }

        public boolean isEmailVerified () {
            return isEmailVerified;
        }

        @Override
        public String toString () {
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
