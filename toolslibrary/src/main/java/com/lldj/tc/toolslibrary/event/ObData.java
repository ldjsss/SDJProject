package com.lldj.tc.toolslibrary.event;

public class ObData {
    private String key;
    private Object value;
    private String tag;

    public ObData(String key, Object value){
        this.key = key;
        this.value = value;
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

    @Override
    public String toString() {
        return "ObData{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", tag='" + tag + '\'' +
                '}';
    }
}
