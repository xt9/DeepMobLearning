package xt9.deepmoblearning.common.util;

/**
 * Created by xt9 on 2017-06-17.
 */
public class AnimationHelper {
    private boolean hasStarted = false;
    private int renderPos = -1;
    private int renderStop = 0;
    private int callsSinceRenderStart = 0;

    public String animate(String string, int frame, boolean loop) {
        String result = "";

        if(!this.hasStarted) {
            this.hasStarted = true;
            this.renderPos = -1;
            this.renderStop = string.length();
        } else {
            callsSinceRenderStart++;
            if(this.renderPos < this.renderStop) {
                if(renderPos >= 0) {
                    result = string.substring(0, this.renderPos);
                }
                this.renderPos = callsSinceRenderStart % frame == 0 ? this.renderPos + 1 : this.renderPos;
            } else {
                if(loop) {
                    this.hasStarted = false;
                }
                result = string;
            }
        }

        return result;
    }
}
