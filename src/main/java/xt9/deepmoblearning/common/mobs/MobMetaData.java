package xt9.deepmoblearning.common.mobs;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.items.ItemPristineMatter;
import xt9.deepmoblearning.common.util.MathHelper;

/**
 * Created by xt9 on 2017-06-09.
 */
public abstract class MobMetaData {
    protected String name;
    private String pluralName;
    protected String key;
    protected int matterType;
    protected int numberOfHearts;
    protected int interfaceScale;
    protected int interfaceOffsetX;
    protected int interfaceOffsetY;

    protected World world;

    public MobMetaData(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, int matterType) {
        this.world = Minecraft.getMinecraft().world;
        this.key = key;
        this.name = name;
        this.pluralName = pluralName;
        this.numberOfHearts = numberOfHearts;
        this.interfaceScale = interfaceScale;
        this.interfaceOffsetX = interfaceOffsetX;
        this.interfaceOffsetY = interfaceOffsetY;
        this.matterType = matterType;
    }

    public String getName() {
        return this.name;
    }

    public String getPluralName() {
        return this.pluralName;
    }

    public String getKey() {
        return this.key;
    }

    public int getNumberOfHearts() {
        return this.numberOfHearts;
    }

    public int getInterfaceScale() {
        return this.interfaceScale;
    }

    public int getInterfaceOffsetX() {
        return this.interfaceOffsetX;
    }

    public int getInterfaceOffsetY() {
        return this.interfaceOffsetY;
    }

    public int getMatterType() {
        return this.matterType;
    }

    public String getMatterTypeName() {
        switch(getMatterType()) {
            case 0: return "§aOverworldian§r";
            case 1: return "§cHellish§r";
            case 2: return "§dExtraterrestrial§r";

            default: return "§aOverworldian§r";
        }
    }

    public ItemStack getPristineMatter(int amount) {
        ItemStack matter = new ItemStack(Registry.pristineMatter, amount);
        matter.setItemDamage(ItemPristineMatter.getItemDamageFromKey(getKey()));
        return matter;
    }

    public int getSimulationTickCost() {
        int cost = Config.dataModel.get(getKey()).getInt();
        cost = MathHelper.ensureRange(cost, 1, DeepConstants.MAX_DATA_MODEL_COST);
        return cost;
    }

    public abstract String[] getMobTrivia();
    public abstract Entity getEntity();

    public Entity getExtraEntity() {
        return null;
    }

    public int getExtraInterfaceOffsetX() {
        return 0;
    }

    public int getExtraInterfaceOffsetY() {
        return 0;
    }


}
