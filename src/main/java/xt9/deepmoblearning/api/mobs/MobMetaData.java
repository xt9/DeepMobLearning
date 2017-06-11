package xt9.deepmoblearning.api.mobs;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2017-06-09.
 */
public abstract class MobMetaData {
    protected World world;
    public MobMetaData() {
        this.world = Minecraft.getMinecraft().world;
    }

    public abstract Entity getEntity();
    public abstract String getMobName();
    public abstract String[] getMobTrivia();
    public abstract int getNumberOfHearts();
    public abstract int getInterfaceScale();
    public abstract int getInterfaceOffsetX();
    public abstract int getInterfaceOffsetY();

    public Entity getChildEntity() {
        return null;
    }

    public int getChildInterfaceOffsetX() {
        return 0;
    }

    public int getChildInterfaceOffsetY() {
        return 0;
    }
}
