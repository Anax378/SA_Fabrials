package com.anax.sa_fabrials.block.custom;

import com.anax.sa_fabrials.block.ModBlocks;
import com.anax.sa_fabrials.block.entity.ModBlockEntities;
import com.anax.sa_fabrials.block.entity.custom.StormlightPipeBlockEntity;
import com.anax.sa_fabrials.block.entity.custom.TopazCrystalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class StormlightPipeBlock extends BaseEntityBlock {

    public static final BooleanProperty CONNECTED_UP = BooleanProperty.create("connected_up");
    public static final BooleanProperty CONNECTED_DOWN = BooleanProperty.create("connected_down");
    public static final BooleanProperty CONNECTED_SOUTH = BooleanProperty.create("connected_south");
    public static final BooleanProperty CONNECTED_NORTH = BooleanProperty.create("connected_north");
    public static final BooleanProperty CONNECTED_WEST = BooleanProperty.create("connected_west");
    public static final BooleanProperty CONNECTED_EAST = BooleanProperty.create("connected_east");

    public static final BooleanProperty IS_INPUT_UP = BooleanProperty.create("is_input_up");
    public static final BooleanProperty IS_INPUT_DOWN = BooleanProperty.create("is_input_down");
    public static final BooleanProperty IS_INPUT_SOUTH = BooleanProperty.create("is_input_south");
    public static final BooleanProperty IS_INPUT_NORTH = BooleanProperty.create("is_input_north");
    public static final BooleanProperty IS_INPUT_WEST = BooleanProperty.create("is_input_west");
    public static final BooleanProperty IS_INPUT_EAST = BooleanProperty.create("is_input_east");


    public StormlightPipeBlock(Properties properties) {
        super(properties);
    }

    static final VoxelShape CORE = Block.box(5, 5, 5, 11, 11, 11);
    static final VoxelShape NORTH = Block.box(5, 5, 0, 11, 11, 5);
    static final VoxelShape SOUTH = Block.box(5, 5, 11, 11, 11, 16);
    static final VoxelShape WEST = Block.box(0, 5, 5, 5, 11, 11);
    static final VoxelShape EAST = Block.box(11, 5, 5, 16, 11, 11);
    static final VoxelShape UP = Block.box(5, 11, 5, 11, 16, 11);
    static final VoxelShape DOWN = Block.box(5, 0, 5, 11, 5, 11);

    public boolean connects(Direction direction, BlockPos pos, LevelAccessor level){
        return level.getBlockState(pos.relative(direction)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get());

    }


    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        VoxelShape shape = CORE;

        if(blockState.getValue(CONNECTED_UP)){shape = add(shape, UP);}
        if(blockState.getValue(CONNECTED_DOWN)){shape = add(shape, DOWN);}
        if(blockState.getValue(CONNECTED_NORTH)){shape = add(shape, NORTH);}
        if(blockState.getValue(CONNECTED_SOUTH)){shape = add(shape, SOUTH);}
        if(blockState.getValue(CONNECTED_WEST)){shape = add(shape, WEST);}
        if(blockState.getValue(CONNECTED_EAST)){shape = add(shape, EAST);}

        return shape;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if(connects(pDirection, pCurrentPos, pLevel)){
            if(pDirection == Direction.DOWN){pState = pState.setValue(CONNECTED_DOWN, Boolean.TRUE);}
            if(pDirection == Direction.UP){pState = pState.setValue(CONNECTED_UP, Boolean.TRUE);}
            if(pDirection == Direction.NORTH){pState = pState.setValue(CONNECTED_NORTH, Boolean.TRUE);}
            if(pDirection == Direction.SOUTH){pState = pState.setValue(CONNECTED_SOUTH, Boolean.TRUE);}
            if(pDirection == Direction.WEST){pState = pState.setValue(CONNECTED_WEST, Boolean.TRUE);}
            if(pDirection == Direction.EAST){pState = pState.setValue(CONNECTED_EAST, Boolean.TRUE);}
        }else{
            if(pDirection == Direction.DOWN){pState = pState.setValue(CONNECTED_DOWN, Boolean.FALSE);}
            if(pDirection == Direction.UP){pState = pState.setValue(CONNECTED_UP, Boolean.FALSE);}
            if(pDirection == Direction.NORTH){pState = pState.setValue(CONNECTED_NORTH, Boolean.FALSE);}
            if(pDirection == Direction.SOUTH){pState = pState.setValue(CONNECTED_SOUTH, Boolean.FALSE);}
            if(pDirection == Direction.WEST){pState = pState.setValue(CONNECTED_WEST, Boolean.FALSE);}
            if(pDirection == Direction.EAST){pState = pState.setValue(CONNECTED_EAST, Boolean.FALSE);}
        }

        return pState;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext){
        BlockState state = this.defaultBlockState();
        BlockPos pos = blockPlaceContext.getClickedPos();

        if(connects(Direction.DOWN, blockPlaceContext.getClickedPos(), blockPlaceContext.getLevel())){state = state.setValue(CONNECTED_DOWN, Boolean.TRUE);}else{state = state.setValue(CONNECTED_DOWN, Boolean.FALSE);}
        if(connects(Direction.UP, blockPlaceContext.getClickedPos(), blockPlaceContext.getLevel())){state = state.setValue(CONNECTED_UP, Boolean.TRUE);}else{state = state.setValue(CONNECTED_UP, Boolean.FALSE);}
        if(connects(Direction.WEST, blockPlaceContext.getClickedPos(), blockPlaceContext.getLevel())){state = state.setValue(CONNECTED_WEST, Boolean.TRUE);}else{state = state.setValue(CONNECTED_WEST, Boolean.FALSE);}
        if(connects(Direction.EAST, blockPlaceContext.getClickedPos(), blockPlaceContext.getLevel())){state = state.setValue(CONNECTED_EAST, Boolean.TRUE);}else{state = state.setValue(CONNECTED_EAST, Boolean.FALSE);}
        if(connects(Direction.NORTH, blockPlaceContext.getClickedPos(), blockPlaceContext.getLevel())){state = state.setValue(CONNECTED_NORTH, Boolean.TRUE);}else{state = state.setValue(CONNECTED_NORTH, Boolean.FALSE);}
        if(connects(Direction.SOUTH, blockPlaceContext.getClickedPos(), blockPlaceContext.getLevel())){state = state.setValue(CONNECTED_SOUTH, Boolean.TRUE);}else{state = state.setValue(CONNECTED_SOUTH, Boolean.FALSE);}

        if(blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.DOWN)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get()) && blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.DOWN)).getValue(IS_INPUT_UP)){state = state.setValue(IS_INPUT_DOWN, Boolean.FALSE);}else{state = state.setValue(IS_INPUT_DOWN, Boolean.TRUE);}
        if(blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.UP)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get()) && blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.UP)).getValue(IS_INPUT_DOWN)){state = state.setValue(IS_INPUT_UP, Boolean.FALSE);}else{state = state.setValue(IS_INPUT_UP, Boolean.TRUE);}
        if(blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.WEST)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get()) && blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.WEST)).getValue(IS_INPUT_EAST)){state = state.setValue(IS_INPUT_WEST, Boolean.FALSE);}else{state = state.setValue(IS_INPUT_WEST, Boolean.TRUE);}
        if(blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.EAST)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get()) && blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.EAST)).getValue(IS_INPUT_WEST)){state = state.setValue(IS_INPUT_EAST, Boolean.FALSE);}else{state = state.setValue(IS_INPUT_EAST, Boolean.TRUE);}
        if(blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.NORTH)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get()) && blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.NORTH)).getValue(IS_INPUT_SOUTH)){state = state.setValue(IS_INPUT_NORTH, Boolean.FALSE);}else{state = state.setValue(IS_INPUT_NORTH, Boolean.TRUE);}
        if(blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.SOUTH)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get()) && blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.SOUTH)).getValue(IS_INPUT_NORTH)){state = state.setValue(IS_INPUT_SOUTH, Boolean.FALSE);}else{state = state.setValue(IS_INPUT_SOUTH, Boolean.TRUE);}

        return state;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(
                CONNECTED_UP,
                CONNECTED_DOWN,
                CONNECTED_NORTH,
                CONNECTED_SOUTH,
                CONNECTED_WEST,
                CONNECTED_EAST,
                IS_INPUT_UP,
                IS_INPUT_DOWN,
                IS_INPUT_SOUTH,
                IS_INPUT_NORTH,
                IS_INPUT_WEST,
                IS_INPUT_EAST);
    }
    public VoxelShape add(VoxelShape a, VoxelShape b){
        return Stream.of(a, b).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new StormlightPipeBlockEntity(blockPos, blockState);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.STORMLIGHT_PIPE_BLOCK_ENTITY.get(), StormlightPipeBlockEntity::tick);
    }
}
