package com.ziv.contact;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.ziv.contact", appContext.getPackageName());

    }
}

/**
 * Tips:
 * <p>
 * 1. @Test : 测试方法，测试程序会运行的方法，后边可以跟参数代表不同的测试，如(expected=XXException.class) 异常测试，(timeout=xxx)超时测试
 * 2. @Ignore : 被忽略的测试方法
 * 3. @Before: 每一个测试方法之前运行
 * 4. @After : 每一个测试方法之后运行
 * 5. @BeforeClass: 所有测试开始之前运行
 * 6. @AfterClass: 所有测试结束之后运行
 * fail方法是指测试失败
 * assertEquals测试2个参数是否相等，具体参考相应API
 */