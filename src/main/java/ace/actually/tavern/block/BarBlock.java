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

        if(hand.equals(Hand.MAIN_HAND))
        {
            BarBlockEntity barBlockEntity = (BarBlockEntity) world.getBlockEntity(pos);
            if(!barBlockEntity.hasDrink() && player.getScoreboardTags().contains("bartender"))
            {
                barBlockEntity.setHasDrink(true);
            }
            else
            {
                for (int i = 0; i < player.getInventory().size(); i++) {
                    if(player.getInventory().getStack(i).getItem()==Items.EMERALD)
                    {
                        player.getInventory().getStack(i).decrement(1);
                        barBlockEntity.addEmeralds(1);
                        ItemStack brew = new ItemStack(Tavern.BEER_BREW);
                        brew.setCustomName(new LiteralText(NameGenerator.genAlcohol()));
                        player.giveItemStack(brew);
                        break;
                    }
                }
            }
        }


        return super.onUse(state, world, pos, player, hand, hit);
    }
}
