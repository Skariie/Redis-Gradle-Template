package fr.skariie.templates;

import fr.skariie.templates.data.Redis;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private Redis jedis;

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        instance = this;
        if (getConfig().getBoolean("redis.enabled")) {
            jedis = new Redis(getConfig().getString("redis.host"),
                    getConfig().getInt("redis.port"),
                    getConfig().getString("jedis.password")
            );
            jedis.connect();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        jedis.disconnect();
    }

    /**
     * Get the instance of Main class
     * @return instance of Main class
     */
    public static Main getInstance() {
        return instance;
    }
}
