package info.nordbyen.survivalheaven.subplugins.regions;

import info.nordbyen.survivalheaven.api.regions.*;
import org.bukkit.*;
import java.util.*;

public class RegionManager implements IRegionManager
{
    private final ArrayList<RegionData> regions;
    
    public RegionManager() {
        this.regions = new ArrayList<RegionData>();
    }
    
    @Override
    public void addRegion(final RegionData region) {
        if (!this.regions.contains(region)) {
            this.regions.add(region);
        }
    }
    
    @Override
    public RegionData getRegionAt(final Location loc) {
        if (this.getRegionsAt(loc).length <= 0) {
            return new DefaultRegion();
        }
        return this.getRegionsAt(loc)[0];
    }
    
    @Override
    public RegionData[] getRegions() {
        final RegionData[] res = new RegionData[this.regions.size()];
        int i = 0;
        for (final RegionData region : this.regions) {
            res[i++] = region;
        }
        return res;
    }
    
    @Override
    public RegionData[] getRegionsAt(final Location loc) {
        final ArrayList<RegionData> res = new ArrayList<RegionData>();
        RegionData[] regions;
        for (int length = (regions = this.getRegions()).length, j = 0; j < length; ++j) {
            final RegionData region = regions[j];
            if (region.containsLocation(loc)) {
                res.add(region);
            }
        }
        final int num = res.size();
        final RegionData[] result = new RegionData[num];
        for (int i = 0; i < num; ++i) {
            RegionData topRegion = null;
            final ArrayList<RegionData> res_removed = new ArrayList<RegionData>();
            for (final RegionData region2 : res) {
                if (topRegion == null) {
                    topRegion = region2;
                    res_removed.add(topRegion);
                }
                else {
                    if (region2.getZValue() <= topRegion.getZValue()) {
                        continue;
                    }
                    topRegion = region2;
                    res_removed.add(topRegion);
                }
            }
            for (final RegionData region2 : res_removed) {
                res.remove(region2);
            }
            result[i] = topRegion;
        }
        return result;
    }
    
    @Override
    public void removeRegion(final RegionData region) {
        if (this.regions.contains(region)) {
            this.regions.remove(region);
        }
    }
}
