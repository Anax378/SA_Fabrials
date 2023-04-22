package com.anax.sa_fabrials.block.custom;

import com.anax.sa_fabrials.block.ModBlocks;
import com.anax.sa_fabrials.block.entity.ModBlockEntities;
import com.anax.sa_fabrials.block.entity.custom.StormlightPipeBlockEntity;
import com.anax.sa_fabrials.block.entity.custom.TopazCrystalBlockEntity;
import com.anax.sa_fabrials.util.stormlight.StormlightStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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
        if(level.getBlockState(pos.relative(direction)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get())){return true;}
        if(level.getBlockEntity(pos.relative(direction)) != null && level.getBlockEntity(pos.relative(direction)).getCapability(StormlightStorage.STORMLIGHT_STORAGE).isPresent()){return true;}
        return false;
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

        if(blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.DOWN)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get()) && !blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.DOWN)).getValue(IS_INPUT_UP)){state = state.setValue(IS_INPUT_DOWN, Boolean.TRUE);}else{state = state.setValue(IS_INPUT_DOWN, Boolean.FALSE);}
        if(blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.UP)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get()) && !blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.UP)).getValue(IS_INPUT_DOWN)){state = state.setValue(IS_INPUT_UP, Boolean.TRUE);}else{state = state.setValue(IS_INPUT_UP, Boolean.FALSE);}
        if(blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.WEST)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get()) && !blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.WEST)).getValue(IS_INPUT_EAST)){state = state.setValue(IS_INPUT_WEST, Boolean.TRUE);}else{state = state.setValue(IS_INPUT_WEST, Boolean.FALSE);}
        if(blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.EAST)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get()) && !blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.EAST)).getValue(IS_INPUT_WEST)){state = state.setValue(IS_INPUT_EAST, Boolean.TRUE);}else{state = state.setValue(IS_INPUT_EAST, Boolean.FALSE);}
        if(blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.NORTH)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get()) && !blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.NORTH)).getValue(IS_INPUT_SOUTH)){state = state.setValue(IS_INPUT_NORTH, Boolean.TRUE);}else{state = state.setValue(IS_INPUT_NORTH, Boolean.FALSE);}
        if(blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.SOUTH)).is(ModBlocks.STORMLIGHT_PIPE_BLOCK.get()) && !blockPlaceContext.getLevel().getBlockState(pos.relative(Direction.SOUTH)).getValue(IS_INPUT_NORTH)){state = state.setValue(IS_INPUT_SOUTH, Boolean.TRUE);}else{state = state.setValue(IS_INPUT_SOUTH, Boolean.FALSE);}

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
    public static Direction getClickedDirection (Vec3 loc, VoxelShape shape, BlockPos pos){
        AABB bounds = shape.bounds();
        Vec3 rLoc = new Vec3(loc.x-pos.getX(), loc.y-pos.getY(), loc.z-pos.getZ());

        if(rLoc.z > bounds.maxZ){return Direction.SOUTH;}
        if(rLoc.z < bounds.minZ){return Direction.NORTH;}
        if(rLoc.x > bounds.maxX){return Direction.EAST;}
        if(rLoc.x < bounds.minX){return Direction.WEST;}
        if(rLoc.y > bounds.maxY){return Direction.UP;}
        if(rLoc.y < bounds.minY){return Direction.DOWN;}

        System.out.println("its null");
        return null;

    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if(!level.isClientSide() && interactionHand == InteractionHand.MAIN_HAND) {
            Direction direction = getClickedDirection(blockHitResult.getLocation(), CORE, blockPos);
            System.out.println(direction);
            if (direction == null) {
                return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
            }
            if (direction == Direction.SOUTH) {
                level.setBlock(blockPos, blockState.setValue(IS_INPUT_SOUTH, !blockState.getValue(IS_INPUT_SOUTH)), 3);
            }
            if (direction == Direction.NORTH) {
                level.setBlock(blockPos, blockState.setValue(IS_INPUT_NORTH, !blockState.getValue(IS_INPUT_NORTH)), 3);
            }
            if (direction == Direction.EAST) {
                level.setBlock(blockPos, blockState.setValue(IS_INPUT_EAST, !blockState.getValue(IS_INPUT_EAST)), 3);
            }
            if (direction == Direction.WEST) {
                level.setBlock(blockPos, blockState.setValue(IS_INPUT_WEST, !blockState.getValue(IS_INPUT_WEST)), 3);
            }
            if (direction == Direction.UP) {
                level.setBlock(blockPos, blockState.setValue(IS_INPUT_UP, !blockState.getValue(IS_INPUT_UP)), 3);
            }
            if (direction == Direction.DOWN) {
                level.setBlock(blockPos, blockState.setValue(IS_INPUT_DOWN, !blockState.getValue(IS_INPUT_DOWN)), 3);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.STORMLIGHT_PIPE_BLOCK_ENTITY.get(), StormlightPipeBlockEntity::tick);
    }
}
