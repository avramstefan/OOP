package cards.Minion;

import cards.Card;
import fileio.CardInput;

public final class TheRipper extends Card {
    private String type;
    private String specificType;

    public TheRipper(final CardInput cardInput) {
        super(cardInput);
        this.type = "Minion";
        this.specificType = "FrontMinion";
    }

    @Override
    public void useMinionAbility(final Card cardAttacked) {
        super.useMinionAbility(cardAttacked);
        cardAttacked.setAttackDamage(cardAttacked.getAttackDamage() - 2);

        if (cardAttacked.getAttackDamage() < 0) {
            cardAttacked.setAttackDamage(0);
        }
    }
    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public String getSpecificType() {
        return specificType;
    }

    @Override
    public void setSpecificType(final String specificType) {
        this.specificType = specificType;
    }
}
