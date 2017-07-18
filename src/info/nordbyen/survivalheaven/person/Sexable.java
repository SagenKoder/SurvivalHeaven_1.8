package info.nordbyen.survivalheaven.person;

public interface Sexable
{
    Sex getSex();
    
    void setSex(final Sex p0);
    
    public enum Sex
    {
        MALE("MALE", 0), 
        FEMALE("FEMALE", 1), 
        UNKNOWN("UNKNOWN", 2);
        
        private Sex(final String s, final int n) {
        }
    }
}
