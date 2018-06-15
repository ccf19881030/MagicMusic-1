package music.magic.magicmusic;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by admin on 2018/5/3 0003.
 */

public class DialogUtil {
    private static Dialog dialog;

    public static void ShowLoadingDialog(Context cot, String str) {
        try {
            dialog = new Dialog(cot, R.style.testDialog);
            View view = LayoutInflater.from(cot).inflate(R.layout.dialog_loading, null);
            dialog.setContentView(view);
            TextView tishi = (TextView) dialog.findViewById(R.id.tishi);
            tishi.setText(str);
            dialog.setCancelable(true);
            if (dialog != null) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void ShowLoadingDialogUnCancle(Context cot, String str) {
        try {
            dialog = new Dialog(cot, R.style.testDialog);
            View view = LayoutInflater.from(cot).inflate(R.layout.dialog_loading, null);
            dialog.setContentView(view);
            TextView tishi = (TextView) dialog.findViewById(R.id.tishi);
            tishi.setText(str);
            dialog.setCancelable(false);
            if (dialog != null) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void HideLoadingDialog(Context cot) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
