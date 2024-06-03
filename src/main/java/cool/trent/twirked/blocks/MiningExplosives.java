/*
 * Based on GunpowderBarrelBlock from ImmersiveEngineering
 * https://github.com/BluSunrize/ImmersiveEngineering/blob/1.20.1/src/main/java/blusunrize/immersiveengineering/common/blocks/wooden/GunpowderBarrelBlock.java
 *
 * Original code is licensed under "Blu's License of Common Sense"
 * BluSunrize
 * Copyright (c) 2017
 */

package cool.trent.twirked.blocks;

import cool.trent.twirked.entities.MiningExplosiveEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MiningExplosives extends TntBlock {
    public static final Supplier<Properties> PROPERTIES = () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .ignitedByLava()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .sound(SoundType.WOOD)
            .strength(2f, 5f);

    public MiningExplosives(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face)
    {
        return 100;
    }

    @Override
    public void wasExploded(@NotNull Level world, @NotNull BlockPos pos, @NotNull Explosion explosion) {
        // Should ignite here?
    }

    private MiningExplosiveEntity spawnExplosive(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity igniter)
    {
        MiningExplosiveEntity explosive = new MiningExplosiveEntity(world, pos, igniter, state, 5);
        explosive.setDropChance(1);
        world.addFreshEntity(explosive);
        return explosive;
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @org.jetbrains.annotations.Nullable Direction face, @org.jetbrains.annotations.Nullable LivingEntity igniter)
    {
        MiningExplosiveEntity explosive = spawnExplosive(world, pos, state, igniter);
        world.playSound(null, explosive.getX(), explosive.getY(), explosive.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.removeBlock(pos, false);
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion)
    {
        super.onBlockExploded(state, world, pos, explosion);
        if(!world.isClientSide)
        {
            MiningExplosiveEntity explosive = spawnExplosive(world, pos, state, explosion.getIndirectSourceEntity());
            explosive.setFuse((short)(world.random.nextInt(explosive.getFuse()/4)+explosive.getFuse()/8));
        }
    }
}
