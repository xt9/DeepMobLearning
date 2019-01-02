package xt9.deepmoblearning.plugins.guideapi.categories;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.BookBinder;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;

import amerifrance.guideapi.page.PageText;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.trials.TrialFactory;
import xt9.deepmoblearning.common.trials.TrialRuleset;
import xt9.deepmoblearning.common.trials.affix.ITrialAffix;
import xt9.deepmoblearning.common.trials.affix.TrialAffixFactory;

import java.util.*;

/**
 * Created by xt9 on 2018-06-08.
 */
public class CategoryTrial {
    public static void init(BookBinder instance) {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();

        entries.put(new ResourceLocation(DeepConstants.MODID, "trial_key"), new EntryItemStack(getTrialKeyPages(), "Trial Key", new ItemStack(Registry.trialKey)));
        entries.put(new ResourceLocation(DeepConstants.MODID, "available_trials"), new EntryItemStack(getAvailableAttunementPages(), "Available Trials", new ItemStack(Items.SKULL)));
        entries.put(new ResourceLocation(DeepConstants.MODID, "trial_keystone"), new EntryItemStack(getTrialKeystonePages(), "Trial Keystone", new ItemStack(Registry.trialKeystoneItem)));
        entries.put(new ResourceLocation(DeepConstants.MODID, "affixes"), new EntryItemStack(getAffixPages(), "Trial Affixes", new ItemStack(Items.WRITABLE_BOOK)));

        instance.addCategory(new CategoryItemStack(entries, "Trials", new ItemStack(Registry.trialKeystoneItem)));
    }

    private static NonNullList<IPage> getTrialKeyPages() {
        NonNullList<IPage> pages = NonNullList.create();
        String page1String = "§3Trial Key§r\nThis little guy starts trials.\n\nAttune by having trial key(s) in your inventory aswell as a deep learner filled with one data model that match the Available trials list.";
        String page2String = "Kill a mob that match that data model to trigger the attunement.\n\nThe key will inherit it's Tier from the data model.";
        String page3String = "Trial wavecount\n" +
            "\nFaulty: §3" + TrialRuleset.getMaxWaveFromTier(0) + "§r wave(s)" +
            "\nBasic: §3" + TrialRuleset.getMaxWaveFromTier(1) + "§r wave(s)" +
            "\nAdvanced: §3" + TrialRuleset.getMaxWaveFromTier(2) + "§r wave(s)" +
            "\nSuperior: §3" + TrialRuleset.getMaxWaveFromTier(3) + "§r wave(s)" +
            "\nSelf Aware: §3" + TrialRuleset.getMaxWaveFromTier(4) + "§r wave(s)";

        pages.add(new PageText(page1String));
        pages.add(new PageText(page2String));
        pages.add(new PageText(page3String));
        return pages;
    }

    private static NonNullList<IPage> getAvailableAttunementPages() {
        StringBuilder pageString = new StringBuilder("§3Available attunements:§r\n");

        NonNullList<String> availableTrials = TrialFactory.getValidTrialsHumanReadable();
        int i = 0;

        for (String mobName : availableTrials) {
            i++;
            if(i == availableTrials.size()) {
                pageString.append(mobName);
            }else {
                pageString.append(mobName).append(", ");
            }
        }

        NonNullList<IPage> pages = NonNullList.create();
        pages.add(new PageText(pageString.toString()));
        return pages;
    }

    private static NonNullList<IPage> getTrialKeystonePages() {
        String page = "§3Trial keystone§r"
            + "\nFight in trials to speed up the collection of data for your data models."
            + "\nConquering a trial will result in great rewards.";

        NonNullList<IPage> pages = NonNullList.create();
        pages.add(new PageText(page));
        return pages;
    }

    private static NonNullList<IPage> getAffixPages() {
        NonNullList<IPage> pages = NonNullList.create();
        NonNullList<ITrialAffix> affixes = TrialAffixFactory.getAllAffixes();
        String firstAffixPage = "§3Affixes§r\nAffixes will alter what happens during a trial. The behaviour of affixes vary greatly, an affix could be a buff to the player, or some in-world event.";
        String secondAffixPage = "§3Affix count§r\nDetermined by Trial Key tier\n"
            + "\nFaulty: §3" + TrialRuleset.getNumberOfAffixesPerTier(0) + "§r"
            + "\nBasic: §3" + TrialRuleset.getNumberOfAffixesPerTier(1) + "§r"
            + "\nAdvanced: §3" + TrialRuleset.getNumberOfAffixesPerTier(2) + "§r"
            + "\nSuperior: §3" + TrialRuleset.getNumberOfAffixesPerTier(3) + "§r"
            + "\nSelf Aware: §3" + TrialRuleset.getNumberOfAffixesPerTier(4) + "§r";

        pages.add(new PageText(firstAffixPage));
        pages.add(new PageText(secondAffixPage));
        affixes.forEach(affix -> pages.add(new PageText(affix.getAffixNameWithFormatting() + "\n" + affix.getAffixDescription())));
        return pages;
    }
}
