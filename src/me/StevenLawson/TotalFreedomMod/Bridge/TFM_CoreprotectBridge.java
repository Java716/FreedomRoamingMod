package me.StevenLawson.TotalFreedomMod.Bridge;

import java.util.List;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import java.lang.String;
import java.lang.Integer;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.plugin.Plugin;

public class TFM_CoreprotectBridge {
public static CoreProtectAPI getCoreProtect() {
Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");
     
// Check that CoreProtect is loaded
if (plugin == null || !(plugin instanceof CoreProtect)) {
    return null;
}
        
// Check that the API is enabled
CoreProtectAPI CoreProtect = ((CoreProtect)plugin).getAPI();
if (CoreProtect.isEnabled()==false){
    return null;
}

// Check that a compatible version of the API is loaded
if (CoreProtect.APIVersion() != 2){
    return null;
}
return CoreProtect;
}
}