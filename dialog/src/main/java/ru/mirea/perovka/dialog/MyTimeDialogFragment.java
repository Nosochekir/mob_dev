package ru.mirea.perovka.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class MyTimeDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        return new TimePickerDialog(getActivity(), (view, selectedHour, selectedMinute) -> {

            if (getActivity() instanceof ru.mirea.perovka.dialog.Dialog) {
                ((ru.mirea.perovka.dialog.Dialog) getActivity()).onTimeSet(selectedHour, selectedMinute);
            }
        }, hour, minute, true);
    }
}
