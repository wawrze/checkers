package checkers.gameplay;

import java.io.Serializable;

public class RulesSet implements Serializable {

    private final boolean isVictoryConditionsReversed;
    private final boolean isQueenRangeOne;
    private final boolean isCaptureAny;
    private final boolean isPawnMoveBackward;
    private final boolean isPawnCaptureBackward;
    private final boolean isQueenRangeOneAfterCapture;
    private final String name;
    private final String description;

    public RulesSet(final boolean isVictoryConditionsReversed, final boolean isQueenRangeOne,
                    final boolean isCaptureAny, final boolean isPawnMoveBackward, final boolean isPawnCaptureBackward,
                    final boolean isQueenRangeOneAfterCapture, final String name, final String description) {
        this.isVictoryConditionsReversed = isVictoryConditionsReversed;
        this.isQueenRangeOne = isQueenRangeOne;
        this.isCaptureAny = isCaptureAny;
        this.isPawnMoveBackward = isPawnMoveBackward;
        this.isPawnCaptureBackward = isPawnCaptureBackward;
        this.isQueenRangeOneAfterCapture = isQueenRangeOneAfterCapture;
        this.name = name;
        this.description = description;
    }

    public boolean isVictoryConditionsReversed() {
        return isVictoryConditionsReversed;
    }

    public boolean isQueenRangeOne() {
        return isQueenRangeOne;
    }

    public boolean isCaptureAny() {
        return isCaptureAny;
    }

    public boolean isPawnMoveBackward() {
        return isPawnMoveBackward;
    }

    public boolean isPawnCaptureBackward() {
        return isPawnCaptureBackward;
    }

    public boolean isQueenRangeOneAfterCapture() {
        return isQueenRangeOneAfterCapture;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        String s = "\"" + name + "\"\n\n";
        s += "Description: " + description + "\n\n";
        s += "Victory conditions: " + (isVictoryConditionsReversed ? "reversed" : "standard") + "\t";
        s += "Capture: " + (isCaptureAny ? "any" : "longest") + "\n";
        s += "Pawn move backward: " + (isPawnMoveBackward ? "yes" : "no") + "\t\t\t";
        s += "Queen range: " + (isQueenRangeOne ? "one field" : "any") + "\n";
        s += "Pawn capture backward: " + (isPawnCaptureBackward ? "yes" : "no") + "\t\t";
        s += "Queen move after capture: " + (isQueenRangeOneAfterCapture ? "one field" : "any") + "\n";
        return s;
    }

}
