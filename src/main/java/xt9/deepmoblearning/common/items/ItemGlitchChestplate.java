package xt9.deepmoblearning.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.events.PlayerHandler;

/**
 * Created by xt9 on 2018-05-16.
 */
public class ItemGlitchChestplate extends ItemArmor {
    public String itemName = "glitch_infused_chestplate";

    public ItemGlitchChestplate() {
        super(ArmorMaterial.DIAMOND, 1, EntityEquipmentSlot.CHEST);
        setUnlocalizedName(DeepConstants.MODID + "." + itemName);
        setCreativeTab(DeepMobLearning.creativeTab);
        setRegistryName(itemName);
    }

    public static boolean isEquippedByPlayer(EntityPlayer player) {
        return player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemGlitchChestplate;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);

        if(!player.capabilities.allowFlying) {
            player.capabilities.allowFlying = true;
            PlayerHandler.FLYING_PLAYERS.add(player.getUniqueID());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if(isInCreativeTab(tab)) {
            list.add(new ItemStack(this));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName();
    }
}
