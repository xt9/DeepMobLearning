package xt9.deepmoblearning.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.CommonProxy;
import xt9.deepmoblearning.common.util.KeyboardHelper;

import java.util.List;

public class ItemDeepLearner extends ItemBase implements IItemGui {
    public ItemDeepLearner() {
        super("deep_learner", 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        CommonProxy.openItemGui(player, hand == EnumHand.MAIN_HAND ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND);
        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean adv) {
        NonNullList<ItemStack> internalChips = ItemMobChip.getValidFromList(getContainedItems(stack));

        if(internalChips.size() > 0) {
            if(!KeyboardHelper.isHoldingShift()) {
                list.add(I18n.format("deepmoblearning.holdshift"));
            } else {
                list.add("Contains the following models");
                for(int i = 0; i < internalChips.size(); i++) {
                    ItemStack chip = internalChips.get(i);
                    if(chip.getItemDamage() != 0) {
                        list.add(ItemMobChip.getTierName(chip, false) + " " + chip.getDisplayName());
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

        for(int i = 0; i < list.size(); i++) {
            NBTTagCompound tag = new NBTTagCompound();
            list.get(i).writeToNBT(tag);
            inventory.appendTag(tag);
        }
        deepLearner.setTagCompound(new NBTTagCompound());
        deepLearner.getTagCompound().setTag("inventory", inventory);
    }

    public int getGuiID() {
        return DeepConstants.ITEM_DEEP_LEARNER_GUI_ID;
    }

    public static int numOfInternalSlots() {
        return DeepConstants.DEEP_LEARNER_INTERNAL_SLOTS_SIZE;
    }
}