package cards.Hero;

import cards.Card;
import table.Table;
import fileio.CardInput;

import java.util.ArrayList;

public final class EmpressThorina extends Card {
    private String type;

    public EmpressThorina(final CardInput cardInput) {
        super(cardInput);
        this.type = "Hero";
    }

    /**
      {@inheritDoc}

      Empress Thorina's ability for removing from a row
      the card with the maximum health.
     */
    public void useHeroAbility(final Table table, final int affectedRow) {
        ArrayList<Card> cards = table.getCards().get(affectedRow);

        if (cards.size() == 0) {
            return;
        }

        super.removeCardOfMaxHealth(cards);
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

}
