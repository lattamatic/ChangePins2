package sandeep.city;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sandeep on 26/2/15.
 */
public class CustomDialog extends Dialog implements View.OnClickListener{

    Button set;
    EditText cat;
    Context c;

    public CustomDialog(Context context) {
        super(context);
        c=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        set=(Button)findViewById(R.id.bSetCat);
        cat=(EditText)findViewById(R.id.etCat);
        set.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        FinalComplaint.category=cat.getText().toString();
        dismiss();

    }
}
