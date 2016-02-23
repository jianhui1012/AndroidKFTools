package com.tools.kf.gisandroidmap;

import com.tools.kf.configs.BasicAppConfig;

/**
 * Created by djh on 2016/1/23.
 */
public class AppConfig extends BasicAppConfig {

    //更新下载目录--默认目录(测试)
    public static final String updateapkdir = "/AndroidKFTools/";
    //更新配置文件地址--默认下载地址(测试)
    public static final String updateapkurl = "http://xyxc.ohedu.net/Android/update/update_vessel.txt";
    //网络接口请求域名
    public static final String webseriviceym = "http://xyxc.ohedu.net/Android/";
    //获取经纬度信息及周边的船只超过限定距离的接口
    public static  final String getJinWeiDu=webseriviceym+"";
}
