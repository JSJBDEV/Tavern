package ace.actually.tavern.block;

import ace.actually.tavern.Tavern;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.Nullable;

public class TwentyOnesBlockEntity extends BlockEntity {

    int counter = 0;
    String gameState = "NO_GAME";
    NbtList cards;

    public TwentyOnesBlockEntity(BlockPos pos, BlockState state) {
        super(Tavern.TWENTY_ONES_BLOCK_ENTITY, pos, state);
        cards=new NbtList();
        gameState="NO_GAME";
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("counter",counter);
        nbt.put("cards",cards);
        nbt.putString("gamestate",gameState);
        super.writeNbt(nbt);

    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        counter=nbt.getInt("counter");
        cards= (NbtList) nbt.get("cards");
        gameState = nbt.getString("gamestate");
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

    public static void tick(World world, BlockPos pos, BlockState state, TwentyOnesBlockEntity be)
    {

        if(be.counter!=10)
        {
            be.count();
            be.markDirty();
            world.updateListeners(pos,state,state, Block.NOTIFY_LISTENERS);
            //System.out.println(be.counter);
        }
    }
    public void drawCard(int direction,float offset)
    {
        counter=0;
        NbtCompound card = new NbtCompound();
        card.putBoolean("isDrawn",false);
        card.putInt("direction",direction);

        card.putFloat("offset",offset);
        card.putString("face",getRandomCard());
        cards.add(card);
        markDirty();
        world.updateListeners(this.pos,this.getCachedState(),this.getCachedState(), Block.NOTIFY_LISTENERS);
    }

    public void drawCard(boolean isWest, float offset)
    {

        counter=0;
        NbtCompound card = new NbtCompound();
        card.putBoolean("isDrawn",false);
        if(isWest)
        {
            card.putInt("direction",1);
        }
        else
        {
            card.putInt("direction",0);
        }

        card.putFloat("offset",offset);
        card.putString("face",getRandomCard());
        cards.add(card);
        markDirty();
        world.updateListeners(this.pos,this.getCachedState(),this.getCachedState(), Block.NOTIFY_LISTENERS);

    }

    private void doesDirectionLose(int direction)
    {
        int number = 0;
        for(NbtElement element: cards)
        {
            NbtCompound compound = (NbtCompound) element;
            if(compound.getInt("direciton")==direction)
            {
                String face = compound.getString("face");
                switch (face.charAt(0))
                {
                    case 'A' -> number+=1;
                    case '2' -> number+=2;
                    case '3' -> number+=3;
                    case '4' -> number+=4;
                    case '5' -> number+=5;
                    case '6' -> number+=6;
                    case '7' -> number+=7;
                    case '8' -> number+=8;
                    case '9' -> number+=9;
                    case 'J', 'Q', 'K' -> number+=10;
                }
            }

        }
        if(number>21)
        {
            setGameState(direction+"_LOST");
        }
    }



    private static final String[] numbers = {"A","2","3","4","5","6","7","8","9","J","Q","K"};
    private static final String[] suites = {"♠","♥","♦","♣"};
    private static String getRandomCard()
    {
        return numbers[RandomUtils.nextInt(0,numbers.length)]+suites[RandomUtils.nextInt(0,suites.length)];
    }


    public void count()
    {
        counter++;
        if(counter==10)
        {

            for (int i = 0; i < cards.size(); i++) {
                NbtCompound compound = cards.getCompound(i);
                compound.putBoolean("isDrawn",true);
                cards.set(i,compound);

            }
            resetCounter();

        }
        markDirty();
        world.updateListeners(this.pos,this.getCachedState(),this.getCachedState(), Block.NOTIFY_LISTENERS);
    }


    public void setGameState(String gameState) {
        this.gameState = gameState;
        markDirty();
        world.updateListeners(this.pos,this.getCachedState(),this.getCachedState(), Block.NOTIFY_LISTENERS);
    }

    public void resetCounter()
    {
        counter=0;
        markDirty();
        world.updateListeners(this.pos,this.getCachedState(),this.getCachedState(), Block.NOTIFY_LISTENERS);
    }
}
