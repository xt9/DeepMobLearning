package xt9.deepmoblearning.plugins.guideapi.categories;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.BookBinder;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageItemStack;
import amerifrance.guideapi.page.PageText;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearningbm.ModConfig;
import xt9.deepmoblearningbm.util.Catalyst;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xt9 on 2018-07-09.
 */
public class CategoryBloodMagic {
    public static void init(BookBinder instance) {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();

        entries.put(new ResourceLocation(DeepConstants.MODID, "digital_mob_agonizer"), new EntryItemStack(getDigitalAgonizerPage(), "Digital Mob Agonizer", new ItemStack(xt9.deepmoblearningbm.common.Registry.blockDigitalAgonizerItem)));
        entries.put(new ResourceLocation(DeepConstants.MODID, "altar_linker"), new EntryItemStack(getAltarLinkerPages(), "Altar Linker", new ItemStack(xt9.deepmoblearningbm.common.Registry.itemAltarLinker)));
        entries.put(new ResourceLocation(DeepConstants.MODID, "catalysts"), new EntryItemStack(getCatalystPages(), "Catalysts", new ItemStack(Registry.glitchHeart)));

        instance.addCategory(new CategoryItemStack(entries, "Blood Magic Addon", new ItemStack(xt9.deepmoblearningbm.common.Registry.blockDigitalAgonizerItem)));
    }

    private static List<IPage> getDigitalAgonizerPage() {
        NonNullList<IPage> pages = NonNullList.create();
        pages.add(new PageText("§3Digital Mob Agonizer§r\nWhen supplied with power and a Data Model it will digitally torture the AI inside the Data Model. Besides being really fun, this process will also produce Essence in a linked altar.\n\nBase fluid amounts on next page."));

        String secondPage = "§3Essence Amount§r"
            + "\nFaulty: Too low tier"
            + "\nBasic: §3" + ModConfig.essenceAmountSubCat.getTierEssenceAmount(1) + "§rmb"
            + "\nAdvanced: §3" + ModConfig.essenceAmountSubCat.getTierEssenceAmount(2) + "§rmb"
            + "\nSuperior: §3" + ModConfig.essenceAmountSubCat.getTierEssenceAmount(3) + "§rmb"
            + "\nSelf Aware: §3" + ModConfig.essenceAmountSubCat.getTierEssenceAmount(4) + "§rmb"
            + "\n\nFinal amount affected by number of Sacrifice runes on altar, and the Catalyst multipler";

        pages.add(new PageText(secondPage));
        return pages;
    }

    private static List<IPage> getAltarLinkerPages() {
        NonNullList<IPage> pages = NonNullList.create();
        pages.add(new PageItemStack("§3Altar Linker§r\nUsed to link a Digital Mob Agonizer to an Altar\n\nMax linking range: 25 blocks", new ItemStack(xt9.deepmoblearningbm.common.Registry.itemAltarLinker)));
        return pages;
    }

    private static NonNullList<IPage> getCatalystPages() {
        NonNullList<IPage> pages = NonNullList.create();

        pages.add(new PageText("§3Catalysts§r\nCatalysts can be used in the Digital Mob Agonizer to amplify how much essence you get per operation, catalysts vary in potency and how many operations it can amplify before running out."));
        Catalyst.catalysts.forEach(catalyst -> pages.add(new PageItemStack("§3" + catalyst.getStack().getDisplayName() + "§r\nOperations: " + catalyst.getOperations() + "\nMultiplier: " + catalyst.getMultiplier() + "x", catalyst.getStack())));
        return pages;
    }
}
