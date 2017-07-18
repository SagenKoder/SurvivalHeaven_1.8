package info.nordbyen.survivalheaven;

import org.bukkit.plugin.java.*;
import java.util.*;
import org.bukkit.block.*;
import info.nordbyen.survivalheaven.api.command.*;
import info.nordbyen.survivalheaven.subplugins.blockdata.*;
import info.nordbyen.survivalheaven.api.mysql.*;
import info.nordbyen.survivalheaven.api.wand.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import info.nordbyen.survivalheaven.api.regions.*;
import info.nordbyen.survivalheaven.subplugins.groupmanager.*;
import info.nordbyen.survivalheaven.subplugins.homes.*;
import info.nordbyen.survivalheaven.subplugins.ban.*;
import info.nordbyen.survivalheaven.subplugins.mysql.*;
import info.nordbyen.survivalheaven.subplugins.rankmanager.*;
import info.nordbyen.survivalheaven.subplugins.wand.*;
import org.bukkit.*;
import java.io.*;
import java.net.*;
import java.nio.channels.*;
import info.nordbyen.survivalheaven.subplugins.subplugin.*;
import info.nordbyen.survivalheaven.subplugins.DenyPlayerMode.*;
import info.nordbyen.survivalheaven.api.subplugin.*;
import info.nordbyen.survivalheaven.subplugins.merchant.*;
import info.nordbyen.survivalheaven.subplugins.shop.*;
import info.nordbyen.survivalheaven.subplugins.title.*;
import info.nordbyen.survivalheaven.subplugins.bitly.plugin.*;
import info.nordbyen.survivalheaven.subplugins.blockprotection.*;
import info.nordbyen.survivalheaven.subplugins.commands.*;
import info.nordbyen.survivalheaven.subplugins.regions.*;
import info.nordbyen.survivalheaven.subplugins.craftmanager.*;
import info.nordbyen.survivalheaven.subplugins.votifyer.*;
import info.nordbyen.survivalheaven.subplugins.autobroadcast.*;
import info.nordbyen.survivalheaven.subplugins.uendeligdropper.*;
import info.nordbyen.survivalheaven.subplugins.playerdata.*;
import info.nordbyen.survivalheaven.subplugins.particles.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import info.nordbyen.survivalheaven.subplugins.chairs.*;
import info.nordbyen.survivalheaven.subplugins.serverutil.listeners.*;

public class SH extends JavaPlugin implements ISH
{
    public static ArrayList<String> mutedPlayers;
    public static HashMap<String, ParticleEffect> particles;
    public static ArrayList<String> pokes;
    public static HashSet<Block> blocks;
    public static ArrayList<String> fallprotected;
    public static final String MOTTO;
    public static final String PREFIX;
    public static final String PATH_TO_CONFIG_FOLDER = "./plugins/SurvivalHeaven/";
    public static final String NAME;
    private static final boolean DEBUG = false;
    private static final boolean MYSQL_DEBUG = false;
    private static ISH iSurvivalHeaven;
    private static JavaPlugin plugin;
    public HashMap<String, AbstractCommand> commands;
    private String version;
    private String name;
    private IBlockManager blockManager;
    private IMysqlManager mysqlManager;
    private IWandManager wandManager;
    private IPlayerDataManager playerDataManager;
    private IRankManager rankManager;
    private ISubPluginManager subpluginManager;
    private IAnnoSubPluginManager annoSubPluginManager;
    private IRegionManager regionManager;
    private FriendManager friendManager;
    private HomeManager homeManager;
    private BanManager banManager;
    
    static {
        SH.mutedPlayers = new ArrayList<String>();
        SH.particles = new HashMap<String, ParticleEffect>();
        SH.pokes = new ArrayList<String>();
        SH.blocks = new HashSet<Block>();
        SH.fallprotected = new ArrayList<String>();
        MOTTO = ChatColor.LIGHT_PURPLE + "Skapt for spillerne";
        PREFIX = ChatColor.RED + "S" + ChatColor.GRAY + "H ";
        NAME = ChatColor.RED + "Survival" + ChatColor.GRAY + "Heaven" + ChatColor.LIGHT_PURPLE + "-ALPHA " + ChatColor.RESET;
    }
    
    public SH() {
        this.commands = new HashMap<String, AbstractCommand>();
        this.version = null;
        this.name = null;
    }
    
    public static ISH getManager() {
        return SH.iSurvivalHeaven;
    }
    
    public static JavaPlugin getPlugin() {
        return SH.plugin;
    }
    
    private void disableSubPlugins() {
        this.getSubPluginManager().disableAll();
        this.getAnnoSubPluginManager().disableAll();
    }
    
    private void enableSubPlugins() {
        this.getSubPluginManager().enableAll();
        this.getAnnoSubPluginManager().enableAll();
    }
    
    public IAnnoSubPluginManager getAnnoSubPluginManager() {
        if (this.annoSubPluginManager == null) {
            this.annoSubPluginManager = new AnnoSubPluginManager();
        }
        return this.annoSubPluginManager;
    }
    
    public IBlockManager getBlockManager() {
        if (this.blockManager == null) {
            this.blockManager = new BlockManager();
        }
        return this.blockManager;
    }
    
    public FriendManager getFriendManager() {
        if (this.friendManager == null) {
            this.friendManager = new FriendManager();
        }
        return this.friendManager;
    }
    
    public HomeManager getHomeManager() {
        if (this.homeManager == null) {
            this.homeManager = new HomeManager();
        }
        return this.homeManager;
    }
    
    public IMysqlManager getMysqlManager() {
        if (this.mysqlManager == null) {
            this.mysqlManager = new MysqlManager();
        }
        return this.mysqlManager;
    }
    
    public IPlayerDataManager getPlayerDataManager() {
        if (this.playerDataManager == null) {
            this.playerDataManager = new PlayerDataManager();
        }
        return this.playerDataManager;
    }
    
    public String getPluginName() {
        return this.name;
    }
    
    public IRankManager getRankManager() {
        if (this.rankManager == null) {
            this.rankManager = new RankManager();
        }
        return this.rankManager;
    }
    
    public IRegionManager getRegionManager() {
        if (this.regionManager == null) {
            this.regionManager = new RegionManager();
        }
        return this.regionManager;
    }
    
    public ISubPluginManager getSubPluginManager() {
        if (this.subpluginManager == null) {
            this.subpluginManager = new SubPluginManager();
        }
        return this.subpluginManager;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public IWandManager getWandManager() {
        if (this.wandManager == null) {
            this.wandManager = new WandManager();
        }
        return this.wandManager;
    }
    
    public BanManager getBanManager() {
        if (this.banManager == null) {
            this.banManager = new BanManager();
        }
        return this.banManager;
    }
    
    public static boolean downloadPlugin(final String id) {
        InputStreamReader in = null;
        try {
            URL url = new URL("https://api.curseforge.com/servermods/files?projectIds=" + id);
            final URLConnection urlConnection = url.openConnection();
            in = new InputStreamReader(urlConnection.getInputStream());
            final char[] charArray = new char[1024];
            final StringBuilder sb = new StringBuilder();
            int numCharsRead;
            while ((numCharsRead = in.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            String result = sb.toString();
            result = result.replace("\\/", "/").replaceAll(".*\"downloadUrl\":\"", "").split("\",\"")[0];
            final String[] split = result.split("/");
            url = new URL(result);
            final String path = SH.plugin.getDataFolder().getParentFile().getAbsoluteFile() + "/" + split[split.length - 1];
            final ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            final FileOutputStream fos = new FileOutputStream(path);
            fos.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
            info("Finished downloading " + split[split.length - 1] + ". Loading dependecy");
            Bukkit.getServer().getPluginManager().loadPlugin(new File(path));
            fos.close();
            return true;
        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex2) {
            ex2.printStackTrace();
        }
        catch (InvalidPluginException ex3) {
            ex3.printStackTrace();
        }
        catch (InvalidDescriptionException ex4) {
            ex4.printStackTrace();
        }
        catch (UnknownDependencyException ex5) {
            ex5.printStackTrace();
        }
        finally {
            try {
                in.close();
            }
            catch (IOException ex6) {
                ex6.printStackTrace();
            }
        }
        return false;
    }
    
    public void loadDependencies() {
    }
    
    private void loadJars() {
        AnnoSubPluginLoader.testLoadJars();
    }
    
    private void registerSubPlugins() {
        this.getSubPluginManager().addSubPlugin(new DenyPlayerMode("DenyPlayerMode"));
        this.getSubPluginManager().addSubPlugin(new Merchant("Merchant"));
        this.getSubPluginManager().addSubPlugin(new ShopHandler("Shop"));
        this.getSubPluginManager().addSubPlugin(new TitleAPI("TitleAPI"));
        this.getSubPluginManager().addSubPlugin(new RegionUpdater("RegionUpdater"));
        this.getSubPluginManager().addSubPlugin(new ShortLink("BitLy_UrlShortener"));
        this.getSubPluginManager().addSubPlugin(new BlockProtection("BlockProtection"));
        this.getSubPluginManager().addSubPlugin(new PlayerDataManagerPlugin("PlayerDataManager"));
        this.getSubPluginManager().addSubPlugin(new Commands("Kommandoer"));
        this.getSubPluginManager().addSubPlugin(new InventoryMenuTester("MenuTester"));
        this.getSubPluginManager().addSubPlugin(new BlockLagManager.BlockLagPlugin("BlockLagManager"));
        this.getSubPluginManager().addSubPlugin(new CraftManager("CraftManager"));
        this.getSubPluginManager().addSubPlugin(new Votifyer("Votifyer"));
        this.getSubPluginManager().addSubPlugin(new AutoBroadcastPlugin("AutoBroadcast"));
        this.getSubPluginManager().addSubPlugin(new KillCounterPlugin());
        this.getAnnoSubPluginManager().addClass(InfinityDispenser.class);
        this.getAnnoSubPluginManager().addClass(WarningManager.class);
    }
    
    private void unregisterSubPlugins() {
        this.getSubPluginManager().unregisterAll();
    }
    
    public void onEnable() {
        SH.plugin = this;
        SH.iSurvivalHeaven = this;
        this.version = this.getDescription().getVersion();
        this.name = this.getDescription().getName();
        info(ChatColor.YELLOW + "STARTER PLUGIN " + this.toString());
        info(ChatColor.GREEN + "******************************************************************");
        info(ChatColor.RESET + "Starter " + SH.NAME + ChatColor.RESET + " v. " + ChatColor.YELLOW + this.version);
        info(ChatColor.GREEN + "------------------------------------------------------------------");
        this.getAnnoSubPluginManager();
        this.getBlockManager();
        this.getMysqlManager();
        this.getPlayerDataManager();
        this.getWandManager();
        this.getFriendManager();
        this.getHomeManager();
        this.getBanManager();
        this.getServer().getPluginManager().registerEvents((Listener)new ParticleListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new ChairsListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new ServerUtilsListener(), (Plugin)this);
        try {
            this.registerSubPlugins();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.enableSubPlugins();
        info(ChatColor.GOLD + "Sjekker om alle n\u00f8dvendige plugins er her...");
        final Plugin pex = Bukkit.getPluginManager().getPlugin("PermissionsEx");
        if (pex == null) {
            info(ChatColor.GOLD + "PermissionsEx mangler. Starter nedlasting....");
            try {
                downloadPlugin("31279");
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        info(ChatColor.GREEN + "******************************************************************");
    }
    
    public void onDisable() {
        info(ChatColor.YELLOW + "STOPPER PLUGIN " + this.toString());
        this.disableSubPlugins();
        this.unregisterSubPlugins();
    }
    
    public static void info(final Object... strings) {
        for (final Object s : strings) {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(SH.PREFIX) + ChatColor.WHITE + s);
        }
    }
    
    public static void mysql_debug(final Object... strings) {
    }
    
    public static void error(final Object... strings) {
    }
    
    public static void warning(final Object... strings) {
    }
    
    public static void debug(final Object... strings) {
    }
}
