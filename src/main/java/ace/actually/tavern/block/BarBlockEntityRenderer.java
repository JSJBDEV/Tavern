package ace.actually.tavern.block;

import ace.actually.tavern.Tavern;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

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
            //matrices.scale(-0.1f,-0.1f,0.1f);
            //MinecraftClient.getInstance().textRenderer.draw(matrices,"test",1,1,1);
            matrices.translate(0.6,0.2,1);
            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);
            matrices.pop();
        }

    }
}
