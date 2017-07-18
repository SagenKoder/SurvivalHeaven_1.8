package info.nordbyen.survivalheaven.api.playerdata.warning;

import info.nordbyen.survivalheaven.api.playerdata.*;
import java.sql.*;
import java.util.*;

public interface IWarningManager
{
    void addWarning(final Date p0, final IPlayerData p1, final IPlayerData p2, final String p3) throws SQLException;
    
    void addWarning(final Date p0, final IPlayerData p1, final IPlayerData p2, final String p3, final IWarning.Level p4) throws SQLException;
    
    List<IWarning> getEveryWarnings();
    
    IWarning getWarningFromId(final int p0);
    
    List<IWarning> getWarningsFromName(final String p0);
    
    List<IWarning> getWarningsFromPlayer(final IPlayerData p0);
    
    List<IWarning> getWarningsFromUuid(final String p0);
    
    void removeWarning(final int p0);
    
    void removeWarning(final IWarning p0);
    
    public interface IWarning
    {
        Date getDate();
        
        int getId();
        
        Level getLevel();
        
        String getMessage();
        
        IPlayerData getPlayer();
        
        IPlayerData getSetter();
        
        public enum Level
        {
            LOW("LOW", 0, 1), 
            MEDIUM("MEDIUM", 1, 2), 
            HIGH("HIGH", 2, 3);
            
            int level;
            
            static Level getLevelFromInt(final int level) {
                if (level == 1) {
                    return Level.LOW;
                }
                if (level == 2) {
                    return Level.MEDIUM;
                }
                if (level == 3) {
                    return Level.HIGH;
                }
                throw new IllegalArgumentException("level " + level + " finnes ikke!");
            }
            
            private Level(final String s, final int n, final int level) {
                this.level = level;
            }
            
            public int asInt() {
                return this.level;
            }
        }
    }
}
