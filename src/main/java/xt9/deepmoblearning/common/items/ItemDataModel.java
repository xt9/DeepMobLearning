package xt9.deepmoblearning.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.mobmetas.MobKey;
import xt9.deepmoblearning.common.util.DataModelExperience;
import xt9.deepmoblearning.common.util.KeyboardHelper;
import xt9.deepmoblearning.common.util.DataModel;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by xt9 on 2017-06-10.
 */
public class ItemDataModel extends ItemBase {
    private String mobKey;

    private ItemDataModel(String name, String mobKey) {
        super(name, 1);
        this.mobKey = mobKey;
    }

    public String getMobKey() {
        return mobKey;
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
        if(DataModel.hasExtraTooltip(stack)) {
            list.add(new TextComponentString(DataModel.getExtraTooltip(stack)));
        }

        if(!KeyboardHelper.isHoldingShift()) {
            list.add(new TextComponentString(I18n.format("deepmoblearning.holdshift")));
        } else {
            list.add(new TextComponentString(I18n.format("deepmoblearning.data_model.tier", DataModel.getTierName(stack, false))));
            int tier = DataModel.getTier(stack);
            if(tier != DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
                list.add(new TextComponentString(I18n.format("deepmoblearning.data_model.data.collected", DataModel.getCurrentTierSimulationCountWithKills(stack), DataModel.getTierRoof(stack))));
                list.add(new TextComponentString(I18n.format("deepmoblearning.data_model.data.killmultiplier", DataModelExperience.getKillMultiplier(DataModel.getTier(stack)))));
            }
            list.add(new TextComponentString(I18n.format("deepmoblearning.data_model.rfcost", DataModel.getSimulationTickCost(stack))));
            list.add(new TextComponentString(I18n.format("deepmoblearning.data_model.type", DataModel.getMatterTypeName(stack))));
        }
    }

    public static class Blaze extends ItemDataModel {
        public Blaze() {
            super("data_model_blaze", MobKey.BLAZE);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Creeper extends ItemDataModel {
        public Creeper() {
            super("data_model_creeper", MobKey.CREEPER);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Dragon extends ItemDataModel {
        public Dragon() {
            super("data_model_dragon", MobKey.DRAGON);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Enderman extends ItemDataModel {
        public Enderman() {
            super("data_model_enderman", MobKey.ENDERMAN);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Ghast extends ItemDataModel {
        public Ghast() {
            super("data_model_ghast", MobKey.GHAST);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Guardian extends ItemDataModel {
        public Guardian() {
            super("data_model_guardian", MobKey.GUARDIAN);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Shulker extends ItemDataModel {
        public Shulker() {
            super("data_model_shulker", MobKey.SHULKER);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Skeleton extends ItemDataModel {
        public Skeleton() {
            super("data_model_skeleton", MobKey.SKELETON);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Slime extends ItemDataModel {
        public Slime() {
            super("data_model_slime", MobKey.SLIME);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Spider extends ItemDataModel {
        public Spider() {
            super("data_model_spider", MobKey.SPIDER);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Witch extends ItemDataModel {
        public Witch() {
            super("data_model_witch", MobKey.WITCH);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Wither extends ItemDataModel {
        public Wither() {
            super("data_model_wither", MobKey.WITHER);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class WitherSkeleton extends ItemDataModel {
        public WitherSkeleton() {
            super("data_model_wither_skeleton", MobKey.WITHERSKELETON);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Zombie extends ItemDataModel {
        public Zombie() {
            super("data_model_zombie", MobKey.ZOMBIE);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }

    }

    public static class TE extends ItemDataModel {
        public TE() {
            super("data_model_thermal_elemental", MobKey.TE);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class TwilightForest extends ItemDataModel {
        public TwilightForest() {
            super("data_model_twilight_forest", MobKey.TWILIGHTFOREST);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class TwilightSwamp extends ItemDataModel {
        public TwilightSwamp() {
            super("data_model_twilight_swamp", MobKey.TWILIGHTSWAMP);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class TwilightDarkwood extends ItemDataModel {
        public TwilightDarkwood() {
            super("data_model_twilight_darkwood", MobKey.TWILIGHTDARKWOOD);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class TwilightGlacier extends ItemDataModel {
        public TwilightGlacier() {
            super("data_model_twilight_glacier", MobKey.TWILIGHTGLACIER);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class TinkerSlime extends ItemDataModel {
        public TinkerSlime() {
            super("data_model_tinker_slime", MobKey.TINKERSLIME);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }
}
