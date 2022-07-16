package ace.actually.tavern.mixin;

import ace.actually.tavern.Tavern;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.*;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

@Mixin(PlacedFeature.class)
public class PlacedFeatureMixin {

    @Shadow @Final private RegistryEntry<ConfiguredFeature<?, ?>> feature;

    @Inject(at = @At("TAIL"), method = "generate(Lnet/minecraft/world/gen/feature/FeaturePlacementContext;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;)Z")
    private void generate(FeaturePlacementContext context, Random random, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {

        //Tavern.LOGGER.info(feature.value().toString());
        if(feature.value().config() instanceof BlockPileFeatureConfig config)
        {

            if(config.stateProvider.getBlockState(random, pos).getBlock()==Blocks.HAY_BLOCK)
            {

                context.getWorld().setBlockState(pos.add(10,20,0), Tavern.PORT_A_TAVERN_BLOCK.getDefaultState(), Block.NOTIFY_ALL);
                context.getWorld().createAndScheduleBlockTick(pos.add(10,20,0),Tavern.PORT_A_TAVERN_BLOCK,1);
            }
            Tavern.LOGGER.info(config.stateProvider.getBlockState(random, pos).getBlock().toString());
        }
        if(feature.value()==PileConfiguredFeatures.PILE_HAY.value())
        {
            Tavern.LOGGER.info("hay");
        }


    }
}
