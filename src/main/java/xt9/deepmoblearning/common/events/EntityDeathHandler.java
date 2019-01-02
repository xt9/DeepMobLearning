package xt9.deepmoblearning.common.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.capabilities.IPlayerTrial;
import xt9.deepmoblearning.common.capabilities.PlayerTrialProvider;
import xt9.deepmoblearning.common.items.*;
import xt9.deepmoblearning.common.mobmetas.MobMetaData;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;
import xt9.deepmoblearning.common.trials.TrialFactory;
import xt9.deepmoblearning.common.trials.TrialRuleset;
import xt9.deepmoblearning.common.trials.affix.TrialAffixKey;
import xt9.deepmoblearning.common.util.PlayerHelper;
import xt9.deepmoblearning.common.util.TrialKey;
import xt9.deepmoblearning.common.util.DataModel;


/**
 * Created by xt9 on 2017-06-11.
 */
@Mod.EventBusSubscriber
public class EntityDeathHandler {

    @SubscribeEvent
    public static void dropEvent(LivingDropsEvent event) {
        EntityLivingBase e = event.getEntityLiving();
        NBTTagCompound eData = e.getEntityData();
        World world = e.getEntityWorld();

        // Cancel the event(no drops) if the mob was spawned by the trial
        if(eData.hasKey(TileEntityTrialKeystone.NBT_LONG_TILE_POS)) {
            event.setCanceled(true);
        } else if(eData.hasKey(DeepConstants.NBT_STRING_AFFIX_CONNECTION)) {
            String affixKey = eData.getString(DeepConstants.NBT_STRING_AFFIX_CONNECTION);

            // Drop the held block from the loot hoarders
            if(affixKey.equals(TrialAffixKey.LOOT_HOARDERS)) {
                event.getDrops().clear();
                event.getDrops().add(new EntityItem(world, e.posX, e.posY, e.posZ, e.getHeldItem(EnumHand.MAIN_HAND)));
            }
        }
    }


    @SubscribeEvent
    public static void entityDeath(LivingDeathEvent event) {
        if(event.getEntityLiving().getEntityData().hasKey(TileEntityTrialKeystone.NBT_LONG_TILE_POS)) {
            handleTrialMobDeath(event);
        }

        if(event.getSource().getTrueSource() instanceof EntityPlayer) {
            handlePlayerKilledEntity(event);
        }

        if(event.getEntityLiving() instanceof EntityPlayer) {
            handlePlayerDeathDuringTrial(event);
        }
    }


    @SuppressWarnings("ConstantConditions")
    private static void handlePlayerKilledEntity(LivingDeathEvent event) {
        EntityPlayerMP player = (EntityPlayerMP) event.getSource().getTrueSource();

        NonNullList<ItemStack> inventory = NonNullList.create();
        inventory.addAll(player.inventory.mainInventory);
        inventory.addAll(player.inventory.offHandInventory);

        // Grab the deep learners and combat trial items from a players inventory
        NonNullList<ItemStack> deepLearners = getDeepLearners(inventory);
        NonNullList<ItemStack> trialKeys = getTrialKeys(inventory);
        NonNullList<ItemStack> updatedModels = NonNullList.create();

        // Update every data model in every deeplearner that match the kill event
        deepLearners.forEach(stack -> {
            NonNullList<ItemStack> models = updateAndReturnModels(stack, event, player);
            updatedModels.addAll(models);
        });

        // Return early if no models were affected
        if(updatedModels.size() == 0) {
            return;
        }

        // Chance to drop pristine matter from the model that gained data
        if(ItemGlitchArmor.isSetEquippedByPlayer(player)) {
            IPlayerTrial cap = player.getCapability(PlayerTrialProvider.PLAYER_TRIAL_CAP, null);
            if(!cap.isTrialActive()) {
                ItemGlitchArmor.dropPristineMatter(event.getEntityLiving().world, event.getEntityLiving().getPosition(), updatedModels.get(0), player);
            }
        }

        if(player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() instanceof ItemGlitchSword) {
            IPlayerTrial cap = player.getCapability(PlayerTrialProvider.PLAYER_TRIAL_CAP, null);
            if(!cap.isTrialActive()) {
                ItemStack sword = player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
                if(ItemGlitchSword.canIncreaseDamage(sword)) {
                    ItemGlitchSword.increaseDamage(sword, player);
                }
            }
        }

        // Attune the trial key if possible
        trialKeys.forEach(stack -> attuneTrialKey(stack, updatedModels.get(0), event, player));
    }

    private static void handleTrialMobDeath(LivingDeathEvent event) {
        Long pos = event.getEntity().getEntityData().getLong(TileEntityTrialKeystone.NBT_LONG_TILE_POS);

        BlockPos tilePos = BlockPos.fromLong(pos);
        TileEntity tile = event.getEntityLiving().getEntityWorld().getTileEntity(tilePos);

        // Check if the tile still exists (Could be broken after the mob got the nbt tag)
        if(tile instanceof TileEntityTrialKeystone) {
            TileEntityTrialKeystone keystone = (TileEntityTrialKeystone) tile;
            if(keystone.isTrialActive()) {
                keystone.catchMobDeath();
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static void handlePlayerDeathDuringTrial(LivingDeathEvent event) {
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();

        IPlayerTrial cap = player.getCapability(PlayerTrialProvider.PLAYER_TRIAL_CAP, null);


        BlockPos tilePos = BlockPos.fromLong(cap.getTilePos());
        TileEntity tile = event.getEntityLiving().getEntityWorld().getTileEntity(tilePos);

        // Check if the tile still exists (Could be broken after the mob got the nbt tag)
        if(tile instanceof TileEntityTrialKeystone) {
            TileEntityTrialKeystone keystone = (TileEntityTrialKeystone) tile;
            if(keystone.isTrialActive()) {
                keystone.playerDied((EntityPlayerMP) player);
            }
        }
    }

    private static NonNullList<ItemStack> updateAndReturnModels(ItemStack deepLearner, LivingDeathEvent event, EntityPlayerMP player) {
        NonNullList<ItemStack> deepLearnerItems = ItemDeepLearner.getContainedItems(deepLearner);
        NonNullList<ItemStack> result = NonNullList.create();

        deepLearnerItems.forEach(stack -> {
            if (stack.getItem() instanceof ItemDataModel) {
                MobMetaData meta = DataModel.getMobMetaData(stack);

                if (meta.entityLivingMatchesMob(event.getEntityLiving())) {
                    DataModel.increaseMobKillCount(stack, player);
                    result.add(stack);
                }
            }
            ItemDeepLearner.setContainedItems(deepLearner, deepLearnerItems);
        });

        return result;
    }

    private static void attuneTrialKey(ItemStack trialKey, ItemStack dataModel, LivingDeathEvent event, EntityPlayerMP player) {
        if(!(TrialKey.isAttuned(trialKey) || dataModel.isEmpty())) {
            String mobKey = DataModel.getMobKey(dataModel);

            if(TrialFactory.isMobKeyValidForTrial(mobKey)) {
                TrialKey.attune(trialKey, dataModel, player);
                addAffixes(trialKey, dataModel);
            } else {
                MobMetaData meta = DataModel.getMobMetaData(dataModel);
                PlayerHelper.sendMessage(player, new TextComponentString("Can't attune the " + trialKey.getDisplayName() + ", There is no Trial for: " + meta.getName()));
            }
        }
    }

    private static void addAffixes(ItemStack trialKey, ItemStack dataModel) {
        int numberOfAffixes = TrialRuleset.getNumberOfAffixesPerTier(DataModel.getTier(dataModel));
        NonNullList<String> affixes = NonNullList.create();

        for (int i = 0; i < numberOfAffixes; i++) {
            affixes.add(TrialAffixKey.getRandomKey(affixes));
        }

        TrialKey.setAffixList(trialKey, affixes);
    }

    private static NonNullList<ItemStack> getTrialKeys(NonNullList<ItemStack> inventory) {
        NonNullList<ItemStack> result = NonNullList.create();
        inventory.forEach(stack -> {
            if(stack.getItem() instanceof ItemTrialKey) {
                result.add(stack);
            }
        });

        return result;
    }

    private static NonNullList<ItemStack> getDeepLearners(NonNullList<ItemStack> inventory) {
        NonNullList<ItemStack> result = NonNullList.create();
        inventory.forEach(stack -> {
            if(stack.getItem() instanceof ItemDeepLearner) {
                result.add(stack);
            }
        });

        return result;
    }

}
