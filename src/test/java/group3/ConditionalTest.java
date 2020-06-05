package group3;


import group3.metric_analysis.conditional_nesting.ConditionalNestingAnalysis;
import group3.metric_analysis.conditional_nesting.ConditionalNestingTracker;
import group3.metric_analysis.fan_out.FanOutAnalysis;
import group3.metric_analysis.fan_out.FanOutTracker;
import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.code.CtIf;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for Depth of Conditional Nesting Algorithm.
 */
public class ConditionalTest
{
    /**
     * Test getMethods() returns all methods of a class including the constructor. This test should return class methods
     */
    @Test
    public void test_analysis_get_methods_1()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_methods\\TestGetMethods.java", true);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);

        //gets methods from class
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        ArrayList<CtExecutable<?>> methodsArrayList = new ArrayList<>(methodsCollection);

        //gets constructor from class
        Set<? extends CtConstructor<?>> constructorCollection = classObject.getConstructors();
        ArrayList<CtExecutable<?>> constructorArrayList = new ArrayList<CtExecutable<?>>(constructorCollection);
        //adds constructor to methods
        methodsArrayList.addAll(constructorArrayList);

        ArrayList<CtExecutable<?>> methodsArrayListFromClass =  analysis.getMethodsFromClassObject(classObject);
//        Expected Output of Object as a String
//        [public void test1() {
//        }, public void test2() {
//        }, public TestGetMethods() {
//        }]
        assertEquals(methodsArrayList, methodsArrayListFromClass);
    }
    /**
     * Test getMethods() returns all methods of a class including the constructor. This test should return class methods and main method
     */
    @Test
    public void test_analysis_get_methods_2()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_methods\\TestGetMethods2.java", true);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);

        //gets methods from class
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        ArrayList<CtExecutable<?>> methodsArrayList = new ArrayList<>(methodsCollection);

        //gets constructor from class
        Set<? extends CtConstructor<?>> constructorCollection = classObject.getConstructors();
        ArrayList<CtExecutable<?>> constructorArrayList = new ArrayList<CtExecutable<?>>(constructorCollection);
        //adds constructor to methods
        methodsArrayList.addAll(constructorArrayList);

        ArrayList<CtExecutable<?>> methodsArrayListFromClass =  analysis.getMethodsFromClassObject(classObject);
//        Expected Output of Object as a String
//        [public static void main(java.lang.String[] args) {
//        }, public void test1() {
//        }, public void test2() {
//        }, public TestGetMethods2() {
//        }]
        assertEquals(methodsArrayList, methodsArrayListFromClass);
    }
    /**
     * Test getMethods() returns all methods of a class including the constructor. This test should return class methods and constructor
     */
    @Test
    public void test_analysis_get_methods_3()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_methods\\TestGetMethods3.java", true);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);

        //gets methods from class
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        ArrayList<CtExecutable<?>> methodsArrayList = new ArrayList<>(methodsCollection);

        //gets constructor from class
        Set<? extends CtConstructor<?>> constructorCollection = classObject.getConstructors();
        ArrayList<CtExecutable<?>> constructorArrayList = new ArrayList<CtExecutable<?>>(constructorCollection);
        //adds constructor to methods
        methodsArrayList.addAll(constructorArrayList);

        ArrayList<CtExecutable<?>> methodsArrayListFromClass =  analysis.getMethodsFromClassObject(classObject);
//        Expected Output of Object as a String
//        [public void test1() {
//        }, public void test2() {
//        }, public TestGetMethods3() {
//        }]
        assertEquals(methodsArrayList, methodsArrayListFromClass);
    }

    /**
     * Test getFirstIfStatementFromCodeBlock() returns first CtIf statement from CtCodeBlock. If non exists return null
     */
    @Test
    public void test_get_first_if_statement_from_code_block_1()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_analysis_behaviour\\TestGetFirstIfStatementFromCodeBlock.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testGetFirstIfStatementFromCodeBlock1").get(0);
        CtIf expectedIfStatement = Query.getElements(method, new TypeFilter<CtIf>(CtIf.class)).get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        CtIf ifStatement = analysis.getFirstIfStatementFromCodeBlock(method.getDirectChildren().get(1));

        assertEquals(expectedIfStatement, ifStatement);
    }
    /**
     * Test getFirstIfStatementFromCodeBlock() returns first CtIf statement from CtCodeBlock. If non exists return null
     */
    @Test
    public void test_get_first_if_statement_from_code_block_2()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_analysis_behaviour\\TestGetFirstIfStatementFromCodeBlock.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testGetFirstIfStatementFromCodeBlock2").get(0);
//        CtIf expectedIfStatement = Query.getElements(method, new TypeFilter<CtIf>(CtIf.class)).get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        CtIf ifStatement = analysis.getFirstIfStatementFromCodeBlock(method.getDirectChildren().get(1));

        assertEquals(null, ifStatement);
    }
    /**
     * Test getFirstIfStatementFromCodeBlock() returns first CtIf statement from CtCodeBlock. If non exists return null
     */
    @Test
    public void test_get_first_if_statement_from_code_block_3()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_analysis_behaviour\\TestGetFirstIfStatementFromCodeBlock.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testGetFirstIfStatementFromCodeBlock3").get(0);
        CtIf expectedIfStatement = Query.getElements(method, new TypeFilter<CtIf>(CtIf.class)).get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        CtIf ifStatement = analysis.getFirstIfStatementFromCodeBlock(method.getDirectChildren().get(1));

        assertEquals(expectedIfStatement, ifStatement);
    }
    /**
     * Test getFirstIfStatementFromCodeBlock() returns first CtIf statement from CtCodeBlock. If non exists return null
     */
    @Test
    public void test_get_first_if_statement_from_code_block_4()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_analysis_behaviour\\TestGetFirstIfStatementFromCodeBlock.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testGetFirstIfStatementFromCodeBlock4").get(0);
        CtIf expectedIfStatement = Query.getElements(method, new TypeFilter<CtIf>(CtIf.class)).get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        CtIf ifStatement = analysis.getFirstIfStatementFromCodeBlock(method.getDirectChildren().get(1));

        assertEquals(expectedIfStatement, ifStatement);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_1()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_1").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 1);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_2()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_2").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 1);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_3()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_3").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 1);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_4()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_4").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 1);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_5()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_5").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 1);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_6()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_6").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 1);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_7()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_7").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 2);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_8()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_8").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 2);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_9()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_9").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 2);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_10()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_10").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 2);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_11()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_11").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 2);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_12()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_12").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 2);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_13()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_13").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 2);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_14()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_14").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 2);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_15()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_15").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 2);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_16()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_16").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 2);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_doDepth_17()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_17").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 2);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_if_behaviour_depth_3_1()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_depth_3_1").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 3);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_if_behaviour_depth_3_2()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_depth_3_2").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 3);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_if_behaviour_depth_3_3()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_depth_3_3").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 3);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_if_behaviour_depth_3_4()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_depth_3_4").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 3);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_if_behaviour_depth_3_5()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_depth_3_5").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 3);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_if_behaviour_depth_3_6()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_depth_3_6").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 3);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_if_behaviour_depth_3_7()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_depth_3_7").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 3);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_if_behaviour_depth_3_8()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_depth_3_8").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 3);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_if_behaviour_depth_4_1()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_depth_4_1").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 4);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_if_behaviour_depth_4_2()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_depth_4_2").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 4);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_if_behaviour_depth_4_3()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_depth_4_3").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 4);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_if_behaviour_depth_4_4()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_depth_4_4").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 4);
    }
    /**
     * Test depthOfIfStatements behaves as expected
     */
    @Test
    public void test_if_behaviour_depth_4_5()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\test_if_behaviour\\TestIfBehaviour.java", true);
        CtClass classObject = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod)classObject.getMethodsByName("testIfBehaviour_depth_4_5").get(0);

        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis();
        assertEquals(analysis.doDepth(method), 4);
    }

    /**
     * Test comparison between third party application output and Condition Output
     */
    @Test
    public void test_against_third_party_application() {
        Launcher launcher = Utilities.importCodeSample("code_samples\\fastjson", true);
        ConditionalNestingAnalysis analysis = new ConditionalNestingAnalysis(6);
        analysis.performAnalysis(launcher);
        int count = 0;
        for (Map.Entry<String, HashMap<String, Integer>> classObject : analysis.getClassConditionalNestingScores().entrySet()) {
            String classString = classObject.getKey();
            HashMap<String, Integer> MethodMaxDepth = classObject.getValue();
            count += MethodMaxDepth.size();
        }
        //Checking that the returned value was less than compared to the third party was as close as I could get.
        //It is possible that the third party application took into account switch statements, depth of loops etc which
        //is why a greater value was returned from the third party application
        assertTrue(count<=7);
    }
    /**
     * Test params changing output
     */
    @Test
    public void test_minDepthParam() {
        int minDepth;
        Launcher launcher = Utilities.importCodeSample("code_samples\\fastjson", true);
        ConditionalNestingAnalysis analysis;

        String json1;
        String json2;
        String json3;

        minDepth = 5;
        analysis = new ConditionalNestingAnalysis(minDepth);
        analysis.performAnalysis(launcher);
        json1 = analysis.getClassConditionalNestingScoresJsonUnformatted();

        minDepth = 6;
        analysis = new ConditionalNestingAnalysis(minDepth);
        analysis.performAnalysis(launcher);
        json2 = analysis.getClassConditionalNestingScoresJsonUnformatted();

        minDepth = 7;
        analysis = new ConditionalNestingAnalysis(minDepth);
        analysis.performAnalysis(launcher);
        json3 = analysis.getClassConditionalNestingScoresJsonUnformatted();

        assertTrue(json1.length() >= json2.length() && json2.length() >= json3.length());
    }
}
