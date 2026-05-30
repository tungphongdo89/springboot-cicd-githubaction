package com.tungphongdo.service;

import org.springframework.stereotype.Service;

@Service
public class XmlService {
    public String getXmlData() {
        return "<data><item>Item1</item><item>Item2</item></data>";
    }
}
