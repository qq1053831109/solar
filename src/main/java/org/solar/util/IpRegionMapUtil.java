package org.solar.util;

import io.github.elkan1788.ip2region.DataBlock;
import io.github.elkan1788.ip2region.IPSearcher;
import io.github.elkan1788.ip2region.IPSearcherException;
import io.github.elkan1788.ip2region.RegionAddress;
import java.io.IOException;

public class IpRegionMapUtil {
    private    static  IPSearcher iPSearcher;

    static {
        try {
            iPSearcher = new IPSearcher();
        } catch (IPSearcherException e) {
            e.printStackTrace();
        }
    }

    public static String getCountry(String ip)   {
            DataBlock dataBlock=getDataBlock(ip);
            if (dataBlock==null){
                return null;
            }
            RegionAddress regionAddress= dataBlock.getRegionAddr();
            return regionAddress.getCountry();
    }
    public static String getCity(String ip)  {
        DataBlock dataBlock=getDataBlock(ip);
        if (dataBlock==null){
            return null;
        }
        RegionAddress regionAddress= dataBlock.getRegionAddr();
        return regionAddress.getCity();
    }
    public static String getCountryCity(String ip)  {
        DataBlock dataBlock=getDataBlock(ip);
        if (dataBlock==null){
            return null;
        }
        RegionAddress regionAddress= dataBlock.getRegionAddr();
        return regionAddress.getCountry()+regionAddress.getCity();
    }
    public final static DataBlock getDataBlock(String ip)  {
        try {

            return iPSearcher.binarySearch(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
