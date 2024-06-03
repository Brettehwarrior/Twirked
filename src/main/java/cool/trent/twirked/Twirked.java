package cool.trent.twirked;

import cool.trent.twirked.registration.BlockRegistration;
import cool.trent.twirked.registration.CreativeTabRegistration;
import cool.trent.twirked.registration.EntityRegistration;
import cool.trent.twirked.registration.ItemRegistration;
import cool.trent.twirked.events.EventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Twirked.MOD_ID)
public class Twirked {
    public static final String MOD_ID = "twirked";

    public Twirked() {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistration.ITEMS.register(eventBus);
        BlockRegistration.BLOCKS.register(eventBus);
        BlockRegistration.BLOCK_ITEMS.register(eventBus);
        CreativeTabRegistration.CREATIVE_TABS.register(eventBus);
        EntityRegistration.ENTITY_TYPES.register(eventBus);
    }
}
