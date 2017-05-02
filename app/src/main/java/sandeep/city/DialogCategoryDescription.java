package sandeep.city;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by sandeep_chi on 5/2/2017.
 */

public class DialogCategoryDescription extends Dialog {

    TextView categoryTitle, categoryDescription;
    String title, description;

    public DialogCategoryDescription(Context context) {
        super(context);
    }

    public DialogCategoryDescription(Context context, String title, String description){
        super(context);
        this.title = title;
        this.description = description;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_cat_description);

        categoryTitle = (TextView) findViewById(R.id.tvCategory);
        categoryDescription = (TextView) findViewById(R.id.tvCategoryDescription);

        categoryTitle.setText(title);
        categoryDescription.setText(description);
    }
}
