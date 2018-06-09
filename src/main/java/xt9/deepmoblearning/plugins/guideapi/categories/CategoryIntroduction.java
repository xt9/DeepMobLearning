package xt9.deepmoblearning.plugins.guideapi.categories;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.BookBinder;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageItemStack;
import amerifrance.guideapi.page.PageText;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.util.Tier;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xt9 on 2018-06-08.
 */
public class CategoryIntroduction {
    public static void init(BookBinder instance, ItemStack book) {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();

        entries.put(new ResourceLocation(DeepConstants.MODID, "mod_introduction"), new EntryItemStack(getModIntroductionPages(), "Foreword", book));
        entries.put(new ResourceLocation(DeepConstants.MODID, "crafting_ingredients"), new EntryItemStack(getCraftingIngredientPages(), "Crafting Ingredients", new ItemStack(Registry.sootedRedstone)));
        entries.put(new ResourceLocation(DeepConstants.MODID, "deep_learner"), new EntryItemStack(getDeepLearnerPages(), "The Deep Learner", new ItemStack(Registry.deepLearner)));
        entries.put(new ResourceLocation(DeepConstants.MODID, "data_models"), new EntryItemStack(getDataModelpages(), "Data Models", new ItemStack(Registry.dataModelBlank)));

        instance.addCategory(new CategoryItemStack(entries, "Introduction", new ItemStack(Registry.deepLearner)));
    }

    private static NonNullList<IPage> getModIntroductionPages() {
        NonNullList<IPage> pages = NonNullList.create();
        pages.add(new PageText("§3Deep Mob Learning§r\nWhat is it?\n\nThe goal of Deep Mob Learning is to be a simple and lag-free way to gather mob resources.\n\nThe mods mechanics encourage players to explore and engage in combat and benefit from it later on."));
        return pages;
    }

    private static List<IPage> getCraftingIngredientPages() {
        NonNullList<IPage> pages = NonNullList.create();
        pages.add(new PageItemStack("To get started you'll need some sooted redstone.\n\nCrushing some redstone on a coal block should have the desired effect.", new ItemStack(Registry.sootedRedstone)));
        return pages;
    }

    private static List<IPage> getDeepLearnerPages() {
        NonNullList<IPage> pages = NonNullList.create();
        pages.add(new PageText("§3The Deep Learner§r\nA scanning device that can record data from the demise of monsters.\n\nIn order to collect data you must populate one of the 4 available slots in the Deep Learner with data models.\n\nProjectile kills will count."));
        return pages;
    }

    private static List<IPage> getDataModelpages() {
        NonNullList<IPage> pages = NonNullList.create();
        String page1String = "There's lots of different Data Models available, they are crafted with one of the drops from a monster and a Blank Data Model.\n\nAll data models start out pretty dumb (" + Tier.getTierName(0 , false) + " tier) but gradually get smarter and smarter as they contain more data.";
        String page2String = "Number of monsters defeated to reach the next model tier\n\n"
            + "To " + Tier.getTierName(1, false) + ": " + Config.modelExperience.get("killsToTier1").getInt() + "\n"
            + "To " + Tier.getTierName(2, false) + ": " + Config.modelExperience.get("killsToTier2").getInt() + "\n"
            + "To " + Tier.getTierName(3, false) + ": " + Config.modelExperience.get("killsToTier3").getInt() + "\n"
            + "To " + Tier.getTierName(4, false) + ": " + Config.modelExperience.get("killsToTier4").getInt() + "\n"
            + "\nHigher tiers get more data from defeating foes.";

        pages.add(new PageText(page1String));
        pages.add(new PageText(page2String));
        return pages;
    }


}
