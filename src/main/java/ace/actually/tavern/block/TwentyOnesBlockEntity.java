package ace.actually.tavern.block;

import ace.actually.tavern.Tavern;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class TwentyOnesBlockEntity extends BlockEntity {
    public TwentyOnesBlockEntity(BlockPos pos, BlockState state) {
        super(Tavern.TWENTY_ONES_BLOCK_ENTITY, pos, state);
    }
}
