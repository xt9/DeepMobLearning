package xt9.deepmoblearning.common.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.inventory.ContainerDeepLearner;
import xt9.deepmoblearning.common.network.Network;
import xt9.deepmoblearning.common.network.messages.PlayerOpenDeepLearner;
import xt9.deepmoblearning.common.proxy.ServerProxy;
import xt9.deepmoblearning.common.util.ItemStackNBTHelper;
import xt9.deepmoblearning.common.util.KeyboardHelper;
import xt9.deepmoblearning.common.util.DataModel;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDeepLearner extends ItemBase implements IInteractionObject {
    public ItemDeepLearner() {
        super("deep_learner", 1);
    }

    @Override
    @SuppressWarnings({"NullableProblems", "unchecked"})
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nullable EnumHand hand) {
        BlockPos pos = Minecraft.getInstance().objectMouseOver.getBlockPos();
        if (!player.isSneaking() && !world.getBlockState(pos).hasTileEntity()) {
            Network.channel.sendToServer(new PlayerOpenDeepLearner());
        }
        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
        NonNullList<ItemStack> internalDataModels = DataModel.getValidFromList(getContainedItems(stack));

        list.add(new TextComponentString("Will display a §bHUD§7 when in mainhand or offhand"));
        list.add(new TextComponentString("and populated with data models"));

        if(internalDataModels.size() > 0) {
            if(!KeyboardHelper.isHoldingShift()) {
                list.add(new TextComponentString(I18n.format("deepmoblearning.holdshift")));
            } else {
                list.add(new TextComponentString("Contains the following models"));
                for (ItemStack dataModel : internalDataModels) {
                    if (dataModel.getItem() instanceof ItemDataModel) {
                        list.add(new TextComponentString(DataModel.getTierName(dataModel, false) + " " + dataModel.getDisplayName()));
                    }
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static NonNullList<ItemStack> getContainedItems(ItemStack deepLearner) {
        NonNullList<ItemStack> list = NonNullList.withSize(numOfInternalSlots(), ItemStack.EMPTY);

        // Load the inventory if the ItemStack has a NBTTagcompound
        if(deepLearner.hasTag()) {
            NBTTagCompound currentTag = deepLearner.getTag();
            if(currentTag.hasKey("inventory")) {
                NBTTagList inventory = deepLearner.getTag().getList("inventory", Constants.NBT.TAG_COMPOUND);

                for(int i = 0; i < inventory.size(); i++) {
                    NBTTagCompound tag = inventory.getCompound(i);
                    list.set(i, ItemStack.read(tag));
                }
            }
        }
        return list;
    }

    public static void setContainedItems(ItemStack deepLearner, NonNullList<ItemStack> list) {
        NBTTagList inventory = new NBTTagList();

        for (ItemStack stack : list) {
            NBTTagCompound tag = new NBTTagCompound();
            stack.write(tag);
            inventory.add(tag);
        }

        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("inventory", inventory);
        deepLearner.setTag(tag);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public static int numOfInternalSlots() {
        return DeepConstants.DEEP_LEARNER_INTERNAL_SLOTS_SIZE;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public Container createContainer(InventoryPlayer inventory, EntityPlayer player) {
        return new ContainerDeepLearner(inventory);
    }

    @Override
    @SuppressWarnings({"NullableProblems", "ConstantConditions"})
    public String getGuiID() {
        return new ResourceLocation(DeepConstants.MODID, "item/deep_learner").toString();
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public ITextComponent getName() {
        return new TextComponentString("Deep Learner");
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        return null;
    }
}