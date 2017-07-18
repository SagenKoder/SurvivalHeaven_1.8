package info.nordbyen.survivalheaven.api.util;

import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

public enum Skull
{
    ARROW_LEFT("ARROW_LEFT", 0, "arrowleft", "MHF_ArrowLeft"), 
    ARROW_RIGHT("ARROW_RIGHT", 1, "arrowright", "MHF_ArrowRight"), 
    ARROW_UP("ARROW_UP", 2, "arrowup", "MHF_ArrowUp"), 
    ARROW_DOWN("ARROW_DOWN", 3, "arrowdown", "MHF_ArrowDown"), 
    QUESTION("QUESTION", 4, "question", "MHF_Question"), 
    EXCLAMATION("EXCLAMATION", 5, "exclamation", "MHF_Exclamation"), 
    CAMERA("CAMERA", 6, "camera", "FHG_Cam"), 
    ZOMBIE_PIGMAN("ZOMBIE_PIGMAN", 7, "pigman", "MHF_PigZombie"), 
    PIG("PIG", 8, "pig", "MHF_Pig"), 
    SHEEP("SHEEP", 9, "sheep", "MHF_Sheep"), 
    BLAZE("BLAZE", 10, "blaze", "MHF_Blaze"), 
    CHICKEN("CHICKEN", 11, "chicken", "MHF_Chicken"), 
    COW("COW", 12, "cow", "MHF_Cow"), 
    SLIME("SLIME", 13, "slime", "MHF_Slime"), 
    SPIDER("SPIDER", 14, "spider", "MHF_Spider"), 
    SQUID("SQUID", 15, "squid", "MHF_Squid"), 
    VILLAGER("VILLAGER", 16, "villager", "MHF_Villager"), 
    OCELOT("OCELOT", 17, "ocelot", "MHF_Ocelot"), 
    HEROBRINE("HEROBRINE", 18, "herobrine", "MHF_Herobrine"), 
    LAVA_SLIME("LAVA_SLIME", 19, "lavaslime", "MHF_LavaSlime"), 
    MOOSHROOM("MOOSHROOM", 20, "mooshroom", "MHF_MushroomCow"), 
    GOLEM("GOLEM", 21, "golem", "MHF_Golem"), 
    GHAST("GHAST", 22, "ghast", "MHF_Ghast"), 
    ENDERMAN("ENDERMAN", 23, "enderman", "MHF_Enderman"), 
    CAVE_SPIDER("CAVE_SPIDER", 24, "cavespider", "MHF_CaveSpider"), 
    CACTUS("CACTUS", 25, "cactus", "MHF_Cactus"), 
    CAKE("CAKE", 26, "cake", "MHF_Cake"), 
    CHEST("CHEST", 27, "chest", "MHF_Chest"), 
    MELON("MELON", 28, "melon", "MHF_Melon"), 
    LOG("LOG", 29, "log", "MHF_OakLog"), 
    PUMPKIN("PUMPKIN", 30, "pumpkin", "MHF_Pumpkin"), 
    TNT("TNT", 31, "tnt", "MHF_TNT"), 
    DYNAMITE("DYNAMITE", 32, "dynamite", "MHF_TNT2");
    
    String skull_name;
    String skull_id;
    
    private Skull(final String s, final int n, final String name, final String id) {
        this.skull_name = name;
        this.skull_id = id;
    }
    
    public String getSkullId() {
        return this.skull_id;
    }
    
    public static Skull[] getBonus() {
        final Skull[] sk = { Skull.ARROW_LEFT, Skull.ARROW_RIGHT, Skull.ARROW_UP, Skull.ARROW_DOWN, Skull.CAMERA, Skull.EXCLAMATION, Skull.QUESTION };
        return sk;
    }
    
    public static Skull[] getBlocks() {
        final Skull[] sk = { Skull.CACTUS, Skull.CAKE, Skull.CHEST, Skull.MELON, Skull.LOG, Skull.PUMPKIN, Skull.TNT, Skull.DYNAMITE };
        return sk;
    }
    
    public static Skull[] getCreatures() {
        final Skull[] sk = { Skull.ZOMBIE_PIGMAN, Skull.PIG, Skull.SHEEP, Skull.SLIME, Skull.LAVA_SLIME, Skull.SPIDER, Skull.CAVE_SPIDER, Skull.OCELOT, Skull.GHAST, Skull.ENDERMAN, Skull.HEROBRINE, Skull.MOOSHROOM, Skull.GOLEM, Skull.ENDERMAN, Skull.BLAZE, Skull.CHICKEN, Skull.COW, Skull.SQUID, Skull.VILLAGER };
        return sk;
    }
    
    public static Skull[] getSkulls() {
        final Skull[] sk = { Skull.ARROW_LEFT, Skull.ARROW_RIGHT, Skull.ARROW_UP, Skull.ARROW_DOWN, Skull.CACTUS, Skull.CAKE, Skull.CHEST, Skull.MELON, Skull.LOG, Skull.PUMPKIN, Skull.TNT, Skull.DYNAMITE, Skull.ZOMBIE_PIGMAN, Skull.PIG, Skull.SHEEP, Skull.SLIME, Skull.OCELOT, Skull.GHAST, Skull.LAVA_SLIME, Skull.SPIDER, Skull.CAVE_SPIDER, Skull.HEROBRINE, Skull.MOOSHROOM, Skull.GOLEM, Skull.ENDERMAN, Skull.BLAZE, Skull.CHICKEN, Skull.COW, Skull.SQUID, Skull.VILLAGER, Skull.CAMERA, Skull.QUESTION, Skull.EXCLAMATION };
        return sk;
    }
    
    public ItemStack getItem() {
        final ItemStack s = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final SkullMeta sm = (SkullMeta)s.getItemMeta();
        sm.setOwner(this.skull_id);
        s.setItemMeta((ItemMeta)sm);
        return s;
    }
}
