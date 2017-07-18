package info.nordbyen.Ziputils;

final class IdentityNameMapper implements NameMapper
{
    public static final NameMapper INSTANCE;
    
    static {
        INSTANCE = new IdentityNameMapper();
    }
    
    @Override
    public String map(final String name) {
        return name;
    }
}
