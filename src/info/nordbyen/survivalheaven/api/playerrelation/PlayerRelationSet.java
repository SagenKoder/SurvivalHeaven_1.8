package info.nordbyen.survivalheaven.api.playerrelation;

public class PlayerRelationSet
{
    private final FriendRelation friendRelation;
    private final GroupRelation groupRelation;
    
    public PlayerRelationSet(final FriendRelation friendRelation, final GroupRelation groupRelation) {
        this.friendRelation = friendRelation;
        this.groupRelation = groupRelation;
    }
    
    public FriendRelation getFriendRelation() {
        return this.friendRelation;
    }
    
    public GroupRelation getGroupRelation() {
        return this.groupRelation;
    }
}
