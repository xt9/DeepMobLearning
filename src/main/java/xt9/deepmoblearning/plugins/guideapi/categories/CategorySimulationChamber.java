package xt9.deepmoblearning.plugins.guideapi.categories;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.BookBinder;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageItemStack;
import amerifrance.guideapi.page.PageJsonRecipe;
import amerifrance.guideapi.page.PageText;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.util.Tier;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xt9 on 2018-06-08.
 */
public class CategorySimulationChamber {
    public static void init(BookBinder instance) {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();

        entries.put(new ResourceLocation(DeepConstants.MODID, "simulation_chamber"), new EntryItemStack(getSimulationChamberPages(), "Simulation Chamber", new ItemStack(Registry.simulationChamberItem)));
        entries.put(new ResourceLocation(DeepConstants.MODID, "polymer_clay"), new EntryItemStack(getPolymerClayPages(), "Polymer Clay", new ItemStack(Registry.polymerClay)));
        entries.put(new ResourceLocation(DeepConstants.MODID, "living_matter"), new EntryItemStack(getLivingMatterPages(), "Living Matter", new ItemStack(Registry.livingMatterHellish)));
        entries.put(new ResourceLocation(DeepConstants.MODID, "pristine_matter"), new EntryItemStack(getPristineMatterPages(), "Pristine Matter", new ItemStack(Registry.pristineMatterZombie)));

        instance.addCategory(new CategoryItemStack(entries, "Simulation Chamber", new ItemStack(Registry.simulationChamberItem)));
    }

    private static List<IPage> getSimulationChamberPages() {
        NonNullList<IPage> pages = NonNullList.create();
        String pageString = "§3Simulation Chamber§r\nWhen your data model has reached the " + Tier.getTierName(1, false) + " Tier, you can run simulations on it to gain more data aswell as Living Matter & Pristine matter.\n\nThe simulation process requires " + new ItemStack(Registry.polymerClay).getDisplayName() + " and power.";

        pages.add(new PageText(pageString));
        pages.add(new PageText("§3Machine Sides§r\nInput: Top\nOutput: All other sides\n\nThe machine cannot autu-output or auto-input."));
        return pages;
    }

    private static List<IPage> getPolymerClayPages() {
        NonNullList<IPage> pages = NonNullList.create();
        ItemStack dye = new ItemStack(Items.DYE);
        dye.setItemDamage(6);
        pages.add(new PageIRecipe(new ShapedOreRecipe(new ResourceLocation(DeepConstants.MODID, "polymer_clay"), new ItemStack(Registry.polymerClay, 16), "GC ", "CDC", " CI", 'G', Items.GOLD_INGOT, 'C', Items.CLAY_BALL, 'D', dye, 'I', Items.IRON_INGOT)));
        return pages;
    }

    private static List<IPage> getLivingMatterPages() {
        NonNullList<IPage> pages = NonNullList.create();
        String page1String = "§3Living Matter§r\nYou get 1 Living Matter per operation in the simulation chamber.\n\nWill match the Type of the Data Model that you run through the simulation chamber, for example the Zombie Data model would process into Overworldian living matter.";
        String page2String = "The various Living Matters can be used to transmute regular dimension ingredients, check the use cases of Living Matter in JEI for more information.\n\nYou can also consume Living Matter to gain Experience.";

        pages.add(new PageText(page1String));
        pages.add(new PageText(page2String));
        return pages;
    }

    private static List<IPage> getPristineMatterPages() {
        NonNullList<IPage> pages = NonNullList.create();
        String pageString = "§3Pristine Matter§r\nA secondary output from the Simulation Chamber, the chance depends on the Tier of the Data Model.\n\nCheck the Simulation Chamber JEI entry for values\n\nCan be used to fabricate loot in the Loot Fabricator.";

        pages.add(new PageText(pageString));
        return pages;
    }
}
