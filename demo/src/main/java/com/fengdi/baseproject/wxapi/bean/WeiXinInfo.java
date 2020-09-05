package com.fengdi.baseproject.wxapi.bean;

/**
 *  @作者 ：hexingbo
 *  @时间 ：2019/4/1
 *  @描述 ：WeiXinInfo
 */
public class WeiXinInfo {

    /**
     * openid : oi_g56InAWasPbidqKHTEVh_g9dc
     * nickname : Works℡¹³⁰⁶⁵¹³⁰³⁵⁰
     * sex : 1
     * language : zh_CN
     * city : Los Angeles City
     * province : California
     * country : US
     * headimgurl : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJiaUNpUJxemZZtvpH86Aq6Qq1Ex4mVlohx34T9p3wwaQWbEMQNShGGtwiat7Ld2tmAr83EyuoNeGrw/132
     * privilege : []
     * unionid : orOfx03AGtDwKuaCr1-Sk0fgL6EI
     */

    private String openid;
    private String nickname;
    private int sex;
    private int age;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        return "WeiXinInfo{" +
                "openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", language='" + language + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }
}
