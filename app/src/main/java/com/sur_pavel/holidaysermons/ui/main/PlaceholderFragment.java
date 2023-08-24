package com.sur_pavel.holidaysermons.ui.main;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sur_pavel.holidaysermons.R;
import com.sur_pavel.holidaysermons.databinding.ActivitySecondBinding;
import com.sur_pavel.holidaysermons.databinding.FragmentSecondBinding;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
    private PageViewModel pageViewModel;
    private FragmentSecondBinding binding;
    private WebView webView;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        webView = binding.webView;
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState.getBundle("webViewState"));
        } else {
            webView.setVerticalScrollBarEnabled(true);
            webView.setHorizontalScrollBarEnabled(true);

            webView.setWebViewClient(new WebViewClient() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(request.getUrl().toString());
                    return false;
                }
            });
            binding.forwardArrow.setOnClickListener(view -> webView.goForward());
            binding.backArrow.setOnClickListener(view -> webView.goBack());
            pageViewModel.getText().observe(getViewLifecycleOwner(), s -> {
                if (s != null) {
                    binding.text.setText(String.format("св. %s", s.replace("+ слово на +", ": ")));
                    webView.loadUrl(GOOGLE_SEARCH_URL + "?q=" + s + " &num=30");
                }
            });
        }
        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();
        webView.saveState(bundle);
        outState.putBundle("webViewState", bundle);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        final MenuItem searchItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) searchItem.getActionView();

        ActivitySecondBinding activitySecondBinding = ActivitySecondBinding.inflate(getLayoutInflater());
        View toolbar = activitySecondBinding.appBarSecond.toolbar;
        ViewGroup.LayoutParams navButtonsParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Context context = searchView.getContext();
        Button btnNext = new Button(context);
        btnNext.setBackground(context.getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24));

        Button btnPrev = new Button(searchView.getContext());
        btnPrev.setBackground(context.getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24));
        btnPrev.setLayoutParams(navButtonsParams);
        btnNext.setLayoutParams(navButtonsParams);

        TextView searchStats = new TextView(searchView.getContext());

        LinearLayout searchLinearLayout = (LinearLayout) searchView.getChildAt(0);
        searchLinearLayout.addView(searchStats);
        searchLinearLayout.addView(btnPrev, navButtonsParams);
        searchLinearLayout.addView(btnNext, navButtonsParams);
        searchLinearLayout.setGravity(Gravity.BOTTOM);

        final String[] searchQuery = new String[1];
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery[0] = newText;
                if (!newText.equals("")) {
                    webView.findAllAsync(newText);
                } else {
                    webView.clearMatches();
                }
                return false;
            }
        });

        btnNext.setOnClickListener(v -> {
            if (!searchQuery[0].equals("")) {
                webView.findNext(true);
            }
        });

        btnPrev.setOnClickListener(v -> {
            if (!searchQuery[0].equals("")) {
                webView.findNext(false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search_bar) {

            return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    public boolean onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}