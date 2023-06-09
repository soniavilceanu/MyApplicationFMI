package com.example.myapplicationfmi;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class activities_fragment extends Fragment {

    FloatingActionButton buttonCreateDashboardTab;

    // Generate dynamic view IDs
    int previousDashboardTabDateId;
    int previousDashboardTabTitleId;
    int previousDashboardTabBodyId;
    int previousDashboardTabId;
    private RelativeLayout activitiesRelativeLayout;

    ArrayList <Integer> verticalChain = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_activities_fragment, container, false);
        activitiesRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.activitiesRelativeLayout);
//        placeholderLayout = rootView.findViewById(R.id.placeholderLayout);


        buttonCreateDashboardTab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        buttonCreateDashboardTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(previousDashboardTabId == 0){
                    previousDashboardTabId = R.id.dashboardTab;
                    previousDashboardTabTitleId = R.id.dashboardTabTitle;
                    previousDashboardTabDateId = R.id.dashboardTabDate;
                    previousDashboardTabBodyId = R.id.dashboardTabBody;

                    //View inflatedView = getLayoutInflater().inflate(R.layout.fragment_activities_fragment, null);
//                    previousDashboardTabId = ((ConstraintLayout)rootView.findViewById(R.id.dashboardTab)).getId();
//                    previousDashboardTabTitleId = ((TextView)rootView.findViewById(R.id.dashboardTabTitle)).getId();
//                    previousDashboardTabDateId = ((TextView)rootView.findViewById(R.id.dashboardTabDate)).getId();
//                    previousDashboardTabBodyId = ((TextView)rootView.findViewById(R.id.dashboardTabBody)).getId();

                    verticalChain.add(previousDashboardTabId);
                }

                // Generate dynamic view IDs
                int newDashboardTabDateId = View.generateViewId();
                int newDashboardTabTitleId = View.generateViewId();
                int newDashboardTabBodyId = View.generateViewId();
                int newDashboardTabId = View.generateViewId();

                // Create a new ConstraintLayout
                ConstraintLayout constraintLayout = new ConstraintLayout(v.getContext());
                constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT));
                constraintLayout.setId(newDashboardTabId);
                constraintLayout.setBackgroundResource(R.drawable.dashboard_article_background);
                constraintLayout.setPadding(10, 10, 10, 10);
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) constraintLayout.getLayoutParams();
                //layoutParams.setMarginStart(30);
                //layoutParams.setMarginEnd(30);
                layoutParams.setMargins(30,30,30,0);

                layoutParams.topMargin = 30;
                //layoutParams.setMargins(0, 30, 0, 0);

                constraintLayout.setLayoutParams(layoutParams);

                // Create the TextView for dashboardTabDate
                TextView dashboardTabDate = new TextView(v.getContext());
                dashboardTabDate.setLayoutParams(new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT));
                dashboardTabDate.setId(newDashboardTabDateId);
                dashboardTabDate.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.lavender_border));
                //dashboardTabDate.setBackgroundResource(R.drawable.lavender_border);
                dashboardTabDate.setPadding(8, 4, 8, 4);
                //dashboardTabDate2.setPadding(dpToPx(8), dpToPx(4), dpToPx(8), dpToPx(4));
                dashboardTabDate.setText("11.11.1999");
                dashboardTabDate.setTypeface(null, Typeface.BOLD);
                dashboardTabDate.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                dashboardTabDate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                ConstraintLayout.LayoutParams dateParams = (ConstraintLayout.LayoutParams) dashboardTabDate.getLayoutParams();
                //layoutParams.setMarginEnd(dpToPx(0));
                dateParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                dateParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                dashboardTabDate.setLayoutParams(dateParams);

                // Create the ImageView
                ImageView imageView = new ImageView(v.getContext());
                imageView.setLayoutParams(new ConstraintLayout.LayoutParams(
                        dpToPx(30), dpToPx(30)));
                imageView.setId(View.generateViewId());
                imageView.setImageResource(R.drawable.baseline_open_in_new_24);
                imageView.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.black));

                ConstraintLayout.LayoutParams imageParams = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
                imageParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                imageParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                imageView.setLayoutParams(imageParams);

                // Create the TextView for dashboardTabTitle
                TextView dashboardTabTitle = new TextView(v.getContext());
                dashboardTabTitle.setLayoutParams(new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT));
                dashboardTabTitle.setId(newDashboardTabTitleId);
                dashboardTabTitle.setText("TITLU NOU");
                dashboardTabTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                dashboardTabTitle.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));

                ConstraintLayout.LayoutParams titleParams = (ConstraintLayout.LayoutParams) dashboardTabTitle.getLayoutParams();
                titleParams.topToBottom = newDashboardTabDateId;

                dashboardTabTitle.setLayoutParams(titleParams);

                // Create the TextView for dashboardTabBody
                TextView dashboardTabBody = new TextView(v.getContext());
                dashboardTabBody.setLayoutParams(new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT));
                dashboardTabBody.setId(newDashboardTabBodyId);
                //dashboardTabBody2.setPadding(0, getResources().getDimensionPixelSize(R.dimen.margin_top), 0, 0);
                dashboardTabBody.setPadding(0, dpToPx(5), 0, 0);
                dashboardTabBody.setEllipsize(TextUtils.TruncateAt.END);
                dashboardTabBody.setMaxLines(3);
                dashboardTabBody.setText("LOREM lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem");
                //dashboardTabBody2.setTextColor(getResources().getColor(R.color.black));
                dashboardTabBody.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                dashboardTabBody.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                ConstraintLayout.LayoutParams bodyParams = (ConstraintLayout.LayoutParams) dashboardTabBody.getLayoutParams();
                bodyParams.topToBottom = newDashboardTabTitleId;

                dashboardTabBody.setLayoutParams(bodyParams);


//                Guideline guideline = new Guideline(v.getContext());
//                guideline.setId(View.generateViewId());
//                constraintLayout.addView(guideline, new ConstraintLayout.LayoutParams(
//                        ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT));
//


               verticalChain.add(newDashboardTabId);


                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(newDashboardTabId, ConstraintSet.TOP, previousDashboardTabId, ConstraintSet.BOTTOM, 0);

                //constraintSet.createVerticalChain(activitiesRelativeLayout.getId(), ConstraintSet.TOP, activitiesRelativeLayout.getId(), ConstraintSet.BOTTOM, toIntArray(verticalChain), null, ConstraintSet.CHAIN_PACKED);

                constraintSet.connect(newDashboardTabDateId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
                constraintSet.connect(newDashboardTabDateId, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);

                constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                constraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);

                constraintSet.connect(newDashboardTabTitleId, ConstraintSet.TOP, newDashboardTabDateId, ConstraintSet.BOTTOM);

                constraintSet.connect(newDashboardTabBodyId, ConstraintSet.TOP, newDashboardTabTitleId, ConstraintSet.BOTTOM);


//                constraintSet.connect(guideline.getId(), ConstraintSet.TOP, previousDashboardTabId, ConstraintSet.BOTTOM);
//                constraintSet.connect(guideline.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
//                constraintSet.connect(guideline.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
//                constraintSet.connect(newDashboardTabId, ConstraintSet.TOP, guideline.getId(), ConstraintSet.BOTTOM);

                constraintSet.applyTo(constraintLayout);


                constraintLayout.addView(dashboardTabDate);
                constraintLayout.addView(imageView);
                constraintLayout.addView(dashboardTabTitle);
                constraintLayout.addView(dashboardTabBody);

                activitiesRelativeLayout.addView(constraintLayout);

                previousDashboardTabBodyId = newDashboardTabBodyId;
                previousDashboardTabDateId = newDashboardTabDateId;
                previousDashboardTabId = newDashboardTabId;
                previousDashboardTabTitleId = newDashboardTabTitleId;

            }
        });

        return rootView;
    }


    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    int[] toIntArray(ArrayList<Integer> list)  {
        int[] ret = new int[list.size()];
        int i = 0;
        for (Integer e : list)
            ret[i++] = e;
        return ret;
    }
}