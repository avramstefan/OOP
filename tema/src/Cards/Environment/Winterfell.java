package Cards.Environment;

import Cards.Card;
import Table.Table;
import fileio.CardInput;

import java.util.ArrayList;

public class Winterfell extends Card {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private String type;
    private String specificType;

    Winterfell() {
        super();
    }

    public Winterfell(CardInput cardInput) {
        super(cardInput);
        this.type = "Environment";
        this.specificType = "Winterfell";
    }

    @Override
    public void useSpecialAbility(Table table, int affectedRow) {
        for (Card card : table.getCards().get(affectedRow))
            card.setFrozen(true);
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
