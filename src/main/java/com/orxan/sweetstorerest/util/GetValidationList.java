package com.orxan.sweetstorerest.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetValidationList {
    public static List<String> copyValuesToList(Map validation) {
        List<String> finalList=new ArrayList<>();
        Map <Boolean,List<String>> map= (Map<Boolean, java.util.List<String>>) validation.values();
        for (List<String> list:map.values()) {
            list.stream().forEach(finalList::add);
        }
        return finalList;
    }
}
