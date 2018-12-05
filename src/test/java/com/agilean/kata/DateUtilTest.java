package com.agilean.kata;

import mockit.*;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateUtilTest {
    /**
     * Mock某个类方法
     */
    @Test
    public void testGetCurrentDateStr_MockGetCurrentDateStr_DateStrEquals() {
        //DateUtil.class,要Mock的类
        new Expectations(DateUtil.class) {
            {
                //要Mock的方法now,其他方法DateUtil.class
                DateUtil.getCurrentDateStr();
                //期望方法返回的结果
                result = mockDate();
            }
        };
        assertEquals("2010-07-22 15:52:55", DateUtil.getCurrentDateStr());
    }

    /**
     * Mock 某个类方法根据不同参数返回不同值
     */
    @Test
    public void testGetCurrentDateStr_MockNowFormatType_DateStrEquals() {
        new Expectations(DateUtil.class) {
            {
                DateUtil.getCurrentDateStrByFormatType(anyInt);
                result = new Delegate() {
                    public String getCurrentDateStrByFormatType(int type) {
                        if (type == 1) {
                            return "2010/07/22 15:52:55";
                        } else {
                            return "2010-07-22 15:52:55";
                        }
                    }
                };
            }
        };
        assertEquals("2010-07-22 15:52:55", DateUtil.getCurrentDateStrByFormatType(2));

    }

    public static Date mockDate() {
        Calendar c = Calendar.getInstance();
        c.set(2010, 6, 22, 15, 52, 55);
        return c.getTime();
    }
}

