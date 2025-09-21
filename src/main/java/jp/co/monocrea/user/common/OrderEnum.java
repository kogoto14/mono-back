package jp.co.monocrea.user.common;

public enum OrderEnum {
    ASC("ASC"),
    DESC("DESC");

    private String value;

    OrderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
