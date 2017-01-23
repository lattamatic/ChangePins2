package sandeep.city;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sandeep on 25/3/16.
 */
public class DetailedReport extends ActionBarActivity implements TextWatcher{

    EditText title, description;
    ImageView edit,gallery,takepic,back;
    LinearLayout addImage;
    int checkmode,checkChanges;
    Boolean inEditMode;
    DataHelp dh;
    int id;
    String t, des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_detailed_layout);

        dh = new DataHelp(this);

        id=getIntent().getIntExtra("id",-1);
        Log.d("id",id+" ");

        SingleReport report = dh.getaReport(id);


        title = (EditText) findViewById(R.id.etComplainTitle);
        description = (EditText) findViewById(R.id.etDescription);
        edit = (ImageView) findViewById(R.id.ivEdit);
        gallery = (ImageView) findViewById(R.id.ivUploadImage);
        takepic = (ImageView) findViewById(R.id.ivTakePic);
        back = (ImageView) findViewById(R.id.ivBack);
        addImage = (LinearLayout) findViewById(R.id.llAddImage);
        checkmode = 1;
        checkChanges=1;

        inEditMode = false;


        title.setText(report.getTitle());
        description.setText(report.getDesc());

        title.addTextChangedListener(this);
        description.addTextChangedListener(this);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inEditMode) {
                    turnEditOn();
                    inEditMode=true;
                } else {
                    turnEditOff();
                    inEditMode=false;
                    if(checkChanges!=1){
                        saveChanges();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            Dialog d;

            @Override
            public void onClick(View v) {
                if (checkmode==1){
                    turnEditOff();
                    checkmode=2;
                }else{
                    checkmode=1;
                    if(checkChanges==2){
                        d = new Dialog(DetailedReport.this);
                        d.setContentView(R.layout.dialog_discard_changes);

                        TextView yes = (TextView) d.findViewById(R.id.tvYes);
                        TextView no = (TextView) d.findViewById(R.id.tvNo);

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                d.dismiss();
                                finish();
                            }
                        });

                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                d.dismiss();
                            }
                        });

                        d.show();
                    }else{
                        finish();
                    }
                }
            }
        });

        turnEditOff();
    }

    private void saveChanges() {
        dh.report_update(id, "Title","Description",0.0,0.0, "Himalayas");
    }

    private void turnEditOn(){
        title.setFocusable(true);
        title.setFocusableInTouchMode(true);
        description.setFocusableInTouchMode(true);
        description.setFocusable(true);
        edit.setImageResource(R.drawable.tick);
        addImage.setVisibility(View.VISIBLE);
    }

    private void turnEditOff(){
        title.setFocusable(false);
        title.setFocusableInTouchMode(false);
        description.setFocusableInTouchMode(false);
        description.setFocusable(false);
        edit.setImageResource(android.R.drawable.ic_menu_edit);
        addImage.setVisibility(View.GONE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        checkChanges=2;
    }

    @Override
    public void afterTextChanged(Editable s) {
        checkChanges=2;
    }
}
