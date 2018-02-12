package com.mindinfo.xchangemall.xchangemall.Fragments.categories.postADD;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.mindinfo.xchangemall.xchangemall.Constants.NetworkClass;
import com.mindinfo.xchangemall.xchangemall.Fragments.categories.postProperty.Postyour4Property;
import com.mindinfo.xchangemall.xchangemall.R;
import com.mindinfo.xchangemall.xchangemall.activities.main.MainActivity;
import com.mindinfo.xchangemall.xchangemall.adapter.MULTIPLEsELECTIONcATEGORY;
import com.mindinfo.xchangemall.xchangemall.beans.categories;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.mindinfo.xchangemall.xchangemall.adapter.MULTIPLEsELECTIONcATEGORY.idarray;
import static com.mindinfo.xchangemall.xchangemall.storage.MySharedPref.getData;
import static com.mindinfo.xchangemall.xchangemall.storage.MySharedPref.saveData;

public class Postyour3Add extends Fragment implements View.OnClickListener, BaseSliderView.OnSliderClickListener {


    ArrayList<String> imageSet = new ArrayList<String>();
    MULTIPLEsELECTIONcATEGORY postadapter;
    private Button next_btn;

    private FragmentManager fm;
    private ImageView cross_imageView;
    private TextView pageNo_textView;
    private ImageButton back_arrowImage;
    private ListView cat_sub_list_view;
    private ArrayList<categories> arrayList = new ArrayList<>();
    private String MainCatType;
    private SliderLayout imageSlider;
    private Button cat_TextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.postyour3add,null);

        //Get fm
        fm = getActivity().getSupportFragmentManager();

      Bundle bundle = this.getArguments();
        if(bundle!=null)
        {
            MainCatType = bundle.getString("MainCatType");
            imageSet = new ArrayList<String>();
            imageSet = bundle.getStringArrayList("imageSet");
        }
        MainCatType=getData(getActivity().getApplicationContext(),"pcat_id","");

        imageSlider = (SliderLayout)v.findViewById(R.id.slider);
        cat_sub_list_view = (ListView) v.findViewById(R.id.cat_sub_list_view);
        next_btn = (Button) v.findViewById(R.id.next_btn);
        cross_imageView = (ImageView) v.findViewById(R.id.cross_imageView);
        pageNo_textView = (TextView) v.findViewById(R.id.pageNo_textView);
        back_arrowImage = (ImageButton) v.findViewById(R.id.back_arrowImage);
        cat_TextView = (Button) v.findViewById(R.id.cat_TextView);



        pageNo_textView.setText("3of7");

        if (MainCatType.equals("101"))
            cat_TextView.setText("For Sale");
        else if (MainCatType.equals("102"))
            cat_TextView.setText("Property Rent");

        else if (MainCatType.equals("272"))
            cat_TextView.setText("Property Sale");

        System.out.println("**** main cat type ***** " +MainCatType);

        next_btn.setOnClickListener(this);
        back_arrowImage.setOnClickListener(this);
        cross_imageView.setOnClickListener(this);


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

            if (getData(getActivity().getApplicationContext(), "first_entry_cat", "") != null) {

                if (getData(getActivity().getApplicationContext(), "first_entry_cat", "").equals("true"))
                {
                    idarray.clear();
                }

            }
         postadapter = new MULTIPLEsELECTIONcATEGORY(arrayList,getActivity());
        cat_sub_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        cat_sub_list_view.setAdapter(postadapter);

        String str = MainCatType;

        NetworkClass.getListData(str, arrayList, getActivity().getApplicationContext());

        postadapter.notifyDataSetChanged();


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.next_btn:

                System.out.println("**** main cat type on click ***** " +MainCatType);
                System.out.println("********* selected fields ***********");
                System.out.println(idarray);
                System.out.println(idarray.size());

                if (idarray.size()>0) {
                    saveData(getActivity().getApplicationContext(), "first_entry_cat", "false");

                    Bundle bundle = new Bundle();
                    //bundle.putStringArray("imagess",str_image_arr);
                    bundle.putStringArrayList("imageSet", imageSet);
                    bundle.putStringArrayList("categories", idarray);
                    bundle.putString("MainCatType", MainCatType);

                    String category = idarray.toString().replace("[", "").replace("]", "")
                            .replace(", ", ",");
                    saveData(getActivity(),"categories",category);


                    if (MainCatType.equals("104")) {


                        Postyour4Add postyour4Add = new Postyour4Add();
                        postyour4Add.setArguments(bundle);
                        fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.allCategeries, postyour4Add)
                                .addToBackStack(null).commit();
                    }
                    else if (MainCatType.equals("102") || MainCatType.equals("272") )
                    {
                        Postyour4Property postyour4add = new Postyour4Property();
                        postyour4add.setArguments(bundle);
                        fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.allCategeries, postyour4add).addToBackStack(null).commit();

                    }


                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Please select atleast 1 category to proceed ",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.back_arrowImage:
                saveData(getActivity().getApplicationContext(), "first_entry_cat", "false");

                getActivity().onBackPressed();
                break;
            case R.id.cross_imageView:
                OpenMainActivity();
                break;
        }
    }

    private void OpenMainActivity()
    {
        Intent i = new Intent(getActivity(), MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        getActivity().startActivity(i);
    }

    private void opnenext(String str)
    {

        Bundle bundle = new Bundle();
        bundle.putString("MainCatType",MainCatType);
        bundle.putString("sub_cat_id",str);
        bundle.putStringArrayList("imageSet",imageSet);

        if (MainCatType.equals("101")) {
            Postyour4Add postyour4add = new Postyour4Add();
            postyour4add.setArguments(bundle);
            fm.beginTransaction().replace(R.id.allCategeries, postyour4add).addToBackStack(null).commit();

        }
        else if (MainCatType.equals("102"))
        {
            Postyour4Property postyour4add = new Postyour4Property();
            postyour4add.setArguments(bundle);
            fm.beginTransaction().replace(R.id.allCategeries, postyour4add).addToBackStack(null).commit();

        }

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}

