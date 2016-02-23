package com.tools.kf.dialog;

/**
 * Created by djh on 2016/1/21.
 */
public class DialogManger {

    private DialogManger dialogManger;

    private DialogManger() {

    }


    /***
     * 实例化对话框管理对象
     * @return
     */
    public DialogManger getInstance() {
        if (dialogManger == null) {
            synchronized (DialogManger.class) {
                if (dialogManger == null) {
                    dialogManger = new DialogManger();
                }
            }
        }
        return  dialogManger;
    }





}
