package ace.actually.tavern.block;

import ace.actually.tavern.Tavern;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BarBlockEntity extends BlockEntity {

    private boolean hasDrink = false;
    public BarBlockEntity(BlockPos pos, BlockState state) {
        super(Tavern.BAR_BLOCK_ENTITY, pos, state);

    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putBoolean("has_drink",hasDrink);
        super.writeNbt(nbt);

    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        hasDrink=nbt.getBoolean("has_drink");
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public void setHasDrink(boolean hasDrink) {
        this.hasDrink = hasDrink;
        markDirty();

    }

    public boolean hasDrink() {
        return hasDrink;
    }
}
