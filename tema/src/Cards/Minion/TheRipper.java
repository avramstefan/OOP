package Cards.Minion;

import Cards.Card;
import Table.Table;
import fileio.CardInput;

import java.util.ArrayList;

public class TheRipper extends Card {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private String type;
    private String specificType;

    TheRipper() {
        super();
    }

    public TheRipper(CardInput cardInput) {
        super(cardInput);
        this.type = "Minion";
        this.specificType = "FrontMinion";
    }

    @Override
    public void useMinionAbility(Card cardAttacked) {
        super.useMinionAbility(cardAttacked);
        cardAttacked.setAttackDamage(cardAttacked.getAttackDamage() - 2);

        if (cardAttacked.getAttackDamage() < 0)
            cardAttacked.setAttackDamage(0);
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getSpecificType() {
        return specificType;
    }

    @Override
    public void setSpecificType(String specificType) {
        this.specificType = specificType;
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
