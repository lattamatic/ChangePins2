package sandeep.city;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sandeep on 2/3/15.
 */
public class CDialog extends DialogFragment implements View.OnClickListener{

    Button set;
    EditText editText;
    Communicator communicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator= (Communicator) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog,null);
        set= (Button) v.findViewById(R.id.bSetCat);
        editText= (EditText) v.findViewById(R.id.etCat);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bSetCat){
            communicator.onDialogMessage(editText.getText().toString());
            dismiss();
        }
    }

    interface Communicator
    {
        public void onDialogMessage(String message);
    }
}