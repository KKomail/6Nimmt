import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;



public class Table {
    /**
     * Total number of player. Use this variable whenever possible
     */
    private static final int NUM_OF_PLAYERS = 4;
    /**
     * Total number of cards used in this game. Use this variable whenever possible
     */
    private static final int TOTAL_NUMBER_OF_CARD = 104;
    /**
     * The four stacks of cards on the table.
     */
    private Card[][] stacks = new Card[4][6];
    /**
     * This number of cards of each stack on the table. For example, if the variable
     * stacks stores
     * -------------------------
     * | 0 | 10 13 14 -- -- -- |
     * | 1 | 12 45 -- -- -- -- |
     * | 2 | 51 55 67 77 88 90 |
     * | 3 | 42 -- -- -- -- -- |
     * -------------------------
     * 
     * stacksCount should be {3, 2, 6, 1}.
     * 
     * You are responsible to maintain the data consistency.
     */
    private int[] stacksCount = new int[4];
    /**
     * The array of players
     */
    private Player[] players = new Player[NUM_OF_PLAYERS];

    /**
     * Default constructor
     * 
     * In the constructor, you should perform the following tasks:
     * 
     * 1. Initialize cards for play. You should construct enough number of cards
     * to play. These cards should be unique (i.e., no two cards share the same
     * number). The value of card must be between 1 to 104. The number of bullHead
     * printed on each card can be referred to the rule.
     * 
     * 2. Initialize four player. The first player should be a human player, call
     * "Kevin". The other player should be a computer player. These computer player
     * should have the name "Computer #1", "Computer #2", "Computer #3".
     * 
     * 3. Deal randomly 10 cards to each player. A card can only be dealt to one
     * player. That is, no two players can have the same card.
     * 
     * 4. Deal a card on each stack. The card dealt on the stack should not be dealt
     * to any player. Card dealt on each stack should also be unique (no two stack
     * have the same card).
     * 
     */
    public Table() {

        //1.
        Card[] cards = new Card[TOTAL_NUMBER_OF_CARD];

        for (int i = 0; i < TOTAL_NUMBER_OF_CARD; i++) {
            cards[i] = new Card(i + 1);
        }

        //2.
        players[0] = new Player("Kevin");
        players[1] = new Player();
        players[2] = new Player();
        players[3] = new Player();

        //3.
        Card[] newCards = new Card[cards.length - 1];


        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 4; i++) {
                int randomVar = ThreadLocalRandom.current().nextInt(0, TOTAL_NUMBER_OF_CARD);
                players[i].dealCard(cards[randomVar]);


                //Remove cards that have been dealt so they are not repeated
                for (int f = 0, k = 0; f < cards.length; f++) {
                    if (cards[f].equals(cards[randomVar])) {
                        continue;
                    }
                    newCards[k++] = cards[f];
                }
//                cards = newCards;

            }

        }


        //4.


        int count = 0;

        for (int i = 0; i < stacks.length; i++) {
            for (int j = 0; j < stacks[i].length; j++) {
                int randomVar = ThreadLocalRandom.current().nextInt(0, TOTAL_NUMBER_OF_CARD - 1);
                stacks[i][0] = (cards[randomVar]);
                count++;

                //Remove cards so duplication does not occur
                for (int l = 0, m = 0; l < cards.length - 1; l++) {
                    if (cards[j].equals(cards[randomVar])) {
                        continue;
                    }
                    newCards[m++] = cards[l];
                }
                cards = newCards;
                //updating stacks
                stacksCount[i] = count;
            }
        }
    }

    /**
     * This method is to find the correct stack that a card should be added to
     * according to the rule. It should return the stack among which top-card of
     * that stack is the largest of those smaller than the card to be placed. (If
     * the rule sounds complicate to you, please refer to the game video.)
     * 
     * In case the card to be place is smaller than the top cards of all stacks,
     * return -1.
     * 
     * @param card - the card to be placed
     * @return the index of stack (0,1,2,3) that the card should be place or -1 if
     *         the card is smaller than all top cards
     */
    public int findStackToAdd(Card card) {

        int[] differences = new int[4];

        //create an array that holds all the differences of the card dealt and the card in the stack
        if(differences[0] > 0 && differences[1] > 0 && differences[2] > 0 && differences[3] > 0) {
            for (int i = 0; i < stacks.length; i++) {
                differences[i] = card.getNumber() - stacks[i][stacksCount[i] - 1].getNumber();

            }
        }


        if(differences[0] < 0 && differences[1] < 0 && differences[2] < 0 && differences[3] < 0) {
            return -1;
        }
        else {


            //if differences is smallest return that stack index
            if (differences[0] < differences[1] &&
                    differences[0] < differences[2] &&
                    differences[0] < differences[3])
                return 0;
            else if (differences[1] < differences[0] &&
                    differences[1] < differences[2] &&
                    differences[1] < differences[3])
                return 1;
            else if (differences[2] < differences[0] &&
                    differences[2] < differences[1] &&
                    differences[2] < differences[3])
                return 2;
            else if (differences[3] < differences[0] &&
                    differences[3] < differences[1] &&
                    differences[3] < differences[2])
                return 3;
        }


        return - 1;

    }

    /**
     * To print the stacks on the table. Please refer to the demo program for the
     * format. Within each stack, the card should be printed in ascending order,
     * left to right. However, there is no requirement on the order of stack to
     * print.
     */
    public void print() {


            System.out.println("----------Table----------");

            for(int i = 0; i < stacks.length; i++) {
                System.out.print("Stacks: " + i + ":");
                for (int j = 0; j < stacks[i].length; j++) {
                    if(stacks[i][j] != null) {
                        System.out.println(stacks[i][j]);
                    }
                }
            }


            System.out.println("-------------------------");

    }

    /**
     * This method is the main logic of the game. You should create a loop for 10
     * times (running 10 rounds). In each round all players will need to play a
     * card. These cards will be placed to the stacks from small to large according
     * to the rule of the game.
     * 
     * In case a player plays a card smaller than all top cards, he will be
     * selecting one of the stack of cards and take them to his/her own score pile.
     * If the player is a human player, he will be promoted for selection. If the
     * player is a computer player, the computer player will select the "cheapest"
     * stack, i.e. the stack that has fewest bull heads. If there are more than
     * one stack having fewest bull heads, selecting any one of them.
     */
    public void runApp() {
        for (int turn = 0; turn < 10; turn++) {
            // print Table
            print();

            // TODO


            Card[] playerCards = new Card[4];


            playerCards[0] = players[0].playCard();

            for (int i = 1; i < playerCards.length; i++) {
                playerCards[i] = players[i].playCardRandomly();
            }

            for (int i = 0; i < 4; i++) {
                System.out.println(players[i].getName() + " :" + playerCards[i]);
            }


            for (int i = 0; i < 4; i++) {
                int index = findStackToAdd(playerCards[i]);
                System.out.println("Place the card " + playerCards[i] + " for " + players[i].getName());

                if (playerCards[i].equals(players[0])) { //if player is playing
                    if (index != -1) {
                        if (stacksCount[index] < stacks[index].length) { //if stack is not full.....
                            stacks[index][(stacksCount[index])] = playerCards[i];
                            stacksCount[index]++;
                        }

                        if (stacksCount[index] == stacks[index].length) { //if stack is full
                            players[i].moveToPile(stacks[index], stacks[index].length - 1);

                            stacks[index][0] = playerCards[i];
                            stacksCount[index] = 1;

                        }
                    } else { //if -1 player has to select the stack to replace
                        int selector = 0;
                        while (selector < 0 || selector > 3) {
                            Scanner in = new Scanner(System.in);
                            System.out.println("Pick a stack to collect the cards.");
                            selector = in.nextInt();
                        }

                        //move stack to players pile
                        stacksCount[selector] = 1;
                        stacks[selector][0] = playerCards[i]; //reset the stack with a replacement card
                        players[i].moveToPile(stacks[selector], stacksCount[selector]);

                    }


                } else { //if player is computer

                    if (index != -1) {
                        if (stacksCount[index] < stacks[index].length) {
                            stacksCount[index]++;
                            stacks[index][(stacksCount[index])] = playerCards[i];
                        }

                        if (stacksCount[index] == stacks[index].length) {
                            players[i].moveToPile(stacks[index], stacks[index].length - 1);

                            stacks[index][0] = playerCards[i];
                            stacksCount[index] = 1;



                        } else {

                            int min = 0;

                            int[] newStack = new int[stacksCount.length];
                            for (int y = 0; y < stacksCount.length; y++) {
                                for (int z = 0; z < stacksCount[y]; z++) {
                                    newStack[y] = stacks[y][z].getBullHead();
                                }
                            }

                            int minStack = newStack[min];

                            for (int stackToChoose = 1; stackToChoose < newStack.length; stackToChoose++) {
                                if (minStack > newStack[stackToChoose]) {
                                    min = stackToChoose;
                                    minStack = newStack[stackToChoose];

                                }
                            }

                            // reset the stack with a replacement card
                            stacksCount[min] = 1;
                            stacks[min][0] = playerCards[i];
                            players[i].moveToPile(stacks[min], stacksCount[min]);

                        }
                    }
                }
            }

        }
        for (Player p : players) {
            System.out.println(p.getName() + " has a score of " + p.getScore());
            p.printPile();
        }
    }

    /**
     * Programme main. You should not change this.
     * 
     * @param args - no use.
     */
    public static void main(String[] args) {
        new Table().runApp();
    }

}
