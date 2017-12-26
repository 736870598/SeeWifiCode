package com.sunxiaoyu.seewificode;

import java.io.Serializable;

/**
 *
 * Created by Sunxy on 2017/12/26.
 */
public class WifiModel implements Serializable {

    private String ssid;
    private String passWord;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
