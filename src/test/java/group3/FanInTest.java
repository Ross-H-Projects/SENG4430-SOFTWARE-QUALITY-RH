package group3;


import group3.metric_analysis.fan_in.FanInTracker;
import org.junit.Test;
import spoon.Launcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertTrue(res.contains("\"FanIn1_1()\":1"));
        assertTrue(res.contains("\"FanIn2_2()\":1"));
        assertTrue(res.contains("\"FanIn2_1()\":2"));
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
        assertTrue(res.contains("\"FanIn1\":2"));
        assertTrue(res.contains("\"FanIn2\":2"));
        assertTrue(res.contains("\"FanIn3\":0"));
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
        assertFalse(res.contains("\"FanIn1\":2"));
        assertFalse(res.contains("\"FanIn2\":2"));
        assertTrue(res.contains("\"FanIn3\":0"));
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

        assertTrue(res.contains("\"FanIn1()\":2"));
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

        assertTrue(res.contains("\"FanIn1\":2"));
    }

    
    /**
     * Test against 3rd party metric tool. Slight differences in results so test only checks for existence of modules within the threshold
     */
    @Test
    public void test_against_third_party_tool() {
        Launcher launcher = Utilities.importCodeSample("C:\\Users\\Owner\\Documents\\SENG4430\\fastjson-master\\src\\main\\java\\com\\alibaba\\fastjson\\", true);
        FanInTracker tester = new FanInTracker(new String[] {"-max", "5", "-module"});
        tester.run(launcher);
        String res = tester.toJson();

        assertTrue(res.contains("\"com.alibaba.fastjson.asm.ByteVector\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.asm.ClassReader\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.asm.ClassWriter\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.asm.FieldWriter\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.asm.Item\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.asm.Label\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.asm.MethodCollector\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.asm.MethodWriter\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.JSONPathException\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.JSONPObject\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.JSONReader\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.JSONWriter\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.parser.DefaultExtJSONParser\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.parser.JSONReaderScanner\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.parser.deserializer.ArrayListTypeFieldDeserializer\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.AnnotationSerializer\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.AppendableSerializer\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.ArraySerializer\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.ASMSerializerFactory\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.AtomicCodec\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.AwtCodec\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.BeforeFilter\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.BigDecimalCodec\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.BigIntegerCodec\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.BooleanCodec\""));
        assertTrue(res.contains("\"com.alibaba.fastjson.serializer.ByteBufferCodec\""));
    }
}
