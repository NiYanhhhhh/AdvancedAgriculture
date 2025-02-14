package dev.krysztal.advagri.foundation;

import dev.krysztal.advagri.item.AdvAgriItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class AdvAgriItemGroups {

  public static ItemGroup ADV_AGRI_CORE_ITEM_GROUP = FabricItemGroupBuilder.build(
    new Identifier(AdvAgriConstants.MODID, "agriculture"),
    () -> new ItemStack(AdvAgriItems.BAMBOO_LEAVES)
  );
}
