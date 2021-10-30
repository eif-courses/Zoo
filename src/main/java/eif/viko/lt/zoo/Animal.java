package eif.viko.lt.zoo;

public class Animal {
    private String name;
    private String description;
    private String imageURL;
    private boolean isCleaned;
    private boolean isHungry;
    private boolean isHere;

    public Animal() {
    }

    public Animal(String name, String description, String imageURL, boolean isCleaned, boolean isHungry, boolean isHere) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.isCleaned = isCleaned;
        this.isHungry = isHungry;
        this.isHere = isHere;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public boolean isCleaned() {
        return isCleaned;
    }

    public boolean isHungry() {
        return isHungry;
    }

    public boolean isHere() {
        return isHere;
    }
}
