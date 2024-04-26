package list.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Memory {
    private List<String> cards;

    public Memory() {
        cards = new ArrayList<>();
        cards.add("Thomas");
        cards.add("Luc");
        cards.add("Luka");
        cards.add("Azad");
        cards.add("Costa");
        cards.add("Anthony");
        cards.add("Aurore");

        // Doubler les cartes pour le jeu
        cards.addAll(cards);
        // Mélanger les cartes
        Collections.shuffle(cards);
    }

    public List<String> getCards() {
        return cards;
    }
}
