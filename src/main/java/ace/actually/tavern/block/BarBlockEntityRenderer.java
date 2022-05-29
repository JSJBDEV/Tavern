package ace.actually.tavern.block;

import ace.actually.tavern.Tavern;
import ace.actually.tavern.Utils3f;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

public class BarBlockEntityRenderer implements BlockEntityRenderer<BarBlockEntity> {

    private static ItemStack stack = new ItemStack(Tavern.BEER_BREW);

    public BarBlockEntityRenderer(BlockEntityRendererFactory.Context ctx)
    {

    }

    @Override
    public void render(BarBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {


        if(entity.hasDrink())
        {
            matrices.push();
            matrices.translate(1,0.2,1);
            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);
            matrices.pop();
        }
        if(MinecraftClient.getInstance().player.isSneaking())
        {
            matrices.push();
            matrices.translate(0,1,0.6);
            matrices.multiply(Utils3f.v0v180v0);
            matrices.scale(-0.01f,-0.01f,0.01f);
            String[] parts = entity.getBrew().split(" ");
            for (int i = 0; i < parts.length; i++) {
                MinecraftClient.getInstance().textRenderer.draw(matrices,parts[i],1,i*8,1);
            }
            matrices.pop();
        }

    }
}
