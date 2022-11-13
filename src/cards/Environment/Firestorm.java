package cards.Environment;

import cards.Card;
import table.Table;
import fileio.CardInput;

import java.util.ArrayList;

public final class Firestorm extends Card {
    private String type;
    private String specificType;

    public Firestorm(final CardInput cardInput) {
        super(cardInput);
        this.type = "Environment";
        this.specificType = "Firestorm";
    }

    @Override
    public void useEnvironmentAbility(final Table table, final int affectedRow) {
        ArrayList<Card> rowCards = table.getCards().get(affectedRow);
        ArrayList<Integer> cardsToBeRemoved = new ArrayList<>();

        for (int i = 0; i < rowCards.size(); i++) {
            rowCards.get(i).setHealth(rowCards.get(i).getHealth() - 1);
            if (rowCards.get(i).getHealth() == 0) {
                cardsToBeRemoved.add(i);
            }
        }

        int alreadyRemoved = 0;
        for (Integer idx : cardsToBeRemoved) {
            rowCards.remove(idx - alreadyRemoved);
            alreadyRemoved++;
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
