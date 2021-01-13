package com.hadi.wenarkhas.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hadi.wenarkhas.R;
import com.hadi.wenarkhas.adapters.HomeRecyclerViewAdapter;
import com.hadi.wenarkhas.models.Post;
import com.hadi.wenarkhas.utils.network.GsonRequest;
import com.hadi.wenarkhas.utils.network.NetworkHelper;
import com.hadi.wenarkhas.utils.network.VolleySingleton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    RecyclerView homeRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeRecyclerView = view.findViewById(R.id.homeRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        homeRecyclerView.setLayoutManager(layoutManager);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                "Please wait...", true);

        // volley
        String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_POSTS);
        Map<String, String> params = new HashMap();
        params.put("adType", "all");
        GsonRequest<Post[]> myGsonRequest = new GsonRequest<Post[]>(Request.Method.POST, url, Post[].class, null, params,
                new Response.Listener<Post[]>() {
                    @Override
                    public void onResponse(Post[] response) {
                        dialog.dismiss();
                        HomeRecyclerViewAdapter homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(Arrays.asList(response));
                        homeRecyclerView.setAdapter(homeRecyclerViewAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        NetworkHelper.handleError(error);
                    }
                });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(myGsonRequest);


    }
}
