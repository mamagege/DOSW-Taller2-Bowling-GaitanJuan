package edu.eci.dosw.bowling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BowlingGameTest {

    // TEST 01 CASO DE PRUEBA -VALORES NORMALES

    @Test
    @DisplayName("roll(0) suma 0 puntos")
    void rollZeroPins_addsZeroPoints() {
        BowlingGame game = new BowlingGame();
        game.roll(0);
        assertEquals(0, game.score());
    }

    // TEST 02 CASO DE PRUEBA -VALORES NEGATIVOS
    @Test
    @DisplayName("roll(-1) lanza IllegalArgumentException")

    void rollNegativePins_throwsException() {

        BowlingGame game = new BowlingGame();

        assertThrows(

                IllegalArgumentException.class,

                () -> game.roll(-1)

        );

    }

    // TEST 03 CASO DE PRUEBA -VALORES MAYORES A 10
    @Test
    @DisplayName("roll(11) lanza IllegalArgumentException")
    void rollMoreThan10Pins_throwsException() {
        BowlingGame game = new BowlingGame();
        assertThrows(
                IllegalArgumentException.class,
                () -> game.roll(11));
    }

    // TEST 04 CASO DE PRUEBA -Dos tiros en un frame suman > 10

    @Test
    @DisplayName("roll(6) y luego roll(5) lanza IllegalArgumentException")
    void roll6And5_throwsException() {
        BowlingGame game = new BowlingGame();
        game.roll(6);
        assertThrows(
                IllegalArgumentException.class,
                () -> game.roll(5));
    }

    // TEST 05 CASO DE PRUEBA - JUEGO COMPLETO
    @Test
    @DisplayName("Juego completo")
    void gameComplete() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 20; i++) {
            game.roll(1);
        }
        assertThrows(
                IllegalStateException.class,
                () -> game.roll(1));
    }

    // TEST 06 CASO DE PRUEBA - MARCA STRIKE EN FRAME Y AVANZA
    @Test
    @DisplayName("Strike en un frame")
    void strikeInFrame() {
        BowlingGame game = new BowlingGame();
        game.roll(10);
        assertEquals(1, game.getFrames().size());
        assertEquals(FrameType.STRIKE, game.getFrames().get(0).getType());
    }

    // TEST 07 CASO DE PRUEBA - MARCA DE SPARE CON 5 + 5

    @Test
    @DisplayName("Spare con 5 + 5")
    void spareInFrame() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        game.roll(5);
        assertEquals(1, game.getFrames().size());
        assertEquals(FrameType.SPARE, game.getFrames().get(0).getType());
    }
    

}
