package kr.danso.test.utils;

import kr.danso.test.utils.locations.SmartLocation;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class LocationUtils {
    private LocationUtils(){

    }

    public static SmartLocation getLocation(String location){
        try {
            if (location == null || location.isEmpty())
                return null;

            String[] sections = location.split(",");

            double x = Double.parseDouble(sections[1]);
            double y = Double.parseDouble(sections[2]);
            double z = Double.parseDouble(sections[3]);
            float yaw = sections.length > 5 ? Float.parseFloat(sections[4]) : 0;
            float pitch = sections.length > 4 ? Float.parseFloat(sections[5]) : 0;

            return new SmartLocation(sections[0], x, y, z, yaw, pitch);
        }catch(Exception ex){
            throw ex;
        }
    }

    public static String getLocation(Location location){
        return location == null ? "" : location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
    }

    public static Location getRelative(Location location, BlockFace face){
        return location.clone().add(face.getModX(), face.getModY(), face.getModZ());
    }

    public static Location getBlockLocation(Location location){
        return new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
