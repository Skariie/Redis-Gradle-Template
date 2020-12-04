package fr.skariie.templates.data;

import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;

public class Redis {

    private Jedis jedis;
    private final String host;
    private final int port;
    private final String password;

    /**
     * Set host, port and password able to connect to redis server
     * @param host Give host of the redis server
     * @param port Give port of redis server
     * @param password If redis server need password give them
     * @author Skariie_#0001
     */
    public Redis(String host, int port, String password) {
        this.host = host;
        this.port = port;
        this.password = password;
    }

    /**
     * Connect to redis server
     * @author Skariie_#0001
     */
    public void connect() {
        if (!isConnected()) {
            Bukkit.getLogger().info("Connecting to redis server...");
            try {
                jedis = new Jedis(host, port);
                Bukkit.getLogger().info("Successfully connected to redis server !");
            } catch (NullPointerException e) {
                Bukkit.getLogger().warning("Can't connect to redis server !");
                e.printStackTrace();
            }
            if (password != null) {
                jedis.auth(password);
            }
        }
    }

    /**
     * Check if redis server is connected
     * @return true -> connected, false -> disconnected
     */
    public boolean isConnected() {
        return jedis.isConnected();
    }

    /**
     * Disconnect from redis server
     * @author Skariie_#0001
     */
    public void disconnect() {
        if (isConnected()) {
            Bukkit.getLogger().info("Disconnecting from redis server...");
            jedis.disconnect();
            Bukkit.getLogger().info("Successfully disconnect from redis server !");
        }
    }

    /**
     * Select the database in redis server
     * @param database database who want to switch
     * @author Skariie_#0001
     */
    public void selectDatabase(int database) {
        jedis.select(database);
    }

    /**
     * Return value of a redis server's key
     * @param key Key of the redis server
     * @return Value of the key
     * @author Skariie_#0001
     */
    public String get(String key) {
        try {
            return jedis.get(key);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get expiration time of redis key
     * @param key Key of redis server
     * @return Value of the key
     * @author Skariie_#0001
     */
    public String getTime(String key) {
        try {
            return String.valueOf(jedis.ttl(key));
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Set key with an expire time
     * @param key Key of redis server
     * @param value Value attribuated to key
     * @param seconds Expiration time in second
     * @author Skariie_#0001
     */
    public void setExpire(String key, String value, int seconds) {
        try {
            jedis.setex(key, seconds, value);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
