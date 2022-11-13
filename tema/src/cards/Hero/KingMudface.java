package cards.Hero;

import cards.Card;
import table.Table;
import fileio.CardInput;

public final class KingMudface extends Card {
    private String type;

    public KingMudface(final CardInput cardInput) {
        super(cardInput);
        this.type = "Hero";
    }

    /**
     {@inheritDoc}

     King Mudface' capability of raising his allies' health, given
     the target row.
     */
    public void useHeroAbility(final Table table, final int affectedRow) {
        for (Card card : table.getCards().get(affectedRow)) {
            card.setHealth(card.getHealth() + 1);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
