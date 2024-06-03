package cool.trent.twirked.registration;

import cool.trent.twirked.Twirked;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Twirked.MOD_ID);

    public static final RegistryObject<Item> DIAMOND_POTATO = CreativeTabRegistration.addToTab(ITEMS.register("diamond_potato",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(6)
                            .saturationMod(0.5f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 2), 1.0f)
                            .build())
                    .rarity(Rarity.RARE))));
}
