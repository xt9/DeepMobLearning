package xt9.deepmoblearning.common.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.materials.GlitchToolMaterial;
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

    public ItemGlitchSword() {
        super(new GlitchToolMaterial(), 0, 0.0f, new Item.Properties().group(DeepMobLearning.tab).maxStackSize(1));
        String itemName = "glitch_infused_sword";
        setRegistryName(itemName);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
        list.add(new TextComponentString("§rBonus: Quick learner§r"));
        list.add(new TextComponentString("§r(Bonuses are disabled during Trials)§r"));
        list.add(new TextComponentString("§6The Data gained from the demise of a mob is doubled,§r"));
        list.add(new TextComponentString("§6when Data is gained there is also a small chance§r"));
        list.add(new TextComponentString("§6that the sword will get a permanent damage increase.§r"));
        list.add(new TextComponentString(""));
        list.add(new TextComponentString("Current damage increase: §b" + getPermanentWeaponDamage(stack) + " §r(Max " + DAMAGE_BONUS_MAX +")§r"));
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
            modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getTier().getAttackDamage() + getPermanentWeaponDamage(stack), 0));
            modifiers.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return modifiers;
    }
}
