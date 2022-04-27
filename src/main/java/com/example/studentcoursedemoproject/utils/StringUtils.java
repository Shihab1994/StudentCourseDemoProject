package com.example.studentcoursedemoproject.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class StringUtils {

    public static boolean isNotEmpty(String str) {
        return Objects.nonNull(str) && str.trim().length() > 0;
    }

    public static boolean nonNull(Object boj) {
        return Objects.nonNull(boj);
    }

    public static boolean isNotEmpty(Integer integer) {
        return Objects.nonNull(integer) && integer > 0;
    }

    public static boolean isEmpty(String str) {
        return !isNotEmpty(str);
    }

    public static boolean isEmpty(Integer integer) {
        return !isNotEmpty(integer);
    }

    public static boolean isEmptyArr(Set<?> strArr) {
        return strArr.size() == 0;
    }

    public static boolean isNumericString(String code) {
        return code.matches("[0-9]+");
    }

    public static boolean isAnyEmpty(String... strings) {
        return Arrays.stream(strings).anyMatch(StringUtils::isEmpty);
    }

    public static boolean isAllNotEmpty(String... strings) {
        return Arrays.stream(strings).noneMatch(StringUtils::isEmpty);
    }

    public static String joinWithDelimiter(String delimiter, String... values) {

        List<String> elements = Arrays.asList(values);

        StringBuilder sb = new StringBuilder();
        elements.forEach(s -> {
            String str = StringUtils.isNotEmpty(s) ? s.trim() : "";

            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(str);
        });
        return sb.toString();
    }

    public static String booleanToStr(Boolean bol) {
        return String.valueOf(bol);
    }

    public static boolean isNotEmpty(Object obj) {
        return Objects.nonNull(obj);
    }

    public static String trim(String str) {
        return str.trim();
    }

//    public static String objectToJson(Object obj) {
//        return toJson(obj);
//    }

//    public static String toJson(Object obj) {
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
//                .create();
//        return gson.toJson(obj);
//    }

    public static String randomString() {
        return String.valueOf(ThreadLocalRandom.current().nextLong(100000000000L, 899999999999L));
    }

    public static Long getRecordId(Long previousId) {
        Long i = previousId == null ? 0 : previousId;
        i=i+1;
        return  i;
    }

    /**
     * A common method for all enums since they can't have another base class
     * @param <T> Enum type
     * @param c enum type. All enums must be all caps.
     * @param string case insensitive
     * @return corresponding enum, or null
     */
    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if( c != null && string != null ) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch(IllegalArgumentException ex) {
            }
        }
        return null;
    }
}
