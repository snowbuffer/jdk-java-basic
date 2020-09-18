package com.snowbuffer.study.java.spring.annotation.tx;

import com.snowbuffer.study.java.spring.annotation.common.BeanDefinitionPrintUtil;
import com.snowbuffer.study.java.spring.annotation.tx.service.UserService;
import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 20:16
 */
public class TxConfigTest {

    public static class MethodRuleCustom implements MethodRule {

        @Override
        public Statement apply(Statement base, FrameworkMethod method, Object target) {
            System.out.println("--");
            return base;
        }
    }

    public static class TestRuleCustom implements TestRule {


        @Override
        public Statement apply(Statement base, Description description) {
            System.out.println("++");
            return base;
        }
    }

    @ClassRule
    public static final TestRuleCustom tes = new TestRuleCustom();

    @Rule
    public final MethodRuleCustom methods = new MethodRuleCustom();

    @Before
    public void beforeeee() {
        System.out.println("++before");
    }

    @After
    public void aftereeee() {
        System.out.println("++after");
    }

    @AfterClass
    public static void aftereeeeClass() {
        System.out.println("++afterClass");
    }

    @BeforeClass
    public static void beforeeeeclass() {
        System.out.println("++beforeClass");
    }

    @Test
    public void test() {
        System.out.println("==");
        System.out.println("==");
    }

    @Test
    public void testNormal() {
        BeanDefinitionPrintUtil.print(TxConfig.class);
        UserService userService = BeanDefinitionPrintUtil.getApplicationContext().getBean(UserService.class);
        userService.query("one");
        try {
            userService.update("one", "update");
        } catch (Exception e) {
            e.printStackTrace();
        }
        userService.query("one");
    }

    @Test
    public void testFailure() {
        BeanDefinitionPrintUtil.print(TxConfig.class);
        UserService userService = BeanDefinitionPrintUtil.getApplicationContext().getBean(UserService.class);
        userService.query("one");
        try {
            userService.update("one", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userService.query("one");
    }
}