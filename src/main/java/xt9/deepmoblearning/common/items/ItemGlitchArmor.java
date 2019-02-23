package xt9.deepmoblearning.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.materials.GlitchArmorMaterial;
import xt9.deepmoblearning.common.mobmetas.MobMetaData;
import xt9.deepmoblearning.common.util.DataModel;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-05-16.
 */
public class ItemGlitchArmor extends ItemArmor {
    private static final int PRISTINE_SET_CHANCE = 18;
    private static final int PRISTINE_SET_NUMBER_OF_DROPS = 2;

    public ItemGlitchArmor(String itemName, EntityEquipmentSlot slot) {
        super(new GlitchArmorMaterial(), slot, ItemBase.getDefaultProps(1));
        setRegistryName(itemName);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
        list.add(new TextComponentString("§rBonus while full set is equipped§r"));
        list.add(new TextComponentString("§r(Bonuses are disabled during Trials)§r"));
        list.add(new TextComponentString("§6  1. " + PRISTINE_SET_CHANCE + "% chance to drop " + PRISTINE_SET_NUMBER_OF_DROPS + " Pristine Matter§r"));
        list.add(new TextComponentString("§6     when a Data Model gains Data.§r"));
        list.add(new TextComponentString("§6  2. Flight & Immunity against fall damage§r"));
    }

    public static boolean isSetEquippedByPlayer(EntityPlayer player) {
        return player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemGlitchArmor &&
            player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemGlitchArmor &&
            player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemGlitchArmor &&
            player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemGlitchArmor;
    }

    public static void dropPristineMatter(World world, BlockPos position, ItemStack stack, EntityPlayer player) {
        if(ThreadLocalRandom.current().nextInt(1, 100) <= PRISTINE_SET_CHANCE) {
            MobMetaData meta = DataModel.getMobMetaData(stack);
            EntityItem drop = new EntityItem(world, position.getX(), position.getY(), position.getZ(), meta.getPristineMatterStack(PRISTINE_SET_NUMBER_OF_DROPS));
            drop.setDefaultPickupDelay();
            world.spawnEntity(drop);
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() instanceof ItemGlitchIngot;
    }

    public static class ItemGlitchHelmet extends ItemGlitchArmor {
        public ItemGlitchHelmet() {
            super("glitch_infused_helmet", EntityEquipmentSlot.HEAD);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class ItemGlitchChestplate extends ItemGlitchArmor {
        public ItemGlitchChestplate() {
            super("glitch_infused_chestplate", EntityEquipmentSlot.CHEST);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class ItemGlitchLeggings extends ItemGlitchArmor {
        public ItemGlitchLeggings() {
            super("glitch_infused_leggings", EntityEquipmentSlot.LEGS);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class ItemGlitchBoots extends ItemGlitchArmor {
        public ItemGlitchBoots() {
            super("glitch_infused_boots", EntityEquipmentSlot.FEET);
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

}
