package xt9.deepmoblearning.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xt9.deepmoblearning.api.items.ExperienceItem;
import xt9.deepmoblearning.common.util.KeyboardHelper;
import xt9.deepmoblearning.common.util.NBTHelper;

import java.util.List;

/**
 * Created by xt9 on 2017-06-10.
 */
public class ItemMobChip extends ItemBase {
    public ItemMobChip() {
        super("mob_chip", 1, "default", "zombie", "skeleton", "blaze", "enderman", "wither", "witch", "spider", "creeper");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean adv) {
        if(stack.getItemDamage() != 0) {
            if(!KeyboardHelper.isHoldingShift()) {
                list.add(I18n.format("deepmoblearning.holdshift"));
            } else {
                list.add(I18n.format("deepmoblearning.mob_chip.tier", getTierName(stack)));
                list.add(I18n.format("deepmoblearning.mob_chip.killcount", toHumdanReadablePlural(stack), getMobKillCount(stack)));
                list.add(I18n.format("deepmoblearning.mob_chip.kills.to.next.tier", getKillsToNextTier(stack)));
                list.add(
                        I18n.format(
                                "deepmoblearning.mob_chip.simulations.to.next.tier",
                                ItemMobChip.getTier(stack) < 1 ? "§k" + getSimulationsToNextTier(stack) : "" + getSimulationsToNextTier(stack)
                        )
                );
            }
        }
    }

    public static String getSubName(ItemStack stack) {
        Item item = stack.getItem();
        return ((ItemMobChip) item).getSubNames()[stack.getItemDamage()];
    }

    public static NonNullList<ItemStack> getValidFromList(NonNullList<ItemStack> list) {
        NonNullList<ItemStack> filteredList = NonNullList.create();

        for(ItemStack stack : list) {
            Item item = stack.getItem();

            if(item instanceof ItemMobChip) {
                String subName = ((ItemMobChip) item).getSubNames()[stack.getItemDamage()];
                if(!subName.equals("default")) {
                    filteredList.add(stack);
                }
            }
        }

        return filteredList;
    }

    public static String toHumdanReadablePlural(ItemStack stack) {
        switch(getSubName(stack)) {
            case "zombie": return "Zombies";
            case "skeleton": return "Skeletons";
            case "blaze": return "Blazes";
            case "enderman": return "Endermen";
            case "wither": return "Withers";
            case "witch": return "Witches";
            case "spider": return "Spiders";
            case "creeper": return "Creepers";
            default: return "Default";
        }
    }

    public static double getSuccessChance(ItemStack stack) {
        // Todo configurable
        switch(getTier(stack)) {
            case 0: return 0.0;
            case 1: return 0.05;
            case 2: return 0.25;
            case 3: return 0.60;
            default: return 0.0;
        }
    }

    public static String getTierName(ItemStack stack) {
        switch(getTier(stack)) {
            case 0: return "§8Faulty§r";
            case 1: return "§aBasic§r";
            case 2: return "§5Advanced§r";
            case 3: return "§dSupreme§r";
            case 4: return "§6Self Aware§r";
            default: return "Faulty";
        }
    }

    // Use as a helper for the machine, to explain why tier 0 is not working etc.
    public static String[] getSimulatorMachineHelp(ItemStack stack) {
        switch(getTier(stack)) {
            case 0: return new String[] {"This model does not contain enough data", "to accomplish anything useful."};
            case 1: return new String[] {""};
            case 2: return new String[] {""};
            case 3: return new String[] {""};
            default: return new String[] {""};
        }
    }

    // Called by deep learners
    public static void increaseMobKillCount(ItemStack stack) {
        // Get our current tier before increasing the kill count;
        int tier = getTier(stack);
        int killCount = getMobKillCount(stack);
        killCount = killCount + 1;
        setMobKillCount(stack, killCount);

        if(ExperienceItem.shouldIncreaseTier(tier, killCount, getSimulationCount(stack))) {
            // TODO Gratz you leveled up, DO STUFF Player notice maybe?
            setTier(stack, tier + 1);
        }
    }

    // Called by machines
    public static void increaseSimulationCount(ItemStack stack) {
        int tier = getTier(stack);
        int simulationCount = getSimulationCount(stack);
        simulationCount = simulationCount + 1;
        setSimulationCount(stack, simulationCount);


        if(ExperienceItem.shouldIncreaseTier(tier, getMobKillCount(stack), simulationCount)) {
            // TODO Gratz you leveled up, DO STUFF Player notice maybe?
            setTier(stack, tier + 1);
        }
    }

    public static int getMaxKillsForCurrentTier(ItemStack stack) {
        int currentTier = getTier(stack);
        return  ExperienceItem.getMaxKills(currentTier);
    }

    public static int getKillCountForCurrentTier(ItemStack stack) {
        int currentTier = getTier(stack);
        int killCount = getMobKillCount(stack);
        return ExperienceItem.getKillCountForCurrentTier(currentTier, killCount);
    }

    public static int getKillsToNextTier(ItemStack stack) {
        return ExperienceItem.killsToNextTier(getTier(stack), getMobKillCount(stack), getSimulationCount(stack));
    }

    public static int getSimulationsToNextTier(ItemStack stack) {
        return ExperienceItem.simulationsToNextTier(getTier(stack), getMobKillCount(stack), getSimulationCount(stack));
    }

    public static int getTier(ItemStack stack) {
        return NBTHelper.getInt(stack, "tier", 0);
    }

    public static void setTier(ItemStack stack, int tier) {
        NBTHelper.setInt(stack, "tier", tier);
    }

    public static int getMobKillCount(ItemStack stack) {
        return NBTHelper.getInt(stack, "mobKillCount", 0);
    }

    public static void setMobKillCount(ItemStack stack, int count) {
        NBTHelper.setInt(stack, "mobKillCount", count);
    }

    public static int getSimulationCount(ItemStack stack) {
        return NBTHelper.getInt(stack, "simulationCount", 0);
    }

    public static void setSimulationCount(ItemStack stack, int count) {
        NBTHelper.setInt(stack, "simulationCount", count);
    }

    public static boolean entityLivingMatchesType(EntityLivingBase entityLiving, ItemStack stack) {
        String subName = getSubName(stack);

        return (entityLiving instanceof EntityZombie && subName.equals("zombie")) ||
                (entityLiving instanceof EntitySkeleton && subName.equals("skeleton")) ||
                (entityLiving instanceof EntityBlaze && subName.equals("blaze")) ||
                (entityLiving instanceof EntityEnderman && subName.equals("enderman")) ||
                (entityLiving instanceof EntityWither && subName.equals("wither")) ||
                (entityLiving instanceof EntityWitch && subName.equals("witch")) ||
                (entityLiving instanceof  EntitySpider && subName.equals("spider")) ||
                (entityLiving instanceof EntityCreeper && subName.equals("creeper"));
    }
}
