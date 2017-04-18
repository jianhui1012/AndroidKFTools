package com.tools.kf.sample_demo.http.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/25 0025.
 */
public class ResponseNetImageEntity {

    private int itemsOnPage;

    private List<NetImageListEntity> items;

    public int getItemsOnPage() {
        return itemsOnPage;
    }

    public void setItemsOnPage(int itemsOnPage) {
        this.itemsOnPage = itemsOnPage;
    }

    public List<NetImageListEntity> getItems() {
        return items;
    }

    public void setItems(List<NetImageListEntity> items) {
        this.items = items;
    }
}
