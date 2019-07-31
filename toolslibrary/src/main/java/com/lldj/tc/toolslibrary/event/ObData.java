package com.lldj.tc.toolslibrary.event;

public class ObData implements Cloneable {
    private String key;
    private Object value;
    private String tag;
    private String tag1;

    public ObData(String key, Object value){
        this.key = key;
        this.value = value;
    }

    public ObData(String key, Object value, String tag){
        this.key = key;
        this.value = value;
        this.tag = tag;
    }

    public ObData(String key, Object value, String tag, String tag1){
        this.key = key;
        this.value = value;
        this.tag = tag;
        this.tag1 = tag1;
    }

    public String getKey() { return key; }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public ObData clone() throws CloneNotSupportedException {
        return (ObData)super.clone();
    }

    @Override
    public String toString() {
        return "ObData{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", tag='" + tag + '\'' +
                '}';
    }
}
