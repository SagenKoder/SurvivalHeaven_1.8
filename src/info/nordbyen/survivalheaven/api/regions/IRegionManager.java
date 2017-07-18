package info.nordbyen.survivalheaven.api.regions;

import info.nordbyen.survivalheaven.subplugins.regions.*;
import org.bukkit.*;

public interface IRegionManager
{
    void addRegion(final RegionData p0);
    
    RegionData getRegionAt(final Location p0);
    
    RegionData[] getRegions();
    
    RegionData[] getRegionsAt(final Location p0);
    
    void removeRegion(final RegionData p0);
}
