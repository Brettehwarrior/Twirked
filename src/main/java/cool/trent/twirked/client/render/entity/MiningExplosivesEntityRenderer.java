package cool.trent.twirked.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import cool.trent.twirked.entities.MiningExplosiveEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Quaternionf;

public class MiningExplosivesEntityRenderer extends EntityRenderer<MiningExplosiveEntity> {
    public MiningExplosivesEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.5f;
    }

    @Override
    public void render(MiningExplosiveEntity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn)
    {
        if(entity.block==null)
            return;
        BlockRenderDispatcher blockRendererDispatcher = Minecraft.getInstance().getBlockRenderer();
        matrixStackIn.pushPose();
        matrixStackIn.translate(0, 0.5F, 0);
        if(entity.getFuse()-partialTicks+1 < 10)
        {
            float f = 1.0F-((float)entity.getFuse()-partialTicks+1.0F)/10.0F;
            f = Mth.clamp(f, 0.0F, 1.0F);
            f = f*f;
            f = f*f;
            float f1 = 1.0F+f*0.3F;
            matrixStackIn.scale(f1, f1, f1);
        }
        matrixStackIn.mulPose(new Quaternionf().rotateXYZ(0, -Mth.HALF_PI, 0));
        matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
        matrixStackIn.mulPose(new Quaternionf().rotateXYZ(0, Mth.HALF_PI, 0));

        int overlay;
        if(entity.getFuse()/5%2==0)
            overlay = OverlayTexture.pack(OverlayTexture.u(1.0F), 10);
        else
            overlay = OverlayTexture.NO_OVERLAY;
        blockRendererDispatcher.renderSingleBlock(entity.block, matrixStackIn, bufferIn, packedLightIn, overlay);

        matrixStackIn.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(MiningExplosiveEntity miningExplosiveEntity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
