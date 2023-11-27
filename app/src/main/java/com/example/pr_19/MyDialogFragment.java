package com.example.pr_19;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class MyDialogFragment extends DialogFragment {

    private EditText userInput;
    private DatePickerDialog datePickerDialog;

    public interface DialogListener {
        void onDataReceived(String data);
    }

    private DialogListener dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);
        userInput = view.findViewById(R.id.edit_text_dialog);
        Button pickDateButton = view.findViewById(R.id.button_pick_date);

        datePickerDialog = new DatePickerDialog(
                requireActivity(),
                dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Выберите дату")
                .setPositiveButton("Ок", (dialog, which) -> {
                    String data = userInput.getText().toString();
                    if (dialogListener != null) {
                        dialogListener.onDataReceived(data);
                    }
                })
                .setNegativeButton("Отмена", (dialog, which) -> {
                    dismiss();
                });

        return builder.create();
    }

    public void setDialogListener(DialogListener listener) {
        this.dialogListener = listener;
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            userInput.setText(selectedDate);

        }
    };

    public void setDatePickerDialog(DatePickerDialog datePickerDialog) {
        this.datePickerDialog = datePickerDialog;
    }
}
