package com.anax.sa_fabrials.block;

import com.anax.sa_fabrials.SAFabrials;
import com.anax.sa_fabrials.block.custom.StormlightPipeBlock;
import com.anax.sa_fabrials.block.custom.TopazCrystalBlock;
import com.anax.sa_fabrials.item.ModCreativeModeTab;
import com.anax.sa_fabrials.item.ModItems;
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


    public static final RegistryObject<Block> TOPAZ_CRYSTAL_BLOCK = registerBlock("topaz_crystal_block",
            () -> new TopazCrystalBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()), ModCreativeModeTab.TEST_MOD_TAB);

    public static final RegistryObject<Block> STORMLIGHT_PIPE_BLOCK = registerBlock("stormlight_pipe",
            () -> new StormlightPipeBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()), ModCreativeModeTab.TEST_MOD_TAB);



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
        return ModItems.ITEMS.register(
                name,
                () -> new BlockItem(block.get(),new Item.Properties().tab(tab)));
    }


    private static <T extends Block> RegistryObject<T> registerBlock
            (String name, Supplier<T> block, CreativeModeTab tab,String tooltipKey){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab, tooltipKey);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem
            (String name, RegistryObject<T> block, CreativeModeTab tab, String tooltipKey){
        return ModItems.ITEMS.register(
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
