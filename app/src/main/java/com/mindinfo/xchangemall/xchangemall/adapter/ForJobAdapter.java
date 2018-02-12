package com.mindinfo.xchangemall.xchangemall.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindinfo.xchangemall.xchangemall.R;
import com.mindinfo.xchangemall.xchangemall.activities.business_page.Business_postownerProfileActivity;
import com.mindinfo.xchangemall.xchangemall.activities.job_Activities.JobsCatDetailsActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.mindinfo.xchangemall.xchangemall.Constants.NetworkClass.Send_fav;
import static com.mindinfo.xchangemall.xchangemall.Constants.NetworkClass.openReportWarning;
import static com.mindinfo.xchangemall.xchangemall.activities.main.BaseActivity.BASE_URL_NEW;
import static com.mindinfo.xchangemall.xchangemall.other.GeocodingLocation.getAddressFromLatlng;
import static com.mindinfo.xchangemall.xchangemall.storage.MySharedPref.getData;


/**
 * Created by Mind Info- Android on 09-Nov-17.
 */

public class ForJobAdapter extends BaseAdapter {

    public String str_image_arr[];
    FragmentManager fm;
    String user_id,address,fav_Status,report_Status, getPostTitle,post_id,fragment_name,jobtype,salary,getItem_image;
    JSONArray jobj;
    JSONObject responseDEtailsOBJ;
    String response_fav,applied_status;
    private Activity context;

    public ForJobAdapter(Activity context, JSONArray jobj, String fragment_name) {
        this.context = context;
        this.jobj = jobj;
        this.fragment_name = fragment_name;
    }

    @Override
    public int getCount() {
        return jobj.length();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ResourceAsColor", "ViewHolder"})
    @Override
    public View getView(final int position, View view, ViewGroup container) {
        LayoutInflater inflater = context.getLayoutInflater();
View rowView;

rowView=view;
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            rowView = inflater.inflate(R.layout.itemlist_jobs, container, false);
        } catch (InflateException e) {
            e.printStackTrace();
        }
        final ViewHolder holder = new ViewHolder();
        JSONObject responseobj = new JSONObject();
        try {
            responseobj = jobj.getJSONObject(position);
            double lat = Double.parseDouble(responseobj.getString("latitude"));
            double lng = Double.parseDouble(responseobj.getString("longitude"));

            address = getAddressFromLatlng(new LatLng(lat, lng), context, 0);

             jobtype = responseobj.getString("job_type");
             salary = responseobj.getString("salary_as_per");
            fav_Status = responseobj.getString("favorite_status");
            report_Status = responseobj.getString("report_status");

            user_id = responseobj.getString("user_id");
            getPostTitle = responseobj.getString("title");
            post_id = responseobj.getString("id");


            getItem_image = responseobj.getString("featured_img");

            System.out.println("********** item position *******");
            System.out.println(position);
            System.out.println("** item at position *****");
            System.out.println(post_id);
            System.out.println(jobtype);
            System.out.println(fav_Status);
            System.out.println(report_Status);
            System.out.println(getPostTitle);
            System.out.println(address);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.buy_now_btn = (Button) rowView.findViewById(R.id.buyNow);
        holder.item_location = (TextView) rowView.findViewById(R.id.item_location);
        holder.ItemTitleText = (TextView) rowView.findViewById(R.id.ItemTitleText);
        holder.ItemSubTitleText = (TextView) rowView.findViewById(R.id.ItemSubTitleText);
        holder.jobtype_textview = (TextView) rowView.findViewById(R.id.jobtypeTv);
        holder.salary_tv = (TextView) rowView.findViewById(R.id.salaryTV);
        holder.itemImageView = (ImageView) rowView.findViewById(R.id.itemImageView);
        holder.ImageView_fav = (LinearLayout) rowView.findViewById(R.id.ImageView_fav);
        holder.ImageView_report = (LinearLayout) rowView.findViewById(R.id.ImageView_report);
        holder.mainLay = (LinearLayout) rowView.findViewById(R.id.mainLay);
        holder.fav_img = (ImageView) rowView.findViewById(R.id.fav_img);
        holder.report_img = (ImageView) rowView.findViewById(R.id.report_img);

        if (fav_Status.equals("0"))
            Picasso.with(context).load(R.drawable.favv).into(holder.fav_img);
        else if (fav_Status.equals("1"))
            Picasso.with(context).load(R.drawable.fav).into(holder.fav_img);


        if (report_Status.equals("0"))
            Picasso.with(context).load(R.drawable.flag_red).into(holder.report_img);
        else if (report_Status.equals("1"))
            Picasso.with(context).load(R.drawable.flag_green).into(holder.report_img);


        Typeface face = Typeface.createFromAsset(context.getAssets(),
                "fonts/estre.ttf");
        holder.item_location.setTypeface(face);
        holder.ItemTitleText.setTypeface(face);
        holder.jobtype_textview.setTypeface(face);
        holder.salary_tv.setTypeface(face);

        holder.item_location.setText(address);
        holder.ItemTitleText.setText(getPostTitle);
        holder.jobtype_textview.setText(jobtype);
        holder.salary_tv.setText(salary);



//            new AsyncTaskLoadImage(holder.itemImageView,context).execute(getItem_image);

//            Bitmap image = BitmapFactory.decodeStream((InputStream)url.getContent());
//
//
////            HttpURLConnection connection =
////                    (HttpURLConnection) url.openConnection();
////
////            connection.setDoInput(true);
////            connection.connect();
////            InputStream input = connection.getInputStream();
////            Bitmap image = BitmapFactory.decodeStream(input);
//
//            Bitmap newbm = getResizedBitmap(image,100,100);
//            holder.itemImageView.setImageBitmap(newbm);


//        Picasso.with(context)
//                .load(getItem_image)
//                .placeholder(R.drawable.no_img)
//                .into(holder.itemImageView);

        Glide.with(context)
                .load(getItem_image)
                .asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.no_img)
                .into(holder.itemImageView);

        holder.ImageView_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_id = getData(context, "user_id", "");
                try {
                    post_id = jobj.getJSONObject(position).getString("id");
                    System.out.println("** item at click *****");
                    System.out.println(post_id);
                    Send_fav(user_id, post_id, holder.fav_img,context);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        holder.ImageView_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_id = getData(context, "user_id", "");
                try {
                    post_id = jobj.getJSONObject(position).getString("id");
                    System.out.println("** item at click *****");
                    System.out.println(post_id);
                    openReportWarning(user_id, post_id, holder.report_img,context);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        if (fragment_name.equals("business")) {
            holder.buy_now_btn.setText("View");

            holder.mainLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextAct = new Intent(context, Business_postownerProfileActivity.class);
                    context.startActivity(nextAct);
                }
            });

        }
        else if (fragment_name.equals("job"))
        {
            holder.buy_now_btn.setText("Apply Now");

            holder.mainLay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    applyJob(position);

                }


            });

            holder.buy_now_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (fragment_name.equals("job"))
                        applyJob(position);
                }
            });
        }
        return rowView;
    }





    public void applyJob(int position)
    {

        try {
            getItem_image = jobj.getJSONObject(position).getString("featured_img");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<String> postarr = new ArrayList<String>();

        postarr.add(getItem_image);

        for (int i = 0; i < postarr.size(); i++) {
            String image_str = postarr.get(i);
            str_image_arr = new String[]{image_str};
        }

        try {
            post_id = jobj.getJSONObject(position).getString("id");
            user_id = getData(context.getApplicationContext(),"user_id","");                    System.out.println("** item at click *****");
            System.out.println(post_id);
            getPostDetails(user_id, post_id, postarr);
        } catch (JSONException e) {

        }
    }


    private void getPostDetails(String user_id, final String post_id, final
    ArrayList<String> postarr) {
        final AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        params.put("user_id", user_id);
        params.put("post_id", post_id);
        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(context, "Please wait ...", "Loading Post", true);
        ringProgressDialog.setCancelable(false);

        client.post(BASE_URL_NEW + "get_post_details", params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                responseDEtailsOBJ = response;

                ringProgressDialog.dismiss();

                System.out.println(response);
                try {
                    response_fav = response.getString("status");
                    if (response_fav.equals("1")) {
                        JSONObject obj = response.getJSONObject("result");
                        responseDEtailsOBJ = obj;
                        openNextAct(responseDEtailsOBJ);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ringProgressDialog.dismiss();
                Toast.makeText(context,"Unable to get details ,Try again",Toast.LENGTH_SHORT).show();
                System.out.println(errorResponse);
                responseDEtailsOBJ = errorResponse;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                ringProgressDialog.dismiss();
                Toast.makeText(context,"Unable to get details ,Try again",Toast.LENGTH_SHORT).show();

                System.out.println(responseString);

            }

        });

    }

    private void openNextAct(JSONObject responseDEtailsOBJ) {

        Intent nextAct = new Intent(context, JobsCatDetailsActivity.class);
        nextAct.putExtra("productDetails",responseDEtailsOBJ.toString());
        nextAct.putExtra("fragment_name",fragment_name);

        context.startActivity(nextAct);


    }

    @SuppressLint("StaticFieldLeak")

    class ViewHolder {
        public TextView item_location, ItemTitleText, ItemSubTitleText, jobtype_textview, salary_tv;
        public ImageView itemImageView,fav_img,report_img;
        public LinearLayout ImageView_fav,ImageView_report;
        public Button buy_now_btn;
        LinearLayout mainLay;
    }

}
