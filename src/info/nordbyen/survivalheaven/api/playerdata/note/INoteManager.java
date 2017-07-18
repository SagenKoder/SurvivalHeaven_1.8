package info.nordbyen.survivalheaven.api.playerdata.note;

import info.nordbyen.survivalheaven.api.playerdata.*;
import java.sql.*;
import java.util.*;

public interface INoteManager
{
    void addNote(final Date p0, final IPlayerData p1, final IPlayerData p2, final String p3) throws SQLException, Exception;
    
    List<INote> getEveryNotes();
    
    INote getNoteFromId(final int p0);
    
    List<INote> getNotesFromName(final String p0);
    
    List<INote> getNotesFromPlayer(final IPlayerData p0);
    
    List<INote> getNotesFromUuid(final String p0);
    
    void removeNote(final INote p0) throws SQLException;
    
    void removeNote(final int p0) throws SQLException;
    
    public interface INote
    {
        Date getDate();
        
        int getId();
        
        String getMessage();
        
        IPlayerData getPlayer();
        
        IPlayerData getSetter();
    }
}
