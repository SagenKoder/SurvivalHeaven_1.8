package info.nordbyen.survivalheaven.api.mysql;

import java.util.*;
import org.bukkit.*;
import java.sql.*;

public interface IMysqlManager
{
    boolean checkConnection() throws SQLException;
    
    boolean clearTable(final String p0);
    
    void close();
    
    boolean deleteTable(final String p0);
    
    Connection getConnection();
    
    String getDate(final Date p0);
    
    Date getDate(final String p0);
    
    String getLocation(final Location p0);
    
    Location getLocation(final String p0);
    
    boolean insert(final String p0, final Object[] p1, final Object[] p2);
    
    Connection open();
    
    ResultSet query(final String p0) throws SQLException;
    
    ResultSet query(final String p0, final int p1) throws SQLException;
}
