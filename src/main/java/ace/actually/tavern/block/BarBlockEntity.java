package ace.actually.tavern.block;

import ace.actually.tavern.NameGenerator;
import ace.actually.tavern.Tavern;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.synth.Region;

public class BarBlockEntity extends BlockEntity {

    private boolean hasDrink = false;
    private int emeralds = 0;
    private String brew = "";
    public BarBlockEntity(BlockPos pos, BlockState state) {
        super(Tavern.BAR_BLOCK_ENTITY, pos, state);
        brew= NameGenerator.genAlcohol();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putBoolean("has_drink",hasDrink);
        nbt.putInt("emeralds",emeralds);
        nbt.putString("brew",brew);
        super.writeNbt(nbt);

    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        hasDrink=nbt.getBoolean("has_drink");
        emeralds=nbt.getInt("emeralds");
        brew=nbt.getString("brew");
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
        System.out.println("has drink");
        markDirty();
        world.updateListeners(this.pos,this.getCachedState(),this.getCachedState(), Block.NOTIFY_LISTENERS);


    }

    public boolean hasDrink() {
        return hasDrink;
    }

    public int getEmeralds() {
        return emeralds;
    }

    public void addEmeralds(int i)
    {
        emeralds+=i;
        markDirty();
    }

    public String getBrew() {
        return brew;
    }
}
