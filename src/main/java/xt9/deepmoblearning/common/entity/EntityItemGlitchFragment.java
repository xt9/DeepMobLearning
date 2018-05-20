package xt9.deepmoblearning.common.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.items.ItemGlitchIngot;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-05-16.
 */
public class EntityItemGlitchFragment extends EntityItem {
    private long progress = 0;

    public EntityItemGlitchFragment(World worldIn) {
        super(worldIn);
    }

    public EntityItemGlitchFragment(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z);
        setItem(stack);
        setPickupDelay(15);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if(isInWater()) {
            ThreadLocalRandom rand = ThreadLocalRandom.current();
            AxisAlignedBB box = new AxisAlignedBB(posX - 1, posY - 1, posZ - 1, posX + 1, posY + 1, posZ + 1);
            List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, box);

            progress++;
            boolean isValidEntities = isItemListValid(entities);
            if (!isValidEntities) {
                progress = 0;
            }

            if (world.isRemote) {
                DeepMobLearning.proxy.spawnSmokeParticle(world,
                    posX + rand.nextDouble(-0.25D, 0.25D),
                    posY + rand.nextDouble(-0.1D, 0.1D),
                    posZ + rand.nextDouble(-0.25D, 0.25D),
                    rand.nextDouble(-0.08, 0.08D),
                    rand.nextDouble(-0.08D, 0.22D),
                    rand.nextDouble(-0.08D, 0.08D),
                    "cyan"
                );

                // If all valid entities exist, spawn more particles
                if(isValidEntities) {
                    for (int i = 0; i < 3; i++) {
                        DeepMobLearning.proxy.spawnSmokeParticle(world,
                            posX + rand.nextDouble(-0.25D, 0.25D),
                            posY + rand.nextDouble(-0.1D, 0.8D),
                            posZ + rand.nextDouble(-0.25D, 0.25D),
                            rand.nextDouble(-0.08, 0.08D),
                            rand.nextDouble(-0.08D, 0.22D),
                            rand.nextDouble(-0.08D, 0.08D),
                            "cyan"
                        );
                    }
                }
            }

            if (!world.isRemote) {
                if (progress >= 35) {
                    entities.forEach(entityItem -> {
                        if (!(entityItem.getItem().getItem() instanceof ItemGlitchIngot)) {
                            entityItem.getItem().shrink(1);
                        }
                    });

                    entities.forEach(entityItem -> {
                        if (entityItem.getItem().getCount() == 0) {
                            entityItem.setDead();
                            progress = 0;
                        }
                    });


                    EntityItem newItem = new EntityItem(world, posX, posY + 0.6D, posZ, new ItemStack(Registry.glitchInfusedIngot, 1));
                    newItem.motionX = rand.nextDouble(-0.2D, 0.2D);
                    newItem.motionY = 0;
                    newItem.motionZ = rand.nextDouble(-0.2D, 0.2D);
                    newItem.setDefaultPickupDelay();

                    world.spawnEntity(newItem);
                }
            }
        }
    }

    public boolean isItemListValid(List<EntityItem> list) {
        // Don't check for glitch fragment because if it wasn't here the method would not run
        boolean foundGold = false;
        boolean foundLapis = false;

        for (EntityItem entityItem : list) {
            if(ItemStack.areItemsEqual(entityItem.getItem(), new ItemStack(Items.GOLD_INGOT))) {
                foundGold = true;
            }
            if(ItemStack.areItemStacksEqual(entityItem.getItem(), new ItemStack(Items.DYE, entityItem.getItem().getCount(), 4))) {
                foundLapis = true;
            }
        }

        return foundGold && foundLapis;
    }



}
