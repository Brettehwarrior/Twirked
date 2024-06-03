package cool.trent.twirked.registration;

import cool.trent.twirked.Twirked;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CreativeTabRegistration {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Twirked.MOD_ID);

    public static final List<Supplier<? extends ItemLike>> CREATIVE_TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> TWIRKED_CREATIVE_TAB = CREATIVE_TABS.register("twirked_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.twirked_tab"))
                    .icon(ItemRegistration.DIAMOND_POTATO.get()::getDefaultInstance)
                    .displayItems((itemDisplayParameters, output) -> {
                        CREATIVE_TAB_ITEMS.forEach(e -> output.accept(e.get()));
                    })
                    .build());

    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
        CREATIVE_TAB_ITEMS.add(itemLike);
        return itemLike;
    }
}
