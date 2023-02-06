package dev.krysztal.advagri.entity.block;

import dev.krysztal.advagri.entity.AdvAgriEntities;
import dev.krysztal.advagri.foundation.resources.AdvAgriElementResourceManager;
import dev.krysztal.advagri.foundation.resources.element.ElementsCombination;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Slf4j
public class WaterWellBlockEntity extends BlockEntity {

  private int tickCount = 0;
  private final int maxTickCount = 8 + (new Random().nextInt(-4, 4));

  @Getter
  @Setter
  private int phosphorusElement = 0;

  @Getter
  @Setter
  private int potassiumElement = 0;

  @Getter
  @Setter
  private int nitrogenElement = 0;

  @Getter
  @Setter
  private boolean wet = false;

  public WaterWellBlockEntity(BlockPos pos, BlockState state) {
    super(AdvAgriEntities.BlockEntities.WATER_WELL_BLOCK_ENTITY, pos, state);
  }

  public void tick(
    World world,
    BlockPos pos,
    BlockState state,
    WaterWellBlockEntity entity
  ) {
    if (++tickCount >= maxTickCount) tickCount = 0; else return;

    this.setWet(world.getBlockState(pos.up()).getBlock() == Blocks.WATER);

    log.info(
      new Vec3d(pos.getX() + 0.5, pos.up(1).getY() + 0.5, pos.getZ() + 0.5)
        .toString()
    );

    log.info(
      "{} {} {}",
      this.nitrogenElement,
      this.phosphorusElement,
      this.phosphorusElement
    );

    var itemEntities = world.getEntitiesByClass(
      ItemEntity.class,
      Box.from(new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)),
      EntityPredicates.VALID_ENTITY
    );

    for (var itemEntity : itemEntities) {
      var itemIdentifier = itemEntity
        .getStack()
        .getItem()
        .getIdentifier()
        .toString();
      var combinationOptional = AdvAgriElementResourceManager
        .getElementRegistry()
        .stream()
        .filter($ -> $.getItem().equals(itemIdentifier))
        .findFirst();
      if (combinationOptional.isEmpty()) continue;
      var combination = combinationOptional.get();

      itemEntity.getStack().decrement(1); // Get item, then decrement it.

      var elementsCombination = combination.getElementsCombination();

      this.nitrogenElement += elementsCombination.getNitrogen();
      this.phosphorusElement += elementsCombination.getPhosphorus();
      this.potassiumElement += elementsCombination.getPotassium();

      break;
    }

    BlockEntity.markDirty(world, pos, state);
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    nbt.putInt("PotassiumElement", this.getPotassiumElement());
    nbt.putInt("PhosphorusElement", this.getPhosphorusElement());
    nbt.putInt("NitrogenElement", this.getNitrogenElement());

    nbt.putBoolean("Wet", this.isWet());

    super.readNbt(nbt);
  }

  @Override
  protected void writeNbt(NbtCompound nbt) {
    this.setPotassiumElement(nbt.getInt("PotassiumElement"));
    this.setPhosphorusElement(nbt.getInt("PhosphorusElement"));
    this.setNitrogenElement(nbt.getInt("NitrogenElement"));

    this.setWet(nbt.getBoolean("Wet"));

    super.writeNbt(nbt);
  }
}
