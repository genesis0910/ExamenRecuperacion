package facci.pm.MenendezCastro.ExamenRecuperacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button get, post;
    EditText cedula, materia, estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get = (Button) findViewById(R.id.button_get);
        post = (Button) findViewById(R.id.button_post);

        cedula= (EditText) findViewById(R.id.editText_cedula);
        materia= (EditText) findViewById(R.id.editText_materia);
        estado= (EditText) findViewById(R.id.editText_estado);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, consulta1.class);
                intent.putExtra("cedula", cedula.getText().toString());
                startActivity(intent);

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealizarPost();
            }
        });
    }

    public void RealizarPost() {

        JSONObject js = new JSONObject();
        try {
            js.put("cedula", cedula.getText().toString());
            js.put("nombre",materia.getText().toString());
            js.put("estado", estado.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url = "https://backend-posts.herokuapp.com/subject";


        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, js,
        new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("error", "Response_Code from Volley" + "\n" + response.toString() + " i am king");
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Erro", "Error: " + error.getMessage());
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        Log.e("error", "onErrorResponse: of uploadUser" + res);
                        //   JSONObject obj = new JSONObject(res);
                    } catch (Exception e1) {
                        // Couldn't properly decode data to string

                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        Log.e("error", "uploadUser:  near volley new request ");
        // Adding request to request queue
        Volley.newRequestQueue(this).add(postRequest);
    }
}
