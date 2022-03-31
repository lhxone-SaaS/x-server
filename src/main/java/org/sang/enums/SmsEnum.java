package org.sang.enums;

public enum SmsEnum {

    PHONE_CODE_LOGIN("891481", "code, minutes"),
    PHONE_CODE_REG("891746", "code, minutes"),
    PHONE_CODE_RESET_PASS("891748", "code")

    ;
    private String templateId;

    private String keys;

    SmsEnum(String templateId, String keys) {
        this.templateId = templateId;
        this.keys = keys;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getKeys() {
        return keys;
    }
}
