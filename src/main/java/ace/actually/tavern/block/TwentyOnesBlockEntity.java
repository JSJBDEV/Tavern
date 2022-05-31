package ace.actually.tavern.block;

import ace.actually.tavern.Tavern;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;

public class TwentyOnesBlockEntity extends BlockEntity {

    int counter = 0;
    NbtList cards;

    public TwentyOnesBlockEntity(BlockPos pos, BlockState state) {
        super(Tavern.TWENTY_ONES_BLOCK_ENTITY, pos, state);
        cards=new NbtList();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("counter",counter);
        nbt.put("cards",cards);
        super.writeNbt(nbt);

    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        counter=nbt.getInt("counter");
        cards= (NbtList) nbt.get("cards");
    }

    public void drawCard(boolean isWest, int offset)
    {
        NbtCompound card = new NbtCompound();
        card.putBoolean("isDrawn",false);
        card.putBoolean("isWest",isWest);
        card.putInt("offset",offset);
        card.putString("face","5♠");
        cards.add(card);
        counter=0;
    }
    public void count()
    {
        counter++;
        if(counter==10)
        {
            for(NbtElement element: cards)
            {
                NbtCompound compound = (NbtCompound) element;
                compound.putBoolean("isDrawn",true);
            }
        }
        markDirty();
    }


}
