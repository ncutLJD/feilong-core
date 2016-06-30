/*
 * Copyright (C) 2008 feilong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.core.bean;

import static com.feilong.core.bean.ConvertUtil.toLong;
import static com.feilong.core.bean.ConvertUtil.toMap;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.StringUtil;
import com.feilong.test.User;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class ConvertUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.3.0
 */
public class ConvertUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertUtilTest.class);

    /**
     * Test to iterator.
     */
    @Test
    public void testToIterator(){
        // *************************逗号分隔的数组********************************
        LOGGER.debug(StringUtils.center("逗号分隔的数组", 60, "*"));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator("1,2")));

        // ************************map*********************************
        LOGGER.debug(StringUtils.center("map", 60, "*"));
        Map<String, String> map = new HashMap<String, String>();

        map.put("a", "1");
        map.put("b", "2");

        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator(map)));

        // ***************************array******************************
        LOGGER.debug(StringUtils.center("array", 60, "*"));
        Object[] array = { "5", 8 };
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator(array)));
        // ***************************collection******************************
        LOGGER.debug(StringUtils.center("collection", 60, "*"));
        Collection<String> collection = new ArrayList<String>();
        collection.add("aaaa");
        collection.add("nnnnn");

        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator(collection)));

        // **********************enumeration***********************************
        LOGGER.debug(StringUtils.center("enumeration", 60, "*"));
        Enumeration<Object> enumeration = new StringTokenizer("this is a test");
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator(enumeration)));
    }

    /**
     * TestConvertUtilTest.
     */
    @Test
    public void testConvertUtilTest(){
        int[] i2 = { 1, 2 };
        LOGGER.debug(JsonUtil.format(IteratorUtils.getIterator(i2), 0, 0));

        Iterator<Integer> iterator = ConvertUtil.toIterator(i2);
        LOGGER.debug(JsonUtil.format(iterator, 0, 0));
    }

    /**
     * Test to big decimal.
     */
    @Test
    public void testToBigDecimal(){
        assertEquals(null, ConvertUtil.toBigDecimal(null));
        assertEquals(BigDecimal.valueOf(1111), ConvertUtil.toBigDecimal(1111));
        assertEquals(BigDecimal.valueOf(0.1), ConvertUtil.toBigDecimal(0.1));
    }

    /**
     * Test.
     */
    @Test
    public void test(){
        BigDecimal a = ConvertUtil.toBigDecimal("1.000000");
        BigDecimal b = new BigDecimal(1);
        LOGGER.debug(a.compareTo(b) + "");
        LOGGER.debug(a.equals(b) + "");
    }

    /**
     * Test to longs.
     */
    @Test
    public void testToLongs(){
        assertArrayEquals(ConvertUtil.<Long> toArray(1L, 2L, 3L), ConvertUtil.toLongs("1,2,3"));
        assertArrayEquals(ConvertUtil.<Long> toArray(1L, 2L, 3L), ConvertUtil.toLongs(new String[] { "1", "2", "3" }));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toLongs(new String[] { "1", null, "2", "3" }), 0, 0));

        assertSame(null, ConvertUtil.toLongs(null));
    }

    /**
     * Test to longs1.
     */
    @Test
    public void testToLongs1(){
        assertArrayEquals(new Long[] { 1L, 2L, 8L }, ConvertUtil.toLongs(ConvertUtil.toList("1", "2", "8")));
    }

    /**
     * Test to strings.
     */
    @Test
    public void testToStrings(){
        LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings("{5,4, 8,2;8 9_5@3`a}"), 0, 0));

        assertArrayEquals(new String[] { "1", "2", "5" }, ConvertUtil.toStrings(new Integer[] { 1, 2, 5 }));
        assertArrayEquals(null, ConvertUtil.toStrings(null));

        LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings("null,1,2,3,\"4\",\'aaaa\'")));
    }

    /**
     * Test to integers.
     */
    @Test
    public void testToIntegers(){
        assertEquals(null, ConvertUtil.toIntegers(null));

        LOGGER.debug(JsonUtil.format(ConvertUtil.toIntegers(new String[] { "1", "2", "3" }), 0, 0));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIntegers(new String[] { "1", null, "2", "3" }), 0, 0));

        assertArrayEquals(new Integer[] { 1, 2, 3 }, ConvertUtil.toIntegers("1,2,3"));
    }

    /**
     * Test to integers1.
     */
    @Test
    public void testToIntegers1(){
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIntegers(ConvertUtil.toList("1", "2", "8")), 0, 0));
    }

    /**
     * Test to integers2.
     */
    @Test
    public void testToIntegers2(){
        assertArrayEquals(null, ConvertUtil.toIntegers(null));
    }

    /**
     * Test to long.
     */
    @Test
    public void testToLong(){
        assertEquals((Object) 1L, ConvertUtil.toLong("1"));
        assertEquals(null, toLong(null));
        LOGGER.debug("" + ConvertUtil.toLong(new String[] { "1", "2", "3" }));
        LOGGER.debug("" + ConvertUtil.toLong(new String[] { "1", null, "2", "3" }));
        LOGGER.debug("" + ConvertUtil.toLong("1,2,3"));
    }

    /**
     * Test to boolean.
     */
    @Test
    public void testToBoolean(){
        assertEquals(true, ConvertUtil.toBoolean(1L));
        assertEquals(true, ConvertUtil.toBoolean("1"));
        assertEquals(null, ConvertUtil.toBoolean(null));
        assertEquals(false, ConvertUtil.toBoolean("9"));
        assertEquals(false, ConvertUtil.toBoolean(new String[] { "0", "1", "2", "3" }));
        assertEquals(true, ConvertUtil.toBoolean(new String[] { "1", null, "2", "3" }));
        assertEquals(false, ConvertUtil.toBoolean("1,2,3"));
    }

    /**
     * Test to integer.
     */
    @Test
    public void testToInteger(){
        LOGGER.debug("" + ConvertUtil.toInteger(new String[] { "1", "2", "3" }));
        assertEquals((Object) 1, ConvertUtil.toInteger("1"));
        assertEquals(null, ConvertUtil.toInteger(null));
        LOGGER.debug("" + ConvertUtil.toInteger(new String[] { "1", null, "2", "3" }));
        LOGGER.debug("" + ConvertUtil.toInteger("1,2,3"));
    }

    /**
     * Test to integer.
     */
    @Test
    public void testToInteger2(){
        assertEquals(null, ConvertUtil.toInteger(null));
        assertEquals(8, ConvertUtil.toInteger(8L).intValue());
        assertEquals(8, ConvertUtil.toInteger("8").intValue());
        assertEquals(8, ConvertUtil.toInteger(new BigDecimal("8")).intValue());
    }

    /**
     * Test to integer3.
     */
    @Test
    public void testToInteger3(){
        assertEquals(1, ConvertUtil.toInteger(null, 1).intValue());
        assertEquals(8, ConvertUtil.toInteger(8L, 1).intValue());
        assertEquals(8, ConvertUtil.toInteger("8", 1).intValue());
        assertEquals(8, ConvertUtil.toInteger(new BigDecimal("8"), 1).intValue());
    }

    /**
     * Test to intege4.
     */
    @Test
    public void testToIntege4(){
        assertEquals(null, ConvertUtil.toInteger("aaaa"));
    }

    /**
     * Test to intege5.
     */
    @Test
    public void testToIntege5(){
        assertEquals(1, ConvertUtil.toInteger("aaaa", 1).intValue());
    }

    /**
     * To t test.
     */
    @Test
    public void testConvert(){
        String[] tokenizeToStringArray = StringUtil.tokenizeToStringArray("6", "_");

        LinkedList<Serializable> linkedList = new LinkedList<Serializable>();

        for (String string : tokenizeToStringArray){
            Serializable t = ConvertUtil.convert(string, Serializable.class);
            LOGGER.debug(t.getClass().getCanonicalName());
            linkedList.add(t);
        }

        Serializable l = 6L;

        LOGGER.debug("linkedList:{},contains:{},{}", linkedList, l, linkedList.contains(l));
    }

    /**
     * To t test.
     */
    @Test
    public void testConvert1(){
        String[] strings = null;
        Serializable t = ConvertUtil.toArray(strings, Serializable.class);
        assertEquals(null, t);
    }

    /**
     * Test convert3.
     */
    @Test(expected = NullPointerException.class)
    public void testConvert3(){
        String[] strings = ConvertUtil.toArray("");
        ConvertUtil.toArray(strings, null);
    }

    /**
     * Test convert2.
     */
    @Test
    public void testConvert2(){
        assertEquals(1, ConvertUtil.convert("1", Integer.class).intValue());
        assertEquals(1, ConvertUtil.convert("1", Long.class).intValue());
        assertEquals(0, ConvertUtil.convert("", Integer.class).intValue());
    }

    /**
     * Test to string2.
     */
    @Test
    public void testToString2(){
        Integer[] int1 = { 2, null, 1, null };
        LOGGER.debug(ConvertUtil.toString(int1));

        ArrayConverter arrayConverter = new ArrayConverter(ArrayUtils.EMPTY_INT_ARRAY.getClass(), new IntegerConverter());
        arrayConverter.setOnlyFirstToString(false);
        arrayConverter.setDelimiter(',');
        arrayConverter.setAllowedChars(new char[] { '.', '-' });
        LOGGER.debug(arrayConverter.convert(String.class, int1));
    }

    /**
     * Test convert array.
     */
    @Test
    public void testConvertArray(){
        String[] int1 = { "2", "1" };
        assertArrayEquals(new Long[] { 2L, 1L }, ConvertUtil.toArray(int1, Long.class));
    }

    /**
     * Test to string.
     */
    @Test
    public void testToString33(){
        ToStringConfig toStringConfig = new ToStringConfig(",");
        Object[] arrays = { "222", "1111" };
        assertEquals("222,1111", ConvertUtil.toString(toStringConfig, arrays));

        Integer[] array1 = { 2, 1 };
        assertEquals("2,1", ConvertUtil.toString(toStringConfig, array1));

        Integer[] array2 = { 2, 1, null };
        toStringConfig = new ToStringConfig(",");
        toStringConfig.setIsJoinNullOrEmpty(false);
        assertEquals("2,1", ConvertUtil.toString(toStringConfig, array2));

        Integer[] array3 = { 2, null, 1, null };
        toStringConfig = new ToStringConfig(",");
        toStringConfig.setIsJoinNullOrEmpty(false);
        assertEquals("2,1", ConvertUtil.toString(toStringConfig, array3));
    }

    /**
     * Test to string2.
     */
    @Test
    public void testToString22(){
        int[] int1 = { 2, 1 };
        assertEquals("2,1", ConvertUtil.toString(new ToStringConfig(","), int1));
        assertEquals("2", ConvertUtil.toString(new ToStringConfig(","), 2));
        assertEquals(",,,", ConvertUtil.toString(new ToStringConfig(",", true), ",", ","));
        assertEquals("2,", ConvertUtil.toString(new ToStringConfig(",", true), new Integer(2), null));
    }

    /**
     * To array.
     */
    @Test
    public void testToArray0(){
        List<String> list = ConvertUtil.toList("xinge", "feilong");
        assertArrayEquals(new String[] { "xinge", "feilong" }, ConvertUtil.toArray(list, String.class));
    }

    /**
     * To array2.
     */
    @Test
    public void testToArray(){
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        LOGGER.debug(JsonUtil.format(ConvertUtil.toArray(user1, user2)));

    }

    /**
     * Test to array1.
     */
    @Test
    public void testToArray1(){
        assertArrayEquals(new String[] { "xinge", "feilong" }, ConvertUtil.toArray("xinge", "feilong"));
    }

    /**
     * Test to array4.
     */
    @Test
    public void testToArray4(){
        assertArrayEquals(new String[] {}, ConvertUtil.<String> toArray());
        assertArrayEquals(null, ConvertUtil.<String> toArray(null));
    }

    /**
     * To list.
     */
    @Test
    public void testToList(){
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, "a", "b");
        Enumeration<String> enumeration = ConvertUtil.toEnumeration(list);

        List<String> list2 = ConvertUtil.toList(enumeration);
        LOGGER.debug(JsonUtil.format(list2, 0, 0));

        enumeration = null;
        assertEquals(Collections.emptyList(), ConvertUtil.toList(enumeration));
    }

    /**
     * Test to enumeration.
     */
    @Test
    public void testToEnumeration(){
        assertEquals(Collections.emptyEnumeration(), ConvertUtil.toEnumeration(null));
    }

    /**
     * Test to map.
     */
    @Test
    public void testToMap(){
        Map<String, String> map = ConvertUtil.toMap(

                        Pair.of("张飞", "丈八蛇矛"),
                        Pair.of("关羽", "青龙偃月刀"),
                        Pair.of("赵云", "龙胆枪"),
                        Pair.of("刘备", "双股剑"));

        assertThat(map, allOf(hasEntry("张飞", "丈八蛇矛"), hasEntry("关羽", "青龙偃月刀"), hasEntry("赵云", "龙胆枪"), hasEntry("刘备", "双股剑")));

    }

    /**
     * Test to map1.
     */
    @Test
    public void testToMap1(){
        Map<String, String> map = ConvertUtil.toMap("张飞", "丈八蛇矛");
        assertThat(map, allOf(notNullValue(), hasEntry("张飞", "丈八蛇矛")));

    }

    /**
     * Test to map2.
     */
    @Test
    public void testToMap2(){
        Map<String, String> map = ConvertUtil.toMap(null, "丈八蛇矛");
        assertThat(map, allOf(notNullValue(), hasEntry(null, "丈八蛇矛")));
    }

    /**
     * Test to map3.
     */
    @Test
    public void testToMap3(){
        Map<String, String> map = ConvertUtil.toMap(
                        ConvertUtil.toList(
                                        new SimpleEntry<>("张飞", "丈八蛇矛"),
                                        new SimpleEntry<>("关羽", "青龙偃月刀"),
                                        new SimpleEntry<>("赵云", "龙胆枪"),
                                        new SimpleEntry<>("刘备", "双股剑")));
        assertThat(map, allOf(hasEntry("张飞", "丈八蛇矛"), hasEntry("关羽", "青龙偃月刀"), hasEntry("赵云", "龙胆枪"), hasEntry("刘备", "双股剑")));

    }

    /**
     * Test to map4.
     */
    @Test
    public void testToMap4(){
        Map<String, String> map = ConvertUtil.toMap(
                        new SimpleEntry<>("张飞", "丈八蛇矛"),
                        new SimpleEntry<>("关羽", "青龙偃月刀"),
                        new SimpleEntry<>("赵云", "龙胆枪"),
                        new SimpleEntry<>("刘备", "双股剑"));
        assertThat(map, allOf(hasEntry("张飞", "丈八蛇矛"), hasEntry("关羽", "青龙偃月刀"), hasEntry("赵云", "龙胆枪"), hasEntry("刘备", "双股剑")));

    }

    /**
     * To list.
     */
    @Test
    public void testToList2(){
        Set<String> set = new HashSet<String>();
        Collections.addAll(set, "a", "a", "b", "b");
        assertThat(ConvertUtil.toList(set), hasItems("a", "b", "b", "b"));
    }

    /**
     * To list3.
     */
    @Test
    public void testToList3(){
        LOGGER.debug("{}", ConvertUtil.toList(ConvertUtil.toList("a", "a", "b", "b")));
    }

    /**
     * To list.
     */
    @Test
    public void testToList4(){
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        LOGGER.debug(JsonUtil.format(ConvertUtil.toList(user1, user2)));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toList((User) null)));
    }

    /**
     * 集合转成字符串.
     */
    @Test
    public void testCollectionToString(){
        List<String> list = ConvertUtil.toList("feilong", "", "xinge");

        ToStringConfig toStringConfig = new ToStringConfig(",");
        toStringConfig.setIsJoinNullOrEmpty(false);

        assertEquals("feilong,xinge", ConvertUtil.toString(toStringConfig, list));
    }

    /**
     * 集合转成字符串.
     */
    @Test
    public void testCollectionToString1(){
        List<String> list = new ArrayList<String>();
        list.add("2548");
        list.add("2548");
        list.add("2548");
        list.add("2548");
        list.add("2548");
        list.add("2548");

        ToStringConfig toStringConfig = new ToStringConfig(SystemUtils.LINE_SEPARATOR);
        LOGGER.debug(ConvertUtil.toString(toStringConfig, list));
    }

    /**
     * Test map to enumeration.
     */
    public void testMapToEnumeration(){
        // Enumeration
        final Map<Object, Object> map = new LinkedHashMap<Object, Object>();
        map.put("jinxin", 1);
        map.put(2, 2);
        map.put("甲", 3);
        map.put(4, 4);
        map.put("jinxin1", 1);
        map.put(21, 2);
        map.put("甲1", 3);
        map.put(41, 4);
        Enumeration<Object> enumeration = ConvertUtil.toEnumeration(map.keySet());
        while (enumeration.hasMoreElements()){
            LOGGER.debug("" + enumeration.nextElement());
        }
    }

    /**
     * Test convert utils test22.
     */
    @Test
    public void testConvertUtilsTest22(){
        LOGGER.debug(ConvertUtils.convert(888.000f));
        LOGGER.debug("{}", ConvertUtils.convert(888.000f, BigDecimal.class));
    }

    /**
     * Test convert5.
     */
    @Test
    public void testConvert5(){
        assertEquals("zh_CN", ConvertUtil.convert("zh_CN", Locale.class));
    }

    /**
     * Test to locale.
     */
    @Test
    public void testToLocale(){
        assertEquals(null, ConvertUtil.toLocale(null));
        assertEquals(Locale.CHINA, ConvertUtil.toLocale("zh_CN"));
    }

    @Test
    public void test1(){
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("name", "feilong");
        map.put("age", 18);
        map.put("country", "china");

        Properties properties = org.apache.commons.collections4.MapUtils.toProperties(map);

        LOGGER.debug("" + properties.get("age"));
        LOGGER.debug(properties.getProperty("age"));
        LOGGER.debug(JsonUtil.format(properties));

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug(JsonUtil.format(toMap(properties)));
        }

    }

}
