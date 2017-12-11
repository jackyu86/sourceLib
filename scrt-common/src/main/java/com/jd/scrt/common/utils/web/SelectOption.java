package com.jd.scrt.common.utils.web;

/**
 * Created by wudi10 on 2016/1/28.
 */
public class SelectOption<K, V> {
    private K value;
    private V text;

    public SelectOption(){}

    public SelectOption(K value, V text){
        this.value = value;
        this.text = text;
    }

    public K getValue() {
        return value;
    }

    public void setValue(K value) {
        this.value = value;
    }

    public V getText() {
        return text;
    }

    public void setText(V text) {
        this.text = text;
    }

    public String toString() {
        return "value=" + value.toString() + ",text=" + text.toString();
    }
}
