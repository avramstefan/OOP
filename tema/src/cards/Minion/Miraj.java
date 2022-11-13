package cards.Minion;

import cards.Card;
import fileio.CardInput;

public final class Miraj extends Card {
    private String type;
    private String specificType;

    public Miraj(final CardInput cardInput) {
        super(cardInput);
        this.type = "Minion";
        this.specificType = "FrontMinion";
    }

    @Override
    public void useMinionAbility(final Card cardAttacked) {
        super.useMinionAbility(cardAttacked);

        int temp = cardAttacked.getHealth();
        cardAttacked.setHealth(super.getHealth());
        super.setHealth(temp);

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
