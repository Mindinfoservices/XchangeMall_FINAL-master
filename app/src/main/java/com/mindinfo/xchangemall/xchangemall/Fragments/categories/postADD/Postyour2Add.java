package com.mindinfo.xchangemall.xchangemall.Fragments.categories.postADD;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.mindinfo.xchangemall.xchangemall.Fragments.categories.postJob.PostyourJob;
import com.mindinfo.xchangemall.xchangemall.R;
import com.mindinfo.xchangemall.xchangemall.activities.main.BaseActivity;
import com.mindinfo.xchangemall.xchangemall.activities.main.MainActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mindinfo.xchangemall.xchangemall.storage.MySharedPref.saveData;

public class Postyour2Add extends BaseActivity implements View.OnClickListener, BaseSliderView.OnSliderClickListener {


    String  id;
    ArrayList<String> postarr;
    ArrayList<String> imageSet;
    private ImageView cross_imageView;
    //next_btn
    private Button next_btn;
    private TextView pageNo_textView;
    //Fragment Manager
    private FragmentManager fm;
    private ImageButton back_arrowImage;
    private CircleImageView imageViewJobS, For_sele_imageView, servicesImageView,
            ShowcaseImageView, personalImageView, CommImageView, houseRentalImageView, propertySaleImageView;
    private Fragment fragment;

    private SliderLayout imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postyour2add);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //Get fm
        fm = getSupportFragmentManager();
        findItem();

        OnClick();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageSet = new ArrayList<String>();
            imageSet = bundle.getStringArrayList("imageSet");
            id = bundle.getString("MainCatType");

            System.out.println(" ************* Image Set ***********");
            System.out.println(imageSet);
        }

        HashMap<String, File> url_maps = new HashMap<String, File>();

        for (int i = 0; i < imageSet.size(); i++) {
            url_maps.put("image" + i, new File(imageSet.get(i)));

        }
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
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


    // find item
    private void findItem() {
        imageSlider = (SliderLayout) findViewById(R.id.slider);
        next_btn = (Button) findViewById(R.id.next_btn);
        //  Home_ImageView = (ImageView) v.findViewById(R.id.Home_ImageView);
        cross_imageView = (ImageView) findViewById(R.id.cross_imageView);
        pageNo_textView = (TextView) findViewById(R.id.pageNo_textView);
        back_arrowImage = (ImageButton) findViewById(R.id.back_arrowImage);
//        postCatMRecyclerView = (RecyclerView) v.findViewById(R.id.postCatMRecyclerView);
        pageNo_textView.setText("2of7");

        imageViewJobS = (CircleImageView) findViewById(R.id.imageViewJobS);
        For_sele_imageView = (CircleImageView) findViewById(R.id.For_sele_imageView);
        servicesImageView = (CircleImageView) findViewById(R.id.servicesImageView);
        ShowcaseImageView = (CircleImageView) findViewById(R.id.ShowcaseImageView);
        personalImageView = (CircleImageView) findViewById(R.id.personalImageView);
        CommImageView = (CircleImageView) findViewById(R.id.CommImageView);
        houseRentalImageView = (CircleImageView) findViewById(R.id.houseRentalImageView);
        propertySaleImageView = (CircleImageView) findViewById(R.id.propertySaleImageView);

    }

    //OnClick
    private void OnClick() {
//        Home_ImageView.setOnClickListener(this);
        next_btn.setOnClickListener(this);
        back_arrowImage.setOnClickListener(this);
        cross_imageView.setOnClickListener(this);

        imageViewJobS.setOnClickListener(this);
        For_sele_imageView.setOnClickListener(this);
        servicesImageView.setOnClickListener(this);
        ShowcaseImageView.setOnClickListener(this);
        personalImageView.setOnClickListener(this);
        CommImageView.setOnClickListener(this);
        houseRentalImageView.setOnClickListener(this);
        propertySaleImageView.setOnClickListener(this);
    }


    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_btn:

                opnenext(id);
                break;

            case R.id.imageViewJobS:
//                Toast.makeText(getApplicationContext(), "Under Development", Toast.LENGTH_SHORT).show();

            opnenext("103");
                break;
            case R.id.For_sele_imageView:
                opnenext("104");
                break;

            case R.id.servicesImageView:
                Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_SHORT).show();

//                ImageType_str = "1";
//                opnenext("101");
                break;

            case R.id.ShowcaseImageView:
                Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_SHORT).show();

//                ImageType_str = "5";
//                fragment = new Postyour3Add();
//                opnenext(ImageType_str);
                break;

            case R.id.personalImageView:
                Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_SHORT).show();

//                ImageType_str = "0";
//                fragment = new Postyour3Add();
//                opnenext(ImageType_str);
                break;

            case R.id.CommImageView:
                Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_SHORT).show();

//                ImageType_str = "6";
//                fragment = new Postyour3Add();
//                opnenext(ImageType_str);
                break;

            case R.id.houseRentalImageView:
//               Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_SHORT).show();

//               fragment = new PostyourHouse();
                opnenext("102");
                break;

            case R.id.propertySaleImageView:
//                Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_SHORT).show();

//                ImageType_str = "7";
////                fragment = new PostyourHouse();
               opnenext("272");
                break;

            case R.id.back_arrowImage:
                OpenMainActivity();
                break;
            case R.id.cross_imageView:
                OpenMainActivity();
                break;

        }
    }

    private void OpenMainActivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

    @SuppressLint("ResourceType")
    private void opnenext(String str)
    {

        saveData(getApplicationContext(), "MainCatType", str);

        if (str.equals("0")) {
            Toast.makeText(getApplicationContext(), "Please select category first", Toast.LENGTH_LONG).show();
        }

        else {
            switch (str) {
                case "102":
                  fragment = new Postyour3Add();
                    break;

                case "104":
                    System.out.println("********** click on next with forsale**********");
                    fragment = new Postyour3Add();
                    break;

                case "103":
                    fragment = new PostyourJob();
                    break;

                    case "272":
                    fragment = new Postyour3Add();
                    break;
            }
            saveData(getApplicationContext(), "pcat_id", str);

            if (fragment != null)
            {
                Bundle bundle = new Bundle();
                bundle.putString("MainCatType", str);
                bundle.putStringArrayList("imageSet", imageSet);
                fragment.setArguments(bundle);

                fm.beginTransaction().replace(R.id.allCategeries, fragment).addToBackStack(null)
                        .setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_left_exit)
                        //.setCustomAnimations(R.animator.fragment_slide_left_enter,R.animator.fragment_slide_right_exit)
                        .commit();
            }
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

//        final RelativeLayout open = (RelativeLayout)findViewById(R.id.fullsliderlay);
//        ImageView close = (ImageView) findViewById(R.id.close_slider);
//
//        open.setVisibility(View.VISIBLE);
//
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                open.setVisibility(View.GONE);
//            }
//        });

    }


}
