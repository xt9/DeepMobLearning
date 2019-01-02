package xt9.deepmoblearning.common.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.util.ItemStackNBTHelper;
import xt9.deepmoblearning.common.util.PlayerHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-05-19.
 */
public class ItemGlitchSword extends ItemSword {
    private static final int DAMAGE_BONUS = 2;
    private static final int DAMAGE_BONUS_MAX = 18;
    private static final int DAMAGE_INCREASE_CHANCE = 6;

    private static ToolMaterial material = EnumHelper.addToolMaterial(
        "GLITCH_INFUSED_MATERIAL",
        3,
        2200,
        8.0F,
        9,
        15
    );

    public ItemGlitchSword() {
        super(material);
        String itemName = "glitch_infused_sword";
        setUnlocalizedName(DeepConstants.MODID + "." + itemName);
        setCreativeTab(DeepMobLearning.creativeTab);
        setRegistryName(itemName);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        list.add("§rBonus: Quick learner§r");
        list.add("§r(Bonuses are disabled during Trials)§r");
        list.add("§6The Data gained from the demise of a mob is doubled,§r");
        list.add("§6when Data is gained there is also a small chance§r");
        list.add("§6that the sword will get a permanent damage increase.§r");
        list.add("");
        list.add("Current damage increase: §b" + getPermanentWeaponDamage(stack) + " §r(Max " + DAMAGE_BONUS_MAX +")§r");
    }

    public static void increaseDamage(ItemStack stack, EntityPlayerMP player) {
        if(ThreadLocalRandom.current().nextInt(1, 100) <= DAMAGE_INCREASE_CHANCE) {
            int current = getPermanentWeaponDamage(stack);

            setPermanentWeaponDamage(stack, current + DAMAGE_BONUS >= DAMAGE_BONUS_MAX ? DAMAGE_BONUS_MAX : current + DAMAGE_BONUS);

            if(getPermanentWeaponDamage(stack) >= DAMAGE_BONUS_MAX) {
                PlayerHelper.sendMessage(player, new TextComponentString("Your " + stack.getDisplayName() + " has now reached peak performance!"));
            } else {
                PlayerHelper.sendMessage(player, new TextComponentString("Your " + stack.getDisplayName() + " grows in power!"));
            }
        }
    }

    public static boolean canIncreaseDamage(ItemStack sword) {
        return getPermanentWeaponDamage(sword) < DAMAGE_BONUS_MAX;
    }

    public static int getPermanentWeaponDamage(ItemStack stack) {
        return ItemStackNBTHelper.getInt(stack,"permDamage", 0);
    }

    public static void setPermanentWeaponDamage(ItemStack stack, int damage) {
        ItemStackNBTHelper.setInt(stack,"permDamage", damage);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() instanceof ItemGlitchIngot;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> modifiers = HashMultimap.create();
        if (slot == EntityEquipmentSlot.MAINHAND) {
            modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", material.getAttackDamage() + getPermanentWeaponDamage(stack), 0));
            modifiers.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return modifiers;
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
