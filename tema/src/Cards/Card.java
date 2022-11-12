package Cards;

import Table.Table;
import fileio.CardInput;

import java.util.ArrayList;

public class Card {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private boolean isFrozen;
    private String type;
    private String specificType;
    private boolean hasAttacked; // for turn attack

    public Card() {
    }

    public Card(CardInput cardInput) {
        this.mana = cardInput.getMana();
        this.attackDamage = cardInput.getAttackDamage();
        this.health = cardInput.getHealth();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
        this.type = "Card";
        this.specificType = "NoSpecificType";
        this.isFrozen = false;
        this.hasAttacked = false;
    }

    public void attack(Card cardAttacked, Table table, int x, int y) {
        cardAttacked.setHealth(cardAttacked.getHealth() - this.attackDamage);

        if (cardAttacked.getHealth() <= 0) {
            table.getCards().get(x).remove(y);
        }

        this.hasAttacked = true;
    }
    public boolean hasAttacked() {
        return hasAttacked;
    }

    public Card removeCardOfMaxHealth(ArrayList<Card> cards) {
        int indexOfCardToRemove = 0;
        Card cardToRemove = cards.get(0);

        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getHealth() > cardToRemove.getHealth()) {
                indexOfCardToRemove = i;
                cardToRemove = cards.get(i);
            }
        }

        cards.remove(indexOfCardToRemove);
        return cardToRemove;
    }

    public void useHeroAbility(Table table, int affectedRow) {

    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public void useEnvironmentAbility(Table table, int affectedRow) {

    }

    public void useMinionAbility(Card cardAttacked) {
        this.hasAttacked = true;
    }

    public String getSpecificType() {
        return specificType;
    }

    public void setSpecificType(String specificType) {
        this.specificType = specificType;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CardInput{"
                +  "mana="
                + mana
                +  ", attackDamage="
                + attackDamage
                + ", health="
                + health
                +  ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                +  ""
                + name
                + '\''
                + '}';
    }
}
