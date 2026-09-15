package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de un juego de Bowling para un jugador.
 * Un juego tiene exactamente 10 frames.
 */
public class BowlingGame {

    private final List<Frame> frames;
    private int currentRollInFrame;
    private int firstShotPins;
    private int currentFrame;

    public BowlingGame() {
        this.frames = new ArrayList<>();
        this.currentRollInFrame = 1;
        this.firstShotPins = 0;
        this.currentFrame = 0;
    }

    /**
     * Registra pinos derribados. Lanza IllegalArgumentException si pines < 0 o >
     * 10.
     * Lanza IllegalStateException si el juego ya termino.
     */
    public void roll(int pins) {

        if (pins < 0) {
            throw new IllegalArgumentException("Pines must be positive");
        }
        if (pins > 10) {
            throw new IllegalArgumentException("Pines must be less than or equal to 10");
        }
        if (this.isComplete()) {
            throw new IllegalStateException("Game is already complete");
        }

        if (currentRollInFrame == 1) {
            if (pins == 10) {
                Frame frame = new Frame(FrameType.STRIKE);
                frame.setFirstShot(10);
                frame.setSecondShot(0);
                this.frames.add(frame);
            } else {
                this.firstShotPins = pins;
                this.currentRollInFrame = 2;
            }
        } else {
            if (this.firstShotPins + pins > 10) {
                throw new IllegalArgumentException("Two shots sum more than 10");
            }

            Frame frame;
            if (this.firstShotPins + pins == 10) {
                frame = new Frame(FrameType.SPARE);
            } else {
                frame = new Frame(FrameType.NORMAL);
            }
            frame.setFirstShot(this.firstShotPins);
            frame.setSecondShot(pins);
            this.frames.add(frame);

            this.currentRollInFrame = 1;
            this.firstShotPins = 0;
            this.currentFrame += 1;
        }

    }

    /** Puntaje total. Lanza IllegalStateException si el juego no esta completo. */
    public int score() {
        // TODO: implementar con TDD
        return 0;
    }

    /** true cuando los 10 frames han sido completados. */
    public boolean isComplete() {
        return this.frames.size() == 10;
    }

    public List<Frame> getFrames() {
        return List.copyOf(frames);
    }
}
