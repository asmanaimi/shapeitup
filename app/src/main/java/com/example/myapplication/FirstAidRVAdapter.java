package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.ConversationActions;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class FirstAidRVAdapter extends RecyclerView.Adapter<FirstAidRVAdapter.TipsViewHolder>{
   // private static  final String CONTENT_URL="http://careforu.esy.es/first_aid_content.php";

    List<FirstAidCard> firstAidList;
    boolean offline;
    Context context;
    String htmlres,photo,text,title; int pid;

    FirstAidRVAdapter(List<FirstAidCard> firstAidList, Context context, boolean offline)
    {
        this.firstAidList = firstAidList;
        this.offline = offline;
        this.context = context;
    }


    @Override
    public TipsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        TipsViewHolder tvh = new TipsViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(TipsViewHolder holder, final int position) {
        holder.title.setText(firstAidList.get(position).title);
        holder.content.setText(firstAidList.get(position).content);
        Picasso.with(context).load(firstAidList.get(position).imageId).into(holder.image);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pid = firstAidList.get(position).id;
                text = firstAidList.get(position).content;
                photo = firstAidList.get(position).imageId;
                title = firstAidList.get(position).title;
                if(offline)
                {
                    Intent intent = new Intent(context,FirstAidContent.class);
                    intent.putExtra("pid",pid);
                    intent.putExtra("offline",true);
                    intent.putExtra("title",title);
                    context.startActivity(intent);
                }
               // else
                //    getContent(pid);
            }
        });
        //holder.image.setImageResource(firstAidList.get(position).imageId);

    }

    @Override
    public int getItemCount() {
        return firstAidList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class TipsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView content;
        ImageView image;

        TipsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card);
            title = (TextView)itemView.findViewById(R.id.ftips_title);
            content = (TextView)itemView.findViewById(R.id.ftips_content);
            image = (ImageView)itemView.findViewById(R.id.ftips_image);
        }
    }}
   /* void loadContent(final int id){

    }*/
/*

    private void getContent(final int id) {

        class GetContent extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Fetching Data...","Please Wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }



                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context,error.toString()+"\nPlease Try again in a while",Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("id",String.valueOf(id));
                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
                return null;
            }
        }
        GetContent gc = new GetContent();
        gc.execute();
    }
}*/
