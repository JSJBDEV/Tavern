package ace.actually.tavern.block;

import ace.actually.tavern.Tavern;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
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

        invokeGame(world, pos, hand, hit, player);
        return ActionResult.PASS;
    }

    private void invokeGame(World world, BlockPos pos, Hand hand,BlockHitResult hit, PlayerEntity player)
    {
        if(hand==Hand.MAIN_HAND && !world.isClient)
        {
            TwentyOnesBlockEntity entity = (TwentyOnesBlockEntity) world.getBlockEntity(pos);
            if(
                    entity.gameState.equals("NO_GAME")
                            && hit.getSide() == Direction.NORTH
                            //&& player.isSneaky()
                            && player.getMainHandStack().getItem()== Items.EMERALD
            )
            {
                entity.drawCard(2,0);
                entity.drawCard(2,0);
                entity.setGameState("PLAYERS_CAN_JOIN");
                player.getMainHandStack().decrement(1);
            }
            else if(player.isSneaky() && entity.gameState.equals("PLAYERS_CAN_JOIN"))
            {
                switch (hit.getSide()) {
                    case EAST -> entity.addPlayer(0, 0);
                    case WEST -> entity.addPlayer(1, 0);
                    case NORTH -> {
                        for (int i = 0; i < entity.getPlayers().size(); i++) {
                            String[] cc = entity.getPlayers().getString(i).split(",");
                            entity.drawCard(Integer.parseInt(cc[0]), Float.parseFloat(cc[1]));
                            entity.drawCard(Integer.parseInt(cc[0]), Float.parseFloat(cc[1]));
                        }
                        entity.setGameState("GAME_START");
                    }
                }
            }
            else if(entity.gameState.equals("GAME_START"))
            {
                switch (hit.getSide())
                {
                    case EAST -> entity.drawCard(0,0);
                    case WEST -> entity.drawCard(1,0);
                    case NORTH ->
                            {
                                if(player.isSneaky())
                                {
                                    entity.setGameState("END_ROUND");
                                    int winScore = 0;
                                    String[] winner = null;
                                    for (int i = 0; i < entity.getPlayers().size(); i++)
                                    {
                                        String[] cc = entity.getPlayers().getString(i).split(",");
                                        int total = entity.getPlayerCards(Integer.parseInt(cc[0]),Float.parseFloat(cc[1]));
                                        if(total>winScore && total<22)
                                        {
                                            winner=cc;
                                            winScore=total;
                                        }
                                    }
                                    //TODO: item entity spawns at players offset/direction position

                                    ItemEntity item = new ItemEntity(world,pos.getX(),pos.getY()+1,pos.getZ()+Float.parseFloat(winner[1]),new ItemStack(Items.EMERALD,entity.getPlayers().size()));
                                    world.spawnEntity(item);

                                    world.setBlockState(pos,Tavern.TWENTY_ONES_BLOCK.getDefaultState());
                                }
                                else
                                {
                                    entity.drawCard(2,0);
                                }
                            }
                }


            }
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, Tavern.TWENTY_ONES_BLOCK_ENTITY, TwentyOnesBlockEntity::tick);
    }
}
