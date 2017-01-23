package sandeep.city;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class About extends Fragment {

    TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        text= (TextView) v.findViewById(R.id.textView7);
        text.setText(Html.fromHtml("<b>Changepins</b> is a crowd sourced grass roots initiative to raise awareness about and improve the state of public and social infrastructure leveraging digital and mobile technology.<br></br><br></br>" +
                "We intend to work in tandem with public administration and highlight trends of public concern to be leveraged as intelligence to enable inclusive decision making. We intend to bridge the gap between the public and the local administration, enabling real time reporting and documentation of the problems faced.<br></br><br></br>" +
                " Our vision is to provide the citizens with a platform to participate and contribute for improving their lifestyle and standards of living. A data driven approach is taken to identify the key issues of relevance pertaining to public infrastructure as perceived by the public.<br></br><br></br>" +
                "Local action Groups will be initiated and formed to encourage participation from the public on a very regional scale. Local action groups will play a key role in engaging public to tackle issues which might not be high on government priority.<br></br><br></br>" +
                "Successfully completed initiatives of Local action groups will be documented as an evidence of positive change due to public participation.<br></br>"));
        return v;
    }

}
