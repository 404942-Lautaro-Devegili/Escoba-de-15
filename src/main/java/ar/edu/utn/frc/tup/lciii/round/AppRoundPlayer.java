package ar.edu.utn.frc.tup.lciii.round;

import ar.edu.utn.frc.tup.lciii.*;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AppRoundPlayer extends RoundPlayer {

    private static final Card SEVEN_ORO = new Card(CardSuit.OROS, 7, 7);

    public AppRoundPlayer(User player) {
        super(player);
    }

    @Override
    public void playTurn(List<Card> tableCards) {
        LetterByLetterPrinter.println("Is APP turn...");
        showCardsOnTheTable(tableCards);
        List<Card> selectedCards = selectCardsApp(tableCards);
        if(selectedCards == null) {
            LetterByLetterPrinter.println("The app can't make 15 with the cards. The app, added a card to the table.");
            Card cardToDiscard = getCardToDiscard();
            tableCards.add(cardToDiscard);
            this.getHandCards().remove(cardToDiscard);
        } else {
            takingCardsFromTable(tableCards, selectedCards);
        }
    }

    private Card getCardToDiscard() {
        return this.getHandCards().get(this.getHandCards().size() - 1);
    }

    /**
     * Este método selecciona las cartas que el jugador APP va a jugar.
     * Para esto sigue la siguiente lógica:
     * 1. Busca todas las combinaciones posibles de cartas entre las que están en la mesa y las que tiene en la mano.
     * Siempre que estas sumen 15.
     * 2. Ordena las combinaciones de mayor a menor por cantidad de cartas,
     * quedando arriba de la lista la que más cartas tiene.
     * 3. Si no encuentra ninguna combinación que sume 15, retorna null.
     * 4. Si encuentra solo una combinación que sume 15, selecciona dicha combinación.
     * 5. Si encuentra más de una combinación que sume 15, selecciona por el siguiente orden de prioridad:
     *   a. La combinación que contenga una escoba.
     *   b. La combinación que contenga el 7 de oro.
     *   c. La combinación que contenga más cartas de oro.
     *   d. La combinación que contenga más 7.
     *   e. La combinación que contenga más cartas. En este caso,
     *   la primera de la lista, ya la misma está ordenada por cantidad de cartas.
     *
     * @param tableCards cartas en la mesa.
     *
     * @return lista de cartas seleccionadas por el jugador APP.
     */
    private List<Card> selectCardsApp(List<Card> tableCards) {
        List<Card> selectedCards;
        List<List<Card>> subconjuntos = new ArrayList<>();
        for (Card handCard : this.getHandCards()) {
            List<Card> conjunto = new ArrayList<>(tableCards);
            conjunto.add(handCard);
            List<List<Card>> subconjuntosAux = obtenerSubconjuntos(conjunto);
            subconjuntosAux.removeIf(c -> !c.contains(handCard));
            subconjuntosAux.removeIf(c -> c.stream().mapToInt(Card::getValue).sum() != 15);
            subconjuntos.addAll(subconjuntosAux);
        }

        // TODO: Implementar método a partir de este punto siguiendo estas instrucciones:
        //  1. Si subconjuntos está vacío, retornar null.
        if (subconjuntos.isEmpty()) {
            return null;
        }
        //  2. Ordenar subconjuntos de mayor a menor por cantidad de cartas.
        Comparator<List<Card>> comparator = Comparator.comparingInt(List::size);
        subconjuntos.sort(comparator.reversed());
        List<Card> lcEscoba = getCardsWithEscoba(subconjuntos, tableCards.size()+1);
        List<Card> lcSevenOro = getCardsWithSevenOro(subconjuntos);
        List<Card> lcMoreOro = getCardsWithMoreOros(subconjuntos);
        List<Card> lcMoreSeven = getCardsWithSeven(subconjuntos);
        if (lcEscoba != null) {
            //  3. Llamar al método getCardsWithEscoba, si retorna algo diferente de null, retornar el valor.
            return lcEscoba;
        } else if (lcSevenOro != null) {
            //  4. Llamar al método getCardsWithSevenOro, si retorna algo diferente de null, retornar el valor.
            return  lcSevenOro;
        } else if (lcMoreOro != null) {
            //  5. Llamar al método getCardsWithMoreOros, si retorna algo diferente de null, retornar el valor.
            return lcMoreOro;
        } else if (lcMoreSeven != null) {
            //  6. Llamar al método getCardsWithSeven, si retorna algo diferente de null, retornar el valor.
            return lcMoreSeven;
        } else {
            //  7. Si no se cumple ninguna de las condiciones anteriores, retornar el primer subconjunto con mas cartas.
            return subconjuntos.get(0);
        }

    }

    /**
     * Método que obtiene el subconjunto que contiene más cartas del tipo 7 o null si no existe ninguno.
     *
     * @param subconjuntos lista de subconjuntos en los que buscar.
     * @return el subconjunto que contiene más 7 o null si no existe.
     */
    private List<Card> getCardsWithSeven(List<List<Card>> subconjuntos) {
        // TODO: Implementar lógica para seleccionar el subconjunto que contenga más 7
        int contador1 = 0;
        List<Card> lcaDevolver = null;
        for (List<Card> lc : subconjuntos) {
            int contador2 = 0;
            for (Card c : lc) {
                if (c.getNumber() == 7) {
                    contador2++;
                }
            }
            if (contador2> contador1) {
                lcaDevolver = lc;
                contador1  = contador2;
            }
        }
        return lcaDevolver;
    }

    /**
     * Método que obtiene el subconjunto que contiene una escoba o null si no existe ninguno.
     * Este método valída que un subconjunto de cartas sea igual a la
     * cantidad pasada por parámetro (cartas de la mesa + 1).
     *
     * @param subconjuntos lista de subconjuntos en los que buscar.
     * @param cardsQuantityToEscoba cantidad de cartas necesarias para hacer escoba. Igual a cartas de la mesa + 1.
     * @return el subconjunto que contiene una escoba o null si no existe.
     */
    private List<Card> getCardsWithEscoba(List<List<Card>> subconjuntos, Integer cardsQuantityToEscoba) {
        // TODO: Implementar lógica para seleccionar el subconjunto que contenga una escoba
        for (List<Card> s : subconjuntos) {
            if (s.size() == cardsQuantityToEscoba) {
                return s;
            }
        }
        return null;
    }

    /**
     * Método que obtiene el subconjunto que contiene el 7 de oro o null si no existe ninguno.
     *
     * @param subconjuntos lista de subconjuntos en los que buscar.
     * @return el subconjunto que contiene el 7 de oro o null si no existe.
     */
    private List<Card> getCardsWithSevenOro(List<List<Card>> subconjuntos) {
        // TODO: Implementar lógica para seleccionar el subconjunto que contenga el 7 de oro
        for (List<Card> lc : subconjuntos) {
            for (Card c : lc) {
                if (c.getNumber() == 7 && c.getCardSuit() == CardSuit.OROS) {
                    return lc;
                }
            }
        }
        return null;
    }

    /**
     * Método que obtiene el subconjunto que contiene más cartas de oro o null si no existe ninguno.
     *
     * @param subconjuntos lista de subconjuntos en los que buscar.
     * @return el subconjunto que contiene más oros o null si no existe.
     */
    private List<Card> getCardsWithMoreOros(List<List<Card>> subconjuntos) {
        // TODO: Implementar lógica para seleccionar el subconjunto que contenga más cartas de oro
        int contador1 = 0;
        List<Card> lcaDevolver = null;
        for (List<Card> lc : subconjuntos) {
            int contador2 = 0;
            for (Card c : lc) {
                if (c.getCardSuit() == CardSuit.OROS) {
                    contador2++;
                }
            }
            if (contador2> contador1) {
                lcaDevolver = lc;
                contador1  = contador2;
            }
        }
        return lcaDevolver;
    }

}
