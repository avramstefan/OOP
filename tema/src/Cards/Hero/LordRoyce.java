package Cards.Hero;

import Cards.Card;
import Table.Table;
import fileio.CardInput;

import java.util.ArrayList;

public class LordRoyce extends Card {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private String type;
    private String specificType;

    LordRoyce() {
        super();
    }

    public LordRoyce(CardInput cardInput) {
        super(cardInput);
        this.type = "Hero";
    }

    @Override
    public void useHeroAbility(Table table, int affectedRow) {
        ArrayList<Card> cards = table.getCards().get(affectedRow);

        int indexOfBiggestAttackCard = 0;
        Card card = cards.get(0);

        for (int i = 1; i < cards.size(); i++)
            if (card.getAttackDamage() < cards.get(i).getAttackDamage()) {
                indexOfBiggestAttackCard = i;
                card = cards.get(i);
            }

        card.setFrozen(true);
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
