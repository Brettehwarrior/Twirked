package cool.trent.twirked.events;

import cool.trent.twirked.util.SafeExplosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class EventHandler {
    public static Map<Level, Set<SafeExplosion>> currentExplosions = new WeakHashMap<>();

    @SubscribeEvent
    public void onWorldTick(TickEvent.LevelTickEvent event)
    {
        if(event.level.isClientSide||event.phase!=TickEvent.Phase.START)
            return;

        final Set<SafeExplosion> explosionsInLevel = currentExplosions.get(event.level);
        if(explosionsInLevel!=null)
        {
            Iterator<SafeExplosion> itExplosion = explosionsInLevel.iterator();
            while(itExplosion.hasNext())
            {
                SafeExplosion ex = itExplosion.next();
                ex.doExplosionTick();
                if(ex.isExplosionFinished)
                    itExplosion.remove();
            }
        }
    }
}
