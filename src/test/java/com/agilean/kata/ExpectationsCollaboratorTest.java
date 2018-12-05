package com.agilean.kata;


import org.junit.jupiter.api.Test;
import mockit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpectationsCollaboratorTest {

    @Mocked ExpectationsCollaboratorWith mockWith;
    @Mocked ExpectationsCollaboratorTimes mockTimes;
    @Mocked ExpectationsCollaboratorNulls mockNulls;
    @Mocked ExpectationsCollaboratorResultAndReturns mockResultAndReturns;
    @Mocked ExpectationsCollaboratorDelegate mockDelegate;

    @Test
    public void testMethodForAny_InputAnyParams_ShouldBeOK() throws Exception {
        final ExpectationsCollaboratorAny mockInner = new ExpectationsCollaboratorAnyImpl();

        new Expectations(ExpectationsCollaboratorAnyImpl.class) {
            {
                mockInner.methodForAny1(anyString, anyInt, anyBoolean);
                result = "any";
            }
        };

        assertEquals("any", mockInner.methodForAny1("barfooxyz", 0, Boolean.FALSE));
        mockInner.methodForAny2(2L, new ArrayList<String>());

        new FullVerifications() {
            {
                mockInner.methodForAny2(anyLong, (List<String>) any);
            }
        };
    }

    @Test
    public void testMethodForWith_InputWithParams_ShouldBeOK() throws Exception {
        new Expectations() {{
            mockWith.methodForWith1(withSubstring("foo"), withNotEqual(1));
            result = "with";
        }};

        assertEquals("with", mockWith.methodForWith1("barfooxyz", 2));
        mockWith.methodForWith2(Boolean.TRUE, new ArrayList<String>());

        new Verifications() {{
            mockWith.methodForWith2((Boolean) withNotNull(), withInstanceOf(List.class));
        }};
    }

    @Test
    public void testMethodForNulls_InputNullParams_ShouldBeOK(){
        new Expectations() {{
            mockNulls.methodForNulls1(anyString, null);
            result = "null";
        }};

        assertEquals("null", mockNulls.methodForNulls1("blablabla", new ArrayList<String>()));
        mockNulls.methodForNulls2("blablabla", null);

        new Verifications() {{
            mockNulls.methodForNulls2(anyString, (List<String>) withNull());
        }};
    }

    @Test
    public void testMethodForTimes_CallMethodTimes_ShouldBeOK() {
        new Expectations() {{
            mockTimes.methodForTimes1(); times = 2;
            mockTimes.methodForTimes2();
        }};

        mockTimes.methodForTimes1();
        mockTimes.methodForTimes1();
        mockTimes.methodForTimes2();
        mockTimes.methodForTimes3();
        mockTimes.methodForTimes3();
        mockTimes.methodForTimes3();

        new Verifications() {{
            mockTimes.methodForTimes3();
            minTimes = 1;
            maxTimes = 3;
        }};
    }

    @Test
    public void testMethodForWith_CallMethod_ResultAsMockReturns(){
        new Expectations() {{
            mockResultAndReturns.methodReturnsString(); returns("foo", new Exception(), "bar");
            mockResultAndReturns.methodReturnsInt(); returns(1, 2, 3);
        }};

        assertEquals( "foo", mockResultAndReturns.methodReturnsString());
        try {
            mockResultAndReturns.methodReturnsString();
        } catch (Exception e) { }
        assertEquals( "bar", mockResultAndReturns.methodReturnsString());
        assertEquals(1, mockResultAndReturns.methodReturnsInt());
        assertEquals( 2, mockResultAndReturns.methodReturnsInt());
        assertEquals( 3, mockResultAndReturns.methodReturnsInt());
    }

    @Test
    public void testMethodForDelegate_CallMethod_ResultAsMockReturns() {
        new Expectations() {{

            mockDelegate.methodForDelegate(anyInt);
            result = new Delegate() {
                public int delegate(int i) throws Exception {
                    if(i < 3) {
                        return 5;
                    } else {
                        throw new Exception();
                    }
                }
            };
        }};

        assertEquals( 5, mockDelegate.methodForDelegate(1));
        try {
            mockDelegate.methodForDelegate(3);
        } catch (Exception e) { }
    }

    private class ExpectationsCollaboratorAnyImpl implements ExpectationsCollaboratorAny {
        public String methodForAny1(String s, int i, Boolean b){
            return "";
        }
        public  void methodForAny2(Long l, List<String> lst){

        }
    }

    private class Model {
        public String getInfo(){
            return "info";
        }
    }
}
