package dev.krysztal.advagri.foundation.persistents;

import dev.krysztal.advagri.foundation.term.AdvAgriSeason;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

public class SolarTermPersistentState extends PersistentState {

  private static final String NBT_KEY = "adv_agri_nbt_current_season";
  public static final String ID = "solar_term_persistent";

  @Getter
  @Setter
  private int season = 0;

  @Override
  public NbtCompound writeNbt(NbtCompound nbt) {
    nbt.putInt(NBT_KEY, season);
    return nbt;
  }

  public static AdvAgriSeason getSeason(NbtCompound nbtCompound) {
    var currentSeason = nbtCompound.getInt(NBT_KEY);
    return currentSeason > 0 || currentSeason < AdvAgriSeason.values().length
      ? AdvAgriSeason.values()[currentSeason]
      : AdvAgriSeason.SPRING;
  }

  public static SolarTermPersistentState load(NbtCompound nbtCompound) {
    return new SolarTermPersistentState();
  }

  public static SolarTermPersistentState get(World world) {
    return Objects
      .requireNonNull(world.getServer())
      .getOverworld()
      .getPersistentStateManager()
      .getOrCreate(
        SolarTermPersistentState::load,
        SolarTermPersistentState::new,
        ID
      );
  }

  public static void set(World world, SolarTermPersistentState state) {
    Objects
      .requireNonNull(world.getServer())
      .getOverworld()
      .getPersistentStateManager()
      .set(ID, state);
  }
}
