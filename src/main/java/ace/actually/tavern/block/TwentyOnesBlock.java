package ace.actually.tavern.block;

import ace.actually.tavern.Tavern;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class TwentyOnesBlock extends BlockWithEntity {
    public TwentyOnesBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {

        return new TwentyOnesBlockEntity(pos,state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    /**
     *
     * if(!world.getBlockState(pos.north()).isOf(Tavern.TWENTY_ONES_BLOCK))
     *             {
     *                 if(player.isSneaking())
     *                 {
     *                     System.out.println(entity.cards.size());
     *                     entity.drawCard(false, RandomUtils.nextInt(0,4));
     *                 }
     *                 else
     *                 {
     *                     if(entity.counter!=10)
     *                     {
     *                         entity.count();
     *                     }
     *                 }
     *             }
     */


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if(hand==Hand.MAIN_HAND && !world.isClient)
        {

            TwentyOnesBlockEntity entity = (TwentyOnesBlockEntity) world.getBlockEntity(pos);
            System.out.println(entity.gameState);
            if(
                    entity.gameState.equals("NO_GAME")
                    && hit.getSide() == Direction.NORTH
                    //&& player.isSneaking()
                    && player.getMainHandStack().getItem()== Items.EMERALD
            )
            {
                entity.drawCard(2,0);
                entity.setGameState("WAITING_FOR_EAST");
                player.getMainHandStack().decrement(1);
            }
            else if(entity.gameState.equals("WAITING_FOR_EAST") && hit.getSide() == Direction.EAST)
            {
                entity.drawCard(false,0);
                entity.resetCounter();
                entity.setGameState("START_OR_WEST");
            }
            else if(entity.gameState.equals("START_OR_WEST") && hit.getSide() == Direction.WEST)
            {
                entity.drawCard(true,0);
                entity.resetCounter();
                entity.setGameState("COULD_START");
            }



        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, Tavern.TWENTY_ONES_BLOCK_ENTITY, TwentyOnesBlockEntity::tick);
    }
}
