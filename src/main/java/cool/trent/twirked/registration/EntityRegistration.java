package cool.trent.twirked.registration;

import cool.trent.twirked.Twirked;
import cool.trent.twirked.entities.MiningExplosiveEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistration {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Twirked.MOD_ID);

    public static final RegistryObject<EntityType<MiningExplosiveEntity>> MINING_EXPLOSIVE_ENTITY = ENTITY_TYPES.register(
            "mining_explosive_entity",
            () -> EntityType.Builder.of((EntityType<MiningExplosiveEntity> type, Level level) -> new MiningExplosiveEntity(type, level), MobCategory.MISC)
                    .fireImmune()
                    .sized(0.98f, 0.98f)
                    .build(new ResourceLocation(Twirked.MOD_ID, "mining_explosive_entity").toString()));
}
