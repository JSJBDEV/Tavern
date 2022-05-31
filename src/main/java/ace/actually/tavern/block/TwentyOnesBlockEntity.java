package ace.actually.tavern.block;

import ace.actually.tavern.Tavern;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class TwentyOnesBlockEntity extends BlockEntity {

    int counter = 0;


    public TwentyOnesBlockEntity(BlockPos pos, BlockState state) {
        super(Tavern.TWENTY_ONES_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("counter",counter);
        super.writeNbt(nbt);

    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        counter=nbt.getInt("counter");
    }

    public void count()
    {
        counter++;
        markDirty();
    }
}
