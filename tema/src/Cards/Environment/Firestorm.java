package Cards.Environment;

import Cards.Card;
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

    Firestorm() {
        super();
    }

    public Firestorm(CardInput cardInput) {
        super(cardInput);
        this.type = "Environment";
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