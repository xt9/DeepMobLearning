package xt9.deepmoblearning.common.trials.affix;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.entity.EntityGlitch;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-04-24.
 */
public class LootHoarderAffix implements ITrialAffix {
    private BlockPos pos;
    private World world;
    private int ticks = 0;

    public LootHoarderAffix() {
        this.pos = new BlockPos(0, 0, 0);
        this.world = null;
    }

    public LootHoarderAffix(BlockPos pos, World world) {
        this.pos = pos;
        this.world = world;
    }


    @Override
    public void apply(EntityLiving entity) {

    }

    @Override
    public void applyToGlitch(EntityGlitch entity) {

    }

    @Override
    public void run() {
        ticks++;
        // Once every 15 seconds, 25% chance
        if(ticks % (DeepConstants.TICKS_TO_SECOND * 15) == 0) {
            if(ThreadLocalRandom.current().nextInt(1, 100) > 75) {
                EntityZombie hoarder = new EntityZombie(world);
                hoarder.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
                hoarder.setCustomNameTag("Loot Hoarder");
                hoarder.setChild(true);

                int randomX = pos.getX() + ThreadLocalRandom.current().nextInt(-5, 5);
                int randomY = pos.getY() + ThreadLocalRandom.current().nextInt(0, 1);
                int randomZ = pos.getZ() + ThreadLocalRandom.current().nextInt(-5, 5);

                hoarder.setLocationAndAngles(randomX, randomY, randomZ, 0, 0);
                hoarder.getEntityData().setString(DeepConstants.NBT_STRING_AFFIX_CONNECTION, TrialAffixKey.LOOT_HOARDERS);
                hoarder.enablePersistence();

                // Get loot table
                LootTable table = world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(DeepConstants.MODID, "loot_hoarder"));
                LootContext ctx = new LootContext.Builder((WorldServer)world).build();
                List<ItemStack> looted = table.generateLootForPools(world.rand, ctx);

                if(looted.size() > 0) {
                    hoarder.setHeldItem(EnumHand.MAIN_HAND, looted.get(0));
                }

                world.spawnEntity(hoarder);
            }
            ticks = 0;
        }

    }

    @Override
    public String getAffixName() {
        return "Loot Hoarders";
    }

    @Override
    public String getAffixNameWithFormatting() {
        return "§6"+getAffixName()+"§r";
    }
}
