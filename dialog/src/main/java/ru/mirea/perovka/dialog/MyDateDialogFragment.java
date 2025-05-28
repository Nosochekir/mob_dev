package ru.mirea.perovka.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class MyDateDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        return new DatePickerDialog(getActivity(), (view, selectedYear, selectedMonth, selectedDay) -> {

            if (getActivity() instanceof ru.mirea.perovka.dialog.Dialog) {
                ((ru.mirea.perovka.dialog.Dialog) getActivity()).onDateSet(selectedYear, selectedMonth, selectedDay);
            }
        }, year, month, day);
    }
}