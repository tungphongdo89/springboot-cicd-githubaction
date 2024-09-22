package com.tungphongdo.service.adapter;

import com.tungphongdo.service.JsonDataProcessor;
import com.tungphongdo.service.XmlService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class XmlToJsonAdapter implements JsonDataProcessor {
    private final XmlService xmlService;

    @Override
    public String processJson() {
        String xmlData = xmlService.getXmlData();
//        JSONObject jsonObject = XML.toJSONObject(xmlData); // Convert XML to JSON
        String jsonObject = "data xml convert to json"; // suppose, after converting we have this
        return jsonObject;
    }
}
