package Cards.Environment;

import Cards.Card;
import Table.Table;
import fileio.CardInput;

import java.util.ArrayList;

public class Firestorm extends Card {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private String type;
    private String specificType;

    Firestorm() {
        super();
    }

    public Firestorm(CardInput cardInput) {
        super(cardInput);
        this.type = "Environment";
        this.specificType = "Firestorm";
    }

    @Override
    public void useSpecialAbility(Table table, int affectedRow) {
        ArrayList<Card> rowCards = table.getCards().get(affectedRow);
        ArrayList<Integer> cardsToBeRemoved = new ArrayList<>();

        for (int i = 0; i < rowCards.size(); i++) {
            rowCards.get(i).setHealth(rowCards.get(i).getHealth() - 1);
            if (rowCards.get(i).getHealth() == 0)
                cardsToBeRemoved.add(i);
        }

        int alreadyRemoved = 0;
        for (Integer idx : cardsToBeRemoved) {
            rowCards.remove(idx - alreadyRemoved);
            alreadyRemoved++;
        }
    }

    @Override
    public String getSpecificType() {
        return specificType;
    }

    @Override
    public void setSpecificType(String specificType) {
        this.specificType = specificType;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "CardInput{"
                +  "mana="
                + mana
                +  ", attackDamage="
                + attackDamage
                + ", health="
                + health
                +  ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                +  ""
                + name
                + '\''
                + '}';
    }
}
