package list.memory;

public class Card {
    private String name;
    private String imagePath; // Chemin vers le fichier de l'image

    public Card(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }
}
