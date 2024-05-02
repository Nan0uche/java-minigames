package list.memory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Memory {
    private List<Card> cards;
    private final String cardsDirectory = "img/memorycard"; // Spécifiez le chemin vers le dossier contenant les images des cartes

    public Memory() {
        cards = new ArrayList<>();
        fetchCardsFromDirectory(); // Charger les cartes à partir du dossier local
        // Doubler les cartes pour le jeu
        List<Card> doubledCards = new ArrayList<>(cards);
        cards.addAll(doubledCards);
        // Mélanger les cartes
        Collections.shuffle(cards);
    }


    public List<Card> getCards() {
        return cards;
    }

    private void fetchCardsFromDirectory() {
        File directory = new File(cardsDirectory);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String cardName = file.getName(); // Utiliser le nom du fichier comme nom de la carte
                        String imagePath = file.getAbsolutePath(); // Chemin absolu vers le fichier d'image
                        // Créer un objet Card et l'ajouter à la liste
                        cards.add(new Card(cardName, imagePath));
                    }
                }
            }
        } else {
            System.err.println("Le dossier des cartes n'existe pas ou n'est pas accessible.");
        }
    }


}
