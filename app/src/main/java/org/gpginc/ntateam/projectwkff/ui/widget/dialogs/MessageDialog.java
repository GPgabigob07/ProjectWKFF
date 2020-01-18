package org.gpginc.ntateam.projectwkff.ui.widget.dialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.DialogMessageBinding;
import org.gpginc.ntateam.projectwkff.runtime.util.SkillUtils;

/**
 * Might use {@link MessageDialog.Display} instead;
 */
public class MessageDialog extends DialogFragment
{
    public DialogMessageBinding binder;
    private int message;
    private OnMessageAccept mListener;
    private OnMessageCancel onMessageCancel;
    private boolean cancelable;
    private String extraText;


    @Override
    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    @Deprecated
    /**
     * Used to add a message to MessageDialog
     * @deprecated Use {@link Display} instead
     */
    public MessageDialog setMessage(int message) {
        this.message = message;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        if(extraText!=null && extraText!="")builder.setMessage(getResources().getString(message) + extraText);

        builder.setPositiveButton(R.string.label_accept, (dialog, which) -> {
            dialog.dismiss();
            if(mListener!=null)mListener.onAccept();
        });
        if(cancelable)
        {
            builder.setNegativeButton(android.R.string.cancel, (d, w) -> {
                d.dismiss();
                if (onMessageCancel != null) {
                    onMessageCancel.onCancel();
                }
            });
        }


        return builder.create();
    }

    public void setOnAcceptListener(OnMessageAccept mListener) {
        this.mListener = mListener;
    }

    public MessageDialog setOnMessageCancel(OnMessageCancel onMessageCancel) {
        this.onMessageCancel = onMessageCancel;
        return this;
    }

    public interface OnMessageAccept
    {
        void onAccept();
    }

    public MessageDialog setExtraText(String extraText) {
        this.extraText = extraText;
        return this;
    }


    @SuppressWarnings("deprecation")
    public static final class Display
    {
        private AppCompatActivity t;
        private  MessageDialog d;

        public Display(AppCompatActivity t) {
            this();
            this.t = t;
        }

        public Display(AppCompatActivity t, @StringRes int message)
        {
            this(t);
            d.message = message;
        }

        public Display(AppCompatActivity t, @StringRes int message, String extraText)
        {
            this(t, message);
            d.extraText = extraText;
        }

        public Display() {
            d = new MessageDialog();
        }

        public Display withListener(OnMessageAccept listener)
        {
            d.setOnAcceptListener(listener);
            return this;
        }
        public Display withCancelListener(OnMessageCancel listener)
        {
            d.setCancelable(true);
            d.setOnMessageCancel(listener);
            return this;
        }

        /**
         * Catch message from an InheritedDialog and Display as a normal MessageDialog
         * @param dialog Some dialog
         * @param <D> Any child-class of InheritedDialog as user can usefully create new dialog types by him own and still continue using default Display
         */
        public <D extends InheritedDialog> void from(D dialog)
        {
            d.setMessage(dialog.getMsg());
            d.setOnAcceptListener(dialog.getListener());
            prompt();
        }

        public void prompt()
        {
            d.show(t.getSupportFragmentManager(), null);
        }

        public void directPrompt(FragmentManager manager, int message)
        {
            d.message = message;
            d.show(manager, null);
        }

        /**
         * Construct an InheritedDialog to prevent repetitive display creation code {@link SkillUtils#manageDragonInteraction}
         */
        public static class InheritedDialog extends MessageDialog
        {
            protected int msg;
            public InheritedDialog setMsg(@StringRes int msg)
            {
                this.msg = msg;
                return this;
            }
            protected int getMsg(){return msg;}
        }
    }

    public OnMessageAccept getListener() {
        return this.mListener;
    }

    public interface OnMessageCancel{
        void onCancel();
    }
}
