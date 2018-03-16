package com.mindinfo.xchangemall.xchangemall.Fragments.categories.postBussiness;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindinfo.xchangemall.xchangemall.Fragments.categories.postADD.Postyour5Add;
import com.mindinfo.xchangemall.xchangemall.R;
import com.mindinfo.xchangemall.xchangemall.activities.main.MainActivity;
import com.mindinfo.xchangemall.xchangemall.beans.categories;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.mindinfo.xchangemall.xchangemall.Constants.NetworkClass.getListData;
import static com.mindinfo.xchangemall.xchangemall.Fragments.categories.postADD.Postyour2Add.cross_imageView;
import static com.mindinfo.xchangemall.xchangemall.Fragments.categories.postADD.Postyour2Add.pageNo_textView;


public class PostyouBusiness extends Fragment implements View.OnClickListener {
    //Shard preferences

    ArrayList<String> postarr = new ArrayList<String>();
    TextView business_header, about_header, descripption_header, social_header, add_day_btn, website_header, hours_header;
    int count = 1;
    private Button next_btn;
    private ImageButton back_arrowImage;
    private FragmentManager fm;
    private AppCompatSpinner SpinnerCat, SpinnerWeekName1, SpinnerWeekName2, SpinnerWeekName3, SpinnerWeekName4, SpinnerWeekName5, SpinnerWeekName6, SpinnerWeekName7;
    private AppCompatSpinner SpinnerAM1, SpinnerAM2, SpinnerAM3, SpinnerAM4, SpinnerAM5, SpinnerAM6, SpinnerAM7;
    private AppCompatSpinner SpinnerPM1, SpinnerPM2, SpinnerPM3, SpinnerPM4, SpinnerPM5, SpinnerPM6, SpinnerPM7;
    private RelativeLayout add1, add2, add3, add4, add5, add6, add7;
    private String MainCatType;
    private ArrayList<categories> arrayList = new ArrayList<>();
    private EditText EditTextBusinessName, EditTextAbout, EditTextDescription, EditTextSocialMedia, EditTextWebsite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.postyour4addforbus, null);

        //Get fm
        fm = getActivity().getSupportFragmentManager();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            MainCatType = bundle.getString("MainCatType");
            postarr = bundle.getStringArrayList("imageSet");


        }
        findItem(v);
        setOnClick(v);
        loadSpinner();
        return v;
    }

    //finditem
    private void findItem(View v) {
        SpinnerCat = (AppCompatSpinner) v.findViewById(R.id.SpinnerCat);
        next_btn = (Button) v.findViewById(R.id.next_btn);

        hours_header = (TextView) v.findViewById(R.id.TextViewHouseOfOpertion);
        business_header = (TextView) v.findViewById(R.id.business_name_header);
        descripption_header = (TextView) v.findViewById(R.id.description_header_service);
        social_header = (TextView) v.findViewById(R.id.social_header);
        about_header = (TextView) v.findViewById(R.id.abount_header);
        website_header = (TextView) v.findViewById(R.id.website_header);
        add_day_btn = (TextView) v.findViewById(R.id.add_day_btn);

        back_arrowImage = (ImageButton) v.findViewById(R.id.back_arrowImage);
        EditTextBusinessName = (EditText) v.findViewById(R.id.EditTextBusinessName);
        EditTextAbout = (EditText) v.findViewById(R.id.EditTextAbout);
        EditTextDescription = (EditText) v.findViewById(R.id.EditTextDescription);
        EditTextSocialMedia = (EditText) v.findViewById(R.id.EditTextSocialMedia);
        EditTextWebsite = (EditText) v.findViewById(R.id.EditTextWebsite);

        SpinnerWeekName1 = (AppCompatSpinner) v.findViewById(R.id.SpinnerWeekName);
        SpinnerWeekName2 = (AppCompatSpinner) v.findViewById(R.id.SpinnerWeekName1);
        SpinnerWeekName3 = (AppCompatSpinner) v.findViewById(R.id.SpinnerWeekName2);
        SpinnerWeekName4 = (AppCompatSpinner) v.findViewById(R.id.SpinnerWeekName3);
        SpinnerWeekName5 = (AppCompatSpinner) v.findViewById(R.id.SpinnerWeekName4);
        SpinnerWeekName6 = (AppCompatSpinner) v.findViewById(R.id.SpinnerWeekName5);
        SpinnerWeekName7 = (AppCompatSpinner) v.findViewById(R.id.SpinnerWeekName6);

        SpinnerAM1 = (AppCompatSpinner) v.findViewById(R.id.SpinnerAM);
        SpinnerAM2 = (AppCompatSpinner) v.findViewById(R.id.SpinnerAM1);
        SpinnerAM3 = (AppCompatSpinner) v.findViewById(R.id.SpinnerAM2);
        SpinnerAM4 = (AppCompatSpinner) v.findViewById(R.id.SpinnerAM3);
        SpinnerAM5 = (AppCompatSpinner) v.findViewById(R.id.SpinnerAM4);
        SpinnerAM6 = (AppCompatSpinner) v.findViewById(R.id.SpinnerAM5);
        SpinnerAM7 = (AppCompatSpinner) v.findViewById(R.id.SpinnerAM6);

        SpinnerPM1 = (AppCompatSpinner) v.findViewById(R.id.SpinnerPM);
        SpinnerPM2 = (AppCompatSpinner) v.findViewById(R.id.SpinnerPM1);
        SpinnerPM3 = (AppCompatSpinner) v.findViewById(R.id.SpinnerPM2);
        SpinnerPM4 = (AppCompatSpinner) v.findViewById(R.id.SpinnerPM3);
        SpinnerPM5 = (AppCompatSpinner) v.findViewById(R.id.SpinnerPM4);
        SpinnerPM6 = (AppCompatSpinner) v.findViewById(R.id.SpinnerPM5);
        SpinnerPM6 = (AppCompatSpinner) v.findViewById(R.id.SpinnerPM6);


        add1 = (RelativeLayout) v.findViewById(R.id.add1);
        add2 = (RelativeLayout) v.findViewById(R.id.add2);
        add3 = (RelativeLayout) v.findViewById(R.id.add3);
        add4 = (RelativeLayout) v.findViewById(R.id.add4);
        add5 = (RelativeLayout) v.findViewById(R.id.add5);
        add6 = (RelativeLayout) v.findViewById(R.id.add6);
        add7 = (RelativeLayout) v.findViewById(R.id.add7);


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
        add_day_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                addDay(count);
            }
        });


    }

    private void addDay(int count) {

        switch (count) {
            case 2:
                add2.setVisibility(View.VISIBLE);
                break;
            case 3:
                add3.setVisibility(View.VISIBLE);
                break;
            case 4:
                add4.setVisibility(View.VISIBLE);
                break;
            case 5:
                add5.setVisibility(View.VISIBLE);
                break;
            case 6:
                add6.setVisibility(View.VISIBLE);
                break;
            case 7:
                add7.setVisibility(View.VISIBLE);
                break;
        }
    }

    //set on Click Listener
    private void setOnClick(View v) {
        next_btn.setOnClickListener(this);
        back_arrowImage.setOnClickListener(this);
        cross_imageView.setOnClickListener(this);
    }

    //load spinner cat
    private void loadSpinner() {
        int catm = Integer.parseInt(MainCatType);
        // Toast.makeText(getActivity(), ""+catm, Toast.LENGTH_SHORT).show();
        getListData("101", arrayList, getActivity().getApplicationContext());
        // postAdapter.notifyDataSetChanged();
        ArrayAdapter<categories> dataAdapter = new ArrayAdapter<categories>(getActivity(),
                android.R.layout.simple_spinner_item, arrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCat.setAdapter(dataAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    private void openNext() {
        //EditTextBusinessName,EditTextAbout,EditTextDescription,EditTextSocialMedia,EditTextWebsite;
        if (EditTextBusinessName.getText().length() == 0) {
            ShowToast_msg("Enter Business Name");
            return;
        }
        if (EditTextAbout.getText().length() == 0) {
            ShowToast_msg("Enter About");
            return;
        }
        if (EditTextDescription.getText().length() == 0) {
            ShowToast_msg("Enter Description");
            return;
        }
        if (EditTextSocialMedia.getText().length() == 0) {
            ShowToast_msg("Enter social media link");
            return;
        }


        ArrayList<String> categoryids = new ArrayList<String>();
        categories cat = arrayList.get(SpinnerCat.getSelectedItemPosition());
        categoryids.add(cat.getId());
        cat.getTitle();
        cat.getId();

        System.err.println("========= selected cat for buss =======" + cat.getId() + cat.getTitle());

        JSONArray operationArray = new JSONArray();
           addspinners(SpinnerWeekName1,SpinnerAM1,SpinnerPM1,operationArray);
           addspinners(SpinnerWeekName2,SpinnerAM2,SpinnerPM2,operationArray);
           addspinners(SpinnerWeekName3,SpinnerAM3,SpinnerPM3,operationArray);
           addspinners(SpinnerWeekName4,SpinnerAM4,SpinnerPM4,operationArray);
           addspinners(SpinnerWeekName5,SpinnerAM5,SpinnerPM5,operationArray);
           addspinners(SpinnerWeekName6,SpinnerAM6,SpinnerPM6,operationArray);
           addspinners(SpinnerWeekName7,SpinnerAM7,SpinnerPM7,operationArray);

        Bundle bundle = new Bundle();
        JSONObject bussinessData = new JSONObject();
        try {
            bussinessData.put("business_name", EditTextBusinessName.getText().toString());
            bussinessData.put("postDes", EditTextDescription.getText().toString());
            bussinessData.put("about_business", EditTextAbout.getText().toString());
            bussinessData.put("social_media_link", EditTextSocialMedia.getText().toString());
            bussinessData.put("website_link", EditTextWebsite.getText().toString());
            bussinessData.put("hours_of_operation", operationArray.toString());
            bussinessData.put("category",cat.getId());

            bundle.putString("bussinessobj", bussinessData.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
        //bundle.putStringArray("imagess",str_image_arr);
        bundle.putStringArrayList("imageSet", postarr);
        bundle.putString("MainCatType", "101");
        bundle.putString("sub_cat_id", cat.getId());
        bundle.putStringArrayList("selectedcategories",categoryids);


        Postyour5Add postyour5Add = new Postyour5Add();
        postyour5Add.setArguments(bundle);
        fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.allCategeriesIN, postyour5Add).commit();
    }

    private void addspinners(AppCompatSpinner WeekName1, AppCompatSpinner spinnerAM1, AppCompatSpinner spinnerPM1, JSONArray operationArray) {
        try {

            JSONObject obj1 = new JSONObject();
            obj1.put("days", WeekName1.getSelectedItem().toString());
            obj1.put("open_time", spinnerAM1.getSelectedItem().toString());
            obj1.put("close_time", spinnerPM1.getSelectedItem().toString());
            operationArray.put(obj1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void OpenMainActivity() {
        Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        getActivity().startActivity(i);
    }


    private void ShowToast_msg(String str_msg) {
        Toast.makeText(getActivity(), str_msg, Toast.LENGTH_SHORT).show();
    }
}


