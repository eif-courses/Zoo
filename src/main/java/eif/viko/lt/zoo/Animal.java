package eif.viko.lt.zoo;

public class Animal {
    private int index;
    private String name;
    private String description;
    private String imageURL;
    private boolean isCleaned;
    private boolean isHungry;
    private boolean isHere;

    public Animal() {}

    public Animal(int index, String name, String description, String imageURL, boolean isCleaned, boolean isHungry, boolean isHere) {
        this.index = index;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.isCleaned = isCleaned;
        this.isHungry = isHungry;
        this.isHere = isHere;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isCleaned() {
        return isCleaned;
    }

    public void setCleaned(boolean cleaned) {
        isCleaned = cleaned;
    }

    public boolean isHungry() {
        return isHungry;
    }

    public void setHungry(boolean hungry) {
        isHungry = hungry;
    }

    public boolean isHere() {
        return isHere;
    }

    public void setHere(boolean here) {
        isHere = here;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", isCleaned=" + isCleaned +
                ", isHungry=" + isHungry +
                ", isHere=" + isHere +
                '}';
    }
}
