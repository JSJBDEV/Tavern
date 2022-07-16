package ace.actually.tavern.block;

import ace.actually.tavern.Tavern;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class PortATavernBlock extends Block {
    public PortATavernBlock(Settings settings) {
        super(settings);

    }


    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        int xo = 0;
        int zo = 0;
        if((world.getTopY(Heightmap.Type.WORLD_SURFACE_WG,pos.getX(),pos.getZ())-20)>pos.getY())
        {
            Tavern.spawnStructure(world,pos.add(0,-22,-50),"bar");
        }
        else
        {

            Tavern.spawnStructure(world,pos.add(0,-22,0),"bar");
        }
        int yo = (world.getTopY(Heightmap.Type.WORLD_SURFACE_WG,pos.getX()+xo,pos.getZ()+zo)-20);
        while (!world.getBlockState(pos.add(xo,yo,zo)).isIn(BlockTags.LUSH_GROUND_REPLACEABLE))
        {
            if(random.nextBoolean())
            {
                xo += 20;
            }
            else
            {
                zo += 20;
            }
            yo = (world.getTopY(Heightmap.Type.WORLD_SURFACE_WG,pos.getX()+xo,pos.getZ()+zo)-20);


        }


        world.setBlockState(pos,Blocks.AIR.getDefaultState());
    }
}
