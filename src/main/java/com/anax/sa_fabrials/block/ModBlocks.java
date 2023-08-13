package com.anax.sa_fabrials.block;

import com.anax.sa_fabrials.SAFabrials;
import com.anax.sa_fabrials.block.custom.*;
import com.anax.sa_fabrials.item.ModCreativeModeTab;
import com.anax.sa_fabrials.item.SAItems;
import com.anax.sa_fabrials.item.custom.CrystalBlockItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SAFabrials.MOD_ID);


    public static final RegistryObject<Block> TOPAZ_CRYSTAL_BLOCK = registerBlockWithoutItem("topaz_crystal_block",
            () -> new TopazCrystalBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()), ModCreativeModeTab.SA_FABRIALS_MOD_TAB);

    public static final RegistryObject<Item> TOPAZ_CRYSTAL_BLOCK_ITEM = registerCrystalBLockItem("topaz_crystal_block", TOPAZ_CRYSTAL_BLOCK, ModCreativeModeTab.SA_FABRIALS_MOD_TAB);

    public static final RegistryObject<Block> STORMLIGHT_PIPE_BLOCK = registerBlock("stormlight_pipe",
            () -> new StormlightPipeBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()), ModCreativeModeTab.SA_FABRIALS_MOD_TAB);

    public static final RegistryObject<Block> SPREN_CATCHING_STATION = registerBlock("spren_catching_station_block",
            () -> new SprenCatchingStationBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()), ModCreativeModeTab.SA_FABRIALS_MOD_TAB);

    public static final RegistryObject<Block> ARTIFABRIANS_STATION_BLOCK = registerBlock("artifabrians_station_block",
            () -> new ArtifabriansStationBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()), ModCreativeModeTab.SA_FABRIALS_MOD_TAB);

    public static final RegistryObject<Block> CONJOINED_REDSTONE_LAMP_BLOCK = registerBlockWithoutItem("conjoined_redstone_lamp_block",
            () -> new ConjoinedRedstoneLampBlock(BlockBehaviour.Properties.of(Material.METAL)), ModCreativeModeTab.SA_FABRIALS_MOD_TAB);

    private static <T extends Block> RegistryObject<T> registerBlock
            (String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutItem
            (String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem
            (String name, RegistryObject<T> block, CreativeModeTab tab){
        return SAItems.ITEMS.register(
                name,
                () -> new BlockItem(block.get(),new Item.Properties().tab(tab)));
    }

    private static <T extends Block>RegistryObject<Item> registerCrystalBLockItem
            (String name, RegistryObject<T> block, CreativeModeTab tab){
        return SAItems.ITEMS.register(
                name,
                () -> new CrystalBlockItem(block.get(),new Item.Properties().tab(tab)));
    }


    private static <T extends Block> RegistryObject<T> registerBlock
            (String name, Supplier<T> block, CreativeModeTab tab,String tooltipKey){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab, tooltipKey);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem
            (String name, RegistryObject<T> block, CreativeModeTab tab, String tooltipKey){
        return SAItems.ITEMS.register(
                name,
                () -> new BlockItem(block.get(),new Item.Properties().tab(tab)){
                    @Override
                    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                        pTooltip.add(new TranslatableComponent(tooltipKey));
                    }
                });
    }
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
