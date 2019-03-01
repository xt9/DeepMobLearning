package xt9.deepmoblearning.plugins.patchouli;

import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import vazkii.patchouli.api.PatchouliAPI;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearningbm.ModConstants;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * Created by xt9 on 2019-02-26.
 */
@Mod.EventBusSubscriber
public class Module {
    public static final NonNullList<IRecipe> dmlItemRecipes = NonNullList.create();

    public static void init() {
        ResourceLocation recipeLookup = new ResourceLocation(DeepConstants.MODID, "patchouli_templates/recipelookup.json");

        Supplier<InputStream> recipeLookupSupplier = () -> {
            try {
                return Minecraft.getMinecraft().getResourceManager().getResource(recipeLookup).getInputStream();
            } catch (IOException e) {
                //
            }
            return null;
        };

        PatchouliAPI.instance.registerTemplateAsBuiltin(new ResourceLocation(DeepConstants.MODID, "recipelookup"), recipeLookupSupplier);
    }


    public static void postInit() {
        /* Get the recipes that belong to us, check the output since other mods could have tweaked the recipe */
        ForgeRegistries.RECIPES.getEntries().forEach((r) -> {
            if(r.getValue().getRecipeOutput().getItem().getRegistryName().getResourceDomain().equals(DeepConstants.MODID)) {
                dmlItemRecipes.add(r.getValue());
            }
            /* Add for blood magic addon aswell */
            if(r.getValue().getRecipeOutput().getItem().getRegistryName().getResourceDomain().equals(ModConstants.MODID)) {
                dmlItemRecipes.add(r.getValue());
            }
        });
        PatchouliAPI.instance.reloadBookContents();
    }
}
