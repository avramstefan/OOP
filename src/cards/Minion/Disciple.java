package cards.Minion;

import cards.Card;
import fileio.CardInput;

public final class Disciple extends Card {
    private String type;
    private String specificType;

    public Disciple(final CardInput cardInput) {
        super(cardInput);
        this.type = "Minion";
        this.specificType = "BackMinion";
    }
    @Override
    public void useMinionAbility(final Card cardAttacked) {
        super.useMinionAbility(cardAttacked);
        cardAttacked.setHealth(cardAttacked.getHealth() + 2);
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
