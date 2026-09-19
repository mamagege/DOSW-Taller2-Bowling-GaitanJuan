package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de un juego de Bowling para un jugador.
 * Un juego tiene exactamente 10 frames.
 */
public class BowlingGame {

    private final List<Frame> frames;
    private Frame currentFrameObj;

    public BowlingGame() {
        this.frames = new ArrayList<>();
    }

    /**
     * Registra pinos derribados. Lanza IllegalArgumentException si pines < 0 o >
     * 10.
     * Lanza IllegalStateException si el juego ya termino.
     */
    public void roll(int pins) {
        if (pins < 0 || pins > 10) {
            throw new IllegalArgumentException("Invalid pins");
        }
        if (this.isComplete()) {
            throw new IllegalStateException("Game is already complete");
        }

        if (frames.size() < 9) {
            if (currentFrameObj == null) {
                if (pins == 10) {
                    Frame f = new Frame(FrameType.STRIKE);
                    f.setFirstShot(10);
                    f.setSecondShot(0);
                    frames.add(f);
                } else {
                    currentFrameObj = new Frame(FrameType.NORMAL);
                    currentFrameObj.setFirstShot(pins);
                }
            } else {
                if (currentFrameObj.getFirstShot() + pins > 10) {
                    throw new IllegalArgumentException("Two shots sum more than 10");
                }
                Frame f;
                if (currentFrameObj.getFirstShot() + pins == 10) {
                    f = new Frame(FrameType.SPARE);
                } else {
                    f = new Frame(FrameType.NORMAL);
                }
                f.setFirstShot(currentFrameObj.getFirstShot());
                f.setSecondShot(pins);
                frames.add(f);
                currentFrameObj = null;
            }
        } else {
            // 10th frame logic
            if (frames.size() == 9) {
                if (currentFrameObj == null) {
                    if (pins == 10) {
                        Frame f = new Frame(FrameType.STRIKE);
                        f.setFirstShot(10);
                        frames.add(f);
                        currentFrameObj = f;
                    } else {
                        currentFrameObj = new Frame(FrameType.NORMAL);
                        currentFrameObj.setFirstShot(pins);
                    }
                } else {
                    if (currentFrameObj.getFirstShot() + pins > 10 && currentFrameObj.getFirstShot() < 10) {
                        throw new IllegalArgumentException("Two shots sum more than 10");
                    }
                    Frame f;
                    if (currentFrameObj.getFirstShot() + pins == 10) {
                        f = new Frame(FrameType.SPARE);
                    } else {
                        f = new Frame(FrameType.NORMAL);
                    }
                    f.setFirstShot(currentFrameObj.getFirstShot());
                    f.setSecondShot(pins);
                    frames.add(f);
                    currentFrameObj = f;
                }
            } else { // Bonus shots
                Frame f = frames.get(9);
                if (f.getType() == FrameType.STRIKE) {
                    if (f.getSecondShot() == -1) {
                        f.setSecondShot(pins);
                    } else {
                        if (f.getSecondShot() < 10 && f.getSecondShot() + pins > 10) {
                            throw new IllegalArgumentException("Two shots sum more than 10");
                        }
                        f.setThirdShot(pins);
                    }
                } else if (f.getType() == FrameType.SPARE) {
                    f.setThirdShot(pins);
                }
            }
        }
    }

    /** Puntaje total. Lanza IllegalStateException si el juego no esta completo. */
    public int score() {
        if (!isComplete()) {
            throw new IllegalStateException("Game is not complete");
        }
        return new BowlingScorer().calculate(this.frames);
    }

    /** true cuando los 10 frames han sido completados. */
    public boolean isComplete() {
        if (frames.size() < 10)
            return false;
        Frame f = frames.get(9);
        if (f.getType() == FrameType.NORMAL) {
            return true;
        } else if (f.getType() == FrameType.STRIKE) {
            return f.getSecondShot() != -1 && f.getThirdShot() != -1;
        } else if (f.getType() == FrameType.SPARE) {
            return f.getThirdShot() != -1;
        }
        return false;
    }

    public List<Frame> getFrames() {
        return List.copyOf(frames);
    }
}
