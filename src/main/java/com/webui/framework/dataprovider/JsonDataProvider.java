package com.webui.framework.dataprovider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webui.util.AssertUtils;
import com.webui.util.ParseJsonFile;
import com.webui.util.Utils;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonDataProvider implements Iterator<Object> {

    private Integer currentIndex;
    private final List<JSONObject> originData;
    private static final String separator = File.separator;
    private static final String caseDataPath = "src/main/java";
    private static final String testDataPath = "src/test/java";
    private static final String rootPath = Paths.get(".").normalize().toAbsolutePath().toString();
    private static final String fileType = ".json";

    public JsonDataProvider(Method method) {

        currentIndex = 0;
        String caseName = method.getDeclaringClass().getName() + "." + method.getName();
        String caseFile = readCaseData(method);
        AssertUtils.assertStringIsBlank(caseFile, String.format("找不到%s.json文件!", caseName));
        JSONArray jsonArray = JSONArray.parseArray(ParseJsonFile.readJsonFile(caseFile));
        AssertUtils.assertNotNull(jsonArray, "获取json文件数据失败!");
        originData = new ArrayList<>(jsonArray.size());
        jsonArray.forEach(ite -> originData.add((JSONObject) ite));

    }


    @Override

    public boolean hasNext() {
        return currentIndex < originData.size();
    }

    @Override
    public Object next() {
        JSONObject jsonObject = originData.get(currentIndex);
        currentIndex++;
        return jsonObject;
    }

    String readCaseData(Method method) {
        String path = method.getDeclaringClass().getName().replace(".", separator) + "." + method.getName() + fileType;
        return Utils.getCurrentPath(
                rootPath + separator + caseDataPath + separator + path,
                rootPath + separator + testDataPath + separator + path,
                rootPath + separator + path
        );
    }

    public static void main(String[] args) {
        System.out.println(rootPath);
    }
}
