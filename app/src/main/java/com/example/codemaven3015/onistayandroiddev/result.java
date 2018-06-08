package com.example.codemaven3015.onistayandroiddev;

/**
 * Created by CodeMaven3015 on 6/8/2018.
 */

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

public class result {

    @Element(name = "result", required = false)
    private String result;


    public String getResult(){
        return result;
    }
}
