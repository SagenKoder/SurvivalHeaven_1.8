package info.nordbyen.survivalheaven.api.rankmanager;

public interface IRankManager
{
    BadgeType[] getBadges(final String p0);
    
    String getChatBadgePrefix(final String p0);
    
    String getChatRankPrefix(final String p0);
    
    RankType getRank(final String p0);
    
    void updateNames();
}
