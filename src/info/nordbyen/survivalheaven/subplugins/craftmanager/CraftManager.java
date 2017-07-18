package info.nordbyen.survivalheaven.subplugins.craftmanager;

import info.nordbyen.survivalheaven.api.subplugin.*;
import org.bukkit.material.*;
import org.bukkit.enchantments.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import info.nordbyen.survivalheaven.*;

public class CraftManager extends SubPlugin
{
    MaterialData wool1;
    MaterialData wool2;
    MaterialData wool3;
    MaterialData wool4;
    MaterialData wool5;
    MaterialData wool6;
    MaterialData wool7;
    MaterialData wool8;
    MaterialData wool9;
    MaterialData wool10;
    MaterialData wool11;
    MaterialData wool12;
    MaterialData wool13;
    MaterialData wool14;
    MaterialData wool15;
    MaterialData smoothb;
    ItemStack smoothbrickmossy;
    ItemStack smoothbrickcrackeled;
    ItemStack smoothbricksculpted;
    ItemStack mossywall;
    
    public CraftManager(final String name) {
        super(name);
        this.wool1 = new MaterialData(Material.WOOL, (byte)1);
        this.wool2 = new MaterialData(Material.WOOL, (byte)2);
        this.wool3 = new MaterialData(Material.WOOL, (byte)3);
        this.wool4 = new MaterialData(Material.WOOL, (byte)4);
        this.wool5 = new MaterialData(Material.WOOL, (byte)5);
        this.wool6 = new MaterialData(Material.WOOL, (byte)6);
        this.wool7 = new MaterialData(Material.WOOL, (byte)7);
        this.wool8 = new MaterialData(Material.WOOL, (byte)8);
        this.wool9 = new MaterialData(Material.WOOL, (byte)9);
        this.wool10 = new MaterialData(Material.WOOL, (byte)10);
        this.wool11 = new MaterialData(Material.WOOL, (byte)11);
        this.wool12 = new MaterialData(Material.WOOL, (byte)12);
        this.wool13 = new MaterialData(Material.WOOL, (byte)13);
        this.wool14 = new MaterialData(Material.WOOL, (byte)14);
        this.wool15 = new MaterialData(Material.WOOL, (byte)15);
        this.smoothb = new MaterialData(Material.SMOOTH_BRICK, (byte)0);
        this.smoothbrickmossy = new ItemStack(Material.SMOOTH_BRICK, 1, (short)1, (byte)1);
        this.smoothbrickcrackeled = new ItemStack(Material.SMOOTH_BRICK, 1, (short)1, (byte)2);
        this.smoothbricksculpted = new ItemStack(Material.SMOOTH_BRICK, 1, (short)1, (byte)3);
        this.mossywall = new ItemStack(Material.COBBLE_WALL, 1, (short)1, (byte)1);
    }
    
    public void enable() {
        final ItemStack epeeq = new ItemStack(Material.IRON_SWORD, 1);
        final ItemMeta im = epeeq.getItemMeta();
        im.setDisplayName("Quartz Sword");
        im.addEnchant(Enchantment.KNOCKBACK, 2, true);
        im.addEnchant(Enchantment.DURABILITY, 10, true);
        im.setLore((List)Arrays.asList("This is a quartz sword!"));
        epeeq.setItemMeta(im);
        final ItemStack piocheq = new ItemStack(Material.IRON_PICKAXE, 1);
        final ItemMeta im2 = piocheq.getItemMeta();
        im2.setDisplayName("Quartz Pickaxe");
        im2.addEnchant(Enchantment.DIG_SPEED, 2, true);
        im2.addEnchant(Enchantment.DURABILITY, 10, true);
        im2.setLore((List)Arrays.asList("This is a quartz pickaxe!"));
        piocheq.setItemMeta(im2);
        final ItemStack peleq = new ItemStack(Material.IRON_SPADE, 1);
        final ItemMeta im3 = peleq.getItemMeta();
        im3.setDisplayName("Quartz Spade");
        im3.addEnchant(Enchantment.DIG_SPEED, 2, true);
        im3.addEnchant(Enchantment.DURABILITY, 10, true);
        im3.setLore((List)Arrays.asList("This is a quartz spade!"));
        peleq.setItemMeta(im3);
        final ItemStack hacheq = new ItemStack(Material.IRON_AXE, 1);
        final ItemMeta im4 = hacheq.getItemMeta();
        im4.setDisplayName("Quartz Axe");
        im4.addEnchant(Enchantment.DIG_SPEED, 2, true);
        im4.addEnchant(Enchantment.DURABILITY, 10, true);
        im4.setLore((List)Arrays.asList("This is a quartz axe!"));
        hacheq.setItemMeta(im4);
        final ItemStack bottesq = new ItemStack(Material.IRON_BOOTS, 1);
        final ItemMeta im5 = bottesq.getItemMeta();
        im5.setDisplayName("Quartz Boots");
        im5.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
        im5.addEnchant(Enchantment.DURABILITY, 3, true);
        im5.setLore((List)Arrays.asList("This is quartz boots!"));
        bottesq.setItemMeta(im5);
        final ItemStack jambesq = new ItemStack(Material.IRON_LEGGINGS, 1);
        final ItemMeta im6 = jambesq.getItemMeta();
        im6.setDisplayName("Quartz Leggings");
        im6.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
        im6.addEnchant(Enchantment.DURABILITY, 3, true);
        im6.setLore((List)Arrays.asList("This is quartz leggings!"));
        jambesq.setItemMeta(im6);
        final ItemStack torceq = new ItemStack(Material.IRON_CHESTPLATE, 1);
        final ItemMeta im7 = torceq.getItemMeta();
        im7.setDisplayName("Quartz Chestplate");
        im7.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
        im7.addEnchant(Enchantment.DURABILITY, 3, true);
        im7.setLore((List)Arrays.asList("This is a quartz chestplate!"));
        torceq.setItemMeta(im7);
        final ItemStack helmetq = new ItemStack(Material.IRON_HELMET, 1);
        final ItemMeta im8 = helmetq.getItemMeta();
        im8.setDisplayName("Quartz Helmet");
        im8.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
        im8.addEnchant(Enchantment.DURABILITY, 3, true);
        im8.setLore((List)Arrays.asList("This is a quartz helmet!"));
        helmetq.setItemMeta(im8);
        final ShapedRecipe epeequartz = new ShapedRecipe(epeeq);
        epeequartz.shape(new String[] { "-Q-", "-Q-", "-B-" });
        epeequartz.setIngredient('Q', Material.QUARTZ);
        epeequartz.setIngredient('B', Material.STICK);
        final ShapedRecipe piochequartz = new ShapedRecipe(piocheq);
        piochequartz.shape(new String[] { "QQQ", "-B-", "-B-" });
        piochequartz.setIngredient('Q', Material.QUARTZ);
        piochequartz.setIngredient('B', Material.STICK);
        final ShapedRecipe hachequartz1 = new ShapedRecipe(hacheq);
        hachequartz1.shape(new String[] { "-QQ", "-BQ", "-B-" });
        hachequartz1.setIngredient('Q', Material.QUARTZ);
        hachequartz1.setIngredient('B', Material.STICK);
        final ShapedRecipe hachequartz2 = new ShapedRecipe(hacheq);
        hachequartz2.shape(new String[] { "QQ-", "QB-", "-B-" });
        hachequartz2.setIngredient('Q', Material.QUARTZ);
        hachequartz2.setIngredient('B', Material.STICK);
        final ShapedRecipe pelequartz = new ShapedRecipe(peleq);
        pelequartz.shape(new String[] { "-Q-", "-B-", "-B-" });
        pelequartz.setIngredient('Q', Material.QUARTZ);
        pelequartz.setIngredient('B', Material.STICK);
        final ShapedRecipe bootsq1 = new ShapedRecipe(bottesq);
        bootsq1.shape(new String[] { "---", "Q-Q", "Q-Q" });
        bootsq1.setIngredient('Q', Material.QUARTZ);
        final ShapedRecipe bootsq2 = new ShapedRecipe(bottesq);
        bootsq2.shape(new String[] { "Q-Q", "Q-Q", "---" });
        bootsq2.setIngredient('Q', Material.QUARTZ);
        final ShapedRecipe leggingsq = new ShapedRecipe(jambesq);
        leggingsq.shape(new String[] { "QQQ", "Q-Q", "Q-Q" });
        leggingsq.setIngredient('Q', Material.QUARTZ);
        final ShapedRecipe chestplateq = new ShapedRecipe(torceq);
        chestplateq.shape(new String[] { "Q-Q", "QQQ", "QQQ" });
        chestplateq.setIngredient('Q', Material.QUARTZ);
        final ShapedRecipe chapeauq = new ShapedRecipe(helmetq);
        chapeauq.shape(new String[] { "QQQ", "Q-Q", "---" });
        chapeauq.setIngredient('Q', Material.QUARTZ);
        Bukkit.getServer().addRecipe((Recipe)epeequartz);
        Bukkit.getServer().addRecipe((Recipe)piochequartz);
        Bukkit.getServer().addRecipe((Recipe)hachequartz1);
        Bukkit.getServer().addRecipe((Recipe)hachequartz2);
        Bukkit.getServer().addRecipe((Recipe)pelequartz);
        Bukkit.getServer().addRecipe((Recipe)bootsq1);
        Bukkit.getServer().addRecipe((Recipe)bootsq2);
        Bukkit.getServer().addRecipe((Recipe)leggingsq);
        Bukkit.getServer().addRecipe((Recipe)chestplateq);
        Bukkit.getServer().addRecipe((Recipe)chapeauq);
        final ShapedRecipe herbe = new ShapedRecipe(new ItemStack(Material.GRASS)).shape(new String[] { "-G-", "GHG", "-G-" }).setIngredient('G', Material.VINE).setIngredient('H', Material.DIRT);
        final ShapedRecipe mycelium = new ShapedRecipe(new ItemStack(Material.MYCEL)).shape(new String[] { "-M-", "-H-", "---" }).setIngredient('M', Material.RED_MUSHROOM).setIngredient('H', Material.DIRT);
        final ShapedRecipe mycelium2 = new ShapedRecipe(new ItemStack(Material.MYCEL)).shape(new String[] { "M--", "H--", "---" }).setIngredient('M', Material.RED_MUSHROOM).setIngredient('H', Material.DIRT);
        final ShapedRecipe mycelium3 = new ShapedRecipe(new ItemStack(Material.MYCEL)).shape(new String[] { "--M", "--H", "---" }).setIngredient('M', Material.RED_MUSHROOM).setIngredient('H', Material.DIRT);
        final ShapedRecipe mycelium4 = new ShapedRecipe(new ItemStack(Material.MYCEL)).shape(new String[] { "---", "-M-", "-H-" }).setIngredient('M', Material.RED_MUSHROOM).setIngredient('H', Material.DIRT);
        final ShapedRecipe mycelium5 = new ShapedRecipe(new ItemStack(Material.MYCEL)).shape(new String[] { "---", "M--", "H--" }).setIngredient('M', Material.RED_MUSHROOM).setIngredient('H', Material.DIRT);
        final ShapedRecipe mycelium6 = new ShapedRecipe(new ItemStack(Material.MYCEL)).shape(new String[] { "---", "--M", "--H" }).setIngredient('M', Material.RED_MUSHROOM).setIngredient('H', Material.DIRT);
        final ShapedRecipe bouteilleex = new ShapedRecipe(new ItemStack(Material.EXP_BOTTLE)).shape(new String[] { "GBG", "DFD", "EEE" }).setIngredient('G', Material.GOLD_NUGGET).setIngredient('B', Material.WOOD_BUTTON).setIngredient('D', Material.DIAMOND).setIngredient('F', Material.GLASS_BOTTLE).setIngredient('E', Material.EMERALD);
        final ShapedRecipe toile = new ShapedRecipe(new ItemStack(Material.WEB)).shape(new String[] { "F-F", "-F-", "F-F" }).setIngredient('F', Material.STRING);
        final ShapelessRecipe lainedt1 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool1);
        final ShapelessRecipe lainedt2 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool2);
        final ShapelessRecipe lainedt3 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool3);
        final ShapelessRecipe lainedt4 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool4);
        final ShapelessRecipe lainedt5 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool5);
        final ShapelessRecipe lainedt6 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool6);
        final ShapelessRecipe lainedt7 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool7);
        final ShapelessRecipe lainedt8 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool8);
        final ShapelessRecipe lainedt9 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool9);
        final ShapelessRecipe lainedt10 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool10);
        final ShapelessRecipe lainedt11 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool11);
        final ShapelessRecipe lainedt12 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool12);
        final ShapelessRecipe lainedt13 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool13);
        final ShapelessRecipe lainedt14 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool14);
        final ShapelessRecipe lainedt15 = new ShapelessRecipe(new ItemStack(Material.WOOL)).addIngredient(Material.WATER_BUCKET).addIngredient(this.wool15);
        final ShapedRecipe cobblef = new ShapedRecipe(new ItemStack(Material.MOSSY_COBBLESTONE)).shape(new String[] { "-F-", "FCF", "-F-" }).setIngredient('F', Material.VINE).setIngredient('C', Material.COBBLESTONE);
        final ShapedRecipe cobblewallf = new ShapedRecipe(this.mossywall).shape(new String[] { "-F-", "FCF", "-F-" }).setIngredient('F', Material.VINE).setIngredient('C', Material.COBBLE_WALL);
        final ShapedRecipe gravel = new ShapedRecipe(new ItemStack(Material.GRAVEL, 7)).shape(new String[] { "-F-", "FSF", "-F-" }).setIngredient('F', Material.CLAY).setIngredient('S', Material.SAND);
        final ShapedRecipe emerald = new ShapedRecipe(new ItemStack(Material.EMERALD)).shape(new String[] { "-V-", "VSV", "-V-" }).setIngredient('V', Material.GOLD_NUGGET).setIngredient('S', Material.DIAMOND);
        Bukkit.getServer().addRecipe((Recipe)herbe);
        Bukkit.getServer().addRecipe((Recipe)mycelium);
        Bukkit.getServer().addRecipe((Recipe)mycelium2);
        Bukkit.getServer().addRecipe((Recipe)mycelium3);
        Bukkit.getServer().addRecipe((Recipe)mycelium4);
        Bukkit.getServer().addRecipe((Recipe)mycelium5);
        Bukkit.getServer().addRecipe((Recipe)mycelium6);
        Bukkit.getServer().addRecipe((Recipe)bouteilleex);
        Bukkit.getServer().addRecipe((Recipe)toile);
        Bukkit.getServer().addRecipe((Recipe)lainedt1);
        Bukkit.getServer().addRecipe((Recipe)lainedt2);
        Bukkit.getServer().addRecipe((Recipe)lainedt3);
        Bukkit.getServer().addRecipe((Recipe)lainedt4);
        Bukkit.getServer().addRecipe((Recipe)lainedt5);
        Bukkit.getServer().addRecipe((Recipe)lainedt6);
        Bukkit.getServer().addRecipe((Recipe)lainedt7);
        Bukkit.getServer().addRecipe((Recipe)lainedt8);
        Bukkit.getServer().addRecipe((Recipe)lainedt9);
        Bukkit.getServer().addRecipe((Recipe)lainedt10);
        Bukkit.getServer().addRecipe((Recipe)lainedt11);
        Bukkit.getServer().addRecipe((Recipe)lainedt12);
        Bukkit.getServer().addRecipe((Recipe)lainedt13);
        Bukkit.getServer().addRecipe((Recipe)lainedt14);
        Bukkit.getServer().addRecipe((Recipe)lainedt15);
        Bukkit.getServer().addRecipe((Recipe)cobblef);
        Bukkit.getServer().addRecipe((Recipe)cobblewallf);
        Bukkit.getServer().addRecipe((Recipe)gravel);
        Bukkit.getServer().addRecipe((Recipe)emerald);
    }
    
    public void disable() {
        SH.getPlugin().getServer().clearRecipes();
    }
}