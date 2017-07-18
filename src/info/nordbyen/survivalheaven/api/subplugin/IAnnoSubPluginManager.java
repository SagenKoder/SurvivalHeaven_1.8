package info.nordbyen.survivalheaven.api.subplugin;

public interface IAnnoSubPluginManager
{
    void addClass(final Class<?> p0);
    
    void disableAll();
    
    void enableAll();
    
    void removeClass(final Class<?> p0);
}
