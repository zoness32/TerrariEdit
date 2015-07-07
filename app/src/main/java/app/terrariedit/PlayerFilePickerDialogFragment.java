package app.terrariedit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by teernisse on 5/19/2015.
 */
public class PlayerFilePickerDialogFragment extends DialogFragment {
    private CharSequence[] filenames;

    public interface PlayerFilePickerDialogListener {
        void onPlayerFileSelected(DialogFragment dialog, String filename);
    }

    PlayerFilePickerDialogListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (PlayerFilePickerDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement PlayerFilePickerDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_file)
                .setItems(filenames, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onPlayerFileSelected(PlayerFilePickerDialogFragment.this, filenames[which].toString());
                    }
                });
        return builder.create();
    }

    public void setFilenames(CharSequence[] filenames) {
        this.filenames = filenames;
    }
}
