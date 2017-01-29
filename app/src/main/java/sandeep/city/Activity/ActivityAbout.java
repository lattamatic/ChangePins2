package sandeep.city.Activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import sandeep.city.ChangePinsActivity;
import sandeep.city.R;


public class ActivityAbout extends ChangePinsActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text = (TextView) findViewById(R.id.textView7);
        text.setText(Html.fromHtml("<b>Changepins</b> is a crowd sourced grass roots initiative to raise awareness about and improve the state of public and social infrastructure leveraging digital and mobile technology.<br></br><br></br>" +
                "We intend to work in tandem with public administration and highlight trends of public concern to be leveraged as intelligence to enable inclusive decision making. We intend to bridge the gap between the public and the local administration, enabling real time reporting and documentation of the problems faced.<br></br><br></br>" +
                " Our vision is to provide the citizens with a platform to participate and contribute for improving their lifestyle and standards of living. A data driven approach is taken to identify the key issues of relevance pertaining to public infrastructure as perceived by the public.<br></br><br></br>" +
                "Local action Groups will be initiated and formed to encourage participation from the public on a very regional scale. Local action groups will play a key role in engaging public to tackle issues which might not be high on government priority.<br></br><br></br>" +
                "Successfully completed initiatives of Local action groups will be documented as an evidence of positive change due to public participation.<br></br>"));


    }

    @Override
    protected int getTitleText() {
        return R.string.about;
    }

    @Override
    protected int getLayout() {
        return R.layout.ac_about;
    }

}
