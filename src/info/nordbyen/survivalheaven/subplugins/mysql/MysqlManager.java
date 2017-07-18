package info.nordbyen.survivalheaven.subplugins.mysql;

import info.nordbyen.survivalheaven.api.mysql.*;
import info.nordbyen.survivalheaven.*;
import java.util.*;
import java.text.*;
import org.bukkit.*;
import java.sql.*;

public final class MysqlManager implements IMysqlManager
{
    private final String DATE_FORMAT = "yyyy_MM_dd_HH_mm_ss_SSS";
    private String hostname;
    private String portnmbr;
    private String username;
    private String password;
    private String database;
    protected Connection connection;
    
    public MysqlManager() {
        this.hostname = "";
        this.portnmbr = "";
        this.username = "";
        this.password = "";
        this.database = "";
        this.connection = null;
        MySQLConfiguration.getInstance();
        this.hostname = MySQLConfiguration.getHostName();
        this.portnmbr = new StringBuilder(String.valueOf(MySQLConfiguration.getHostPort())).toString();
        this.username = MySQLConfiguration.getUserName();
        this.password = MySQLConfiguration.getUserPassword();
        this.database = MySQLConfiguration.getDatabaseName();
        this.open();
    }
    
    public MysqlManager(final String hostname, final String portnmbr, final String database, final String username, final String password) {
        this.hostname = "";
        this.portnmbr = "";
        this.username = "";
        this.password = "";
        this.database = "";
        this.connection = null;
        this.hostname = hostname;
        this.portnmbr = portnmbr;
        this.database = database;
        this.username = username;
        this.password = password;
        this.open();
    }
    
    @Override
    public boolean checkConnection() throws SQLException {
        return this.connection.isValid(5);
    }
    
    @Override
    public boolean clearTable(final String table) {
        Statement statement = null;
        String query = null;
        try {
            statement = this.connection.createStatement();
            query = "DELETE FROM " + table;
            SH.mysql_debug(query);
            statement.executeUpdate(query);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }
    
    @Override
    public void close() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean deleteTable(final String table) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            SH.mysql_debug("DROP TABLE " + table);
            statement.executeUpdate("DROP TABLE " + table);
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Connection getConnection() {
        return this.connection;
    }
    
    @Override
    public String getDate(final Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
        return sdf.format(date);
    }
    
    @Override
    public Date getDate(final String date) {
        if (date.equalsIgnoreCase("no")) {
            return new Date();
        }
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
            return sdf.parse(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public String getLocation(final Location loc) {
        final String location = String.valueOf(loc.getWorld().getName()) + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ();
        return location;
    }
    
    @Override
    public Location getLocation(final String loc) {
        if (loc.equalsIgnoreCase("no")) {
            return Bukkit.getWorlds().get(0).getSpawnLocation();
        }
        final String[] split = loc.split(";");
        final Location location = new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
        return location;
    }
    
    @Override
    public boolean insert(final String table, final Object[] column, final Object[] value) {
        Statement statement = null;
        final StringBuilder sb1 = new StringBuilder();
        final StringBuilder sb2 = new StringBuilder();
        for (final Object s : column) {
            sb1.append(String.valueOf(s.toString()) + ",");
        }
        for (final Object s : value) {
            sb2.append("'" + s.toString() + "',");
        }
        final String columns = sb1.toString().substring(0, sb1.toString().length() - 1);
        final String values = sb2.toString().substring(0, sb2.toString().length() - 1);
        try {
            statement = this.connection.createStatement();
            SH.mysql_debug("INSERT INTO " + table + "(" + columns + ") VALUES (" + values + ")");
            statement.execute("INSERT INTO " + table + "(" + columns + ") VALUES (" + values + ")");
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Connection open() {
        String url = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            url = "jdbc:mysql://" + this.hostname + ":" + this.portnmbr + "/" + this.database + "?autoReconnect=true&allowMultiQueries=true";
            return this.connection = DriverManager.getConnection(url, this.username, this.password);
        }
        catch (SQLException e) {
            System.out.print("Kan ikke koble til mySQL server!");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e2) {
            System.out.print("Finner ikke JDBC Driver");
            e2.printStackTrace();
        }
        return null;
    }
    
    @Override
    public ResultSet query(final String query) throws SQLException {
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = this.connection.createStatement();
            SH.mysql_debug(query);
            result = statement.executeQuery(query);
            return result;
        }
        catch (SQLException e) {
            if (e.getMessage().equals("Can not issue data manipulation statements with executeQuery().")) {
                try {
                    statement.executeUpdate(query);
                }
                catch (SQLException ex) {
                    if (e.getMessage().startsWith("Du har en feil i mySQL-syntaxen;")) {
                        String temp = String.valueOf(e.getMessage().split(";")[0].substring(0, 36)) + e.getMessage().split(";")[1].substring(91);
                        temp = temp.substring(0, temp.lastIndexOf("'"));
                        throw new SQLException(temp);
                    }
                    ex.printStackTrace();
                }
            }
            else {
                if (e.getMessage().startsWith("Du har en feil i mySQL-syntaxen;")) {
                    String temp2 = String.valueOf(e.getMessage().split(";")[0].substring(0, 36)) + e.getMessage().split(";")[1].substring(91);
                    temp2 = temp2.substring(0, temp2.lastIndexOf("'"));
                    throw new SQLException(temp2);
                }
                e.printStackTrace();
            }
            return null;
        }
    }
    
    @Override
    public ResultSet query(final String query, final int ret) throws SQLException {
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = this.connection.prepareStatement(query, ret);
            SH.mysql_debug(query);
            statement.executeUpdate();
            result = statement.getGeneratedKeys();
            return result;
        }
        catch (SQLException e) {
            if (e.getMessage().equals("Can not issue data manipulation statements with executeQuery().")) {
                try {
                    statement.executeUpdate(query);
                }
                catch (SQLException ex) {
                    if (e.getMessage().startsWith("Du har en feil i mySQL-syntaxen;")) {
                        String temp = String.valueOf(e.getMessage().split(";")[0].substring(0, 36)) + e.getMessage().split(";")[1].substring(91);
                        temp = temp.substring(0, temp.lastIndexOf("'"));
                        throw new SQLException(temp);
                    }
                    ex.printStackTrace();
                }
            }
            else {
                if (e.getMessage().startsWith("Du har en feil i mySQL-syntaxen;")) {
                    String temp2 = String.valueOf(e.getMessage().split(";")[0].substring(0, 36)) + e.getMessage().split(";")[1].substring(91);
                    temp2 = temp2.substring(0, temp2.lastIndexOf("'"));
                    throw new SQLException(temp2);
                }
                e.printStackTrace();
            }
            return null;
        }
    }
}
