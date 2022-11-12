package Cards.Hero;

import Cards.Card;
import Table.Table;
import fileio.CardInput;

import java.util.ArrayList;

public class EmpressThorina extends Card {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private String type;
    private String specificType;

    EmpressThorina() {
        super();
    }

    public EmpressThorina(CardInput cardInput) {
        super(cardInput);
        this.type = "Hero";
    }

    @Override
    public void useHeroAbility(Table table, int affectedRow) {
        ArrayList<Card> cards = table.getCards().get(affectedRow);

        if (cards.size() == 0)
            return;

        super.removeCardOfMaxHealth(cards);
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
