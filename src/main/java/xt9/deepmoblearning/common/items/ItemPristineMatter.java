package xt9.deepmoblearning.common.items;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.common.config.Config;

/**
 * Created by xt9 on 2017-12-20.
 */
public class ItemPristineMatter extends ItemBase {
    private String mobKey;

    private ItemPristineMatter(String name, String mobKey) {
        super(name, 64);
        this.mobKey = mobKey;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    public String getMobKey() {
        return mobKey;
    }

    public NonNullList<ItemStack> getLootTable() {
        return Config.LootParser.getLootEntries(getMobKey());
    }

    public static class Blaze extends ItemPristineMatter {
        public Blaze() {
            super("pristine_matter_blaze", "blaze");
        }
    }

    public static class Creeper extends ItemPristineMatter {
        public Creeper() {
            super("pristine_matter_creeper", "creeper");
        }
    }

    public static class Dragon extends ItemPristineMatter {
        public Dragon() {
            super("pristine_matter_dragon", "dragon");
        }
    }

    public static class Enderman extends ItemPristineMatter {
        public Enderman() {
            super("pristine_matter_enderman", "enderman");
        }
    }

    public static class Ghast extends ItemPristineMatter {
        public Ghast() {
            super("pristine_matter_ghast", "ghast");
        }
    }

    public static class Skeleton extends ItemPristineMatter {
        public Skeleton() {
            super("pristine_matter_skeleton", "skeleton");
        }
    }

    public static class Slime extends ItemPristineMatter {
        public Slime() {
            super("pristine_matter_slime", "slime");
        }
    }

    public static class Spider extends ItemPristineMatter {
        public Spider() {
            super("pristine_matter_spider", "spider");
        }
    }

    public static class Witch extends ItemPristineMatter {
        public Witch() {
            super("pristine_matter_witch", "witch");
        }
    }

    public static class Wither extends ItemPristineMatter {
        public Wither() {
            super("pristine_matter_wither", "wither");
        }
    }

    public static class WitherSkeleton extends ItemPristineMatter {
        public WitherSkeleton() {
            super("pristine_matter_wither_skeleton", "witherskeleton");
        }
    }

    public static class Zombie extends ItemPristineMatter {
        public Zombie() {
            super("pristine_matter_zombie", "zombie");
        }
    }

    public static class TE extends ItemPristineMatter {
        public TE() {
            super("pristine_matter_thermal_elemental", "thermalelemental");
        }
    }

    public static class TwilightForest extends ItemPristineMatter {
        public TwilightForest() {
            super("pristine_matter_twilight_forest", "twilightforest");
        }
    }

    public static class TwilightSwamp extends ItemPristineMatter {
        public TwilightSwamp() {
            super("pristine_matter_twilight_swamp", "twilightswamp");
        }
    }

    public static class TwilightDarkwood extends ItemPristineMatter {
        public TwilightDarkwood() {
            super("pristine_matter_twilight_darkwood", "twilightdarkwood");
        }
    }

    public static class TwilightGlacier extends ItemPristineMatter {
        public TwilightGlacier() {
            super("pristine_matter_twilight_glacier", "twilightglacier");
        }
    }
}
