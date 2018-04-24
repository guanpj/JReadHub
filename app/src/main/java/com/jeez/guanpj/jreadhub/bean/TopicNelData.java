package com.jeez.guanpj.jreadhub.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopicNelData {

    private boolean state;

    @SerializedName("result")
    private List<Result> resultList;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    public static class Result {

        private long entityId;

        private String entityName;

        @SerializedName("data")
        private List<Data> dataList;

        public long getEntityId() {
            return entityId;
        }

        public void setEntityId(long entityId) {
            this.entityId = entityId;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public List<Data> getDataList() {
            return dataList;
        }

        public void setDataList(List<Data> dataList) {
            this.dataList = dataList;
        }

        public static class Data {

            private String id;

            private String title;

            private String url;

            private String mobileUrl;

            @SerializedName("sources")
            private List<Source> sourceList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getMobileUrl() {
                return mobileUrl;
            }

            public void setMobileUrl(String mobileUrl) {
                this.mobileUrl = mobileUrl;
            }

            public List<Source> getSourceList() {
                return sourceList;
            }

            public void setSourceList(List<Source> sourceList) {
                this.sourceList = sourceList;
            }

            public static class Source {

                private String name;

                private String url;

                private String mobileUrl;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getMobileUrl() {
                    return mobileUrl;
                }

                public void setMobileUrl(String mobileUrl) {
                    this.mobileUrl = mobileUrl;
                }

            }

        }

    }

}
