package cards.Hero;

import cards.Card;
import table.Table;
import fileio.CardInput;

public final class GeneralKocioraw extends Card {
    private String type;

    public GeneralKocioraw(final CardInput cardInput) {
        super(cardInput);
        this.type = "Hero";
    }

    /**
     {@inheritDoc}

     General Kocioraw's ability to raise his allies' attackDamage with 1 unit,
     given the target row.
     */
    public void useHeroAbility(final Table table, final int affectedRow) {
        for (Card card : table.getCards().get(affectedRow)) {
            card.setAttackDamage(card.getAttackDamage() + 1);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
