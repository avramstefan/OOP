package cards.Environment;

import cards.Card;
import table.Table;
import fileio.CardInput;

public final class Winterfell extends Card {
    private String type;
    private String specificType;

    public Winterfell(final CardInput cardInput) {
        super(cardInput);
        this.type = "Environment";
        this.specificType = "Winterfell";
    }

    @Override
    public void useEnvironmentAbility(final Table table, final int affectedRow) {
        for (Card card : table.getCards().get(affectedRow)) {
            card.setFrozen(true);
        }
    }

    @Override
    public String getSpecificType() {
        return specificType;
    }

    @Override
    public void setSpecificType(final String specificType) {
        this.specificType = specificType;
    }


    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
