package xt9.deepmoblearning.common.util;

/**
 * Created by xt9 on 2017-06-17.
 */
public class Animation {
    private boolean hasStarted = false;
    private int renderPos = 0;
    private int renderStop = 0;
    private int callsSinceRenderStart = 0;
    private String currentString = "";

    public String animate(String string, int frame, boolean loop) {
        if(!hasStarted) {
            this.hasStarted = true;
            this.renderPos = 0;
            this.renderStop = string.length();
        } else {
            callsSinceRenderStart++;
            if(renderPos < renderStop) {
                if(renderPos >= 0) {
                    this.currentString = string.substring(0, renderPos);
                }
                this.renderPos = callsSinceRenderStart % frame == 0 ? renderPos + 1 : renderPos;
            } else {
                if(loop) {
                    this.hasStarted = callsSinceRenderStart % frame != 0;
                }
                this.currentString = string;
            }
        }

        return currentString;
    }

    public String getCurrentString() {
        return currentString;
    }

    public boolean hasFinished() {
        return renderPos == renderStop && renderStop != 0;
    }

    public void clear() {
        this.currentString = "";
        this.hasStarted = false;
        this.renderPos = 0;
        this.renderStop = 0;
        this.callsSinceRenderStart = 0;
    }
}
