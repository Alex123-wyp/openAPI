package com.yupi.openapi.model.enums;

/**
 * @author yupeng
 */
public enum InterfaceInfoStatusEnum {
    OFFLINE("Offline", 0),
    ONLINE("Online", 1);


    private final String text;

    private final int value;

    InterfaceInfoStatusEnum(String text, int value){
        this.text = text;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
