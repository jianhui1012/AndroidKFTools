/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tools.kf.sample_demo.utils;


import com.tools.kf.sample_demo.api.ApiConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/3/20.
 * Description:
 */
public class UriHelper {

    private static volatile UriHelper instance = null;

    /**
     * 20 datas per page
     */
    public static final int PAGE_LIMIT = 20;

    public static final String URL_MUSICS_LIST_CHANNEL_ID = "0";

    private UriHelper() {
    }

    public static UriHelper getInstance() {
        if (null == instance) {
            synchronized (UriHelper.class) {
                if (null == instance) {
                    instance = new UriHelper();
                }
            }
        }
        return instance;
    }

    public int calculateTotalPages(int totalNumber) {
        if (totalNumber > 0) {
            return totalNumber % PAGE_LIMIT != 0 ? (totalNumber / PAGE_LIMIT + 1) : totalNumber / PAGE_LIMIT;
        } else {
            return 0;
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public String getImagesListUrl(String category, int pageNum) {
        StringBuffer sb = new StringBuffer();
        sb.append(AppConfig.Urls.BAIDU_IMAGES_URLS);
        sb.append("?col=");
        try {
            sb.append(URLEncoder.encode(category, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&tag=");
        try {
            sb.append(URLEncoder.encode("全部", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&pn=");
        sb.append(pageNum * PAGE_LIMIT);
        sb.append("&rn=");
        sb.append(PAGE_LIMIT);
        sb.append("&from=1");
        return sb.toString();
    }


    /***
     * 搜狗图片接口来源于searchpicturetool工具类
     * @param word
     * @param page
     * @return
     */
    public String getUrl(String word, int page) {
        try {
            word = URLEncoder.encode(word, "gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return AppConfig.Urls.BAIDU_IMAGES_URLS2+"/pics?query="+word+"&start="+page*PAGE_LIMIT+"&reqType=ajax&reqFrom=result";
    }

}
