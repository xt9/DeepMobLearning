package xt9.deepmoblearning.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class ModelGlitch extends ModelBiped {
    private ModelRenderer bipedRightArm;
    private ModelRenderer bipedRightLeg;
    private ModelRenderer bipedLeftLeg;
    private ModelRenderer bipedHeadwear;
    private ModelRenderer bipedLeftArm;
    private ModelRenderer bipedBody;
    private ModelRenderer bipedHead;

    public ModelGlitch() {
        this(0.0F);
    }

    public ModelGlitch(float scale) {
        textureWidth = 64;
        textureHeight = 64;
        bipedHeadwear = new ModelRenderer(this, 32, 0);
        bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
        bipedBody = new ModelRenderer(this, 16, 16);
        bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale);
        bipedRightArm = new ModelRenderer(this, 40, 16);
        bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scale);
        setRotateAngle(bipedRightArm, -0.24100271092617426F, -0.10000000149011613F, 0.07308371672130057F);
        bipedHead = new ModelRenderer(this, 0, 0);
        bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale);
        bipedLeftLeg = new ModelRenderer(this, 0, 16);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.setRotationPoint(1.899999976158142F, 12.0F, 0.10000000149011612F);
        bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
        bipedLeftArm = new ModelRenderer(this, 40, 16);
        bipedLeftArm.mirror = true;
        bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, scale);
        setRotateAngle(bipedLeftArm, -0.26514292933417893F, 0.10000000149011613F, -0.1188379032956101F);
        bipedRightLeg = new ModelRenderer(this, 0, 16);
        bipedRightLeg.setRotationPoint(-1.899999976158142F, 12.0F, 0.10000000149011612F);
        bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        bipedHeadwear.render(f5);
        bipedBody.render(f5);
        bipedRightArm.render(f5);
        bipedHead.render(f5);
        bipedLeftLeg.render(f5);
        bipedLeftArm.render(f5);
        bipedRightLeg.render(f5);
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        float f = MathHelper.sin(swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin((0.5F - (0.5F - swingProgress) * (0.5F - swingProgress)) * (float) Math.PI);
        bipedRightArm.rotateAngleZ = 0.0F;
        bipedLeftArm.rotateAngleZ = 0.0F;
        bipedRightArm.rotateAngleY = -(0.1F - f * 0.2F);
        bipedLeftArm.rotateAngleY = 0.1F - f * 0.2F;
        float f2 = -3.1415927F / 7F;

        bipedRightArm.rotateAngleX = f2;
        bipedLeftArm.rotateAngleX = f2;
        bipedRightArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
        bipedLeftArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;

        bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount;
        bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.8F * limbSwingAmount;
        bipedRightLeg.rotateAngleY = 0.0F;
        bipedLeftLeg.rotateAngleY = 0.0F;

        bipedHead.rotateAngleY = netHeadYaw * 0.012F;
        bipedHead.rotateAngleX = headPitch * 0.012F;

    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
