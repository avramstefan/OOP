package Cards.Environment;

import Cards.Card;
import Table.Table;
import fileio.CardInput;

import java.util.ArrayList;

public class HeartHound extends Card {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private String type;
    private String specificType;

    HeartHound() {
        super();
    }

    public HeartHound(CardInput cardInput) {
        super(cardInput);
        this.type = "Environment";
        this.specificType = "HeartHound";
    }

    @Override
    public void useEnvironmentAbility(Table table, int affectedRow) {
        ArrayList<Card> cards = table.getCards().get(affectedRow);
        Card cardToBeStolen = super.removeCardOfMaxHealth(cards);

        if (affectedRow == 0) {
            table.getCards().get(3).add(cardToBeStolen);
        } else if (affectedRow == 1) {
            table.getCards().get(2).add(cardToBeStolen);
        } else if (affectedRow == 2) {
            table.getCards().get(1).add(cardToBeStolen);
        } else {
            table.getCards().get(0).add(cardToBeStolen);
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
