package com.example.pankaj.assignment.fragment;

/**
 * Created by Pankaj on 21-06-2017.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pankaj.assignment.R;
import com.example.pankaj.assignment.adapter.PaginationAdapter;
import com.example.pankaj.assignment.application.ApplicationUtility;
import com.example.pankaj.assignment.model.Data;
import com.example.pankaj.assignment.model.Example;
import com.example.pankaj.assignment.network.ApiClientMain;
import com.example.pankaj.assignment.sqlite.DatabaseHandler;
import com.example.pankaj.assignment.utils.PaginationAdapterCallback;
import com.example.pankaj.assignment.utils.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopFragment extends Fragment implements PaginationAdapterCallback {
    private RecyclerView main_recycler;
    private ProgressBar main_progress;
    private Button error_btn_retry;
    private TextView error_txt_cause;
    private LinearLayout errorLayout;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;
    private LinearLayoutManager linearLayoutManager;
    ArrayList<Data> dataArrayList = new ArrayList<>();
    private PaginationAdapter paginationAdapter;
    private DatabaseHandler databaseHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        databaseHandler = new DatabaseHandler(getActivity());
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        loadFirstPage();
        error_btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFirstPage();
            }
        });
    }

    private void loadFirstPage() {
        hideErrorView();
        if(ApplicationUtility.checkConnection(getActivity())){
            try {
                Call<Example> call = ApiClientMain.getApiClient().getResponseofTopDeals(currentPage);
                call.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response != null) {
                            List<Data> results = fetchResults(response);
                            main_progress.setVisibility(View.GONE);
                            if(results!=null&&results.size()>0){
                                paginationAdapter.addAll(results);
                                databaseHandler.addServerData(results);
                            }else
                            {
                                paginationAdapter.nodataavailable(true);
                                Toast.makeText(getActivity(),"No more data available",Toast.LENGTH_SHORT).show();
                            }
                            if (currentPage <= TOTAL_PAGES) paginationAdapter.addLoadingFooter();
                            else isLastPage = true;

                        }else {
                            Toast.makeText(getActivity(),"OOps!!! something went wrong",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        t.printStackTrace();
                        showErrorView(t);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            main_progress.setVisibility(View.GONE);
            List<Data> results= databaseHandler.getAllDeals();
            if(results!=null&&results.size()>0){
                paginationAdapter.addAll(results);
            }else
            {
                paginationAdapter.nodataavailable(true);
                Toast.makeText(getActivity(),"No more data available",Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void showErrorView(Throwable throwable) {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            main_progress.setVisibility(View.GONE);

            error_txt_cause.setText(fetchErrorMessage(throwable));
        }
    }

    private String  fetchErrorMessage(Throwable throwable) {

        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (ApplicationUtility.checkConnection(getActivity())) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    private List<Data> fetchResults(Response<Example> response) {
        Example topRatedMovies = response.body();
        return topRatedMovies.getDeals().getData();

    }

    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            main_progress.setVisibility(View.VISIBLE);
        }
    }

    private void initView(View view) {
        main_recycler = (RecyclerView)view.findViewById(R.id.main_recycler);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        main_recycler.setLayoutManager(linearLayoutManager);
        main_recycler.setItemAnimator(new DefaultItemAnimator());
        main_progress = (ProgressBar)view.findViewById(R.id.main_progress);
        error_btn_retry = (Button)view.findViewById(R.id.error_btn_retry);
        error_txt_cause = (TextView)view.findViewById(R.id.error_txt_cause);
        errorLayout = (LinearLayout)view.findViewById(R.id.error_layout);
        paginationAdapter = new PaginationAdapter(this,getActivity());
        main_recycler.setAdapter(paginationAdapter);
        main_recycler.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                loadNextPage();
            }



            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void loadNextPage() {
        if(ApplicationUtility.checkConnection(getActivity())){
            try {
                Call<Example> call = ApiClientMain.getApiClient().getResponseofTopDeals(currentPage);
                call.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        paginationAdapter.removeLoadingFooter();
                        isLoading = false;
                        if (response!= null) {
                            List<Data> results = fetchResults(response);
                            if(results!=null&&results.size()>0){
                                paginationAdapter.addAll(results);
                                databaseHandler.addServerData(results);
                            }else
                            {
                                paginationAdapter.nodataavailable(true);
                                Toast.makeText(getActivity(),"No more data available",Toast.LENGTH_SHORT).show();
                            }
                            if (currentPage <= TOTAL_PAGES) paginationAdapter.addLoadingFooter();
                            else isLastPage = true;

                        }else {
                            Toast.makeText(getActivity(),"OOps!!! something went wrong",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        t.printStackTrace();
                        paginationAdapter.showRetry(true, fetchErrorMessage(t));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            paginationAdapter.nodataavailable(true);
            Toast.makeText(getActivity(),"No more data available",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void retryPageLoad() {
        loadNextPage();
    }
}


