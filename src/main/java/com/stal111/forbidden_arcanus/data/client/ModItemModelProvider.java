package com.stal111.forbidden_arcanus.data.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.ObsidianSkullBlock;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.valhelsia.valhelsia_core.data.ValhelsiaItemModelProvider;

import java.util.Arrays;
import java.util.Objects;

/**
 * Mod Item Model Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.client.ModItemModelProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-26
 */
public class ModItemModelProvider extends ValhelsiaItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ForbiddenArcanus.REGISTRY_MANAGER, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //Block Items
        getRemainingBlockItems().removeIf(item -> ((BlockItem) item.get()).getBlock() instanceof ObsidianSkullBlock);

        takeBlockItem(this::simpleModel,
                NewModBlocks.PIXIE_UTREM_JAR,
                NewModBlocks.CORRUPTED_PIXIE_UTREM_JAR,
                NewModBlocks.NIPA,
                NewModBlocks.FUNGYSS_DOOR
        );
        takeBlockItem(this::simpleModelBlockTexture,
                NewModBlocks.FUNGYSS
        );
        takeBlockItem(this::withParentInventory,
                NewModBlocks.FUNGYSS_BLOCK,
                NewModBlocks.FUNGYSS_BUTTON,
                NewModBlocks.FUNGYSS_FENCE,
                NewModBlocks.DARKSTONE_WALL,
                NewModBlocks.POLISHED_DARKSTONE_WALL,
                NewModBlocks.POLISHED_DARKSTONE_BUTTON,
                NewModBlocks.POLISHED_DARKSTONE_BRICK_WALL,
                NewModBlocks.ARCANE_POLISHED_DARKSTONE_WALL
        );
        takeBlockItem(this::utremJarModel, NewModBlocks.UTREM_JAR);
        takeBlockItem(item -> withParent(item, "arcane_polished_darkstone_pillar_single"), NewModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR);
        takeBlockItem(item -> withParent(item, "fungyss_trapdoor_bottom"), NewModBlocks.FUNGYSS_TRAPDOOR);
        takeBlockItem(item -> simpleModelBlockTexture(item, "arcane_glass"), NewModBlocks.ARCANE_GLASS_PANE);

        forEachBlockItem(this::withParent);

        //Items
        getRemainingItems().removeAll(Arrays.asList(NewModItems.LENS_OF_VERITATIS, NewModItems.OBSIDIAN_SKULL_SHIELD, NewModItems.ZOMBIE_ARM, NewModItems.SHINY_ZOMBIE_ARM));
        forEachItem(this::simpleModel);
    }

    public <T extends Item> void utremJarModel(T item) {
        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
        getBuilder("utrem_jar_water").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/utrem_jar_water");
        getBuilder("utrem_jar_lava").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/utrem_jar_lava");

        getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name)
        .override()
                .predicate(new ResourceLocation("water"), 1.0F)
                .predicate(new ResourceLocation("lava"), 0.0F)
                .model(getExistingFile(modLoc("item/utrem_jar_water"))).end()
        .override()
                .predicate(new ResourceLocation("water"), 0.0F)
                .predicate(new ResourceLocation("lava"), 1.0F)
                .model(getExistingFile(modLoc("utrem_jar_lava"))).end();
    }
}
