package cards.Hero;

import cards.Card;
import table.Table;
import fileio.CardInput;

import java.util.ArrayList;

public final class LordRoyce extends Card {
    private String type;

    public LordRoyce(final CardInput cardInput) {
        super(cardInput);
        this.type = "Hero";
    }

    /**
     {@inheritDoc}

     Lord Royce's ability to freeze the card with the highest attack
     damage from a given row.
     */
    public void useHeroAbility(final  Table table, final int affectedRow) {
        ArrayList<Card> cards = table.getCards().get(affectedRow);

        Card card = cards.get(0);

        for (int i = 1; i < cards.size(); i++) {
            if (card.getAttackDamage() < cards.get(i).getAttackDamage()) {
                card = cards.get(i);
            }
        }
        card.setFrozen(true);
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
