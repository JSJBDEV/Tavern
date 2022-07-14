package ace.actually.tavern.block;

import ace.actually.tavern.Tavern;
import ace.actually.tavern.Utils3f;
import io.netty.util.internal.MathUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

public class TwentyOnesBlockEntityRenderer implements BlockEntityRenderer<TwentyOnesBlockEntity> {
    private static ItemStack stack = new ItemStack(Tavern.BLANK_CARD);
    private static MutableText block = new LiteralText("▮").setStyle(Style.EMPTY.withColor(Formatting.WHITE).withBold(true));

    private double lerp(double a, double b, double f)
    {
        return a + f * (b - a);
    }

    public TwentyOnesBlockEntityRenderer(BlockEntityRendererFactory.Context ctx)
    {

    }

    //he spin
    //matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 4));

    //places that things can exist at
    //dealer is at (0.6,0.1,0) (v270v0v0)
    //west player is at (0,0.5,0.4) (v0v90v0)
    //east player is at (1,0.5,0.6) (v0v270v0)

    @Override
    public void render(TwentyOnesBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        for(NbtElement e: entity.cards)
        {
            NbtCompound compound = (NbtCompound) e;
            renderCard(entity,matrices,compound);
        }



    }

    public void renderCard(TwentyOnesBlockEntity entity, MatrixStack matrices,NbtCompound compound)
    {
        matrices.push();

        int direction = compound.getInt("direction");
        float offset = compound.getFloat("offset");
        offset+=compound.getInt("cards")*0.2f;

        String card = compound.getString("face");

        if(compound.getBoolean("isDrawn"))
        {
            if(direction==1)
            {
                matrices.translate(0,0.5,0.4+offset);
                matrices.multiply(Utils3f.v0v90v0);
            }
            else if(direction==0)
            {
                matrices.translate(1,0.5,0.6+offset);
                matrices.multiply(Utils3f.v0v270v0);
            }
            else if(direction==2)
            {
                matrices.translate(0.6+offset,0.5,0);
            }
        }
        else
        {
            if(direction==1)
            {
                matrices.translate(lerp(0.6,0,entity.counter/10f),lerp(0.1,0.5,entity.counter/10f),lerp(0,0.4+offset,entity.counter/10f));
                float up = (float) lerp(1,0,entity.counter/10f)*270;
                float round = (float) lerp(0,1,entity.counter/10f)*90;
                matrices.multiply(Quaternion.fromEulerXyzDegrees(new Vec3f(up,round,0)));
            }
            else if(direction==0)
            {
                matrices.translate(lerp(0.6,1,entity.counter/10f),lerp(0.1,0.5,entity.counter/10f),lerp(0,0.6+offset,entity.counter/10f));
                float up = (float) lerp(1,0,entity.counter/10f)*270;
                float round = (float) lerp(0,1,entity.counter/10f)*270;
                matrices.multiply(Quaternion.fromEulerXyzDegrees(new Vec3f(up,round,0)));
            }
            else if(direction==2)
            {
                matrices.translate(0.6+offset,lerp(0.1,0.5,entity.counter/10f),0);
                float up = (float) lerp(1,0,entity.counter/10f)*270;
                float round = (float) lerp(0,1,entity.counter/10f)*270;
                matrices.multiply(Quaternion.fromEulerXyzDegrees(new Vec3f(up,round,0)));
            }
        }


        matrices.scale(-0.01f,-0.01f,0.01f);


        matrices.scale(4,4,4);
        matrices.translate(0,0,0.01);
        MinecraftClient.getInstance().textRenderer.draw(matrices,block,1,-2,1);
        matrices.translate(0.7,0.7,-0.01);
        matrices.scale(0.666f,0.666f,0.666f);
        MinecraftClient.getInstance().textRenderer.draw(matrices,"⨅",0,-2,1);
        MinecraftClient.getInstance().textRenderer.draw(matrices,"⨆",0,-2,1);
        matrices.scale(0.3f,0.3f,0.3f);
        matrices.translate(1,2,0);
        if(compound.getBoolean("isDrawn"))
        {
            if(card.contains("♥") || card.contains("♦"))
            {
                MinecraftClient.getInstance().textRenderer.draw(matrices,new LiteralText(card).setStyle(Style.EMPTY.withColor(Formatting.RED)),1,1,1);
            }
            else
            {
                MinecraftClient.getInstance().textRenderer.draw(matrices,card,1,1,1);
            }

        }


        matrices.multiply(Utils3f.v0v180v0);
        matrices.scale(5,5,5);
        matrices.translate(-4,0,0.01);
        MinecraftClient.getInstance().textRenderer.draw(matrices,block,1,-2,1);
        matrices.translate(0.7,0.7,-0.01);
        matrices.scale(0.666f,0.666f,0.666f);
        MinecraftClient.getInstance().textRenderer.draw(matrices,"⨅",0,-2,1);
        MinecraftClient.getInstance().textRenderer.draw(matrices,"⨆",0,-2,1);



        matrices.pop();
    }
}
