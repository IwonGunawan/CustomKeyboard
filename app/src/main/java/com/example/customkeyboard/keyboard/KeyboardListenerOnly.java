package com.example.customkeyboard.keyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;

public class KeyboardListenerOnly implements KeyboardView.OnKeyboardActionListener {

    private InputMethodService inputMethodService;
    private KeyboardView kv;
    private Keyboard keyboard;
    private boolean caps = false;

    public KeyboardListenerOnly(InputMethodService ims, KeyboardView kv, Keyboard keyboard) {
        inputMethodService = ims;
        this.kv = kv;
        this.keyboard = keyboard;
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = inputMethodService.getCurrentInputConnection();

        if (ic != null) {
            switch(primaryCode){
                case Keyboard.KEYCODE_DELETE :
                    ic.deleteSurroundingText(1, 0);
                    break;
                case Keyboard.KEYCODE_SHIFT:
                    caps = !caps;
                    keyboard.setShifted(caps);
                    kv.invalidateAllKeys();
                    break;
                case Keyboard.KEYCODE_DONE:
                    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                    break;
                case 9999:
                    break;
                default:
                    char code = (char)primaryCode;
                    if(Character.isLetter(code) && caps){
                        code = Character.toUpperCase(code);
                    }
                    ic.commitText(String.valueOf(code),1);
            }
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}

