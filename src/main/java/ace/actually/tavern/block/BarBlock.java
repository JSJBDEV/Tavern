package ace.actually.tavern.block;

import ace.actually.tavern.NameGenerator;
import ace.actually.tavern.Tavern;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BarBlock extends Block implements BlockEntityProvider {
    public BarBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BarBlockEntity(pos,state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if(hand.equals(Hand.MAIN_HAND) && !world.isClient)
        {

            BarBlockEntity barBlockEntity = (BarBlockEntity) world.getBlockEntity(pos);
            if(!barBlockEntity.hasDrink())
            {
                if(player.getScoreboardTags().contains(barBlockEntity.getTavern()))
                {

                    if(player.isSneaking())
                    {
                        while (barBlockEntity.getEmeralds()>64)
                        {
                            player.giveItemStack(new ItemStack(Items.EMERALD,64));
                            barBlockEntity.setEmeralds(barBlockEntity.getEmeralds()-64);
                        }
                        if(barBlockEntity.getEmeralds()>0)
                        {
                            player.giveItemStack(new ItemStack(Items.EMERALD,barBlockEntity.getEmeralds()));
                            barBlockEntity.setEmeralds(0);
                        }

                    }
                    else
                    {
                        barBlockEntity.setHasDrink(true);
                    }

                }


            }
            else
            {
                for (int i = 0; i < player.getInventory().size(); i++) {
                    if(player.getInventory().getStack(i).getItem()==Items.EMERALD)
                    {
                        player.getInventory().getStack(i).decrement(1);
                        barBlockEntity.addEmeralds(1);
                        ItemStack brew = new ItemStack(Tavern.BEER_BREW);
                        brew.setCustomName(new LiteralText(barBlockEntity.getBrew()));

                        player.giveItemStack(brew);
                        barBlockEntity.setHasDrink(false);
                        break;
                    }
                }
            }
        }


        return super.onUse(state, world, pos, player, hand, hit);
    }
}
