package info.nordbyen.survivalheaven.person;

import javax.annotation.*;

public interface Nameable
{
    String getFirstName();
    
    String getLastName();
    
    @Nullable
    String[] getMiddleNames();
}
