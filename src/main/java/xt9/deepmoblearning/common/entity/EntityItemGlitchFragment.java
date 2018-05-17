package xt9.deepmoblearning.common.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xt9.deepmoblearning.DeepMobLearning;

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
        isImmuneToFire = true;
        setOnFireFromLava();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();


        if(isInWater() && !world.isRemote) {

            // todo check if iron is present
            EntityItem newItem = new EntityItem(world, posX, posY + 1.0D, posZ, new ItemStack(Items.COAL, getItem().getCount()));
            newItem.motionX = motionX;
            newItem.motionY = 0.22D;
            newItem.motionZ = motionZ;
            newItem.setDefaultPickupDelay();
            newItem.setFire(0);
            ThreadLocalRandom rand = ThreadLocalRandom.current();

            DeepMobLearning.proxy.spawnSmokeParticle(world,
                posX + rand.nextDouble(-0.25D, 0.25D),
                posY  + rand.nextDouble(-0.1D, 0.1D),
                posZ + rand.nextDouble(-0.25D, 0.25D),
                rand.nextDouble(-0.08D, 0.08D),
                rand.nextDouble(-0.08D, 0.22D),
                rand.nextDouble(-0.08D, 0.08D),
                "cyan"
            );

            //setItem(ItemStack.EMPTY);
            //setDead();
            //world.spawnEntity(newItem);
        }
    }

}
