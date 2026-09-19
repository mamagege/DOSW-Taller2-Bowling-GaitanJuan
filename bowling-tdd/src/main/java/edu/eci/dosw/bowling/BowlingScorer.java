package edu.eci.dosw.bowling;

import java.util.List;

public class BowlingScorer {
    public int calculate(List<Frame> frames) {
        int score = 0;
        for (int i = 0; i < 10; i++) {
            Frame f = frames.get(i);
            if (i == 9) {
                score += f.getFirstShot() + f.getSecondShot();
                if (f.getThirdShot() != -1) {
                    score += f.getThirdShot();
                }
            } else if (f.getType() == FrameType.STRIKE) {
                score += 10 + strikeBonus(frames, i);
            } else if (f.getType() == FrameType.SPARE) {
                score += 10 + spareBonus(frames, i);
            } else {
                score += f.getFirstShot() + f.getSecondShot();
            }
        }
        return score;
    }

    private int strikeBonus(List<Frame> frames, int i) {
        Frame next = frames.get(i + 1);
        if (next.getType() == FrameType.STRIKE && i + 1 < 9) {
            Frame nextNext = frames.get(i + 2);
            return 10 + nextNext.getFirstShot();
        } else {
            return next.getFirstShot() + next.getSecondShot();
        }
    }

    private int spareBonus(List<Frame> frames, int i) {
        return frames.get(i + 1).getFirstShot();
    }
}
