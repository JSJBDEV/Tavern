package ace.actually.tavern.block;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class TwentyOnesBlockEntityRenderer implements BlockEntityRenderer<TwentyOnesBlockEntity> {


    public TwentyOnesBlockEntityRenderer(BlockEntityRendererFactory.Context ctx)
    {

    }

    @Override
    public void render(TwentyOnesBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(1,1,0);
        matrices.scale(-0.01f,-0.01f,0.01f);
        MinecraftClient.getInstance().textRenderer.draw(matrices,"You have a 5♠",1,1,1);

        matrices.pop();
    }
}
