package com.lldj.tc;

import android.app.Dialog;
import android.util.Log;

import com.lldj.tc.toolslibrary.view.BaseDialog;

import java.util.LinkedList;
import java.util.List;

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

        Log.e("----dialogList = ", dialogList.size() + "");
        String _tag = dialog.getClass().getSimpleName();
        BaseDialog _have = getDialog(_tag);
        if(_have == null){
            Log.e("error","DialogManager tag is repeat, tag = " + _tag);
            dialogList.add(dialog);
        }
        else{
            if(_have.isShow) return dialog;
        }

        dialog.setTag(_tag);
        dialog.show();

        return dialog;
    }

    public BaseDialog show(BaseDialog dialog, String tag) {
        if(dialog == null) return null;

        BaseDialog _have = getDialog(tag);
        if(_have == null){
            Log.e("error","DialogManager tag is repeat, tag = " + tag);
            dialogList.add(dialog);
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
