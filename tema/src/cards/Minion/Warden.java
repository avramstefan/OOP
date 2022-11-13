package cards.Minion;

import cards.Card;
import fileio.CardInput;

public final class Warden extends Card {
    private String name;
    private String type;
    private String specificType;

    public Warden(final CardInput cardInput) {
        super(cardInput);
        this.type = "Minion";
        this.specificType = "FrontTankMinion";
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
