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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xt9 on 2018-06-08.
 */
public class CategoryGlitches {
    public static void init(BookBinder instance) {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();


        entries.put(new ResourceLocation(DeepConstants.MODID, "glitch_mob"), new EntryItemStack(getGlitchMobPages(), "The System Glitch", new ItemStack(Registry.glitchHeart)));
        entries.put(new ResourceLocation(DeepConstants.MODID, "unstable_glitch_fragments"), new EntryItemStack(getFragmentPages(), "Glitch Fragments", new ItemStack(Registry.glitchFragment)));
        entries.put(new ResourceLocation(DeepConstants.MODID, "glitch_infused_metal"), new EntryItemStack(getMetalPages(), "Glitch Infused Metal", new ItemStack(Registry.infusedIngotBlockItem)));
        entries.put(new ResourceLocation(DeepConstants.MODID, "glitch_equipment"), new EntryItemStack(getGlitchEquipmentPages(), "Glitch Equipment", new ItemStack(Registry.glitchInfusedHelmet)));

        instance.addCategory(new CategoryItemStack(entries, "System Malfunction", new ItemStack(Registry.glitchHeart)));
    }

    private static List<IPage> getGlitchMobPages() {
        NonNullList<IPage> pages = NonNullList.create();
        pages.add(new PageText("§3System Glitch§r\nWill spawn during system malfunctions in the trial. Higher tiered trials have a higher chance of System malfunction.\n\nHighly aggressive, launches corrosive projectiles towards their targets."));
        pages.add(new PageItemStack("§3Corrupted Glitch Heart§r\nWill drop from System Glitches\n(Affected by looting)\n\nThis rare commodity is used to create end-game equipment.", new ItemStack(Registry.glitchHeart)));
        return pages;
    }

    private static List<IPage> getFragmentPages() {
        NonNullList<IPage> pages = NonNullList.create();
        pages.add(new PageItemStack("§3Glitch Fragment§r\nObtained by crushing a Corrupted Glitch Heart on Obsidian, the unstable fragment is useless to you, unless you could stabilize it into it's metal form.", new ItemStack(Registry.glitchFragment)));
        return pages;
    }

    private static List<IPage> getMetalPages() {
        NonNullList<IPage> pages = NonNullList.create();
        pages.add(new PageItemStack("§3Glitch Infused Metal§r\nTLDR: Drop some fragments, lapis and gold ingots into a body of water and hope for the best", new ItemStack(Registry.glitchInfusedIngot)));
        pages.add(new PageText("You've found that lapis is a good stabilization agent for Unstable glitch fragments.\n\nAfter the fragments are stable, they desperately seek out a material to latch on to, you think that gold might be strong enough as a host."));
        pages.add(new PageText("The entire process is delicate, it needs to be performed in water or the materials wont bind properly."));
        return pages;
    }

    private static List<IPage> getGlitchEquipmentPages() {
        NonNullList<IPage> pages = NonNullList.create();
        pages.add(new PageItemStack("§3Glitch Armor Set§r\nArmor pieces crafted with Glitch Infused Ingots.\n\nCheck Armor Tooltips for full set bonus.", new ItemStack(Registry.glitchInfusedChestplate)));
        pages.add(new PageItemStack("§3Glitch Weapon§r\nCrafted with Glitch Infused Ingots, Helps with rapidly leveling Data Models. Check tooltip for full bonus description.", new ItemStack(Registry.glitchInfusedSword)));
        return pages;
    }
}
