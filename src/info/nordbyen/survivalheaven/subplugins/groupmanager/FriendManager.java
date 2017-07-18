package info.nordbyen.survivalheaven.subplugins.groupmanager;

import info.nordbyen.survivalheaven.*;
import org.bukkit.command.*;
import info.nordbyen.survivalheaven.api.mysql.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import java.util.*;
import java.sql.*;

public class FriendManager
{
    private HashMap<String, ArrayList<String>> friendRequests;
    private ArrayList<String[]> friends;
    
    public FriendManager() {
        this.friendRequests = new HashMap<String, ArrayList<String>>();
        this.friends = new ArrayList<String[]>();
        SH.getPlugin().getCommand("venn").setExecutor((CommandExecutor)new FriendManagerCommand());
        try {
            this.createTables();
            this.updateFriendsList();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addFriendrequest(final String from, final String to) {
        SH.debug("from=" + from, "to=" + to, "pr\u00f8ver \u00e5 legge til...");
        if (!this.friendRequests.containsKey(to) || this.friendRequests.get(to) == null) {
            SH.debug("har ikke to i listen fra f\u00f8r");
            final ArrayList<String> list = new ArrayList<String>();
            list.add(from);
            this.friendRequests.put(to, list);
            return;
        }
        SH.debug("den har to i listen fra f\u00f8r");
        this.friendRequests.get(to).add(from);
    }
    
    public void createTables() throws SQLException {
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        sql.query("CREATE TABLE IF NOT EXISTS friend ( `id` INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, `player1` VARCHAR(45) NOT NULL, `player2` VARCHAR(45) NOT NULL )");
    }
    
    public ArrayList<String> getFriendrequestsTo(final String name) {
        SH.debug("name=" + name, "friendRequests.get(name)=" + this.friendRequests.get(name));
        if (this.friendRequests.get(name) == null) {
            this.friendRequests.put(name, new ArrayList<String>());
        }
        return this.friendRequests.get(name);
    }
    
    public ArrayList<IPlayerData> getFriendsWith(final IPlayerData pd) {
        if (this.getFriendsWith(pd.getUUID()) == null) {
            return new ArrayList<IPlayerData>();
        }
        return this.getFriendsWith(pd.getUUID());
    }
    
    public ArrayList<IPlayerData> getFriendsWith(final String uuid) {
        final ArrayList<IPlayerData> ret = new ArrayList<IPlayerData>();
        for (final String[] friendship : this.friends) {
            final String p1 = friendship[0];
            final String p2 = friendship[1];
            if (p1.equalsIgnoreCase(uuid)) {
                ret.add(SH.getManager().getPlayerDataManager().getPlayerData(p2));
            }
            else {
                if (!p2.equalsIgnoreCase(uuid)) {
                    continue;
                }
                ret.add(SH.getManager().getPlayerDataManager().getPlayerData(p1));
            }
        }
        return ret;
    }
    
    public boolean isFriends(final IPlayerData pd1, final IPlayerData pd2) {
        return this.isFriends(pd1.getUUID(), pd2.getUUID());
    }
    
    public boolean isFriends(final String uuid1, final String uuid2) {
        if (uuid1.equals(uuid2)) {
            return true;
        }
        final ArrayList<IPlayerData> friends = this.getFriendsWith(uuid1);
        for (final IPlayerData friend : friends) {
            if (friend.getUUID().equalsIgnoreCase(uuid2)) {
                return true;
            }
        }
        return false;
    }
    
    public void removeFriendRequest(final String from, final String to) {
        if (!this.friendRequests.containsKey(to) || this.friendRequests.get(to) == null) {
            return;
        }
        this.friendRequests.get(to).remove(from);
    }
    
    public void removeFriendship(final String uuid1, final String uuid2) throws SQLException {
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        sql.query("DELETE FROM friend WHERE player1 = '" + uuid1 + "' AND player2 = '" + uuid2 + "' OR player1 = '" + uuid2 + "' AND player2 = '" + uuid1 + "'");
        String[] rem = null;
        for (final String[] friendship : this.friends) {
            final String p1 = friendship[0];
            final String p2 = friendship[1];
            if ((p1.equalsIgnoreCase(uuid1) && p2.equalsIgnoreCase(uuid2)) || (p2.equalsIgnoreCase(uuid1) && p1.equalsIgnoreCase(uuid2))) {
                rem = friendship;
            }
        }
        this.friends.remove(rem);
    }
    
    public void saveFriendsList() throws SQLException {
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        for (final String[] friendship : this.friends) {
            final String p1 = friendship[0];
            final String p2 = friendship[1];
            sql.query("INSERT INTO friend( player1, player2 ) VALUES ( '" + p1 + "', '" + p2 + "' )");
        }
    }
    
    public void setFriendrequestsTo(final String name, final ArrayList<String> list) {
        this.friendRequests.put(name, list);
    }
    
    public void setFriends(final IPlayerData one, final IPlayerData two) throws SQLException {
        this.setFriends(one.getUUID(), two.getUUID());
    }
    
    public void setFriends(final String uuid1, final String uuid2) throws SQLException {
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        sql.query("INSERT INTO friend( player1, player2 ) VALUES ( '" + uuid1 + "', '" + uuid2 + "' )");
        final String[] friendship = { uuid1, uuid2 };
        this.friends.add(friendship);
    }
    
    public void updateFriendsList() throws SQLException {
        final ArrayList<String[]> friends = new ArrayList<String[]>();
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        final ResultSet rs = sql.query("SELECT * FROM friend");
        while (rs != null && rs.next()) {
            final String[] friendship = { rs.getString("player1"), rs.getString("player2") };
            friends.add(friendship);
        }
        this.friends = friends;
    }
}
