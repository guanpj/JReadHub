package com.jeez.guanpj.jreadhub.bean;

import java.util.List;

public class NewTopicCountBean {

    /**
     * count : 2
     * data : [{"id":"4Q7zi5uMR5q"},{"id":"eNChI6ZEoJ"}]
     */

    private int count;
    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 4Q7zi5uMR5q
         */

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
