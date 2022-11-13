package cards.Minion;

import cards.Card;
import fileio.CardInput;

public final class TheCursedOne extends Card {
    private String type;
    private String specificType;

    public TheCursedOne(final CardInput cardInput) {
        super(cardInput);
        this.type = "Minion";
        this.specificType = "BackMinion";
    }

    @Override
    public void useMinionAbility(final Card cardAttacked) {
        super.useMinionAbility(cardAttacked);

        int temp = cardAttacked.getHealth();
        cardAttacked.setHealth(cardAttacked.getAttackDamage());
        cardAttacked.setAttackDamage(temp);
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
