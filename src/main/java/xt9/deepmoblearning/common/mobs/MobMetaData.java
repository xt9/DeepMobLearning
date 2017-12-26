package xt9.deepmoblearning.common.mobs;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.items.ItemMobChip;
import xt9.deepmoblearning.common.items.ItemPristineMatter;

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

    public Entity getExtraEntity() {
        return null;
    }

    public int getExtraInterfaceOffsetX() {
        return 0;
    }

    public int getExtraInterfaceOffsetY() {
        return 0;
    }

    public ItemStack getPristineMatter(ItemStack parentChip, int amount) {
        ItemStack matter = new ItemStack(Registry.pristineMatter, amount);
        String chipSubName = ItemMobChip.getSubName(parentChip);
        matter.setItemDamage(ItemPristineMatter.getMetaFromKey(chipSubName));
        return matter;
    }

    public int getSimulationTickCost() { return 0; }
}
