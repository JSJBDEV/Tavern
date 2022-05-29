package ace.actually.tavern.block;

import ace.actually.tavern.Tavern;
import ace.actually.tavern.Utils3f;
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

public class TwentyOnesBlockEntityRenderer implements BlockEntityRenderer<TwentyOnesBlockEntity> {
    private static ItemStack stack = new ItemStack(Tavern.BLANK_CARD);



    public TwentyOnesBlockEntityRenderer(BlockEntityRendererFactory.Context ctx)
    {

    }

    @Override
    public void render(TwentyOnesBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        float i = 0;
        float j = 0;
        float k = 0;
        Quaternion degY = Quaternion.IDENTITY;


        matrices.push();
        matrices.translate(0.15+i,0.4+j,-0.02+k);
        matrices.scale(-0.01f,-0.01f,0.01f);
        matrices.multiply(degY);
        MinecraftClient.getInstance().textRenderer.draw(matrices,"5♠",1,1,1);
        matrices.pop();


        matrices.push();
        matrices.translate(0.5+i,0+j,0.2+k);


        matrices.multiply(degY);
        matrices.multiply(Utils3f.v90v0v0);
        int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);
        matrices.pop();
    }
}
