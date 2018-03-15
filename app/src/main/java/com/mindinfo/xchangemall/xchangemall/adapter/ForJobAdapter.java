package com.mindinfo.xchangemall.xchangemall.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.mindinfo.xchangemall.xchangemall.R;
import com.mindinfo.xchangemall.xchangemall.activities.business_page.Business_postownerProfileActivity;
import com.mindinfo.xchangemall.xchangemall.activities.job_Activities.JobsCatDetailsActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.mindinfo.xchangemall.xchangemall.Constants.NetworkClass.Send_fav;
import static com.mindinfo.xchangemall.xchangemall.Constants.NetworkClass.getPostDetails;
import static com.mindinfo.xchangemall.xchangemall.Constants.NetworkClass.openReportWarning;
import static com.mindinfo.xchangemall.xchangemall.other.GeocodingLocation.getAddressFromLatlng;
import static com.mindinfo.xchangemall.xchangemall.storage.MySharedPref.getData;


/**
 * Created by Mind Info- Android on 09-Nov-17.
 */

public class ForJobAdapter extends RecyclerView.Adapter<ForJobAdapter.ViewHolder> {

    private String str_image_arr[];
    private String user_id, address, fav_Status, report_Status, getPostTitle, post_id, fragment_name, jobtype, salary, getItem_image;
    private JSONArray jobj;
    private Activity context;

    public ForJobAdapter(Activity context, JSONArray jobj, String fragment_name) {
        this.context = context;
        this.jobj = jobj;
        this.fragment_name = fragment_name;
    }

//
//    private void getPostDetails(String user_id, final String post_id, final
//    ArrayList<String> postarr) {
//        final AsyncHttpClient client = new AsyncHttpClient();
//        final RequestParams params = new RequestParams();
//
//        params.put("user_id", user_id);
//        params.put("post_id", post_id);
//        final ProgressDialog ringProgressDialog;
//        ringProgressDialog = ProgressDialog.show(context, "Please wait ...", "Loading Post", true);
//        ringProgressDialog.setCancelable(false);
//
//        client.post(BASE_URL_NEW + "get_post_details", params, new JsonHttpResponseHandler() {
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                responseDEtailsOBJ = response;
//
//                ringProgressDialog.dismiss();
//
//                System.out.println(response);
//                try {
//                    response_fav = response.getString("status");
//                    if (response_fav.equals("1")) {
//                        JSONObject obj = response.getJSONObject("result");
//                        responseDEtailsOBJ = obj;
//                        openNextAct(responseDEtailsOBJ);
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                ringProgressDialog.dismiss();
//                Toast.makeText(context, "Unable to get details ,Try again", Toast.LENGTH_SHORT).show();
//                System.out.println(errorResponse);
//                responseDEtailsOBJ = errorResponse;
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//
//                ringProgressDialog.dismiss();
//                Toast.makeText(context, "Unable to get details ,Try again", Toast.LENGTH_SHORT).show();
//
//                System.out.println(responseString);
//
//            }
//
//        });
//
//    }

    private void applyJob(int position) {

        try {
            getItem_image = jobj.getJSONObject(position).getString("featured_img");

            ArrayList<String> postarr = new ArrayList<String>();

            postarr.add(getItem_image);

            for (int i = 0; i < postarr.size(); i++) {
                String image_str = postarr.get(i);
                str_image_arr = new String[]{image_str};
            }
            post_id = jobj.getJSONObject(position).getString("id");
            user_id = getData(context.getApplicationContext(), "user_id", "");
            System.out.println("** item at click *****");
            System.out.println(post_id);
            getPostDetails(context, user_id, post_id, postarr, JobsCatDetailsActivity.class, fragment_name);


//            getPostDetails(user_id, post_id, postarr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openNextAct(JSONObject responseDEtailsOBJ) {

        Intent nextAct = new Intent(context, JobsCatDetailsActivity.class);
        nextAct.putExtra("productDetails", responseDEtailsOBJ.toString());
        nextAct.putExtra("fragment_name", fragment_name);

        context.startActivity(nextAct);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        Typeface face = Typeface.createFromAsset(context.getAssets(),
                "fonts/estre.ttf");
        holder.item_location.setTypeface(face);
        holder.ItemTitleText.setTypeface(face);
        holder.jobtype_textview.setTypeface(face);
        holder.salary_tv.setTypeface(face);


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

            holder.item_location.setText(address);
            holder.ItemTitleText.setText(getPostTitle);
            holder.jobtype_textview.setText(jobtype);
            holder.salary_tv.setText(salary);

            if (fav_Status.equals("0"))
                holder.fav_img.setImageResource(R.drawable.favv);
            else if (fav_Status.equals("1"))
                holder.fav_img.setImageResource(R.drawable.fav);


            if (report_Status.equals("0"))
                holder.report_img.setImageResource(R.drawable.flag_red);
            else if (report_Status.equals("1"))
                holder.report_img.setImageResource(R.drawable.flag_green);
if (getItem_image.length()>3)
            Picasso.with(context).load(getItem_image)
                    .placeholder(R.drawable.no_img)
                    .into(holder.itemImageView);
else
{
    holder.itemImageView.setImageResource(R.drawable.no_img);
}

        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.ImageView_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_id = getData(context, "user_id", "");
                try {
                    post_id = jobj.getJSONObject(position).getString("id");
                    System.out.println("** item at click *****");
                    System.out.println(post_id);
                    Send_fav(user_id, post_id, holder.fav_img, context);

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
                    openReportWarning(user_id, post_id, holder.report_img, context);

                } catch (JSONException e) {
                    e.printStackTrace();
                  }

            }
        });


        if (fragment_name.equals("business")) {
            holder.buy_now_btn.setText(R.string.view);

            holder.mainLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextAct = new Intent(context, Business_postownerProfileActivity.class);
                    context.startActivity(nextAct);
                }
            });

        } else if (fragment_name.equals("job")) {
            holder.buy_now_btn.setText(R.string.apply_now);

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


    }

    @Override
    public int getItemCount() {
        return jobj.length();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlist_jobs, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView item_location, ItemTitleText, ItemSubTitleText, jobtype_textview, salary_tv;
        private ImageView itemImageView, fav_img, report_img;
        private LinearLayout ImageView_fav, ImageView_report;
        private Button buy_now_btn;
        private LinearLayout mainLay;

        public ViewHolder(View rowView) {
            super(rowView);

            buy_now_btn = (Button) rowView.findViewById(R.id.buyNow);
            item_location = (TextView) rowView.findViewById(R.id.item_location);
            ItemTitleText = (TextView) rowView.findViewById(R.id.ItemTitleText);
            ItemSubTitleText = (TextView) rowView.findViewById(R.id.ItemSubTitleText);
            jobtype_textview = (TextView) rowView.findViewById(R.id.jobtypeTv);
            salary_tv = (TextView) rowView.findViewById(R.id.salaryTV);
            itemImageView = (ImageView) rowView.findViewById(R.id.itemImageView);
            ImageView_fav = (LinearLayout) rowView.findViewById(R.id.ImageView_fav);
            ImageView_report = (LinearLayout) rowView.findViewById(R.id.ImageView_report);
            mainLay = (LinearLayout) rowView.findViewById(R.id.mainLay);
            fav_img = (ImageView) rowView.findViewById(R.id.fav_img);
            report_img = (ImageView) rowView.findViewById(R.id.report_img);

        }
    }

}
