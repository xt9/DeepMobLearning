package xt9.deepmoblearning.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.CommonProxy;
import xt9.deepmoblearning.common.util.KeyboardHelper;
import xt9.deepmoblearning.common.util.DataModel;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDeepLearner extends ItemBase implements IGuiItem {
    public ItemDeepLearner() {
        super("deep_learner", 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, @Nullable EnumHand hand) {
        Item mainHand = player.getHeldItemMainhand().getItem();
        Item offHand = player.getHeldItemOffhand().getItem();

        if(mainHand instanceof ItemDeepLearner || offHand instanceof ItemDeepLearner && mainHand instanceof ItemAir) {
            CommonProxy.openItemGui(player, hand == EnumHand.MAIN_HAND ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND);
        }
        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        NonNullList<ItemStack> internalDataModels = DataModel.getValidFromList(getContainedItems(stack));

        list.add("Will display a §bHUD§7 when in mainhand or offhand");
        list.add("and populated with data models");

        if(internalDataModels.size() > 0) {
            if(!KeyboardHelper.isHoldingShift()) {
                list.add(I18n.format("deepmoblearning.holdshift"));
            } else {
                list.add("Contains the following models");
                for (ItemStack dataModel : internalDataModels) {
                    if (dataModel.getItem() instanceof ItemDataModel) {
                        list.add(DataModel.getTierName(dataModel, false) + " " + dataModel.getDisplayName());
                    }
                }
            }
        }
    }

    public static NonNullList<ItemStack> getContainedItems(ItemStack deepLearner) {
        NonNullList<ItemStack> list = NonNullList.withSize(numOfInternalSlots(), ItemStack.EMPTY);

        // Load the inventory if the ItemStack has a NBTTagcompound
        if(deepLearner.hasTagCompound()) {
            NBTTagList inventory = deepLearner.getTagCompound().getTagList("inventory", Constants.NBT.TAG_COMPOUND);

            for(int i = 0; i < inventory.tagCount(); i++) {
                NBTTagCompound tag = inventory.getCompoundTagAt(i);
                list.set(i, new ItemStack(tag));
            }

        }
        return list;
    }

    public static void setContainedItems(ItemStack deepLearner, NonNullList<ItemStack> list) {
        NBTTagList inventory = new NBTTagList();

        for (ItemStack aList : list) {
            NBTTagCompound tag = new NBTTagCompound();
            aList.writeToNBT(tag);
            inventory.appendTag(tag);
        }
        deepLearner.setTagCompound(new NBTTagCompound());
        deepLearner.getTagCompound().setTag("inventory", inventory);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public int getGuiID() {
        return DeepConstants.ITEM_DEEP_LEARNER_GUI_ID;
    }

    public static int numOfInternalSlots() {
        return DeepConstants.DEEP_LEARNER_INTERNAL_SLOTS_SIZE;
    }
}