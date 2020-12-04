package com.example.project_mp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FindReferences extends AppCompatActivity {
    private static final String TAG = "Find";
    RecyclerView rvRef;
    RefferenceAdapter adapter;
    List<Refference> listRef;
    globalRef globref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_references);
        rvRef = findViewById(R.id.RVReff);
        rvRef.setLayoutManager(new LinearLayoutManager(this));
        globref = new globalRef();

        adapter = new RefferenceAdapter(this);
        rvRef.setAdapter(adapter);

        Retrofit retrofit = APIRetro.getRetrofit();
        iTunesService serv = retrofit.create(iTunesService.class);
        Call<RefResponse> call = serv.getBooks("ebook","computer");

        call.enqueue(new Callback<RefResponse>() {
            @Override
            public void onResponse(Call<RefResponse> call, Response<RefResponse> response) {
                Log.d(TAG, "onResponse: 1");
                listRef= response.body().results;
                globref.setListR(listRef);
                adapter.setListRef(listRef);
            }

            @Override
            public void onFailure(Call<RefResponse> call, Throwable t) {
                Log.d(TAG, "onResponse: 2");
                if(!globref.getListR().isEmpty()){
                    Log.d(TAG, "onResponse: 2.1");
                    adapter.setListRef(globref.getListR());
                }
                else {
                    Log.d(TAG, "onResponse: 2.2");
                    call.cancel();
                }
            }
        });
    }
}
