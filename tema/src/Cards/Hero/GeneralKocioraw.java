package Cards.Hero;

import Cards.Card;
import Table.Table;
import fileio.CardInput;

import java.util.ArrayList;

public class GeneralKocioraw extends Card {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private String type;
    private String specificType;

    GeneralKocioraw() {
        super();
    }

    public GeneralKocioraw(CardInput cardInput) {
        super(cardInput);
        this.type = "Hero";
    }

    @Override
    public void useHeroAbility(Table table, int affectedRow) {
        for (Card card : table.getCards().get(affectedRow))
            card.setAttackDamage(card.getAttackDamage() + 1);
    }

    @Override
    public String getSpecificType() {
        return specificType;
    }

    @Override
    public void setSpecificType(String minionType) {
        this.specificType = minionType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
