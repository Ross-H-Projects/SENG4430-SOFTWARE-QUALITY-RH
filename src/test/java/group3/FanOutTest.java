package group3;


import group3.metric_analysis.fan_out.FanOutTracker;
import org.junit.Test;
import spoon.Launcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


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
        assertTrue(res.contains("\"fanOut1_1()\":3"));
        assertTrue(res.contains("\"fanOut2_2()\":1"));
        assertTrue(res.contains("\"fanOut2_1()\":2"));
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
        assertTrue(res.contains("\"FanOut1\":1"));
        assertTrue(res.contains("\"FanOut2\":1"));
        assertTrue(res.contains("\"FanOut3\":2"));
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
        assertFalse(res.contains("\"FanOut1\":1"));
        assertFalse(res.contains("\"FanOut2\":1"));
        assertTrue(res.contains("\"FanOut3\":2"));
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
        assertTrue(res.contains("\"FanOutCommented()\":0"));
        assertTrue(res.contains("\"test()\":1"));
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
        assertTrue(res.contains("\"fanOut4_1()\":3"));
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

        assertTrue(res.contains("\"FanOut4\":1"));
    }

    /**
     * Test against 3rd party metric tool. Slight differences in results so test only checks for existence of modules within the threshold
     */
    @Test
    public void test_against_third_party_tool() {
        Launcher launcher = Utilities.importCodeSample("C:\\Users\\Owner\\Documents\\SENG4430\\fastjson-master\\src\\main\\java\\com\\alibaba\\fastjson", true);
        FanOutTracker tester = new FanOutTracker(new String[] {"-min", "10", "-module"});
        tester.run(launcher);
        String res = tester.toJson();

        assertTrue(res.contains("\"com.alibaba.fastjson.JSONPath\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.parser.DefaultJSONParser\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.parser.ParserConfig\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.ASMSerializerFactory\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.CalendarCodec\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.CollectionCodec\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.FieldSerializer\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.JSONSerializer\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.ObjectArrayCodec\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.SerializeConfig\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.util.TypeUtils\""));
    }
}
