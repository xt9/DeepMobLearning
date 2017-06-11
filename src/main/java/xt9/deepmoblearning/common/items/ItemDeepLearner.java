package xt9.deepmoblearning.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import xt9.deepmoblearning.Constants;
import xt9.deepmoblearning.common.CommonProxy;

import javax.annotation.Nullable;

public class ItemDeepLearner extends ItemBase implements IGuiItem {
    public ItemDeepLearner () {
        super("deep_learner", 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        if(worldIn.isRemote) {
            CommonProxy.openItemGui(player, hand == EnumHand.MAIN_HAND ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND);
        }
        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    public int getGuiID() {
        return Constants.ITEM_DEEP_LEARNER_GUI_ID;
    }

    public int numOfInternalSlots() {
        return Constants.DEEP_LEARNER_INTERNAL_SLOTS_SIZE;
    }
}