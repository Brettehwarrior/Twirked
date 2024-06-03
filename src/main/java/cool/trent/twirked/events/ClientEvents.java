package cool.trent.twirked.events;

import com.google.common.eventbus.Subscribe;
import cool.trent.twirked.Twirked;
import cool.trent.twirked.client.render.entity.MiningExplosivesEntityRenderer;
import cool.trent.twirked.registration.EntityRegistration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Twirked.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistration.MINING_EXPLOSIVE_ENTITY.get(), MiningExplosivesEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {

    }
}
