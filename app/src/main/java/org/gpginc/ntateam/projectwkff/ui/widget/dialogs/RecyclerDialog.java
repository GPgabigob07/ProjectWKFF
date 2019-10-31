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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.DialogRecyclerBinding;

public class RecyclerDialog<T extends RecyclerView.Adapter> extends DialogFragment
{
    private DialogRecyclerBinding binder;
    private T mRecycler;
    private OnItemSelected mListener;
    private int titleApar2;
    private String titleApar1;

    public RecyclerDialog(T mRecycler) {
        this.mRecycler = mRecycler;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        if(titleApar1!=null && titleApar2!=0)
        {
            builder.setTitle(titleApar1 + " " + getResources().getString(titleApar2));
        }
        final View v = inflater.inflate(R.layout.dialog_recycler, null);
        binder = DataBindingUtil.bind(v);
        builder.setView(v).setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if(mListener!=null)mListener.OnSelect();
            }
        });
        binder.recyclerDialog.setAdapter(mRecycler);
        binder.recyclerDialog.setLayoutManager(new LinearLayoutManager(v.getContext()));

        if(getActivity() instanceof GameFlux)
        {
            setStyle(STYLE_NORMAL, ((GameFlux)getActivity()).darkTheme ? R.style.AppTheme_Dark : R.style.AppTheme);
        }
        return builder.create();
    }


    public void setOnItemSelected(OnItemSelected mListener) {
        this.mListener = mListener;
    }

    public interface OnItemSelected
    {
        void OnSelect();
    }
}
