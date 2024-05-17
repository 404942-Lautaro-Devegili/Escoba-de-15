package ar.edu.utn.frc.tup.lciii;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.support.ReflectionSupport;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void createDeckTest() {
        // TODO: Crear un test que valide que el mazo se crea con 40 cartas,
        //  que no se incluyen los 8 y 9.
        //  Validar que todas las cartas de un mazo de 40 cartas estén presentes.

        Deck deck = new Deck();
        assertEquals(40, deck.getCards().size());

        for (Card card : deck.getCards()) {
            assertNotEquals(8, card.getNumber());
            assertNotEquals(9, card.getNumber());
        }

        List<Card> allPossibleCards = new ArrayList<>();
        for (CardSuit suit : CardSuit.values()) {
            for (int i = 1; i <= 7; i++) {
                allPossibleCards.add(new Card(suit, i, i));
            }
            allPossibleCards.add(new Card(suit, 10, 8));
            allPossibleCards.add(new Card(suit, 11, 9));
            allPossibleCards.add(new Card(suit, 12, 10));
        }

        for (Card card : allPossibleCards) {
            assertTrue(deck.getCards().contains(card));
        }

        //fail("Not implemented yet");
    }

    @Test
    void takeCardTest() {
        // TODO: Crear un test que valide que al tomar una carta del mazo,
        //  la cantidad de cartas en el mazo disminuye en 1 y que la carta tomada
        //  es la que se esperaba; es decir la que está al tope de la pila.
        Deck deck = new Deck();
        int initialSize = deck.getCards().size();
        Card expectedCard = deck.getCards().peek();
        Card takenCard = deck.takeCard();
        int finalSize = deck.getCards().size();

        assertEquals(initialSize - 1, finalSize);
        assertEquals(expectedCard, takenCard);
        //fail("Not implemented yet");
    }

    @Test
    void isEmptyTest() {
        // TODO: Crear un test que valide que el mazo está vacío cuando no tiene cartas
        //  y que no está vacío cuando tiene al menos una carta.

        Deck deck = new Deck();
        assertFalse(deck.isEmpty());

        while (!deck.isEmpty()) {
            deck.takeCard();
        }

        assertTrue(deck.isEmpty());

        //fail("Not implemented yet");
    }

    @Test
    void shuffleDeckTest() throws NoSuchMethodException {
        // TODO: Crear un test que valide que al mezclar el mazo, las cartas no están en el mismo orden
        //  que al crear el mazo.

        Deck deck = new Deck();
        Method shuffleDeckMethod = Deck.class.getDeclaredMethod("shuffleDeck");
        if (!shuffleDeckMethod.isAccessible()) {
            shuffleDeckMethod.setAccessible(true);
        }

        List<Card> originalOrder = new ArrayList<>(deck.getCards());
        Collections.shuffle(deck.getCards());

        assertNotEquals(originalOrder, new ArrayList<>(deck.getCards()));

        //fail("Not implemented yet");

        /*Deck deck = new Deck();
        List<Card> originalOrder = new ArrayList<>(deck.getCards());

        // Invoke the shuffle method using ReflectionSupport
        ReflectionSupport.invokeMethod(
                Objects.requireNonNull(ReflectionSupport.findMethod(Deck.class, "shuffleDeck").orElse(null)),
                deck
        );

        assertNotEquals(originalOrder, new ArrayList<>(deck.getCards()));*/
    }

}
