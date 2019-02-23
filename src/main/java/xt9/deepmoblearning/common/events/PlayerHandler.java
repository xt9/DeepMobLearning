package xt9.deepmoblearning.common.events;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.items.ItemGlitchArmor;
import xt9.deepmoblearning.common.items.ItemGlitchHeart;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-05-11.
 */
@Mod.EventBusSubscriber
public class PlayerHandler {
    public static final List<UUID> FLYING_PLAYERS = new ArrayList<>();

    @SubscribeEvent
    public static void playerTickUpdate(TickEvent.PlayerTickEvent event) {
        PlayerCapabilities cap = event.player.abilities;

        if(!event.player.world.isRemote) {
            if(!cap.allowFlying && ItemGlitchArmor.isSetEquippedByPlayer(event.player)) {
                cap.allowFlying = true;
                event.player.sendPlayerAbilities();
                PlayerHandler.FLYING_PLAYERS.add(event.player.getUniqueID());
            }

            if(FLYING_PLAYERS.contains(event.player.getUniqueID()) && !ItemGlitchArmor.isSetEquippedByPlayer(event.player)) {
                if(cap.allowFlying && !event.player.isSpectator() && !event.player.isCreative()) {
                    cap.allowFlying = false;
                    cap.isFlying = false;
                    event.player.sendPlayerAbilities();
                }
                FLYING_PLAYERS.removeIf(uuid -> uuid.toString().equals(event.player.getUniqueID().toString()));
            }
        }
    }

    @SubscribeEvent
    public static void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if(FLYING_PLAYERS.contains(event.getPlayer().getUniqueID())) {
            FLYING_PLAYERS.removeIf(uuid -> uuid.toString().equals(event.getPlayer().getUniqueID().toString()));
        }
    }


    @SubscribeEvent
    public static void playerLeftClickedBlock(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack stack = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
        if(Config.isSootedRedstoneCraftingEnabled) {
            if(Objects.equals(stack.getItem().getRegistryName(), new ResourceLocation("minecraft", "redstone")) && isBlock("minecraft:coal_block", event.getWorld().getBlockState(event.getPos()).getBlock())) {
                if(event.getSide() == Dist.DEDICATED_SERVER) {
                    createSootedRedstone(event);
                    stack.shrink(1);
                } else {
                    createSootedRedstoneParticles(event);
                }
            }
        }

        if(stack.getItem() instanceof ItemGlitchHeart && Objects.equals(event.getWorld().getBlockState(event.getPos()).getBlock().getRegistryName(), new ResourceLocation("minecraft", "obsidan"))) {
            if(event.getSide() == Dist.DEDICATED_SERVER) {
                createGlitchFragment(event);
                stack.shrink(1);
            } else {
                createGlitchFragmentparticles(event);
            }
        }
    }

    private static void createGlitchFragmentparticles(PlayerInteractEvent.LeftClickBlock event) {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        Vec3d vector = event.getHitVec();

        for (int i = 0; i < 3; i++) {
            DeepMobLearning.proxy.spawnSmokeParticle(event.getWorld(),
                vector.x + rand.nextDouble(-0.4D, 0.4D),
                vector.y  + rand.nextDouble(-0.1D, 0.4D),
                vector.z + rand.nextDouble(-0.4D, 0.4D),
                rand.nextDouble(-0.08D, 0.08D),
                rand.nextDouble(-0.08D, 0),
                rand.nextDouble(-0.08D, 0.08D),
                "cyan"
            );
        }
    }

    private static void createSootedRedstoneParticles(PlayerInteractEvent.LeftClickBlock event) {
        Vec3d vector = event.getHitVec();
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
    }

    private static void createGlitchFragment(PlayerInteractEvent.LeftClickBlock event) {
        Vec3d vector = event.getHitVec();
        EntityItem item = new EntityItem(event.getWorld(), vector.x, vector.y + 0.5D, vector.z, new ItemStack(Registry.glitchFragment, 3));
        item.setDefaultPickupDelay();
        event.getWorld().spawnEntity(item);
    }

    @SuppressWarnings("ConstantConditions")
    private static boolean isBlock(String unlocalizedPath, Block block) {
        ResourceLocation loc = block.getRegistryName();
        String fullPath = loc.getNamespace() + ":" + loc.getPath();

        return fullPath.equals(unlocalizedPath);
    }

    private static void createSootedRedstone(PlayerInteractEvent.LeftClickBlock event) {
        Vec3d vector = event.getHitVec();

        EntityItem item = new EntityItem(event.getWorld(), vector.x, vector.y + 0.5D, vector.z, new ItemStack(Registry.sootedRedstone, 1));
        item.setDefaultPickupDelay();


        event.getWorld().spawnEntity(item);
    }



}
