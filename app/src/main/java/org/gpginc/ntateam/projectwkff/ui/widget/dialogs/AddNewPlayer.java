package org.gpginc.ntateam.projectwkff.ui.widget.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.DialogAddPlayerBinding;
import org.gpginc.ntateam.projectwkff.runtime.Player;

public class AddNewPlayer extends DialogFragment
{
    public DialogAddPlayerBinding binder;
    private OnCompleteListener mListener;
    private OnFailListener mListener2;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_add_player, null);
        binder = DataBindingUtil.bind(v);
        binder.setNewPlayer(new Player(""));
        builder.setView(v)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(binder.editText.getText().length() > 0)
                        {
                            if(mListener!=null)
                            {
                                mListener.onComplete();
                                dismiss();
                            }
                        }
                        else if(mListener2!=null)
                        {
                            mListener2.onFail();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddNewPlayer.this.getDialog().cancel();
                        mListener=null;
                    }
                });
        return builder.create();
    }

    public void setOnCompleteListener(OnCompleteListener mListener)
    {
        this.mListener = mListener;
    }

    public void setOnFailListener(OnFailListener mListener2) {
        this.mListener2 = mListener2;
    }

    public interface OnCompleteListener
    {
        void onComplete();
    }
    public interface OnFailListener
    {
        void onFail();
    }

}
