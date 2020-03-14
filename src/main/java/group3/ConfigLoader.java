package group3;

import java.util.*;
import java.util.Properties;


public class ConfigLoader
{
    Properties configFile;
    public ConfigLoader()
    {
        configFile = new java.util.Properties();
        try {
            configFile.load(ConfigLoader.class.getClassLoader().
                    getResourceAsStream("resources/config/config.cfg"));
        }catch(Exception eta){
            eta.printStackTrace();
        }
    }

    public String getProperty(String key)
    {
        String value = this.configFile.getProperty(key);
        return value;
    }
}