package org.liyunkun.week9_4listvideo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = ((ListView) findViewById(R.id.lv));
        List<VideoBean> list = pauseJson();
        MyAdapter adapter=new MyAdapter(list,this);
        lv.setAdapter(adapter);
    }

    private List<VideoBean> pauseJson() {
        String json = getJson();
        List<VideoBean> list=new ArrayList<>();
        try {
            JSONArray array = new JSONObject(json).optJSONObject("data").optJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                if (array.getJSONObject(i).has("ad")) {
                    continue;
                }
                JSONObject group = array.optJSONObject(i).optJSONObject("group");
                String videoUrl = group.optJSONObject("360p_video").optJSONArray("url_list").optJSONObject(0).optString("url");
                String title = group.optString("title");
                int height = group.optInt("video_height");
                int width = group.optInt("video_width");
                String imageUrl = group.optJSONObject("medium_cover").optJSONArray("url_list").optJSONObject(0).optString("url");
                list.add(new VideoBean(videoUrl,imageUrl,title,width,height));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String getJson() {
        StringBuffer json = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open("video1.json")));
            String result;
            while(true){
                result=br.readLine();
                if (result == null) {
                    break;
                }else{
                    json.append(result);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
