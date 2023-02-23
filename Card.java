public class Card {
    /**
     * The number of the card
     */
    private int number;
    /**
     * The number of bull head of the card
     */
    private int bullHead;

    /**
     * Other constructor
     * 
     * @param number - the value of the card
     */
    public Card(int number) {

        this.number = number;
        if(getNumber() == 55)
            this.bullHead = 7;
        else if (getNumber() % 11 == 0 && getNumber() != 55)
            this.bullHead = 5;
        else if(getNumber() % 10 == 0)
            this.bullHead = 3;
        else if(getNumber() % 5 == 0 && getNumber() % 10 != 0)
            this.bullHead = 2;
        else
            this.bullHead = 1;

    }

    /**
     * The getter of number
     * 
     * @return - the value of the number
     */
    public int getNumber() {

        return number;

    }

    /**
     * The getter of bull head
     * 
     * @return - the number of bull head
     */
    public int getBullHead() {

        return bullHead;

    }

    /**
     * To print a card. This method has been done for you.
     * 
     * You don't need to change it and you should not change it.
     */
    public void print() {
        System.out.printf("%d(%d)", number, bullHead);
    }

    /**
     * To return the string of a card. This method has been done for you
     * 
     * You don't need to change it and you should not change it.
     */
    public String toString() {
        return number + "(" + bullHead + ")";
    }
}
