package cool.trent.twirked.registration;

import cool.trent.twirked.Twirked;
import cool.trent.twirked.blocks.MiningExplosives;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Twirked.MOD_ID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Twirked.MOD_ID);

    public static final RegistryObject<Block> STUCCO = BLOCKS.register("stucco",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(DyeColor.WHITE)
                    .strength(1.0f, 12f)
                    .instrument(NoteBlockInstrument.BANJO)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<BlockItem> STUCCO_ITEM = CreativeTabRegistration.addToTab(BLOCK_ITEMS.register("stucco", ()-> new BlockItem(STUCCO.get(), new Item.Properties())));

    public static final RegistryObject<Block> MINING_EXPLOSIVES = BLOCKS.register("mining_explosives", ()->
            new MiningExplosives(MiningExplosives.PROPERTIES.get()));
    public static final RegistryObject<BlockItem> MINING_EXPLOSIVE_ITEM = CreativeTabRegistration.addToTab(BLOCK_ITEMS.register("mining_explosives", ()-> new BlockItem(MINING_EXPLOSIVES.get(), new Item.Properties())));

}
