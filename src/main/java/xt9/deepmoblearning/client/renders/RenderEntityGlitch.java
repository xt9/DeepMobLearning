package xt9.deepmoblearning.client.renders;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.entity.EntityGlitch;
import xt9.deepmoblearning.client.model.ModelGlitch;

import javax.annotation.Nullable;

/**
 * Created by xt9 on 2018-04-04.
 */
public class RenderEntityGlitch extends RenderLiving<EntityGlitch> {
    private ResourceLocation texture = new ResourceLocation(DeepConstants.MODID + ":textures/entity/glitch.png");

    public RenderEntityGlitch(RenderManager manager) {
        super(manager, new ModelGlitch(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityGlitch glitch) {
        return texture;
    }

    @Override
    public void doRender(EntityGlitch entity, double x, double y, double z, float entityYaw, float partialTicks) {

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
