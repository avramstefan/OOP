package cards;

import table.Table;
import fileio.CardInput;
import java.util.ArrayList;

public class Card {
    private int mana;
    private int attackDamage;
    private int health;
    private ArrayList<String> colors;
    private String description;
    private String name;
    private String type;
    private String specificType;
    private boolean isFrozen;
    private boolean hasAttacked; // if the card has attacked in a turn

    public Card() {
    }

    public Card(final CardInput cardInput) {
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

    /**
     * Method used for "cardUsesAttack" command.
     *
     * @author Avram Stefan
     * @param cardAttacked by the current card
     * @param table of the current game
     * @param x coordinate of the attacked card
     * @param y coordinate of the attacked card
     */
    public void attack(final Card cardAttacked, final Table table, final int x, final int y) {
        cardAttacked.setHealth(cardAttacked.getHealth() - this.attackDamage);

        if (cardAttacked.getHealth() <= 0) {
            table.getCards().get(x).remove(y);
        }

        this.hasAttacked = true;
    }

    /**
     * Method used for removing the card with the maximum health from a row.
     *
     * @param cards from a given row
     * @return removed Card
     */
    public Card removeCardOfMaxHealth(final ArrayList<Card> cards) {
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

    /**
     * A method used for "useHeroAbility" command.
     *
     * @param table affected by the Hero's ability
     * @param affectedRow from the game table.
     */
    public void useHeroAbility(final Table table, final int affectedRow) {

    }

    /**
     * A method used for "useEnvironmentAbility" command.
     *
     * @param table affected by the Environment Card's ability
     * @param affectedRow from the game table
     */
    public void useEnvironmentAbility(final Table table, final int affectedRow) {

    }

    /**
     * A method used for "useMinionAbility" command.
     *
     * @param cardAttacked by the Minion's ability
     */
    public void useMinionAbility(final Card cardAttacked) {
        this.hasAttacked = true;
    }

    /**
     *
     * @return if the card has attacked this turn.
     */
    public boolean hasAttacked() {
        return hasAttacked;
    }

    /**
     * Used for setting "hasAttacked" variable, which explains
     * if the card may attack this round.
     *
     * @param hasAttacked illustrate if the card has attacked this turn.
     */
    public void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     *
     * @return the specific type of the current ard
     */
    public String getSpecificType() {
        return specificType;
    }

    /**
     * Sets card's specific type.
     *
     * @param specificType to be given to the current card
     */
    public void setSpecificType(final String specificType) {
        this.specificType = specificType;
    }

    /**
     *
     * @return if the card is frozen
     */
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * Sets card's frozen parameter.
     *
     * @param frozen
     */
    public void setFrozen(final boolean frozen) {
        isFrozen = frozen;
    }

    /**
     *
     * @return the general type of the current card
     */
    public String getType() {
        return type;
    }

    /**
     * Sets card's type/
     *
     * @param type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     *
     * @return mana that is necessary for using this card
     */
    public int getMana() {
        return mana;
    }

    /**
     * Sets card's necessary mana for being used.
     *
     * @param mana
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     *
     * @return the card's attack damage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Sets card's attack damage.
     *
     * @param attackDamage
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     *
     * @return the card's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets card's health.
     *
     * @param health
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     *
     * @return card's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets card's description.
     *
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     *
     * @return card's colors
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Sets card's colors.
     *
     * @param colors
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     *
     * @return card's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets card's name.
     *
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }
}
