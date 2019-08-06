package com.lldj.tc;

import android.app.Dialog;
import android.util.Log;

import com.lldj.tc.toolslibrary.view.BaseDialog;

import java.util.LinkedList;
import java.util.List;

import static com.lldj.tc.BuildConfig.DEBUG;

public class DialogManager {
    private List<BaseDialog> dialogList = new LinkedList<BaseDialog>();
    private static DialogManager instance;

    private DialogManager() { }

    public static DialogManager getInstance() {
        if (null == instance) {
            instance = new DialogManager();
        }
        return instance;
    }

    public BaseDialog show(BaseDialog dialog) {
        if(dialog == null) return null;

        String tag = dialog.getClass().getSimpleName();
        BaseDialog _have = getDialog(tag);
        if(_have == null){
            dialogList.add(dialog);
        }
        else{
            if(_have.isShow) {
                if(DEBUG) Log.e("error", String.format("DialogManager tag is repeat, tag = %s", tag));
                return dialog;
            }
        }

        dialog.setTag(tag);
        dialog.show();

        return dialog;
    }

    public BaseDialog show(BaseDialog dialog, String tag) {
        if(dialog == null) return null;

        BaseDialog _have = getDialog(tag);
        if(_have == null){
            dialogList.add(dialog);
        }
        else{
            if(_have.isShow) {
                if(DEBUG) Log.e("error", String.format("DialogManager tag is repeat, tag = %s", tag));
                return dialog;
            }
        }

        dialog.setTag(tag);
        dialog.show();

        return dialog;
    }

    public BaseDialog getDialog(String tag){
        if(tag == null) return null;
        for (BaseDialog _dialog : dialogList) {
            if(tag.equalsIgnoreCase(_dialog.getTag())) {
                return _dialog;
            }
        }

        return null;
    }

    public void removeDialog(BaseDialog dialog){
        if(dialog == null) return;
        for (BaseDialog _dialog : dialogList) {
            if(dialog.getTag().equalsIgnoreCase(_dialog.getTag())) {
                dialogList.remove(_dialog);
                _dialog.dismiss();
                break;
            }
        }
    }

    public void removeDialog(String tag){
        if(tag == null) return;
        for (BaseDialog _dialog : dialogList) {
            if(tag.equalsIgnoreCase(_dialog.getTag())) {
                dialogList.remove(_dialog);
                _dialog.dismiss();
                break;
            }
        }
    }

    public void hideDialog(BaseDialog dialog){
        if(dialog == null) return;
        for (BaseDialog _dialog : dialogList) {
            if(dialog.getTag().equalsIgnoreCase(_dialog.getTag())) {
                _dialog.hide();
                break;
            }
        }
    }

    public void hideDialog(String tag){
        if(tag == null) return;
        for (BaseDialog _dialog : dialogList) {
            if(tag.equalsIgnoreCase(_dialog.getTag())) {
                _dialog.hide();
                break;
            }
        }
    }

    public void removeAll(){
        for (BaseDialog _dialog : dialogList) {
            _dialog.dismiss();
        }
        dialogList.clear();
    }

}
