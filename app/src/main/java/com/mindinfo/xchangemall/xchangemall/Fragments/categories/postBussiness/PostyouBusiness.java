package com.mindinfo.xchangemall.xchangemall.Fragments.categories.postBussiness;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindinfo.xchangemall.xchangemall.Fragments.categories.postADD.Postyour5Add;
import com.mindinfo.xchangemall.xchangemall.R;
import com.mindinfo.xchangemall.xchangemall.activities.main.MainActivity;
import com.mindinfo.xchangemall.xchangemall.adapter.SlideImageAdapter;
import com.mindinfo.xchangemall.xchangemall.beans.categories;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.mindinfo.xchangemall.xchangemall.Constants.NetworkClass.getListData;
import static com.mindinfo.xchangemall.xchangemall.Fragments.categories.postADD.Postyour2Add.cross_imageView;
import static com.mindinfo.xchangemall.xchangemall.Fragments.categories.postADD.Postyour2Add.pageNo_textView;


public class PostyouBusiness extends Fragment implements View.OnClickListener {
    //Shard preferences

    private static ViewPager mPager;
    private static int currentPage = 0;
    ArrayList<String> postarr = new ArrayList<String>();
    TextView business_header,about_header,descripption_header,social_header,website_header,hours_header;
    private Button next_btn;

    private ImageButton back_arrowImage;
    private FragmentManager fm;
    private AppCompatSpinner SpinnerCat;
    private String MainCatType;
    private ArrayList<Uri> XMENArrayUri = new ArrayList<Uri>();
    private ArrayList<categories> arrayList = new ArrayList<>();
    private EditText EditTextBusinessName,EditTextAbout,EditTextDescription,EditTextSocialMedia,EditTextWebsite;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.postyour4addforbus,null);

        //Get fm
        fm = getActivity().getSupportFragmentManager();
        Bundle bundle = this.getArguments();
        if(bundle!=null)
        {
            String str_image_arr[];
            str_image_arr =  bundle.getStringArray("imagess");
            MainCatType = bundle.getString("MainCatType");
            postarr = bundle.getStringArrayList("images");

            for(int i = 0; i<postarr.size();i++)
            {
                XMENArrayUri.add(Uri.parse(postarr.get(i)));
            }

        }
        init(v);
        findItem(v);
        setOnClick(v);
        loadSpinner();
        return v;
    }

    //finditem
    private void findItem(View v)
    {
       SpinnerCat = (AppCompatSpinner) v.findViewById(R.id.SpinnerCat);
        next_btn = (Button) v.findViewById(R.id.next_btn);




        hours_header = (TextView) v.findViewById(R.id.TextViewHouseOfOpertion);
        business_header = (TextView) v.findViewById(R.id.business_name_header);
        descripption_header = (TextView) v.findViewById(R.id.description_header_service);
        social_header = (TextView) v.findViewById(R.id.social_header);
        about_header = (TextView) v.findViewById(R.id.abount_header);
        website_header = (TextView) v.findViewById(R.id.website_header);

        back_arrowImage = (ImageButton) v.findViewById(R.id.back_arrowImage);
        EditTextBusinessName = (EditText) v.findViewById(R.id.EditTextBusinessName);
        EditTextAbout = (EditText) v.findViewById(R.id.EditTextAbout);
        EditTextDescription = (EditText) v.findViewById(R.id.EditTextDescription);
        EditTextSocialMedia = (EditText) v.findViewById(R.id.EditTextSocialMedia);
        EditTextWebsite = (EditText) v.findViewById(R.id.EditTextWebsite);
        Typeface face = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(),
                "fonts/estre.ttf");
        pageNo_textView.setText("3of7");

        pageNo_textView.setTypeface(face);
        hours_header.setTypeface(face);
        business_header.setTypeface(face);
        descripption_header.setTypeface(face);
        about_header.setTypeface(face);
        social_header.setTypeface(face);
        website_header.setTypeface(face);
        hours_header.setTypeface(face);
        EditTextWebsite.setTypeface(face);
        EditTextSocialMedia.setTypeface(face);
        EditTextDescription.setTypeface(face);
        EditTextAbout.setTypeface(face);
        EditTextBusinessName.setTypeface(face);


    }

    //set on Click Listener
    private void setOnClick(View v)
    {
        next_btn.setOnClickListener(this);
        back_arrowImage.setOnClickListener(this);
        cross_imageView.setOnClickListener(this);
    }

    //load spinner cat
    private void loadSpinner()
    {
        int catm = Integer.parseInt(MainCatType);
        // Toast.makeText(getActivity(), ""+catm, Toast.LENGTH_SHORT).show();
        getListData("1",arrayList,getActivity().getApplicationContext());
       // postAdapter.notifyDataSetChanged();
        ArrayAdapter<categories> dataAdapter = new ArrayAdapter<categories>(getActivity(),
                android.R.layout.simple_spinner_item, arrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCat.setAdapter(dataAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.next_btn:
                 openNext();
                // fm.beginTransaction().replace(R.id.activity_camera_new,new Postyour4Add()).addToBackStack(null).commit();
                //getActivity().overridePendingTransition();
                break;

            case R.id.back_arrowImage:
                getActivity().onBackPressed();
                break;
            case R.id.cross_imageView:
                OpenMainActivity();
                break;
        }
    }

    private void openNext()
    {
    //EditTextBusinessName,EditTextAbout,EditTextDescription,EditTextSocialMedia,EditTextWebsite;
        if(EditTextBusinessName.getText().length() == 0)
        {
            ShowToast_msg("Enter Business Name");
            return;
        }
        if(EditTextAbout.getText().length() == 0)
        {
            ShowToast_msg("Enter About");
            return;
        }
        if(EditTextDescription.getText().length() == 0)
        {
            ShowToast_msg("Enter Description");
            return;
        }
        if(EditTextSocialMedia.getText().length() == 0)
        {
            ShowToast_msg("Enter social media link");
            return;
        }
//        if(EditTextWebsite.getText().length() == 0)
//        {
//            ShowToast_msg("Enter website url");
//            return;
//        }
        categories cat = arrayList.get(SpinnerCat.getSelectedItemPosition());
        cat.getTitle();
        cat.getId();

        Bundle bundle = new Bundle();
        JSONObject bussinessData = new JSONObject();
try {
    bussinessData.put("business_name", EditTextBusinessName.getText().toString());
    bussinessData.put("postDes", EditTextDescription.getText().toString());
    bussinessData.put("about_business", EditTextAbout.getText().toString());
    bussinessData.put("social_media_link", EditTextSocialMedia.getText().toString());
    bussinessData.put("website_link", EditTextWebsite.getText().toString());
    ;
    bussinessData.put("hours_of_operation", "");

    bundle.putString("bussinessobj",bussinessData.toString());


}
catch (Exception e)
{
    e.printStackTrace();
}
        //bundle.putStringArray("imagess",str_image_arr);
        bundle.putStringArrayList("images",postarr);
        bundle.putString("MainCatType","101");
        bundle.putString("sub_cat_id",cat.getId());

        Postyour5Add postyour5Add = new Postyour5Add();
        postyour5Add.setArguments(bundle);
        fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.allCategeriesIN,postyour5Add).commit();
    }

    private void OpenMainActivity()
    {
        Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        getActivity().startActivity(i);
    }

    private void init(View v) {

        mPager = (ViewPager) v.findViewById(R.id.viewPager);
        // mPager.setAdapter(new SlideImageAdapter(getActivity(),XMENArray));
        mPager.setAdapter(new SlideImageAdapter(XMENArrayUri,getActivity()));
        //  CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        //  indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMENArrayUri.size()) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);
    }

    private void ShowToast_msg(String str_msg)
    {
        Toast.makeText(getActivity(), str_msg, Toast.LENGTH_SHORT).show();
    }
}


