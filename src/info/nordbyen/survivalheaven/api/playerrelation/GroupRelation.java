package info.nordbyen.survivalheaven.api.playerrelation;

public enum GroupRelation
{
    NONE("NONE", 0), 
    MEMBER("MEMBER", 1), 
    TRUSTED("TRUSTED", 2), 
    OWNER("OWNER", 3);
    
    private GroupRelation(final String s, final int n) {
    }
}
