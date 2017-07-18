package info.nordbyen.survivalheaven.subplugins.uendeligdropper;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.*;

public class Util
{
    private static final Set<Integer> STANDING_MATERIALS;
    private static final HashSet<Byte> STANDING_MATERIALS_TARGET;
    
    static {
        STANDING_MATERIALS = new HashSet<Integer>();
        STANDING_MATERIALS_TARGET = new HashSet<Byte>();
        Util.STANDING_MATERIALS.add(Material.AIR.getId());
        Util.STANDING_MATERIALS.add(Material.SAPLING.getId());
        Util.STANDING_MATERIALS.add(Material.POWERED_RAIL.getId());
        Util.STANDING_MATERIALS.add(Material.DETECTOR_RAIL.getId());
        Util.STANDING_MATERIALS.add(Material.LONG_GRASS.getId());
        Util.STANDING_MATERIALS.add(Material.DEAD_BUSH.getId());
        Util.STANDING_MATERIALS.add(Material.YELLOW_FLOWER.getId());
        Util.STANDING_MATERIALS.add(Material.RED_ROSE.getId());
        Util.STANDING_MATERIALS.add(Material.BROWN_MUSHROOM.getId());
        Util.STANDING_MATERIALS.add(Material.RED_MUSHROOM.getId());
        Util.STANDING_MATERIALS.add(Material.TORCH.getId());
        Util.STANDING_MATERIALS.add(Material.REDSTONE_WIRE.getId());
        Util.STANDING_MATERIALS.add(Material.SEEDS.getId());
        Util.STANDING_MATERIALS.add(Material.SIGN_POST.getId());
        Util.STANDING_MATERIALS.add(Material.WOODEN_DOOR.getId());
        Util.STANDING_MATERIALS.add(Material.LADDER.getId());
        Util.STANDING_MATERIALS.add(Material.RAILS.getId());
        Util.STANDING_MATERIALS.add(Material.WALL_SIGN.getId());
        Util.STANDING_MATERIALS.add(Material.LEVER.getId());
        Util.STANDING_MATERIALS.add(Material.STONE_PLATE.getId());
        Util.STANDING_MATERIALS.add(Material.IRON_DOOR_BLOCK.getId());
        Util.STANDING_MATERIALS.add(Material.WOOD_PLATE.getId());
        Util.STANDING_MATERIALS.add(Material.REDSTONE_TORCH_OFF.getId());
        Util.STANDING_MATERIALS.add(Material.REDSTONE_TORCH_ON.getId());
        Util.STANDING_MATERIALS.add(Material.STONE_BUTTON.getId());
        Util.STANDING_MATERIALS.add(Material.SNOW.getId());
        Util.STANDING_MATERIALS.add(Material.SUGAR_CANE_BLOCK.getId());
        Util.STANDING_MATERIALS.add(Material.DIODE_BLOCK_OFF.getId());
        Util.STANDING_MATERIALS.add(Material.DIODE_BLOCK_ON.getId());
        Util.STANDING_MATERIALS.add(Material.TRAP_DOOR.getId());
        Util.STANDING_MATERIALS.add(Material.PUMPKIN_STEM.getId());
        Util.STANDING_MATERIALS.add(Material.MELON_STEM.getId());
        Util.STANDING_MATERIALS.add(Material.VINE.getId());
        Util.STANDING_MATERIALS.add(Material.FENCE_GATE.getId());
        Util.STANDING_MATERIALS.add(Material.WATER_LILY.getId());
        Util.STANDING_MATERIALS.add(Material.NETHER_FENCE.getId());
        Util.STANDING_MATERIALS.add(Material.NETHER_WARTS.getId());
        Util.STANDING_MATERIALS.add(Material.TRIPWIRE_HOOK.getId());
        Util.STANDING_MATERIALS.add(Material.TRIPWIRE.getId());
        for (final Integer integer : Util.STANDING_MATERIALS) {
            Util.STANDING_MATERIALS_TARGET.add((byte)(Object)integer);
        }
        Util.STANDING_MATERIALS_TARGET.add((byte)Material.WATER.getId());
        Util.STANDING_MATERIALS_TARGET.add((byte)Material.STATIONARY_WATER.getId());
    }
    
    public static Block getBlockTarget(final LivingEntity entity) {
        final Block block = entity.getTargetBlock((HashSet)Util.STANDING_MATERIALS_TARGET, 300);
        return block;
    }
    
    public static boolean getSign(final Block block) {
        if (block.getRelative(BlockFace.EAST).getType() == Material.WALL_SIGN) {
            final Sign sign = (Sign)block.getRelative(BlockFace.EAST).getState();
            if (sign.getLines()[0].equalsIgnoreCase("[infdisp]")) {
                return true;
            }
        }
        if (block.getRelative(BlockFace.WEST).getType() == Material.WALL_SIGN) {
            final Sign sign = (Sign)block.getRelative(BlockFace.WEST).getState();
            if (sign.getLines()[0].equalsIgnoreCase("[infdisp]")) {
                return true;
            }
        }
        if (block.getRelative(BlockFace.NORTH).getType() == Material.WALL_SIGN) {
            final Sign sign = (Sign)block.getRelative(BlockFace.NORTH).getState();
            if (sign.getLines()[0].equalsIgnoreCase("[infdisp]")) {
                return true;
            }
        }
        if (block.getRelative(BlockFace.SOUTH).getType() == Material.WALL_SIGN) {
            final Sign sign = (Sign)block.getRelative(BlockFace.SOUTH).getState();
            if (sign.getLines()[0].equalsIgnoreCase("[infdisp]")) {
                return true;
            }
        }
        return false;
    }
    
    public static Location getTarget(final LivingEntity entity) {
        final Block block = entity.getTargetBlock((HashSet)Util.STANDING_MATERIALS_TARGET, 300);
        return block.getLocation();
    }
}
