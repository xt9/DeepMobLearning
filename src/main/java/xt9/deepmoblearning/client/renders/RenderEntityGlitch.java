package xt9.deepmoblearning.common.renders;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.entity.EntityGlitch;
import xt9.deepmoblearning.common.model.ModelGlitch;

import javax.annotation.Nullable;

/**
 * Created by xt9 on 2018-04-04.
 */
public class RenderEntityGlitch extends RenderLiving<EntityGlitch> {
    private ResourceLocation texture = new ResourceLocation(DeepConstants.MODID + ":textures/entity/glitch.png");
    public static final Factory FACTORY = new Factory();


    public RenderEntityGlitch(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelGlitch(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityGlitch glitch) {
        return texture;
    }

    public static class Factory implements IRenderFactory<EntityGlitch> {
        @Override
        public Render<? super EntityGlitch> createRenderFor(RenderManager manager) {
            return new RenderEntityGlitch(manager);
        }

    }
}
