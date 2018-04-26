package com.jeez.guanpj.jreadhub.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobsBean {

    /**
     * id : 10063
     * uuid : 887e872448a711e8a36a
     * jobTitle : 数据挖掘工程师
     * jobCount : 10
     * companyCount : 9
     * salaryLower : 18
     * salaryUpper : 22
     * experienceLower : 1
     * experienceUpper : 3
     * cities : {"北京":5,"杭州":3,"广州":1}
     * sources : {"拉勾网":7,"BOSS 直聘":3}
     * jobsArray : [{"id":1106057,"url":"https://www.lagou.com/jobs/4478638.html","city":"北京","title":"数据挖掘/机器学习工程师","company":"明略数据","sponsor":false,"siteName":"拉勾网","salaryLower":20,"salaryUpper":40,"experienceLower":-1,"experienceUpper":-1},{"id":1109850,"url":"https://www.zhipin.com/job_detail/d6170231fbe789451n1809i8GFY~.html","city":"杭州","title":"数据挖掘算法工程师","company":"联泰系统","sponsor":false,"siteName":"BOSS 直聘","salaryLower":8,"salaryUpper":15,"experienceLower":1,"experienceUpper":3},{"id":1109614,"url":"https://www.zhipin.com/job_detail/224bf1d5f2777b251n183Ni9E1o~.html","city":"杭州","title":"数据挖掘工程师","company":"数擎","sponsor":false,"siteName":"BOSS 直聘","salaryLower":15,"salaryUpper":30,"experienceLower":3,"experienceUpper":5},{"id":1105104,"url":"https://www.lagou.com/jobs/4477676.html","city":"北京","title":"数据挖掘","company":"洋钱罐","sponsor":false,"siteName":"拉勾网","salaryLower":15,"salaryUpper":30,"experienceLower":1,"experienceUpper":3}]
     * publishDate : 2018-04-24T16:00:50.000Z
     * createdAt : 2018-04-25T16:43:51.893Z
     */

    private int id;
    private String uuid;
    private String jobTitle;
    private int jobCount;
    private int companyCount;
    private int salaryLower;
    private int salaryUpper;
    private int experienceLower;
    private int experienceUpper;
    private CitiesBean cities;
    private SourcesBean sources;
    private String publishDate;
    private String createdAt;
    private List<JobsArrayBean> jobsArray;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getJobCount() {
        return jobCount;
    }

    public void setJobCount(int jobCount) {
        this.jobCount = jobCount;
    }

    public int getCompanyCount() {
        return companyCount;
    }

    public void setCompanyCount(int companyCount) {
        this.companyCount = companyCount;
    }

    public int getSalaryLower() {
        return salaryLower;
    }

    public void setSalaryLower(int salaryLower) {
        this.salaryLower = salaryLower;
    }

    public int getSalaryUpper() {
        return salaryUpper;
    }

    public void setSalaryUpper(int salaryUpper) {
        this.salaryUpper = salaryUpper;
    }

    public int getExperienceLower() {
        return experienceLower;
    }

    public void setExperienceLower(int experienceLower) {
        this.experienceLower = experienceLower;
    }

    public int getExperienceUpper() {
        return experienceUpper;
    }

    public void setExperienceUpper(int experienceUpper) {
        this.experienceUpper = experienceUpper;
    }

    public CitiesBean getCities() {
        return cities;
    }

    public void setCities(CitiesBean cities) {
        this.cities = cities;
    }

    public SourcesBean getSources() {
        return sources;
    }

    public void setSources(SourcesBean sources) {
        this.sources = sources;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<JobsArrayBean> getJobsArray() {
        return jobsArray;
    }

    public void setJobsArray(List<JobsArrayBean> jobsArray) {
        this.jobsArray = jobsArray;
    }

    public static class CitiesBean {
        /**
         * 北京 : 5
         * 杭州 : 3
         * 广州 : 1
         */

        private int 北京;
        private int 杭州;
        private int 广州;

        public int get北京() {
            return 北京;
        }

        public void set北京(int 北京) {
            this.北京 = 北京;
        }

        public int get杭州() {
            return 杭州;
        }

        public void set杭州(int 杭州) {
            this.杭州 = 杭州;
        }

        public int get广州() {
            return 广州;
        }

        public void set广州(int 广州) {
            this.广州 = 广州;
        }
    }

    public static class SourcesBean {
        /**
         * 拉勾网 : 7
         * BOSS 直聘 : 3
         */

        private int 拉勾网;
        @SerializedName("BOSS 直聘")
        private int _$BOSS139; // FIXME check this code

        public int get拉勾网() {
            return 拉勾网;
        }

        public void set拉勾网(int 拉勾网) {
            this.拉勾网 = 拉勾网;
        }

        public int get_$BOSS139() {
            return _$BOSS139;
        }

        public void set_$BOSS139(int _$BOSS139) {
            this._$BOSS139 = _$BOSS139;
        }
    }

    public static class JobsArrayBean {
        /**
         * id : 1106057
         * url : https://www.lagou.com/jobs/4478638.html
         * city : 北京
         * title : 数据挖掘/机器学习工程师
         * company : 明略数据
         * sponsor : false
         * siteName : 拉勾网
         * salaryLower : 20
         * salaryUpper : 40
         * experienceLower : -1
         * experienceUpper : -1
         */

        private int id;
        private String url;
        private String city;
        private String title;
        private String company;
        private boolean sponsor;
        private String siteName;
        private int salaryLower;
        private int salaryUpper;
        private int experienceLower;
        private int experienceUpper;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public boolean isSponsor() {
            return sponsor;
        }

        public void setSponsor(boolean sponsor) {
            this.sponsor = sponsor;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public int getSalaryLower() {
            return salaryLower;
        }

        public void setSalaryLower(int salaryLower) {
            this.salaryLower = salaryLower;
        }

        public int getSalaryUpper() {
            return salaryUpper;
        }

        public void setSalaryUpper(int salaryUpper) {
            this.salaryUpper = salaryUpper;
        }

        public int getExperienceLower() {
            return experienceLower;
        }

        public void setExperienceLower(int experienceLower) {
            this.experienceLower = experienceLower;
        }

        public int getExperienceUpper() {
            return experienceUpper;
        }

        public void setExperienceUpper(int experienceUpper) {
            this.experienceUpper = experienceUpper;
        }
    }
}
