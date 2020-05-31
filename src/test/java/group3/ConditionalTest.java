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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.assertEquals;

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


    //    @Test
//    public void test_empty_class()
//    {
//        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\ConditionalNestingTestEmptyClass.java", true);
//        ConditionalNestingTracker tester = new ConditionalNestingTracker(new String[] {});
//        tester.run(launcher);
//        String res = tester.toJson();
//        assertEquals("{ConditionalTest={ConditionalTest()=0}}", res);
//    }
//    @Test
//    public void test_single_method_class()
//    {
//        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\ConditionalNestingTestSingleMethodClass.java", true);
//        ConditionalNestingTracker tester = new ConditionalNestingTracker(new String[] {});
//        tester.run(launcher);
//        String res = tester.toJson();
//        assertEquals("{ConditionalTest={test1()=3, main(java.lang.String[])=0, ConditionalTest()=0}}", res);
//    }
//    @Test
//    public void test_multiple_method_class()
//    {
//        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\ConditionalNestingTestMultipleMethodClass.java", true);
//        ConditionalNestingTracker tester = new ConditionalNestingTracker(new String[] {});
//        tester.run(launcher);
//        String res = tester.toJson();
//        assertEquals("{ConditionalTest={test1()=3, test2()=4, main(java.lang.String[])=0, ConditionalTest()=0}}", res);
//    }
}
