# SurvivalHeaven
#### En Spigot 1.8 plugin til SurvivalHeaven
```java		
public class TestClass {		
  public static void main( String... args ) {		
    System.out.println( "Velkommen til SurvivalHeavens README.md fil :o" );		
  }		
}		
```		
		
###### Gitter		
		
[![Join the chat at https://gitter.im/SurvivalHeaven/SurvivalHeaven](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/SurvivalHeaven/SurvivalHeaven?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)		
		
		
###### TODO:		
  - ~~fikse pil og bue i ikke-pvp områder~~ DONE		
```java		
@EventHandler		
public void onBowPvP( EntityDamageByEntityEvent e ) {		
  Entity damager = e.getDamager();		
  Entity target = e.getEntity();		
  if( damager instanceof Arrow && target instanceof Player ) {		
    Arrow arrow = (Arrow) damager;		
    Player player = (Player) target;		
    if( arrow.getShooter() instanceof Player ) {		
      Player shooter = (Player) arrow.getShooter();        		
      RegionData region = SH.getManager().getRegionManager().getRegionAt( player.getLocation() );		
      if( !region.isPvp() ) {		
        e.setCancelled( true );		
        FancyMessages.sendActionBar( shooter, ChatColor.RED + "Det er ikke PvP her!" );		
        return;		
      }		
    }		
  }		
}		
```		
  - ~~kode `/homes <spiller>` til stab~~ DONE		
  - ~~kode `/delhome <spiller> <navn>` til stab~~ DONE		
  - kode en kommando for å slette alle hjem til en gitt spiller innen en gitt radius  		
  - ~~kode så når noen skriver `/pvp` eller `/normal` kommer denne lenken opp med info: http://l0lkj.info/1CCTq5p~~ DONE		
  - kode sterkere mobs i pvp med mer drops		
  - fjerne BiomeControl, Disable_TNT og Skins pluginene		
  - kode inventory gui		
  - kode ferdig til BlockLag		
  - fikse auto afk ( av og på )		
    - Spillere går automatisk til afk etter 3 min med idle. 		
    - Afk spillere teleporteres til et afk rom som er beskyttet.		
    - Straks de ikke lengere er idle teleporteres de tilbake		
      - Dette kan disables og enables for hvert spiller		
  - kode `/back`		
  - kode gruppesystemer		
  - kode bankystemer		
  - kode warningsystem		
    - `/warn <spiller> <grunn/beskrivelse>`		
    - `/cwarn <spiller>`		
    - `/delwarn <id>`		
    - `/delwarn <spiller>` så `/confirm` for å fjerne alle advarsler fra en spiller		
  - kode notat system		
  - kode shop		
  - kode minigames		
    - **Kode en minigames manager**		
    - FFA		
    - Spleef		
    - Splegg		
    - ColorJump		
    - integrere fisk		
  - oppdatere til 1.8.3		
    - ~~Laste opp ny versjon~~ DONE		
    - Endre scriptfilen til å brukt denne nye jar-filen		
  - beskytte dyr i beskyttede områder		
  - kode så spillere må skrive navnet til personen med tpaccept		
  - kode `/seen`		
  - beskytte hester og kanskje andre dyr med (kommando?)		
  - lage regioner i nether og the end		
  - ~~fikse tnt i byer/survival~~		
```java		
@EventHandler		
public void onExplode( EntityExplodeEvent e ) {		
  Location loc = e.getLocation();		
  List<Block> blocks = e.blockList();		
		
  if( !SH.getManager().getRegionManager().getRegionAt( loc ).isBp() )		
    return;		
  blocks.clear();		
}		
```		
  - kode noe for withers/creepers/andre entities som sprenger		
  - fikse blockprotection med slabs		
  - fikse blockprotection med dører ( øverste halvdel )		
  - kode `/socialspy`		
  - kode en logger som logger død med inventar, tid, årsak og sted		
  - optimalisere events		
  - optimalisere lagring til databasen		
    - Legge til en boolean for om dataen har blitt endret til hver modul		
  - Random events		
    - Serveren velger ut en random event med faste intervaller, kanskje en gang i timen		
    - Gode og Dårlige		
    - Skjer ikke for spillere som er afk		
      - Må ha et bra afk system som sette deg afk fort		
    - Trenger mange events		
      - Brede[Taiqwor] kan lage en liste over events		
      - De får bare en av rewardsene, ikke alle  		
        1. En villager som kan gi forskellige items (coal, emeralds, iron, mat o.l)		
        2. en zombie som gir blindness, og deretter prøver å drepe valgt spiller (kan droppe 5 rotten flesh, 2 iron ingot, 10 carrot eller 10 potato)		
        3. en villager som teleporter spilleren til ett lukket område, (dersom spilleren godtar dette) og får vedkommende til å gjennomføre ett minigame (med belønninger for å fullføre minigamet, f.eks coal, emeralds, diamonds, iron, mat eller armor) 		
        4. spawner ett skeleton med 50 hjerter i nærheten av spilleren. Dropper ting når den blir drept. Har 1/10 mulighet for å ha på en wither skeleton skull når den spawner. (Skeletons enchanted bow <bue med power 2, punch 2, og infinity 1 (1/10)>, 32 bein, 64 piler, eller en wither skull<1/10>)		
        5.  En villager, som trenger hjelp til å drepe noen høner i nærheten av huset hans (spiller blir teleportert til huset), når oppgaven er utført, blir spilleren belønnet med <10 cooked chiken, 32 feathers, Hunters axe (iron axe med sharpness 2) <1/5>		
        6. en villager som trenger hjelp til å skaffe ressurser til vinteren, kan f.eks være wood, coal, cooked beef eller iron, spiller blir belønnet med 1-5 diamanter eller Resource gathere’s pickaxe <golden pickaxe med silk touch>		
        7. «air drop» en kiste som detter fra himmelen, mellom 10-100 blocks fra spilleren. Kan inneholde iron armor, iron tools eller mat. 		
    - random event og random spiller		
    - tiden mellom hver event er random, men minker for hver spiller som er online		
      - 30-120 min, og minker med 5 minutter for hver spiller som er på?		
  - Kode en autobroadcast med en liste over meldinger 		
    - Sende ut nyttige `tips n' trics` som kommer hvert 15. minutt		
  - Kode en villager som spør spillere om de vil spillet et minigame og få penger/ting for det		
    - Villageren snakker i tekst over hodet ( og i chatten )		
    - Spillere får opp i chatten:		
      - "Ja, jeg vil bli med" "Nei takk"		
      - Om de klikker på ja blir de teleportert og minigamet starter, om de klikker på nei forsvinner villageren 		
  - Gruppesystemer med egne byer		
  - Random områder i pvp blir til questområde hvor det spawner pillarer i ring rundt området. Når bossen er drept forsvinner dette.		
  - kode [privat] skilt for å gjøre kister private til BARE spilleren som eier kisten og ingen andre venner eller andre.		
  - kode `/sol` og `/dag`, spillere kan bare stemme bort annen hver syklus.		
  - kode multiinventar api		
  - kode listener for å bytte inventar mellom survival og kreativ		
		
```java		
public class l0lkj extends Human implements Workable, Feedable, Talkable {		
  		
  @Override		
  public void eat() {		
    System.out.println( "[l0lkj]" + "Hmm, mat :D" );		
  }		
  		
  @Override		
  public void work( Work work ) {		
    for( int i = 0; i < work.getTasks().length; i++ ) {		
      System.out.println( "[l0lkj]" + "Jeg jobber med " + work.getTasks().get( i ).getName() );		
    }		
  }		
  		
  @Override		
  public void speak( String... messages ) {		
    for( String s : messages ) {		
      System.out.println( "[l0lkj]" + s );		
    }		
  }		
}		
```		
		
		
###### Managers		
		
Pluginen har nå 9 managers:		
  - AnnoSubPluginManager		
  - BlockManager		
  - MysqlManager		
  - NoteManager		
  - PlayerDataManager		
  - WandManager		
  - WarningManager		
  - FriendManager		
  - HomeManager		
		
Alle kan fåes tak i gjennom:		
```java		
[MANAGER NAME] manager = SH.getManager().get[MANAGER NAME]();		
```		
		
		
###### Submoduler		
		
Pluginen har et system for submoduler som blir kalt subplugins. Et eksempel på en subplugin er følgende:		
```java		
import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;		
		
public class MySubPlugin extends SubPlugin {		
		
  public MySubPlugin(String name) {		
    super(name);		
  }		
		
  @Override		
  protected void disable() {		
    SH.log( "SubPluginen avslutter" );			
  }		
			
  @Override		
  protected void enable() {		
    SH.log( "SubPluginen starter opp :D" );			
    		
    // registrere listeners		
    Bukkit.getPluginManager().registerEvents( new MyListener(), SH.getPlugin() );		
    		
    // Registrere kommandoer		
    SH.getPlugin().getCommand( "MyCommand" ).setExecutor( new MyCommandExecutor() );		
  }		
}		
```		
		
Submodulene registreres helst i `SH` klassen i metoden `registerSubPlugins()` som følgende:		
```java		
getSubPluginManager().addSubPlugin(new MySubPlugin("MySubPluginName"));		
```		
		
eller fra andre klasser med:		
```java		
SH.getManager().getSubPluginManager().addSubPlugin(new MySubPlugin("MySubPluginName"));		
```		
		
		
###### Finne entity en spiller ser på		
		
```java		
  public Entity getTargetEntity( Player p ) {		
    List<Entity> nearbyE = p.getNearbyEntities(50, 50, 50);		
    ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();		
		
    for (Entity e : nearbyE) {		
      if (e instanceof LivingEntity) {		
        livingE.add((LivingEntity) e);		
      }		
    }		
		
    Entity target = null;		
    BlockIterator bItr = new BlockIterator( p, 50 );		
    Block block;		
    Location loc;		
    int bx, by, bz;		
    double ex, ey, ez;		
    while (bItr.hasNext()) {		
      block = bItr.next();		
      bx = block.getX();		
      by = block.getY();		
      bz = block.getZ();		
      for (LivingEntity e : livingE) {		
        loc = e.getLocation();		
        ex = loc.getX();		
        ey = loc.getY();		
        ez = loc.getZ();		
        if ((bx-.75 <= ex && ex <= bx+1.75) && (bz-.75 <= ez && ez <= bz+1.75) && (by-1 <= ey && ey <= by+2.5)) {		
          target = e;		
          break;		
        }		
      }		
    }		
    return target;		
  }		
```		
		
		
###### Vector fra pitch og yaw		
		
```java		
  public Vector calculateSpeedVector( double yaw, double pitch, double velocity ) {		
    double rYaw = Math.toRadians( yaw + 90 ); // Fra Notch sitt system til vanlige radianer		
    double rPitch = Math.toRadians( -pitch ); 		
    		
    double x = velocity * Math.cos( rYaw ) * Math.cos( rPitch );		
    double y = velocity * Math.sin( rPitch );		
    double z = velocity * Math.sin( rYaw ) * Math.cos( rPitch );		
    		
    return new Vector( x, y, z );		
  }		
```