package group3;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import group3.metric_analysis.fan_out.FanOutTracker;
import org.junit.Test;
import spoon.Launcher;

import java.util.HashMap;

import static org.junit.Assert.*;


/**
 * Unit tests for Fan Out
 */
public class FanOutTest
{
    /**
     * Test that we can the correct result for FanOut.java in method mode
     */
    @Test
    public void test_method_mode()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_out\\FanOut.java", true);
        FanOutTracker tester = new FanOutTracker(new String[] {"-min", "0"});
        tester.run(launcher);
        String res = tester.toJson();
        String resString = res.substring(res.indexOf("result") + 8, res.length() - 2);
        Gson gson = new Gson();
        HashMap<String, HashMap<String, Integer>> resHash = gson.fromJson(resString, new TypeToken<HashMap<String, HashMap<String, Integer>>>() {}.getType());

        assertEquals("Method mode contains score of 3 for fanOut1_1()", 3, (long) resHash.get("FanOut1").get("fanOut1_1()"));
        assertEquals("Method mode contains score of 1 for fanOut2_2()", 1, (long) resHash.get("FanOut2").get("fanOut2_2()"));
        assertEquals("Method mode contains score of 2 for fanOut2_1()", 2, (long) resHash.get("FanOut2").get("fanOut2_1()"));
    }

    /**
     * Test that we can the correct result for FanOut.java in method mode
     */
    @Test
    public void test_module_mode()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_out\\FanOut.java", true);
        FanOutTracker tester = new FanOutTracker(new String[] {"-module", "-min", "0"});
        tester.run(launcher);
        String res = tester.toJson();
        String resString = res.substring(res.indexOf("result") + 8, res.length() - 2);
        Gson gson = new Gson();
        HashMap<String, Integer> resHash = gson.fromJson(resString, new TypeToken<HashMap<String, Integer>>() {}.getType());

        assertEquals("Module mode contains score of 1 for FanOut1", 1, (long) resHash.get("FanOut1"));
        assertEquals("Module mode contains score of 1 for FanOut2", 1, (long) resHash.get("FanOut2"));
        assertEquals("Module mode contains score of 2 for FanOut3", 2, (long) resHash.get("FanOut3"));
    }
//
    /**
     * Test that we can correctly adjust the minimum threshold for fan out
     */
    @Test
    public void test_threshold_adjustment() {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_out\\FanOut.java", true);
        FanOutTracker tester = new FanOutTracker(new String[] {"-module", "-min", "2"});
        tester.run(launcher);
        String res = tester.toJson();
        String resString = res.substring(res.indexOf("result") + 8, res.length() - 2);
        Gson gson = new Gson();
        HashMap<String, Integer> resHash = gson.fromJson(resString, new TypeToken<HashMap<String, Integer>>() {}.getType());

        assertEquals("Module mode contains score of 2 for FanOut3", 2, (long) resHash.get("FanOut3"));
        assertNull("Module mode no score for FanOut1", resHash.get("FanOut1"));
        assertNull("Module mode no score for FanOut2", resHash.get("FanOut2"));
    }

    /**
     * Test that commented invocations do not count as a real invocation
     */
    @Test
    public void test_with_commented_invocations() {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_out\\FanOutCommented.java", true);
        FanOutTracker tester = new FanOutTracker(new String[] {"-min", "0"});
        tester.run(launcher);
        String res = tester.toJson();
        String resString = res.substring(res.indexOf("result") + 8, res.length() - 2);
        Gson gson = new Gson();
        HashMap<String, HashMap<String, Integer>> resHash = gson.fromJson(resString, new TypeToken<HashMap<String, HashMap<String, Integer>>>() {}.getType());

        assertEquals("Method mode contains score of 0 for FanOutCommented()", 0, (long) resHash.get("FanOutCommented").get("FanOutCommented()"));
        assertEquals("Method mode contains score of 1 for test()", 1, (long) resHash.get("FanOutCommented").get("test()"));

    }

    /**
     * Test that fan out only counts a callee once per caller method in method mode
     */
    @Test
    public void test_fan_out_unique_method_mode() {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_out\\FanOut.java", true);
        FanOutTracker tester = new FanOutTracker(new String[] {"-min", "0"});
        tester.run(launcher);
        String res = tester.toJson();
        String resString = res.substring(res.indexOf("result") + 8, res.length() - 2);
        Gson gson = new Gson();
        HashMap<String, HashMap<String, Integer>> resHash = gson.fromJson(resString, new TypeToken<HashMap<String, HashMap<String, Integer>>>() {}.getType());

        assertEquals("Method mode contains score of 3 for fanOut4_1()", 3, (long) resHash.get("FanOut4").get("fanOut4_1()"));
    }

    /**
     * Test that fan out only counts a callee once per caller class in module mode
     */
    @Test
    public void test_fan_out_unique_module_mode() {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_out\\FanOut.java", true);
        FanOutTracker tester = new FanOutTracker(new String[] {"-module", "-min", "0"});
        tester.run(launcher);
        String res = tester.toJson();
        String resString = res.substring(res.indexOf("result") + 8, res.length() - 2);
        Gson gson = new Gson();
        HashMap<String, Integer> resHash = gson.fromJson(resString, new TypeToken<HashMap<String, Integer>>() {}.getType());

        assertEquals("Module mode contains score of 1 for FanOut4", 1, (long) resHash.get("FanOut4"));
    }

    /**
     * Test against 3rd party metric tool. Slight differences in results so test only checks for existence of modules within the threshold
     */
    @Test
    public void test_against_third_party_tool() {
        Launcher launcher = Utilities.importCodeSample("code_samples\\fastjson", true);
        FanOutTracker tester = new FanOutTracker(new String[] {"-min", "10", "-module"});
        tester.run(launcher);
        String res = tester.toJson();
        String resString = res.substring(res.indexOf("result") + 8, res.length() - 2);
        Gson gson = new Gson();
        HashMap<String, Integer> resHash = gson.fromJson(resString, new TypeToken<HashMap<String, Integer>>() {}.getType());

        assertNotNull(resHash.get("com.alibaba.fastjson.JSONPath"));
        assertNotNull(resHash.get("com.alibaba.fastjson.parser.DefaultJSONParser"));
        assertNotNull(resHash.get("com.alibaba.fastjson.parser.ParserConfig"));
        assertNotNull(resHash.get("com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory"));
        assertNotNull(resHash.get("com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.ASMSerializerFactory"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.CalendarCodec"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.CollectionCodec"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.FieldSerializer"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.JSONSerializer"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.ObjectArrayCodec"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.SerializeConfig"));
        assertNotNull(resHash.get("com.alibaba.fastjson.util.TypeUtils"));
    }
}
