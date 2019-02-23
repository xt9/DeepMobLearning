package xt9.deepmoblearning.common.materials;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;

import javax.annotation.Nonnull;

/**
 * Created by xt9 on 2019-02-20.
 */
public class GlitchArmorMaterial implements IArmorMaterial {
    @Nonnull
    @Override
    public String getName() {
        return new ResourceLocation(DeepConstants.MODID, "glitch_infused").toString();
    }

    @Override
    public int getDurability(EntityEquipmentSlot entityEquipmentSlot) {
        switch (entityEquipmentSlot) {
            case FEET: return 400;
            case HEAD: return 400;
            case LEGS: return 400;
            case CHEST: return 400;
            default: return 0;
        }
    }

    @Override
    public int getDamageReductionAmount(EntityEquipmentSlot entityEquipmentSlot) {
        switch (entityEquipmentSlot) {
            case FEET: return 3;
            case HEAD: return 3;
            case LEGS: return 6;
            case CHEST: return 8;
            default: return 0;
        }
    }

    @Override
    public float getToughness() {
        return 3.0f;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Nonnull
    @Override
    public SoundEvent getSoundEvent() {
        return null;
    }

    @Nonnull
    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromStacks(new ItemStack(Registry.glitchInfusedIngot));
    }
}
