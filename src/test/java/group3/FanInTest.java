package group3;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import group3.metric_analysis.fan_in.FanInTracker;
import org.junit.Test;
import spoon.Launcher;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Unit tests for Fan In
 */
public class FanInTest
{
    /**
     * Test that we can the correct result for FanIn.java in method mode
     */
    @Test
    public void test_method_mode()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_in\\FanIn.java", true);
        FanInTracker tester = new FanInTracker(new String[] {"-max", "10"});
        tester.run(launcher);
        String res = tester.toJson();
        String resString = res.substring(res.indexOf("result") + 8, res.length() - 2);
        Gson gson = new Gson();
        HashMap<String, HashMap<String, Integer>> resHash = gson.fromJson(resString, new TypeToken<HashMap<String, HashMap<String, Integer>>>() {}.getType());

        assertEquals("Method mode contains score of 1 for FanIn1_1()", 1, (long) resHash.get("FanIn1").get("FanIn1_1()"));
        assertEquals("Method mode contains score of 1 for FanIn2_2()", 1, (long) resHash.get("FanIn2").get("FanIn2_2()"));
        assertEquals("Method mode contains score of 2 for FanIn2_1()", 2, (long) resHash.get("FanIn2").get("FanIn2_1()"));
    }


    /**
     * Test that we can the correct result for FanIn.java in method mode
     */
    @Test
    public void test_module_mode()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_in\\FanIn.java", true);
        FanInTracker tester = new FanInTracker(new String[] {"-module", "-max", "10"});
        tester.run(launcher);
        String res = tester.toJson();
        String resString = res.substring(res.indexOf("result") + 8, res.length() - 2);
        Gson gson = new Gson();
        HashMap<String, Integer> resHash = gson.fromJson(resString, new TypeToken<HashMap<String, Integer>>() {}.getType());

        assertEquals("Module mode contains score of 2 for FanIn1", 2, (long) resHash.get("FanIn1"));
        assertEquals("Module mode contains score of 2 for FanIn2", 2, (long) resHash.get("FanIn2"));
        assertEquals("Module mode contains score of 0 for FanIn3", 0, (long) resHash.get("FanIn3"));
    }
//
    /**
     * Test that we can correctly adjust the maximum threshold for fan in
     */
    @Test
    public void test_threshold_adjustment() {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_in\\FanIn.java", true);
        FanInTracker tester = new FanInTracker(new String[] {"-module", "-max", "1"});
        tester.run(launcher);
        String res = tester.toJson();
        String resString = res.substring(res.indexOf("result") + 8, res.length() - 2);
        Gson gson = new Gson();
        HashMap<String, Integer> resHash = gson.fromJson(resString, new TypeToken<HashMap<String, Integer>>() {}.getType());

        assertEquals("Module mode contains score of 0 for FanIn3", 0, (long) resHash.get("FanIn3"));
        assertNull("Module mode no score for FanIn1", resHash.get("FanIn1"));
        assertNull("Module mode no score for FanIn2", resHash.get("FanIn2"));
    }

    /**
     * Test that fan in only counts a callee once per caller method in method mode
     */
    @Test
    public void test_fan_in_unique_method_mode() {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_in\\FanIn.java", true);
        FanInTracker tester = new FanInTracker(new String[] {"-max", "10"});
        tester.run(launcher);
        String res = tester.toJson();
        String resString = res.substring(res.indexOf("result") + 8, res.length() - 2);
        Gson gson = new Gson();
        HashMap<String, HashMap<String, Integer>> resHash = gson.fromJson(resString, new TypeToken<HashMap<String, HashMap<String, Integer>>>() {}.getType());

        assertEquals("Method mode contains score of 2 for FanIn1()", 2, (long) resHash.get("FanIn1").get("FanIn1()"));
    }

    /**
     * Test that fan in only counts a callee once per caller class in module mode
     */
    @Test
    public void test_fan_in_unique_module_mode() {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_in\\FanIn.java", true);
        FanInTracker tester = new FanInTracker(new String[] {"-module", "-max", "10"});
        tester.run(launcher);
        String res = tester.toJson();
        String resString = res.substring(res.indexOf("result") + 8, res.length() - 2);
        Gson gson = new Gson();
        HashMap<String, Integer> resHash = gson.fromJson(resString, new TypeToken<HashMap<String, Integer>>() {}.getType());

        assertEquals("Module mode contains score of 2 for FanIn1", 2, (long) resHash.get("FanIn1"));
    }


    /**
     * Test against 3rd party metric tool. Slight differences in results so test only checks for existence of modules within the threshold
     */
    @Test
    public void test_against_third_party_tool() {
        Launcher launcher = Utilities.importCodeSample("code_samples\\fastjson\\", true);
        FanInTracker tester = new FanInTracker(new String[] {"-max", "5", "-module"});
        tester.run(launcher);
        String res = tester.toJson();
        String resString = res.substring(res.indexOf("result") + 8, res.length() - 2);
        Gson gson = new Gson();
        HashMap<String, Integer> resHash = gson.fromJson(resString, new TypeToken<HashMap<String, Integer>>() {}.getType());

        assertNotNull(resHash.get("com.alibaba.fastjson.asm.ByteVector"));
        assertNotNull(resHash.get("com.alibaba.fastjson.asm.ClassReader"));
        assertNotNull(resHash.get("com.alibaba.fastjson.asm.ClassWriter"));
        assertNotNull(resHash.get("com.alibaba.fastjson.asm.FieldWriter"));
        assertNotNull(resHash.get("com.alibaba.fastjson.asm.Item"));
        assertNotNull(resHash.get("com.alibaba.fastjson.asm.Label"));
        assertNotNull(resHash.get("com.alibaba.fastjson.asm.MethodCollector"));
        assertNotNull(resHash.get("com.alibaba.fastjson.asm.MethodWriter"));
        assertNotNull(resHash.get("com.alibaba.fastjson.JSONPathException"));
        assertNotNull(resHash.get("com.alibaba.fastjson.JSONPObject"));
        assertNotNull(resHash.get("com.alibaba.fastjson.JSONReader"));
        assertNotNull(resHash.get("com.alibaba.fastjson.JSONWriter"));
        assertNotNull(resHash.get("com.alibaba.fastjson.parser.DefaultExtJSONParser"));
        assertNotNull(resHash.get("com.alibaba.fastjson.parser.JSONReaderScanner"));
        assertNotNull(resHash.get("com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer"));
        assertNotNull(resHash.get("com.alibaba.fastjson.parser.deserializer.ArrayListTypeFieldDeserializer"));
        assertNotNull(resHash.get("com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer"));
        assertNotNull(resHash.get("com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.AnnotationSerializer"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.AppendableSerializer"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.ArraySerializer"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.ASMSerializerFactory"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.AtomicCodec"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.AwtCodec"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.BeforeFilter"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.BigDecimalCodec"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.BigIntegerCodec"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.BooleanCodec"));
        assertNotNull(resHash.get("com.alibaba.fastjson.serializer.ByteBufferCodec"));
    }
}
