package in.betaturtle.youtubemp3;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;


public class MainActivity extends ActionBarActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_main);
        File folder = new File(Environment.getExternalStorageDirectory() + "/YoutubeMp3");
        if (!folder.exists()) {
            folder.mkdir();
        }
    }



    public void download(View v){
        EditText et = (EditText)findViewById(R.id.editText);
       // Toast.makeText(this, et.getText(), Toast.LENGTH_SHORT).show();
        Log.i("First URL", "http://youtubeinmp3.com/fetch/?api=advanced&format=JSON&video="+et.getText());
        Ion.with(context)
                .load("http://youtubeinmp3.com/fetch/?api=advanced&format=JSON&video="+et.getText())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e!=null)
                            e.printStackTrace();


                        // do stuff with the result or error
                        Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();

                        String url = result.get("link").toString().substring(1, result.get("link").toString().length()-1);
                        String title = Environment.getExternalStorageDirectory()+"/YoutubeMp3/"+result.get("title").toString().substring(1, result.get("title").toString().length()-1)+".mp3";
                        Log.e("url", url);
                        Log.e("title", title);
                        Ion.with(context)
                               // .load(result.get("link").toString().substring(1, result.get("link").toString().length()-1))
                               .load(url)
                                .write(new File(title.replaceAll("[|?*<\":>+\\[\\]/']", "_")))
                                .setCallback(new FutureCallback<File>() {
                                    @Override
                                    public void onCompleted(Exception e, File file) {
                                        if(e!=null)
                                            e.printStackTrace();
                                        Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show();

                                    }
                                });
                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
