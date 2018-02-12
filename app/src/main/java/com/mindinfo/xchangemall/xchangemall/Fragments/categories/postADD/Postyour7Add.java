package com.mindinfo.xchangemall.xchangemall.Fragments.categories.postADD;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.mindinfo.xchangemall.xchangemall.R;
import com.mindinfo.xchangemall.xchangemall.activities.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.mindinfo.xchangemall.xchangemall.Constants.NetworkClass.addJobs;
import static com.mindinfo.xchangemall.xchangemall.Constants.NetworkClass.addProperty;
import static com.mindinfo.xchangemall.xchangemall.Constants.NetworkClass.addpost;
import static com.mindinfo.xchangemall.xchangemall.Fragments.categories.postJob.PostyourJob.job_exp;
import static com.mindinfo.xchangemall.xchangemall.Fragments.categories.postJob.PostyourJob.job_responsbitity;
import static com.mindinfo.xchangemall.xchangemall.Fragments.categories.postJob.PostyourJob.job_salary;
import static com.mindinfo.xchangemall.xchangemall.Fragments.categories.postJob.PostyourJob.job_title;
import static com.mindinfo.xchangemall.xchangemall.storage.MySharedPref.getData;
import static com.mindinfo.xchangemall.xchangemall.storage.MySharedPref.saveData;


public class Postyour7Add extends Fragment implements View.OnClickListener, BaseSliderView.OnSliderClickListener {

    static ArrayList<String> imageSet = new ArrayList<String>();
    ArrayList<String> categoryids ;
    String address;
    String obj;
    JSONObject postOBj= new JSONObject();
    //next_btn
    private TextView next_btn;
    //Fragment Manager
    private FragmentManager fm;
    private String sub_cat_id = "", MainCatType, postTitle = "", postDes = "", postPrice = "",
            postSize = "", postCondition, privacy_str, phone_str = "",
            Existence_str, contName_str = "", language_str = "", lat = "", lng = "", zipcode = "";
    private CheckBox checkboxFree, checkboxPaid, checkboxAutoRenew;
    private ImageView cross_imageView, back_arrowImage;
    private TextView pageNo_textView;
    private String pcat_name;
    private SliderLayout imageSlider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.postyour7add, null);

        //Get fm
        fm = getActivity().getSupportFragmentManager();
        MainCatType = getData(getActivity().getApplicationContext(), "MainCatType", "");


        System.out.println("**** **** Main Cat at post time ******* " + MainCatType);

        finditem(v);
        OnClick(v);
       categoryids = new ArrayList<String>();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            sub_cat_id = bundle.getString("sub_cat_id");
            address = bundle.getString("completeaddress");
            privacy_str = bundle.getString("privacy_str");
            phone_str = bundle.getString("phone_str");
            Existence_str = bundle.getString("Existence_str");
            contName_str = bundle.getString("contName_str");
            language_str = bundle.getString("language_str");
            imageSet = bundle.getStringArrayList("imageSet");
            postTitle = bundle.getString("postTitle");

            if (!MainCatType.equals("101")) {

                postDes = bundle.getString("postDes");
                postPrice = bundle.getString("postPrice");
                postSize = bundle.getString("postSize");
                postCondition = bundle.getString("postCondition");
                pcat_name = bundle.getString("pcat_name");
                categoryids = bundle.getStringArrayList("selectedcategories");
            }

            else if (MainCatType.equals("102") || MainCatType.equals("272"))
            {

                System.out.println("**** **** MAin Cat in if else  ******* " + MainCatType);

                obj = bundle.getString("prop_obj");
                try {
                    postOBj = new JSONObject(obj);
                    System.out.println("** prop obj frag 7 ****");
                    System.out.println(postOBj);
                } catch (JSONException e) {

                }
                categoryids = bundle.getStringArrayList("selectedcategories");
                System.out.println("**** **** Cat in if else  ******* " + categoryids);

            }

            else {
                obj = bundle.getString("bussinessobj");
                saveData(getActivity().getApplicationContext(), "MainCatType", "101");
            }


            lat = bundle.getString("lat");
            lng = bundle.getString("lng");

            System.out.println("************* latlng at post time ****");
            System.out.println(lat);
            System.out.println(lng);


            HashMap<String, File> url_maps = new HashMap<String, File>();

            for (int i = 0; i < imageSet.size(); i++) {
                url_maps.put("image" + i, new File(imageSet.get(i)));


            }
            for (String name : url_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(getActivity().getApplicationContext());
                // initialize a SliderLayout
                textSliderView
                        .description(name)
                        .image(url_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.CenterInside)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);
                imageSlider.stopAutoCycle();
                imageSlider.clearAnimation();
                imageSlider.addSlider(textSliderView);
            }


        }

        return v;
    }

    //find item
    private void finditem(View v) {
        imageSlider = (SliderLayout) v.findViewById(R.id.slider);

        next_btn = (TextView) v.findViewById(R.id.next_btn);
        checkboxFree = (CheckBox) v.findViewById(R.id.checkboxFree);
        checkboxPaid = (CheckBox) v.findViewById(R.id.checkboxPaid);
        checkboxAutoRenew = (CheckBox) v.findViewById(R.id.checkboxAutoRenew);

        cross_imageView = (ImageView) v.findViewById(R.id.cross_imageView);
        pageNo_textView = (TextView) v.findViewById(R.id.pageNo_textView);
        back_arrowImage = (ImageView) v.findViewById(R.id.back_arrowImage);
        pageNo_textView.setText("7of7");
    }

    //Set on Click on
    private void OnClick(View view) {
        next_btn.setOnClickListener(this);
        checkboxFree.setOnClickListener(this);
        checkboxPaid.setOnClickListener(this);
        checkboxAutoRenew.setOnClickListener(this);
        back_arrowImage.setOnClickListener(this);
        cross_imageView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrowImage:
               getActivity().onBackPressed();
                break;
            case R.id.cross_imageView:
                OpenMainActivity();
                break;

            case R.id.next_btn:

                System.out.println("************* Main Cat at post property " + MainCatType);
                System.out.println("************* Cat at post property " + categoryids);
                PostAddbutonClick(MainCatType);
                break;
            case R.id.checkboxFree:
                Switch();
                break;
            case R.id.checkboxPaid:
                Switch1();
                break;
            case R.id.checkboxAutoRenew:
                Switch1();
                break;
        }
    }

    private void Switch() {
        if (checkboxFree.isChecked()) {
            checkboxPaid.setChecked(false);
            checkboxAutoRenew.setChecked(false);
        }
    }

    private void Switch1() {
        if (checkboxFree.isChecked()) {
            checkboxFree.setChecked(false);
        }
    }

    private void PostAddbutonClick(String mainCatType) {

        if (checkboxFree.isChecked()) {

                System.out.println(categoryids);
                System.out.println(mainCatType);


                String user_id = getData(getActivity().getApplicationContext(), "user_id", "");
                String jobtype = "";

                switch (mainCatType) {
                    case "103":

                        jobtype = getData(getActivity().getApplicationContext(), "job_type", "");

                        if (jobtype.equals("Full time"))
                            jobtype = "2";
                        else
                            jobtype = "1";


                        System.out.println("******* edtails for job post ***** ");
                        System.out.println(job_exp);
                        System.out.println(job_responsbitity);
                        System.out.println(job_title);
                        System.out.println(job_salary);
                        addJobs(getActivity(), user_id, contName_str, job_title, postDes, address,
                                phone_str,
                                lat, lng, categoryids, MainCatType,
                                "en", imageSet, job_responsbitity, job_exp, jobtype, job_salary);
                        break;

                    case "104":

                        addpost(getActivity(), user_id, contName_str, postTitle, postDes, postPrice, address, phone_str,
                                lat, lng, categoryids, MainCatType,
                                "en", imageSet);
                        break;


                        case "102":
                            try {
                                String val = getData(getActivity().getApplicationContext(),"prop_obj","");
                                postOBj= new JSONObject(val);

                                String roomUnit =postOBj.getString("room_unit");

                            String prop_desc =postOBj.getString("property_desc");
                            String prop_price =postOBj.getString("property_price");
                            String prop_size =postOBj.getString("prop_size");
                            String washroom_unit =postOBj.getString("bathroom_unit");

                                System.out.println("** adding property ********");

                                addProperty(getActivity(), user_id, contName_str, postTitle, prop_desc, address, phone_str, lat, lng,
                                        categoryids, MainCatType, "en", imageSet,washroom_unit,roomUnit,"yes",
                                        "no",prop_price,prop_size);
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }

                        break;


                            case "272":
                            try {
                                String val = getData(getActivity().getApplicationContext(),"prop_obj","");
                                postOBj= new JSONObject(val);

                                String roomUnit =postOBj.getString("room_unit");

                            String prop_desc =postOBj.getString("property_desc");
                            String prop_price =postOBj.getString("property_price");
                            String prop_size =postOBj.getString("prop_size");
                            String washroom_unit =postOBj.getString("bathroom_unit");

                                System.out.println("** adding property ********");

                                addProperty(getActivity(), user_id, contName_str, postTitle, prop_desc, address, phone_str, lat, lng,
                                        categoryids, MainCatType, "en", imageSet,washroom_unit,roomUnit,"yes",
                                        "no",prop_price,prop_size);
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }

                        break;

                    case "101":
//                        try {
//
//                            JSONObject joboj = new JSONObject(obj);
//                            String bussiness_name, business_about, social_media_link, website_link, hours_of_operation;
//
//                            bussiness_name = joboj.getString("business_name");
//                            business_about = joboj.getString("about_business");
//                            social_media_link = joboj.getString("social_media_link");
//                            website_link = joboj.getString("website_link");
//                            hours_of_operation = joboj.getString("hours_of_operation");
//
//                            NetworkClass.postImage2(getActivity(),
//                                    user_id,
//                                    contName_str,
//                                    "",
//                                    address,
//                                    phone_str,
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    lat,
//                                    lng,
//                                    sub_cat_id,
//                                    MainCatType,
//                                    bussiness_name,
//                                    business_about,
//                                    social_media_link, website_link,
//                                    hours_of_operation,
//                                    "en");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                        break;
                }

        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void OpenMainActivity() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        getActivity().startActivity(i);
    }


//    public static void addpost(final Context context, final String user_id, final String contact_person, final String title, final String description
//            , final String price, final String address, final String phone_no
//            , final String latitude, final String longitude,
//                               final ArrayList<String> category_array, final String parent_category,
//                               final String lng, ArrayList<String> imageSet)
//    {
//
//        final AsyncHttpClient client = new AsyncHttpClient();
//        final RequestParams params = new RequestParams();
//        final ProgressDialog ringProgressDialog;
//        ringProgressDialog = ProgressDialog.show(context, "Please wait ...", "upload process", true);
//        ringProgressDialog.setCancelable(false);
//
//        String category = category_array.toString().replace("[", "").replace("]", "")
//                .replace(", ", ",");
//
//        String contact_by = getData(context,"contact_by","");
//        String currency_code = getData(context,"currency_code","");
//
//
//            params.put("contact_by", contact_by);
//            params.put("title", title);
//            params.put("user_id", user_id);
//            params.put("description", description);
//            params.put("price", price);
//            params.put("address", address);
//            params.put("phone_no", phone_no);
//            params.put("latitude", latitude);
//            params.put("lng", lng);
//            params.put("category", category);
//            params.put("parent_category", parent_category);
//            params.put("contact_person", contact_person);
//        params.put("longitude", longitude);
//        params.put("currency_code", currency_code);
//
//            System.out.println("*************** featured image data ***********");
//
//        try {
//            for (int i = 0; i< Postyour7Add.imageSet.size(); i++)
//            {
//                String result = getData(context,"item_img"+i,"");
//
//                params.put("featured_img"+(i+1),new File(result));
//
//               NullData(context, "item_img"+i);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//
//        }
//
//        System.out.println(params);
//
//        client.post(BASE_URL_NEW+"addpost", params, new JsonHttpResponseHandler() {
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//                    ringProgressDialog.dismiss();
//                    System.out.println("******** response  add post *******");
//                    System.out.println(response);
//
//                    try {
//                        String data = response.getString("status");
//                        if (data.equals("1")) {
//                            Toast.makeText(context,"Post Added ",Toast.LENGTH_LONG).show();
//                            context.startActivity(new Intent(context, MainActivity.class));
//                        }
//                        else
//                        {
//                            Toast.makeText(context,response.getString("message"),Toast.LENGTH_LONG).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                    System.out.println("******** response  add post *******");
//                    System.out.println(errorResponse);
//                    ringProgressDialog.dismiss();
//                    Toast.makeText(context,"Internal Server Error",Toast.LENGTH_LONG).show();
//
//
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                    ringProgressDialog.dismiss();
//                    System.out.println("******** response  add post *******");
//                    System.out.println(responseString);
//
//                }
//
//            });
//
//
//    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}

