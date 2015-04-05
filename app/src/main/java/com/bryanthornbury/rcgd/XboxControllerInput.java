package com.bryanthornbury.rcgd;

/**
 * Created by mozoby on 4/4/15.
 */
public enum XboxControllerInput {
    A(0x04),
    B(0x05),
    X(0x06),
    Y(0x07),
    START(0x08),
    SELECT(0x09),
    DPAD_UP(0x0A),
    DPAD_DOWN(0x0B),
    DPAD_LEFT(0x0C),
    DPAD_RIGHT(0x0D),
    LEFT_STICK(0x0E),
    RIGHT_STICK(0x0F),
    LEFT_BUMPER(0x10),
    RIGHT_BUMPER(0x11),
    LEFT_TRIGGER(0x12),
    RIGHT_TRIGGER(0x13);

    private int key;

    private XboxControllerInput(int key){
        this.key = key;
    }

    public int getKey(){
        return key;
    }
}
