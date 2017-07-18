package info.nordbyen.survivalheaven.api.blockdata;

public enum BlockPlacedType
{
    SURVIVAL("SURVIVAL", 0, "SURVIVAL"), 
    CREATIVE("CREATIVE", 1, "CREATIVE"), 
    WORLDEDIT("WORLDEDIT", 2, "WORLDEDIT");
    
    public final String name;
    
    private BlockPlacedType(final String s, final int n, final String name) {
        this.name = name;
    }
}
