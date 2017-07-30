package com.git.toolbox.status;

/**
 * 数据表的status字段通用取值范围：是否开启  1-开启 2-关闭
 *
 * @author zhk
 */
public enum CommonStatusEnum {


    ENABLED((byte) 1),

    DISABLED((byte) 2);


    private byte value;

    public byte value() {
        return this.value;
    }

    private CommonStatusEnum(byte value) {
        this.value = value;
    }

    /**
     * 根据value返回一个枚举类型
     */
    public static CommonStatusEnum getByValue(byte value) {
        CommonStatusEnum result = null;
        for (CommonStatusEnum status : CommonStatusEnum.values()) {
            if (status.value() == value) {
                result = status;
                break;
            }
        }
        if (result == null) {
            throw new IllegalArgumentException("No element matches " + value);
        }

        return result;
    }
}
