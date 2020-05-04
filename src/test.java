public class test
{
    Properties configFile;
    public test()
    {
    }

    // TEST
    /* TEST */
    public String getProperty(String key)
    {
        String value = configFile.getProperty(key);
        return value;
    }

    // TEST
    /* TEST */
    public String[] getPropertyArray(String key)
    {
        // TEST
        /* TEST */
        String[] values = configFile.getProperty(key).split(",");
        return values;
    }
}