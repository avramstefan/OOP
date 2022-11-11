package Cards.Minion;

import Cards.Card;
import Table.Table;
import fileio.CardInput;

import java.util.ArrayList;

public class TheCursedOne extends Card {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private String type;
    private String specificType;

    TheCursedOne() {
        super();
    }

    public TheCursedOne(CardInput cardInput) {
        super(cardInput);
        this.type = "Minion";
        this.specificType = "BackMinion";
    }

    @Override
    public void useMinionAbility(Card cardAttacked) {
        super.useMinionAbility(cardAttacked);

        int temp = cardAttacked.getHealth();
        cardAttacked.setHealth(cardAttacked.getAttackDamage());
        cardAttacked.setAttackDamage(temp);
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
