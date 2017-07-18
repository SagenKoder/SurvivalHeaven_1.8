package info.nordbyen.survivalheaven.subplugins.shop;

import info.nordbyen.survivalheaven.api.subplugin.*;
import java.util.*;
import org.bukkit.*;

public class ShopHandler extends SubPlugin
{
    ArrayList<Shop> shops;
    
    public ShopHandler(final String name) {
        super(name);
        this.shops = new ArrayList<Shop>();
    }
    
    public void disable() {
    }
    
    public void enable() {
    }
    
    public static class Shop
    {
        ArrayList<ShopItem> items;
        
        public Shop() {
            this.items = new ArrayList<ShopItem>();
        }
        
        void addItem(final ShopItem item) {
            this.items.add(item);
        }
        
        ShopItem getItem(final String name) {
            for (final ShopItem i : this.items) {
                if (i.getName().equalsIgnoreCase(name)) {
                    return i;
                }
            }
            return null;
        }
        
        public static class ShopItem
        {
            Material item;
            String name;
            int price_per;
            
            private ShopItem(final Material item, final String name, final int price_per) {
                this.item = item;
                this.name = name;
                this.price_per = price_per;
            }
            
            int getIndividualPrice() {
                return this.price_per;
            }
            
            String getName() {
                return this.name;
            }
            
            Material getType() {
                return this.item;
            }
        }
    }
}
