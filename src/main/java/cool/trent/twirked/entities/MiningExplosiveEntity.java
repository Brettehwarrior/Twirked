/*
 * Based on IEExplosiveEntity from ImmersiveEngineering
 * https://github.com/BluSunrize/ImmersiveEngineering/blob/1.20.1/src/main/java/blusunrize/immersiveengineering/common/entities/IEExplosiveEntity.java
 *
 * Original code is licensed under "Blu's License of Common Sense"
 * BluSunrize
 * Copyright (c) 2017
 */


package cool.trent.twirked.entities;

import cool.trent.twirked.mixin.accessors.TNTEntityAccess;
import cool.trent.twirked.registration.EntityRegistration;
import cool.trent.twirked.util.SafeExplosion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

public class MiningExplosiveEntity extends PrimedTnt {
    private float size;
    public BlockState block;
    private boolean isFlaming = false;
    private float explosionDropChance;
    private Explosion.BlockInteraction mode = Explosion.BlockInteraction.DESTROY;

    private static final EntityDataAccessor<BlockState> dataMarker_block = SynchedEntityData.defineId(MiningExplosiveEntity.class, EntityDataSerializers.BLOCK_STATE);
    private static final EntityDataAccessor<Integer> dataMarker_fuse = SynchedEntityData.defineId(MiningExplosiveEntity.class, EntityDataSerializers.INT);


    public MiningExplosiveEntity(EntityType<? extends PrimedTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MiningExplosiveEntity(Level world, BlockPos pos, LivingEntity igniter, BlockState blockstate, float size) {
        super(EntityRegistration.MINING_EXPLOSIVE_ENTITY.get(), world);

        this.setPos(pos.getX()+.5, pos.getY()+.5, pos.getZ()+.5);
        double jumpingDirection = world.random.nextDouble()*2*Math.PI;
        this.setDeltaMovement(-Math.sin(jumpingDirection)*0.02D, 0.2, -Math.cos(jumpingDirection)*0.02D);
        this.setFuse(80);
        this.xo = getX();
        this.yo = getY();
        this.zo = getZ();
        ((TNTEntityAccess)this).setOwner(igniter);
        this.size = size;
        this.block = blockstate;
        this.explosionDropChance = 1/size;
        this.setBlockSynced();
    }


    @Override
    public void tick()
    {
        if(level().isClientSide&&this.block==null)
            this.getBlockSynced();

        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();
        if(!this.isNoGravity())
        {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if(this.onGround())
        {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }
        int newFuse = this.getFuse()-1;
        this.setFuse(newFuse);
        if(newFuse <= 0)
        {
            this.discard();

            Explosion explosion = new SafeExplosion(level(), this, getX(), getY()+(getBbHeight()/16f), getZ(), size, isFlaming, mode)
                    .setDropChance(explosionDropChance);
            if(!ForgeEventFactory.onExplosionStart(level(), explosion))
            {
                explosion.explode();
                explosion.finalizeExplosion(true);
            }
        }
        else
        {
            this.updateInWaterStateAndDoFluidPushing();
            this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY()+0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(dataMarker_block, Blocks.AIR.defaultBlockState());
        this.entityData.define(dataMarker_fuse, 0);
    }


    private void getBlockSynced()
    {
        this.block = this.entityData.get(dataMarker_block);
        if(this.block.isAir())
            this.block = null;
        this.setFuse(this.entityData.get(dataMarker_fuse));
    }

    public MiningExplosiveEntity setMode(Explosion.BlockInteraction smoke)
    {
        this.mode = smoke;
        return this;
    }

    public MiningExplosiveEntity setDropChance(float chance)
    {
        this.explosionDropChance = chance;
        return this;
    }

    private void setBlockSynced()
    {
        if(this.block!=null)
        {
            this.entityData.set(dataMarker_block, this.block);
            this.entityData.set(dataMarker_fuse, this.getFuse());
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tagCompound)
    {
        super.addAdditionalSaveData(tagCompound);
        tagCompound.putFloat("explosionPower", size);
        tagCompound.putInt("explosionSmoke", mode.ordinal());
        tagCompound.putBoolean("explosionFire", isFlaming);
        if(this.block!=null)
            tagCompound.putInt("block", Block.getId(this.block));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tagCompound)
    {
        super.readAdditionalSaveData(tagCompound);
        size = tagCompound.getFloat("explosionPower");
        mode = Explosion.BlockInteraction.values()[tagCompound.getInt("explosionSmoke")];
        isFlaming = tagCompound.getBoolean("explosionFire");
        if(tagCompound.contains("block", Tag.TAG_INT))
            this.block = Block.stateById(tagCompound.getInt("block"));
    }

}
