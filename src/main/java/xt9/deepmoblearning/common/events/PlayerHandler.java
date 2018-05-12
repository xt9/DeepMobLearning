package xt9.deepmoblearning.common.events;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.Registry;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-05-11.
 */
@Mod.EventBusSubscriber
public class PlayerHandler {

    @SubscribeEvent
    public static void playerLeftClickedBlock(PlayerInteractEvent.LeftClickBlock event) {
        if(event.getSide() == Side.SERVER) {
            ItemStack stack = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);

            if(stack.getItem() instanceof ItemRedstone && isCoalBlock(event)) {
                Vec3d vector = event.getHitVec();

                EntityItem item = new EntityItem(event.getWorld(), vector.x, vector.y + 0.5D, vector.z, new ItemStack(Registry.charredRedstone, 1));
                item.setDefaultPickupDelay();
                ThreadLocalRandom rand = ThreadLocalRandom.current();

                for (int i = 0; i < 3; i++) {
                    DeepMobLearning.proxy.spawnSmokeParticle(event.getWorld(),
                        vector.x + rand.nextDouble(-0.4D, 0.4D),
                        vector.y  + rand.nextDouble(-0.1D, 0.4D),
                        vector.z + rand.nextDouble(-0.4D, 0.4D),
                        rand.nextDouble(-0.08D, 0.08D),
                        rand.nextDouble(-0.08D, 0),
                        rand.nextDouble(-0.08D, 0.08D),
                        "smoke"
                    );
                }


                event.getWorld().spawnEntity(item);
                stack.shrink(1);
            }

        }
    }

    private static boolean isCoalBlock(PlayerInteractEvent.LeftClickBlock event) {
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
        ResourceLocation loc = block.getRegistryName();
        String fullPath = loc.getResourceDomain() + ":" + loc.getResourcePath();

        return fullPath.equals("minecraft:coal_block");
    }


}
