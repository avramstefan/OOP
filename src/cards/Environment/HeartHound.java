package cards.Environment;

import cards.Card;
import table.Table;
import fileio.CardInput;

import java.util.ArrayList;

import static game.GameConstants.TABLE_ROW_INDEX_0;
import static game.GameConstants.TABLE_ROW_INDEX_1;
import static game.GameConstants.TABLE_ROW_INDEX_2;
import static game.GameConstants.TABLE_ROW_INDEX_3;

public final class HeartHound extends Card {
    private String type;
    private String specificType;

    public HeartHound(final CardInput cardInput) {
        super(cardInput);
        this.type = "Environment";
        this.specificType = "HeartHound";
    }

    @Override
    public void useEnvironmentAbility(final Table table, final int affectedRow) {
        ArrayList<Card> cards = table.getCards().get(affectedRow);
        Card cardToBeStolen = super.removeCardOfMaxHealth(cards);

        if (affectedRow == TABLE_ROW_INDEX_0) {
            table.getCards().get(TABLE_ROW_INDEX_3).add(cardToBeStolen);
        } else if (affectedRow == TABLE_ROW_INDEX_1) {
            table.getCards().get(TABLE_ROW_INDEX_2).add(cardToBeStolen);
        } else if (affectedRow == TABLE_ROW_INDEX_2) {
            table.getCards().get(TABLE_ROW_INDEX_1).add(cardToBeStolen);
        } else {
            table.getCards().get(TABLE_ROW_INDEX_0).add(cardToBeStolen);
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
