package com.tungphongdo.service.adapter;

import com.tungphongdo.service.CsvService;
import com.tungphongdo.service.JsonDataProcessor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class CsvToJsonAdapter implements JsonDataProcessor {

    private final CsvService csvService;

    @Override
    public String processJson() {
        String csvData = csvService.getCsvData();
//        String[] items = csvData.split(",");
//        JSONArray jsonArray = new JSONArray(items);
        String csvObject = "data csv convert to json"; // suppose, after converting we have this
        return csvObject;
    }
}
