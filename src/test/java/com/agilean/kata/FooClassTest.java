package com.agilean.kata;

import mockit.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FooClassTest {
    @Tested
    FooClass fooClass;

    @Injectable
    FooService fooServiceMock;

    @Injectable
    StaticMethodClass staticMethodClassMock;

    @Injectable
    InitClass InitClassMock;

    @Injectable
    FinalClass finalClassMock;

    @Test
    public void testFooClass_CallMethodMsg_ShouldBeOK(){
        new MockUp<InitClass>() {
            @Mock
            public void $init(){

            }
            @Mock
            public String methodMsg(){
                return "init mock message";
            }

        };


        fooClass.constructorTest();
        fooClass.methodMsg();

        new Verifications() {
            {
                assertTrue(InitClassMock.methodMsg().equals("init mock message"));
            }
        };
    }

    @Test
    public void testFooClass_CallDoSth_ShouldBeOK(){
        new Expectations() {{
            fooServiceMock.sumService();
            result = "mock message from expectations";
        }};

        fooClass.doSth();

    }

    @Test
    public void testFooClass_CallStaticMethod_EqualsMockString(){

        new MockUp<StaticMethodClass>() {
            @Mock
            public StaticMethodClass getInstance(){
                return staticMethodClassMock;
            }

            @Mock
            public String getMessage(){
                return "Mocked Message";
            }
        };

        new Verifications() {
            {
                assertTrue(staticMethodClassMock.getMessage().equals("Mocked Message"));
            }
        };
    }

    @Test
    public void testFooClass_CallFinalMethod_EqualsMockString(){

        new MockUp<FinalClass>() {
            @Mock
            public void $init(){

            }

            @Mock
            public String finalMethod(){
                return "final mock message";
            }
        };
        fooClass.finalClassMethodCall();

        new Verifications() {{
            assertTrue(finalClassMock.finalMethod().equals("final mock message"));

        }};
    }
}
