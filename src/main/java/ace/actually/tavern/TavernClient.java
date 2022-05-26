package ace.actually.tavern;

import ace.actually.tavern.block.BarBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

public class TavernClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(Tavern.BAR_BLOCK_ENTITY, BarBlockEntityRenderer::new);
    }
}
