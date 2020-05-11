package com.glodon.tool;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * @author lichong_951@163.com
 * @version V1.0
 * @Description: TODO
 * @date 2019-10-25  10:05
 * @powered by lichong
 */
public class Md5Test {

    @Test
    public void encryption() {
//        CDC1DC87BC3FEA5C7351BD25459F665E
        assertEquals("CDC1DC87BC3FEA5C7351BD25459F665E"
                ,Md5.encryption("https://glm.glodon.com/glm/services/download?key=worker/1438862/photo/recentPhoto_1571929929542.jpg"));
    }

//    https://glm.glodon.com/glm/services/download?key=worker/1438862/photo/recentPhoto_1571970948689.jpg

    @Test
    public void againTest(){
        assertEquals("A68EBD1FE72162D021B39C9E60C0F3CF",
                Md5.encryption("https://glm.glodon.com/glm/services/download?key=worker/1438862/photo/recentPhoto_1571970948689.jpg"));
    }
    private static String getSign(Map<String, String> treeMap) {
        String keyValue = "";
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            if (CheckNull.isNull(keyValue)) {
                keyValue = entry.getKey() + "=" + entry.getValue();
            } else {
                keyValue = keyValue + "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        String needSign = keyValue + "&secretKey=glm-mobile-attendance";
        return Md5.encryption(needSign);
    }
    @Test
    public void testEncryption() {
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("segmentId", "0");
        treeMap.put("face", "1");
        treeMap.put("inOutType", "2");
        treeMap.put("longitude", "3");
        String sign = getSign(treeMap);
        Assert.assertEquals("1D2570315D11FB23512D884212ECD2C8",sign);
    }
}