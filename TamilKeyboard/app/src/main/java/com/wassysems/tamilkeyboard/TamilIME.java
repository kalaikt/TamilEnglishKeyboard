package com.wassysems.tamilkeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.provider.UserDictionary;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;

import com.wassysems.tamilkeyboard.R;

import java.lang.reflect.Array;

/**
 * Created by Kalaikumar on 7/12/2015.
 */

public class TamilIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private Keyboard keyboard;
    int temp_primaryCode =0;
    int temp_specialCode =0;
    int i =0;
    int temp_keycode[] = new int[50];
    private boolean caps = false;



    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeUp() {
    }
    @Override
    public View onCreateInputView() {
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        //UserDictionary.Words.addWord( this , "newMedicalWord", 1, UserDictionary.Words.LOCALE_TYPE_CURRENT);
        return kv;
    }
    private void playClick(int keyCode){
        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(keyCode){
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);
        String stringText = "";
        int[] specialKeys = {3006,3007,3008,3009,3010};
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE :
                ic.deleteSurroundingText(1, 0);
                String strbeforeCursor=ic.getTextBeforeCursor(1000000000,0).toString();
                if(strbeforeCursor.isEmpty() || strbeforeCursor == null){
                    temp_primaryCode = 0;
                }
                temp_specialCode=0;
                //temp_keycode[temp_keycode.length]=primaryCode;
                //System.out.println(strbeforeCursor);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case  Keyboard.KEYCODE_MODE_CHANGE:
                keyboard = new Keyboard(this, R.xml.symbols);
                kv.setKeyboard(keyboard);
                kv.setOnKeyboardActionListener(this);
                break;
            case  -2949:
                keyboard = new Keyboard(this, R.xml.qwerty);
                kv.setKeyboard(keyboard);
                kv.setOnKeyboardActionListener(this);
                break;
            /*case  2965:
                keyboard = new Keyboard(this, R.xml.ka);
                kv.setKeyboard(keyboard);
                kv.setOnKeyboardActionListener(this);
                break;*/
            case  -97:
                keyboard = new Keyboard(this, R.xml.english);
                kv.setKeyboard(keyboard);
                kv.setOnKeyboardActionListener(this);
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char)primaryCode;

                /*switch(primaryCode){
                    case  2965:
                        keyboard = new Keyboard(this, R.xml.ka);
                        kv.setKeyboard(keyboard);
                        kv.setOnKeyboardActionListener(this);
                        break;


                }*/

                if(primaryCode >= 2947 && primaryCode <=2964 && temp_primaryCode > 2964 && temp_primaryCode < 3006){
                    //System.out.println(temp_specialCode);

                    if(temp_specialCode !=0 && temp_specialCode !=2949){
                        ic.deleteSurroundingText(1, 0);
                    }

                    temp_specialCode = primaryCode;

                    switch(primaryCode){
                        case 2950:
                            ic.commitText(String.valueOf((char)3006), 1);
                            temp_primaryCode = 3006;
                            break;
                        case 2951:
                            ic.commitText(String.valueOf((char)3007), 1);
                            temp_primaryCode = 3007;
                            break;
                        case 2952:
                            ic.commitText(String.valueOf((char)3008), 1);
                            temp_primaryCode = 3008;
                            break;
                        case 2953:
                            ic.commitText(String.valueOf((char)3009), 1);
                            temp_primaryCode = 3009;
                            break;
                        case 2954:
                            ic.commitText(String.valueOf((char)3010), 1);
                            temp_primaryCode = 3010;
                            break;
                        case 2958:
                            ic.commitText(String.valueOf((char)3014), 1);
                            temp_primaryCode = 3014;
                            break;
                        case 2959:
                            //ic.commitText(String.valueOf((char)temp_primaryCode), 1);
                            ic.commitText(String.valueOf((char)3015), 1);
                            temp_primaryCode = 3015;
                            break;
                        case 2960:
                            ic.commitText(String.valueOf((char)3016), 1);
                            temp_primaryCode = 3016;
                            break;
                        case 2962:
                            ic.commitText(String.valueOf((char)3018), 1);
                            temp_primaryCode = 3018;
                            break;
                        case 2963:
                            ic.commitText(String.valueOf((char)3019), 1);
                            temp_primaryCode = 3019;
                            break;
                        case 2964:
                            ic.commitText(String.valueOf((char)3020), 1);
                            temp_primaryCode = 3020;
                            break;
                        case 2947:
                            ic.commitText(String.valueOf((char)3021), 1);
                            temp_primaryCode = 3021;
                            break;
                    }
                }else {
                    temp_primaryCode = primaryCode;
                    temp_specialCode=0;
                    if (Character.isLetter(code) && caps) {
                        code = Character.toUpperCase(code);
                    }
                    if (primaryCode == 29651) {
                        ic.commitText(String.valueOf((char)2965), 1);
                        ic.commitText(String.valueOf((char)3021), 1);
                        ic.commitText(String.valueOf((char)2999), 1);
                    }else if (primaryCode == 29652) {
                        ic.commitText(String.valueOf((char)2998), 1);
                        ic.commitText(String.valueOf((char)3021), 1);
                        ic.commitText(String.valueOf((char)2992), 1);
                        ic.commitText(String.valueOf((char)3008), 1);
                    } else{
                        ic.commitText(String.valueOf(code), 1); //2949
                    }
                   /* switch(primaryCode){
                        case 2965:
                            keyboard = new Keyboard(this, R.xml.qwertyka);
                            kv.setKeyboard(keyboard);
                            kv.setOnKeyboardActionListener(this);
                            break;
                    }*/
                }
        }

    }
}