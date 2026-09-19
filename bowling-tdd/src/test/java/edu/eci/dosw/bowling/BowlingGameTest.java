package edu.eci.dosw.bowling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BowlingGameTest {

    private void rollMany(BowlingGame game, int times, int pins) {
        for (int i = 0; i < times; i++) {
            game.roll(pins);
        }
    }

    private void rollPerfectGame(BowlingGame game) {
        for (int i = 0; i < 12; i++) {
            game.roll(10);
        }
    }

    private void rollAllSpares(BowlingGame game, int lastBonus) {
        for (int i = 0; i < 10; i++) {
            game.roll(5);
            game.roll(5);
        }
        game.roll(lastBonus);
    }

    // A CASOS DE PRUEBA

    @Test
    @DisplayName("A1: roll(0) suma 0 puntos")
    void rollZeroPins_addsZeroPoints() {
        BowlingGame game = new BowlingGame();
        game.roll(0);
        assertEquals(0, game.getFrames().size()); 
    }

    @Test
    @DisplayName("A2: roll(-1) lanza IllegalArgumentException")
    void rollNegativePins_throwsException() {
        BowlingGame game = new BowlingGame();
        assertThrows(IllegalArgumentException.class, () -> game.roll(-1));
    }

    @Test
    @DisplayName("A3: roll(11) lanza IllegalArgumentException")
    void rollMoreThan10Pins_throwsException() {
        BowlingGame game = new BowlingGame();
        assertThrows(IllegalArgumentException.class, () -> game.roll(11));
    }

    @Test
    @DisplayName("A4: Dos tiros en un frame suman > 10")
    void roll6And5_throwsException() {
        BowlingGame game = new BowlingGame();
        game.roll(6);
        assertThrows(IllegalArgumentException.class, () -> game.roll(5));
    }

    @Test
    @DisplayName("A5: roll() cuando el juego ya esta completo")
    void rollAfterComplete() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 20, 1);
        assertThrows(IllegalStateException.class, () -> game.roll(1));
    }

    @Test
    @DisplayName("A6: detecta strike en un frame")
    void strikeInFrame() {
        BowlingGame game = new BowlingGame();
        game.roll(10);
        assertEquals(1, game.getFrames().size());
        assertEquals(FrameType.STRIKE, game.getFrames().get(0).getType());
    }

    @Test
    @DisplayName("A7: detecta spare")
    void spareInFrame() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        game.roll(5);
        assertEquals(1, game.getFrames().size());
        assertEquals(FrameType.SPARE, game.getFrames().get(0).getType());
    }

    @Test
    @DisplayName("08: Frame 10 con strike: acepta hasta 3 tiros")
    void strikeInFrame10() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 18, 1);
        game.roll(10);
        assertEquals(10, game.getFrames().size());
        assertEquals(FrameType.STRIKE, game.getFrames().get(9).getType());
        game.roll(1); // 2nd shot
        game.roll(1); // 3rd shot
        assertEquals(10, game.getFrames().size());
    }

    // B CASOS DE PRUEBA

    @Test
    @DisplayName("B1: Juego con todos los tiros a 0 -> score() == 0")
    void allZeroGame() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 20, 0);
        assertEquals(0, game.score());
    }

    @Test
    @DisplayName("B2: Juego sin strikes ni spares")
    void noStrikesNoSpares() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 20, 1);
        assertEquals(20, game.score());
    }

    @Test
    @DisplayName("B3: Un spare en frame 1, primer tiro del frame 2 = 3")
    void oneSpare() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        game.roll(5); // spare
        game.roll(3);
        rollMany(game, 17, 0);
        assertEquals(16, game.score());
    }

    @Test
    @DisplayName("B4: Un strike en frame 1, luego roll(4) + roll(3)")
    void oneStrike() {
        BowlingGame game = new BowlingGame();
        game.roll(10); // strike
        game.roll(4);
        game.roll(3);
        rollMany(game, 16, 0);
        assertEquals(24, game.score());
    }

    @Test
    @DisplayName("B5: Dos strikes consecutivos, luego roll(5)")
    void twoStrikes() {
        BowlingGame game = new BowlingGame();
        game.roll(10); // strike
        game.roll(10); // strike
        game.roll(5);
        game.roll(0);
        rollMany(game, 14, 0);
        assertEquals(45, game.score());
    }

    @Test
    @DisplayName("B6: Todos spares + último tiro = 5 -> score() == 150")
    void allSpares() {
        BowlingGame game = new BowlingGame();
        rollAllSpares(game, 5);
        assertEquals(150, game.score());
    }

    @Test
    @DisplayName("B7: Juego perfecto - 12 strikes - score debe ser 300")
    void perfectGame_scores300() {
        BowlingGame game = new BowlingGame();
        rollPerfectGame(game);
        assertEquals(300, game.score());
    }

    @Test
    @DisplayName("B8: score() antes de completar el juego")
    void scoreBeforeComplete() {
        BowlingGame game = new BowlingGame();
        game.roll(10);
        assertThrows(IllegalStateException.class, () -> game.score());
    }

    // C CASOS DE PRUEBA

    @Test
    @DisplayName("C1: isComplete() al inicio del juego -> false")
    void isCompleteAtStart() {
        BowlingGame game = new BowlingGame();
        assertEquals(false, game.isComplete());
    }

    @Test
    @DisplayName("C2: isComplete() después de 9 frames completos -> false")
    void isCompleteAfter9() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 18, 1);
        assertEquals(false, game.isComplete());
    }

    @Test
    @DisplayName("C3: 10 frames normales completos -> true")
    void isCompleteAfter10Normal() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 20, 1);
        assertEquals(true, game.isComplete());
    }

    @Test
    @DisplayName("C4: Spare en frame 10 + tiro bonus ejecutado -> true")
    void isCompleteAfter10SpareAndBonus() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 18, 0);
        game.roll(5);
        game.roll(5); // spare
        assertEquals(false, game.isComplete());
        game.roll(5); // bonus
        assertEquals(true, game.isComplete());
    }

    @Test
    @DisplayName("C5: Strike en frame 10 + 2 tiros bonus ejecutados -> true")
    void isCompleteAfter10StrikeAnd2Bonus() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 18, 0);
        game.roll(10); // strike
        assertEquals(false, game.isComplete());
        game.roll(5); // bonus 1
        assertEquals(false, game.isComplete());
        game.roll(5); // bonus 2
        assertEquals(true, game.isComplete());
    }

    @Test
    @DisplayName("C6: Juego perfecto: tras el 12º strike -> true")
    void isCompletePerfectGame() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 11; i++) {
            game.roll(10);
        }
        assertEquals(false, game.isComplete());
        game.roll(10);
        assertEquals(true, game.isComplete());
    }
}
