package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.api.playerdata.note.*;
import java.util.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import info.nordbyen.survivalheaven.*;
import java.sql.*;

public class Note implements INoteManager.INote
{
    private final Date date;
    private final IPlayerData player;
    private final IPlayerData setter;
    private final String message;
    private final int id;
    
    Note(final Date date, final IPlayerData player2, final IPlayerData setter2, final String message) throws Exception {
        this.date = date;
        this.player = player2;
        this.setter = setter2;
        this.message = message;
        SH.getManager().getMysqlManager().query("INSERT INTO notes ( date, playeruuid, setteruuid, message ) VALUES ( \"" + SH.getManager().getMysqlManager().getDate(date) + "\", \"" + player2.getUUID() + "\", \"" + ((setter2 == null) ? "NO" : setter2.getUUID()) + "\", \"" + message + "\" )");
        final ResultSet rs = SH.getManager().getMysqlManager().query("SELECT id FROM notes WHERE playeruuid = \"" + player2.getUUID() + "\" AND message = \"" + message + "\" AND date = \"" + SH.getManager().getMysqlManager().getDate(date) + "\";");
        if (rs != null && rs.next()) {
            this.id = rs.getInt("id");
            return;
        }
        throw new Exception("Noe gikk galt!");
    }
    
    Note(final Date date, final IPlayerData iPlayerData, final IPlayerData iPlayerData2, final String message, final int id) throws SQLException {
        this.date = date;
        this.player = iPlayerData;
        this.setter = iPlayerData2;
        this.message = message;
        this.id = id;
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
