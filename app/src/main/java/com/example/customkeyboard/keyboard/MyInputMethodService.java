   package com.example.customkeyboard.keyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dynatrace.android.agent.DTXAction;
import com.dynatrace.android.agent.Dynatrace;
import com.dynatrace.android.agent.conf.DynatraceConfigurationBuilder;
import com.example.customkeyboard.R;


   public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private RelativeLayout rlParent;
    private RelativeLayout rlChild;
    private KeyboardView keyboardView;
    private Keyboard keyboard;

    private EditText etPin;

    private boolean caps = false;

    @Override
    public View onCreateInputView() {
        rlParent = (RelativeLayout) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        rlChild = rlParent.findViewById(R.id.rlChild);
        setKeyboardOnly();
        keyboardView.setOnKeyboardActionListener(this);
        return rlParent;
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    private void setKeyboardOnly() {
        rlChild.removeAllViews();
        etPin = rlParent.findViewById(R.id.etPin);
        keyboardView = rlParent.findViewById(R.id.keyboardView);
        keyboard = new Keyboard(this, R.xml.number_pad_only);
        keyboardView.setKeyboard(keyboard);
//        etPin.setFocusable(true);
    }

    private void setKeyboardForm() {
        RelativeLayout rlForm = (RelativeLayout) getLayoutInflater().inflate(R.layout.layout_form, null);
        rlChild.removeAllViews();
        rlChild.addView(rlForm);
        rlChild.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setKeyboardOnly();
                Dynatrace.endVisit();
            }
        });
        rlChild.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        rlChild.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        rlChild.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        keyboard = new Keyboard(this, R.xml.number_pad_default);
        keyboardView.setKeyboard(keyboard);
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();

        if (ic != null) {
            switch(primaryCode){
                case Keyboard.KEYCODE_DELETE :
                    ic.deleteSurroundingText(1, 0);
                    break;
                case Keyboard.KEYCODE_SHIFT:
                    caps = !caps;
                    keyboard.setShifted(caps);
                    keyboardView.invalidateAllKeys();
                    break;
                case Keyboard.KEYCODE_DONE:
                    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                    break;
                case 9999:
                    Dynatrace.startup(this,
                            new DynatraceConfigurationBuilder(
                                    "968bedbc-2af4-4837-bde4-3be10f55e412",
                                    "https://bf84997coi.bf.dynatrace.com/mbeacon")
                                    .buildConfiguration());
                    Dynatrace.identifyUser("Pixel XL Pie 2");
                    DTXAction searchAction = Dynatrace.enterAction("CustomKeyboard Login");
                    setKeyboardForm();
                    searchAction.leaveAction();
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