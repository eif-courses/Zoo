package eif.viko.lt.zoo;

public class Animal {
    private String name;
    private String description;
    private String imageURL;
    private boolean cleaned;
    private boolean hungry;
    private boolean here;

    public Animal() {}

    public Animal(String name, String description, String imageURL, boolean cleaned, boolean hungry, boolean here) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.cleaned = cleaned;
        this.hungry = hungry;
        this.here = here;
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
        return cleaned;
    }

    public void setCleaned(boolean cleaned) {
        this.cleaned = cleaned;
    }

    public boolean isHungry() {
        return hungry;
    }

    public void setHungry(boolean hungry) {
        this.hungry = hungry;
    }

    public boolean isHere() {
        return here;
    }

    public void setHere(boolean here) {
        this.here = here;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", cleaned=" + cleaned +
                ", hungry=" + hungry +
                ", here=" + here +
                '}';
    }
}
