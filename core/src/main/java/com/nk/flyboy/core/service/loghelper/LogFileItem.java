package com.nk.flyboy.core.service.loghelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created on 2017/4/18.
 */
public class LogFileItem {

    public String fileName="";

    public List<String> buffA=new CopyOnWriteArrayList<>();
    public List<String> buffB=new CopyOnWriteArrayList<>();

    public String currentBuff="A";

    public long currentSize=0;

    public String filePath="";
}
