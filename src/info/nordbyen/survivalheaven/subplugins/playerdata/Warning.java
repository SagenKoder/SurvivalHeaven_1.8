package info.nordbyen.survivalheaven.subplugins.playerdata;

import java.util.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import info.nordbyen.survivalheaven.api.playerdata.warning.*;
import info.nordbyen.survivalheaven.*;
import java.sql.*;

public class Warning implements IWarningManager.IWarning
{
    private final Date date;
    private final IPlayerData player;
    private final IPlayerData setter;
    private final String message;
    private final Level level;
    private final int id;
    
    Warning(final Date date, final IPlayerData player, final IPlayerData setter, final String message, final Level level) throws SQLException {
        this.date = date;
        this.player = player;
        this.setter = setter;
        this.message = message;
        this.level = level;
        final ResultSet rs = SH.getManager().getMysqlManager().query("INSERT INTO warnings ( date, playeruuid, setteruuid, message, level ) VALUES ( \"" + SH.getManager().getMysqlManager().getDate(date) + "\", \"" + player.getUUID() + "\", \"" + ((setter == null) ? "NO" : setter.getUUID()) + "\", \"" + message + "\", `" + level.asInt() + "` );", 1);
        this.id = rs.getInt("id");
    }
    
    @Override
    public Date getDate() {
        return new Date(this.date.getTime());
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
    @Override
    public Level getLevel() {
        return this.level;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }
    
    @Override
    public IPlayerData getPlayer() {
        return this.player;
    }
    
    @Override
    public IPlayerData getSetter() {
        return this.setter;
    }
}
