package ar.edu.utn.frc.tup.lciii;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EscobaGameTest {

    Scanner scanner = Mockito.mock(Scanner.class);

    EscobaGame escobaGame = Mockito.spy(EscobaGame.class);

    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        escobaGame.setScanner(scanner);
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setOut(systemOut);
    }

    @Test
    void welcomeMessage() {
        String expected = "Welcome to the 'Escoba de 15' game!" + System.lineSeparator();
        escobaGame.welcomeMessage();
        assertEquals(expected, getOutput());
    }

    @Test
    void createHumanUser() {
        when(scanner.nextLine()).thenReturn("Hernán");
        User user = escobaGame.createHumanUser();
        assertEquals("Hernán", user.getName());
    }

    @Test
    void createAppUser() {
        User user = escobaGame.createAppUser();
        assertEquals("APP", user.getName());
    }

    @Test
    void wantPlayAgain_TrueAnswer() {
        when(scanner.nextLine()).thenReturn("y");
        Boolean result = escobaGame.wantPlayAgain();
        assertTrue(result);
    }

    @Test
    void wantPlayAgain_FalseAnswer() {
        when(scanner.nextLine()).thenReturn("n");
        Boolean result = escobaGame.wantPlayAgain();
        assertFalse(result);
    }

    @Test
    void getYesNoAnswerTest_YesAnswer() {
        // TODO: Implementar el test para el método getYesNoAnswer de manera tal que se
        //  pruebe que el método retorna false si se ingresa "n" o "N"

        EscobaGame escobaMatchRound = new EscobaGame();

        Optional<Method> opMethod = ReflectionUtils.findMethod(EscobaGame.class, "getYesNoAnswer", String.class);

        Boolean result = true;

        if (opMethod.isPresent()) {
            result = (Boolean) ReflectionUtils.invokeMethod(opMethod.get(), escobaMatchRound, "N");
        } else {
            fail("Method getYesNoAnswer not found");
        }

        assertFalse(result);
    }

    @Test
    void getYesNoAnswerTest_NoAnswer() {
        // TODO: Implementar el test para el método getYesNoAnswer de manera tal que se
        //  pruebe que el método retorna true si se ingresa "y" o "Y"

        EscobaGame escobaMatchRound = new EscobaGame();

        Optional<Method> opMethod = ReflectionUtils.findMethod(EscobaGame.class, "getYesNoAnswer", String.class);

        Boolean result = true;

        if (opMethod.isPresent()) {
            result = (Boolean) ReflectionUtils.invokeMethod(opMethod.get(), escobaMatchRound, "Y");
        } else {
            fail("Method getYesNoAnswer not found");
        }

        assertTrue(result);
    }

    @Test
    void getYesNoAnswerTest_NullAnswer() {
        // TODO: Implementar el test para el método getYesNoAnswer de manera tal que se
        //  pruebe que el método retorna null si se ingresa algo distinto de "y", "Y", "n" o "N"

        EscobaGame escobaMatchRound = new EscobaGame();

        Optional<Method> opMethod = ReflectionUtils.findMethod(EscobaGame.class, "getYesNoAnswer", String.class);

        Boolean result = true;

        if (opMethod.isPresent()) {
            result = (Boolean) ReflectionUtils.invokeMethod(opMethod.get(), escobaMatchRound, "q");
        } else {
            fail("Method getYesNoAnswer not found");
        }

        assertNull(result);
    }

    private String getOutput() {
        return testOut.toString();
    }
}