package ar.edu.utn.frc.tup.lciii;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Clase que representa un mazo de cartas españolas.
 */
public class Deck {

    /**
     * Lista de cartas del mazo
     */
    private final Stack<Card> cards;

    public Stack<Card> getCards() {
        return cards;
    }

    /**
     * Constructor de la clase Deck
     * El constructor crea la lista de cartas y las mezcla
     * Como el juego se juega con 40 cartas, el constructor retorna un mazo sin los 8 y 9 y sin los Jokers
     *
     * @see Deck#cards
     * @see Card
     * @see Stack
     * @see Deck#createDeck()
     */
    public Deck() {
        this.cards = new Stack<Card>();
        createDeck();
        shuffleDeck();
    }

    /**
     * Crea las cartas del mazo
     * El mazo se crea con 40 cartas, sin los 8 y 9 y sin los Jokers
     *
     * @see Deck#cards
     * @see Card
     */
    private void createDeck() {
        // TODO: Implementar la creación de las cartas del mazo. Recuerde que son barajas de 40 cartas
        //  y que no se incluyen los 8 y 9.
        //  Las cartas poseen un CardSuit, un número y un valor.
        //  Los valores de las cartas son los siguientes:
        //  - 1 al 7: su valor es el mismo que su número
        //  - 10: su valor es 8
        //  - 11: su valor es 9
        //  - 12: su valor es 10
        for (int i=0;i<4;i++) {
            CardSuit ca;
            switch (i) {
                case 0: ca = CardSuit.OROS; break;
                case 1: ca = CardSuit.BASTOS; break;
                case 2: ca = CardSuit.COPAS; break;
                case 3: ca = CardSuit.ESPADAS; break;
                default: continue;
            }
            for (int j=1;j<=10;j++) {
                if (j==8 || j==9 || j==10) {
                    this.cards.add(new Card(ca,j+2,j));
                } else {
                    this.cards.add(new Card(ca,j,j));
                }

            }
        }
    }

    /**
     * Toma una carta del mazo y la quita del mismo
     *
     * @return la carta tomada
     *
     * @see Deck#cards
     * @see Card
     * @see Stack
     */
    public Card takeCard() {
        return cards.pop();
    }

    /**
     * Verifica si el mazo está vacío
     *
     * @return true si el mazo está vacío, false en caso contrario
     *
     * @see Deck#cards
     * @see Stack#isEmpty()
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Mezcla la lista de cartas que recibe como parámetro
     *
     * @see Deck#cards
     * @see Collections#shuffle(List)
     */
    private void shuffleDeck() {
        Collections.shuffle(cards);
    }

}
