package br.com.pocketpos.app.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import br.com.pocketpos.R;

public class SupplyCashDialogFragment extends DialogFragment
        implements DialogInterface.OnClickListener {


    private Listener listener;

    private EditText valueEditText;


    public void onAttach(Context context) {

        super.onAttach(context);

        try {

            listener = (SupplyCashDialogFragment.Listener) context;

        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString()
                    + " must implement SupplyCashDialogFragment.Listener");

        }

    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle(R.string.dlg_title_cash_supply);

        builder.setView(inflater.inflate(R.layout.activity_cash_supply_dialog, null))
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, this);

        return builder.create();

    }


    public void onClick(DialogInterface dialog, int which) {

        switch (which){

            case DialogInterface.BUTTON_POSITIVE:

                Double value = new Double(0.0);

                if (!valueEditText.getText().toString().isEmpty())

                    value = Double.valueOf(valueEditText.getText().toString());

                listener.onSupplyCash(value);

                dialog.dismiss();

                break;

        }

    }


    public void onResume() {

        super.onResume();

        valueEditText = getDialog().findViewById(R.id.activity_cash_supply_dialog_value_edittext);

        valueEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    onClick(getDialog(), DialogInterface.BUTTON_POSITIVE);

                    return true;

                }

                return false;

            }

        });

    }


    public interface Listener {

        void onSupplyCash(Double value);

    }


}