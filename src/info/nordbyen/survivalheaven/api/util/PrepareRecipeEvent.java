package info.nordbyen.survivalheaven.api.util;

import org.bukkit.inventory.*;
import org.bukkit.event.*;

public class PrepareRecipeEvent extends Event
{
    private ItemStack result;
    private final ItemStack[] inv;
    private final String name;
    private static HandlerList handlers;
    
    static {
        PrepareRecipeEvent.handlers = new HandlerList();
    }
    
    public static HandlerList getHandlerList() {
        return PrepareRecipeEvent.handlers;
    }
    
    public PrepareRecipeEvent(final ItemStack[] inventory, final ItemStack result, final String name) {
        this.inv = inventory;
        this.setResult(result);
        this.name = name;
    }
    
    public HandlerList getHandlers() {
        return PrepareRecipeEvent.handlers;
    }
    
    public ItemStack[] getInventory() {
        return this.inv;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ItemStack getResult() {
        return this.result;
    }
    
    public void setResult(final ItemStack result) {
        this.result = result;
    }
}
