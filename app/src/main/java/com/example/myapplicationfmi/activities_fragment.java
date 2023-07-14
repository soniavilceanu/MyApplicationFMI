package com.example.myapplicationfmi;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class activities_fragment extends Fragment {

    int previousDashboardTabDateId;
    int previousDashboardTabTitleId;
    int previousDashboardTabBodyId;
    int previousDashboardTabId;

    ImageView dashboardTabImage;
    Button dashboardTabDelete;
    private RelativeLayout activitiesRelativeLayout;
    private LinearLayout fillDashboardTabInfo;
    FloatingActionButton buttonCreateDashboardTab;
    private ArrayList<Integer> dashboardTabIds;
    private Button addDashboardTabInfo;
    private EditText addDashboardTitle;
    private EditText addDashboardBody;
    private EditText addDashboardLink;
    private SQLiteHelperDashboard sqLiteHelperDashboard;
    SQLiteDatabase sqLiteDatabaseObj;
    private ArrayList<String> imageLinkList;
    private  ArrayList<String> tabImageIdList;
    private ScrollView scrollViewDashboards;

    // SQLite database build method.
    public void SQLiteDataBaseBuild(){
        Context context = requireContext();
        sqLiteDatabaseObj = context.openOrCreateDatabase(SQLiteHelperDashboard.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    // SQLite table build method.
    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelperDashboard.TABLE_NAME + "("
                + SQLiteHelperDashboard.Table_Column_ID + " INTEGER PRIMARY KEY, "
                + SQLiteHelperDashboard.Table_Column_1_Titlu + " VARCHAR, "
                + SQLiteHelperDashboard.Table_Column_2_Data + " VARCHAR, "
                + SQLiteHelperDashboard.Table_Column_3_Image_Link + " VARCHAR, "
                + SQLiteHelperDashboard.Table_Column_4_Body + " VARCHAR, "
                + SQLiteHelperDashboard.Table_Column_5_Dashboard_Tab_Id + " VARCHAR, "
                + SQLiteHelperDashboard.Table_Column_6_Dashboard_Tab_Date_Id + " VARCHAR, "
                + SQLiteHelperDashboard.Table_Column_7_Dashboard_Tab_Image_Id + " VARCHAR, "
                + SQLiteHelperDashboard.Table_Column_8_Dashboard_Tab_Delete_Id + " VARCHAR, "
                + SQLiteHelperDashboard.Table_Column_9_Dashboard_Tab_Title_Id + " VARCHAR, "
                + SQLiteHelperDashboard.Table_Column_10_Dashboard_Tab_Body_Id + " VARCHAR);");
    }

    public ArrayList<Integer> getDashboardTabIds() {
        ArrayList<Integer> dashboardTabIds = new ArrayList<>();

        //vezi daca mai trebuie instantiat
        sqLiteDatabaseObj = sqLiteHelperDashboard.getWritableDatabase();
        Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperDashboard.TABLE_NAME, new String[]{SQLiteHelperDashboard.Table_Column_5_Dashboard_Tab_Id}, null, null, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndex(SQLiteHelperDashboard.Table_Column_5_Dashboard_Tab_Id);
            if (columnIndex != -1) {
                while (cursor.moveToNext()) {
                    String dashboardTabId = cursor.getString(columnIndex);
                    dashboardTabIds.add(Integer.valueOf(dashboardTabId));
                }
            }
            cursor.close();
        }
        return dashboardTabIds;
    }
    public ArrayList<String> getDashboardTabTitles() {
        ArrayList<String> dashboardTabTitles = new ArrayList<>();

        //vezi daca mai trebuie instantiat
        sqLiteDatabaseObj = sqLiteHelperDashboard.getWritableDatabase();
        Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperDashboard.TABLE_NAME, new String[]{SQLiteHelperDashboard.Table_Column_1_Titlu}, null, null, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndex(SQLiteHelperDashboard.Table_Column_1_Titlu);
            if (columnIndex != -1) {
                while (cursor.moveToNext()) {
                    String dashboardTabTitle = cursor.getString(columnIndex);
                    dashboardTabTitles.add(dashboardTabTitle);
                }
            }
            cursor.close();
        }
        return dashboardTabTitles;
    }
    public ArrayList<String> getDashboardTabBodies() {
        ArrayList<String> dashboardTabBodies = new ArrayList<>();

        //vezi daca mai trebuie instantiat
        sqLiteDatabaseObj = sqLiteHelperDashboard.getWritableDatabase();
        Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperDashboard.TABLE_NAME, new String[]{SQLiteHelperDashboard.Table_Column_4_Body}, null, null, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndex(SQLiteHelperDashboard.Table_Column_4_Body);
            if (columnIndex != -1) {
                while (cursor.moveToNext()) {
                    String dashboardTabBody = cursor.getString(columnIndex);
                    dashboardTabBodies.add(dashboardTabBody);
                }
            }
            cursor.close();
        }
        return dashboardTabBodies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        sqLiteHelperDashboard = new SQLiteHelperDashboard(requireContext());

        // Creating SQLite database if doesn't exist
        SQLiteDataBaseBuild();
        // Creating SQLite table if doesn't exist.
        SQLiteTableBuild();

//        for(int i = 1; i <= 5; i ++){
//            sqLiteDatabaseObj = sqLiteHelperDashboard.getWritableDatabase();
//            int deletedRows = sqLiteDatabaseObj.delete(SQLiteHelperDashboard.TABLE_NAME, "data = ?", new String[]{"11.07.2023"});
//
//        }

        dashboardTabIds = new ArrayList<Integer>();

        View rootView = inflater.inflate(R.layout.fragment_activities_fragment, container, false);
        activitiesRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.activitiesRelativeLayout);
        dashboardTabImage = (ImageView) rootView.findViewById(R.id.dashboardTabImage);
        dashboardTabDelete = (Button) rootView.findViewById(R.id.dashboardTabDelete);

        fillDashboardTabInfo = (LinearLayout) rootView.findViewById(R.id.fillDashboardTabInfo);
        addDashboardTabInfo = (Button) rootView.findViewById(R.id.addDashboardTabInfo);
        addDashboardTitle = (EditText) rootView.findViewById(R.id.addDashboardTitle);
        addDashboardBody = (EditText) rootView.findViewById(R.id.addDashboardBody);
        addDashboardLink = (EditText) rootView.findViewById(R.id.addDashboardLink);

        scrollViewDashboards = (ScrollView) rootView.findViewById(R.id.scrollViewDashboards);

        //daca e admin
        if(MainActivity.USER_TYPE == 1)
            dashboardTabDelete.setVisibility(View.VISIBLE);
        else dashboardTabDelete.setVisibility(View.GONE);


        buttonCreateDashboardTab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        //daca e admin
        if(MainActivity.USER_TYPE == 1)
            buttonCreateDashboardTab.setVisibility(View.VISIBLE);
        else buttonCreateDashboardTab.setVisibility(View.GONE);


        /**
         * tragem datele dashboard-urilor din baza de date
         */

        ArrayList<String> titluList = new ArrayList<>();
        ArrayList<String> dataList = new ArrayList<>();
        imageLinkList = new ArrayList<>();
        ArrayList<String> bodyList = new ArrayList<>();
        ArrayList<String> tabIdList = new ArrayList<>();
        ArrayList<String> tabDateIdList = new ArrayList<>();
        tabImageIdList = new ArrayList<>();
        ArrayList<String> tabDeleteIdList = new ArrayList<>();
        ArrayList<String> tabTitleIdList = new ArrayList<>();
        ArrayList<String> tabBodyIdList = new ArrayList<>();

        ArrayList<String> allIds = new ArrayList<>();

        //vezi daca mai trebuie instantiat cu sqLiteHelperDashboard.getWritableDatabase();
        sqLiteDatabaseObj = sqLiteHelperDashboard.getWritableDatabase();
                Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperDashboard.TABLE_NAME, new String[]{SQLiteHelperDashboard.Table_Column_1_Titlu, SQLiteHelperDashboard.Table_Column_2_Data, SQLiteHelperDashboard.Table_Column_3_Image_Link, SQLiteHelperDashboard.Table_Column_4_Body,SQLiteHelperDashboard.Table_Column_5_Dashboard_Tab_Id, SQLiteHelperDashboard.Table_Column_6_Dashboard_Tab_Date_Id, SQLiteHelperDashboard.Table_Column_7_Dashboard_Tab_Image_Id, SQLiteHelperDashboard.Table_Column_8_Dashboard_Tab_Delete_Id, SQLiteHelperDashboard.Table_Column_9_Dashboard_Tab_Title_Id, SQLiteHelperDashboard.Table_Column_10_Dashboard_Tab_Body_Id}, null, null, null, null, null);

        if (cursor != null) {
            int titluIndex = cursor.getColumnIndex(SQLiteHelperDashboard.Table_Column_1_Titlu);
            int dataIndex = cursor.getColumnIndex(SQLiteHelperDashboard.Table_Column_2_Data);
            int imageLinkIndex = cursor.getColumnIndex(SQLiteHelperDashboard.Table_Column_3_Image_Link);
            int bodyIndex = cursor.getColumnIndex(SQLiteHelperDashboard.Table_Column_4_Body);
            int tabIdIndex = cursor.getColumnIndex(SQLiteHelperDashboard.Table_Column_5_Dashboard_Tab_Id);
            int tabDateIdIndex = cursor.getColumnIndex(SQLiteHelperDashboard.Table_Column_6_Dashboard_Tab_Date_Id);
            int tabImageIdIndex = cursor.getColumnIndex(SQLiteHelperDashboard.Table_Column_7_Dashboard_Tab_Image_Id);
            int tabDeleteIdIndex = cursor.getColumnIndex(SQLiteHelperDashboard.Table_Column_8_Dashboard_Tab_Delete_Id);
            int tabTitleIdIndex = cursor.getColumnIndex(SQLiteHelperDashboard.Table_Column_9_Dashboard_Tab_Title_Id);
            int tabBodyIdIndex = cursor.getColumnIndex(SQLiteHelperDashboard.Table_Column_10_Dashboard_Tab_Body_Id);

            while (cursor.moveToNext()) {
                if (titluIndex >= 0) {
                    String titlu = cursor.getString(titluIndex);
                    titluList.add(titlu);
                }

                if (dataIndex >= 0) {
                    String data = cursor.getString(dataIndex);
                    dataList.add(data);
                }

                if (imageLinkIndex >= 0) {
                    String imageLink = cursor.getString(imageLinkIndex);
                    imageLinkList.add(imageLink);
                }

                if (bodyIndex >= 0) {
                    String body = cursor.getString(bodyIndex);
                    bodyList.add(body);
                }

                if (tabIdIndex >= 0) {
                    String tabId = cursor.getString(tabIdIndex);
                    tabIdList.add(tabId);
                }

                if (tabDateIdIndex >= 0) {
                    String tabDateId = cursor.getString(tabDateIdIndex);
                    tabDateIdList.add(tabDateId);
                }

                if (tabImageIdIndex >= 0) {
                    String tabImageId = cursor.getString(tabImageIdIndex);
                    tabImageIdList.add(tabImageId);
                }

                if (tabDeleteIdIndex >= 0) {
                    String tabDeleteId = cursor.getString(tabDeleteIdIndex);
                    tabDeleteIdList.add(tabDeleteId);
                }

                if (tabTitleIdIndex >= 0) {
                    String tabTitleId = cursor.getString(tabTitleIdIndex);
                    tabTitleIdList.add(tabTitleId);
                }

                if (tabBodyIdIndex >= 0) {
                    String tabBodyId = cursor.getString(tabBodyIdIndex);
                    tabBodyIdList.add(tabBodyId);
                }
            }
            cursor.close();
        }

        allIds.addAll(tabIdList);
        allIds.addAll(tabBodyIdList);
        allIds.addAll(tabDeleteIdList);
        allIds.addAll(tabDateIdList);
        allIds.addAll(tabTitleIdList);
        allIds.addAll(tabImageIdList);


        /**
         * cream dashboard-urile aduse din baza de date
         */

        for(int k = 0; k < titluList.size(); k ++){
            if(previousDashboardTabId == 0){
                previousDashboardTabId = R.id.dashboardTab;
                previousDashboardTabTitleId = R.id.dashboardTabTitle;
                previousDashboardTabDateId = R.id.dashboardTabDate;
                previousDashboardTabBodyId = R.id.dashboardTabBody;
                dashboardTabIds.add(previousDashboardTabId);

                /**
                 *  dashboardTabIds.add(previousDashboardTabId); cred ca trebuie mutat inainte sa le traga din baza
                 */
            }

            // Generate dynamic view IDs
            int newDashboardTabDateId = Integer.valueOf(tabDateIdList.get(k));
            int newDashboardTabTitleId = Integer.valueOf(tabTitleIdList.get(k));
            int newDashboardTabBodyId = Integer.valueOf(tabBodyIdList.get(k));
            int newDashboardTabId = Integer.valueOf(tabIdList.get(k));
            int newDashboardImageId = Integer.valueOf(tabImageIdList.get(k));
            int newDashboardDeleteId = Integer.valueOf(tabDeleteIdList.get(k));

            dashboardTabIds.add(newDashboardTabId);

            RelativeLayout dashboardTab = new RelativeLayout(requireContext());
            dashboardTab.setId(newDashboardTabId);
            RelativeLayout.LayoutParams dashboardTabParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dashboardTabParams.setMargins(dpToPx(requireContext(),30), dpToPx(requireContext(),30), dpToPx(requireContext(),30), 0);
            dashboardTab.setPadding(10, 10, 10, 10);
            dashboardTab.setLayoutParams(dashboardTabParams);
            dashboardTab.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.dashboard_article_background));

            TextView dashboardTabDate = new TextView(requireContext());
            dashboardTabDate.setId(newDashboardTabDateId);
            RelativeLayout.LayoutParams dashboardTabDateParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dashboardTabDateParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            dashboardTabDateParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            dashboardTabDate.setLayoutParams(dashboardTabDateParams);
            dashboardTabDate.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.lavender_border));
            dashboardTabDate.setPadding(dpToPx(requireContext(),8), dpToPx(requireContext(),4), dpToPx(requireContext(),8), dpToPx(requireContext(),4));
            dashboardTabDate.setText(dataList.get(k));
            dashboardTabDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            dashboardTabDate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            dashboardTab.addView(dashboardTabDate);

            ImageView dashboardTabImage = new ImageView(requireContext());
            dashboardTabImage.setLayoutParams(new RelativeLayout.LayoutParams(dpToPx(requireContext(),30), dpToPx(requireContext(),30)));
            dashboardTabImage.setImageResource(R.drawable.baseline_open_in_new_24);
            dashboardTabImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            dashboardTabImage.setId(newDashboardImageId);
            RelativeLayout.LayoutParams dashboardTabImageParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dashboardTabImageParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            dashboardTabImageParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            dashboardTabImage.setLayoutParams(dashboardTabImageParams);
            dashboardTab.addView(dashboardTabImage);

            Button dashboardTabDelete = new Button(requireContext());
            dashboardTabDelete.setLayoutParams(new RelativeLayout.LayoutParams(dpToPx(requireContext(),30), dpToPx(requireContext(),30)));
            dashboardTabDelete.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_close_24));
            dashboardTabDelete.setId(newDashboardDeleteId);

            RelativeLayout.LayoutParams dashboardTabDeleteParams = new RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams(dpToPx(requireContext(),30), dpToPx(requireContext(),30)));
            dashboardTabDeleteParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            dashboardTabDeleteParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            dashboardTabDeleteParams.setMargins(dpToPx(requireContext(),40), 0,0,0);
            dashboardTabDelete.setLayoutParams(dashboardTabDeleteParams);
            dashboardTab.addView(dashboardTabDelete);

            dashboardTabDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activitiesRelativeLayout.removeView(dashboardTab);
                    if(dashboardTabIds.indexOf((Object)dashboardTab.getId()) == dashboardTabIds.size() - 1 && dashboardTabIds.size() > 1){
                        //is the last element in the dashboardTab list and the list is not made of only 1 element
                        int previousId = dashboardTabIds.get(dashboardTabIds.indexOf((Object)dashboardTab.getId()) - 1);
                        previousDashboardTabId = previousId;
                    }
                    else if(dashboardTabIds.size() > 1){
                        int nextId = dashboardTabIds.get(dashboardTabIds.indexOf((Object)dashboardTab.getId()) + 1);

                        if(dashboardTabIds.indexOf((Object)dashboardTab.getId()) != 0){
                            //is a middle element in the dashboardTab list
                            int previousId = dashboardTabIds.get(dashboardTabIds.indexOf((Object)dashboardTab.getId()) - 1);
                            RelativeLayout nextDashboard = rootView.findViewById(nextId);

                            RelativeLayout.LayoutParams dashboardTabParamsToDelete = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dashboardTabParamsToDelete.addRule(RelativeLayout.BELOW, previousId);
                            dashboardTabParamsToDelete.setMargins(dpToPx(requireContext(),30), dpToPx(requireContext(),30), dpToPx(requireContext(),30), 0);
                            nextDashboard.setPadding(10, 10, 10, 10);
                            nextDashboard.setLayoutParams(dashboardTabParamsToDelete);

                        }
                    }
                    dashboardTabIds.remove((Object)dashboardTab.getId());
                    tabIdList.remove(String.valueOf((Object)dashboardTab.getId()));
                    allIds.remove(String.valueOf((Object)dashboardTab.getId()));

                    /**
                     *  poate ar trebui sterse si image, title, body ids din tabTitleIdList, tabImageIdList, etc.
                     */

                    //vezi daca mai trebuie instantiat sqLiteDatabaseObj
                    sqLiteDatabaseObj = sqLiteHelperDashboard.getWritableDatabase();
                    int deletedRows = sqLiteDatabaseObj.delete(SQLiteHelperDashboard.TABLE_NAME, "dashboard_tab_id = ?", new String[]{String.valueOf(dashboardTab.getId())});
                }
            });

            TextView dashboardTabTitle = new TextView(requireContext());
            dashboardTabTitle.setId(newDashboardTabTitleId);
            RelativeLayout.LayoutParams dashboardTabTitleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dashboardTabTitleParams.addRule(RelativeLayout.BELOW, newDashboardTabDateId);
            dashboardTabTitle.setLayoutParams(dashboardTabTitleParams);
            dashboardTabTitle.setText(titluList.get(k));
            dashboardTabTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            dashboardTabTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            dashboardTab.addView(dashboardTabTitle);


            TextView dashboardTabBody = new TextView(requireContext());
            dashboardTabBody.setId(newDashboardTabBodyId);
            RelativeLayout.LayoutParams dashboardTabBody2Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dashboardTabBody2Params.addRule(RelativeLayout.BELOW, newDashboardTabTitleId);
            dashboardTabBody.setLayoutParams(dashboardTabBody2Params);
            dashboardTabBody.setPadding(0, dpToPx(requireContext(),5), 0, 0);
            dashboardTabBody.setEllipsize(TextUtils.TruncateAt.END);
            dashboardTabBody.setMaxLines(3);
            dashboardTabBody.setText(bodyList.get(k));
            dashboardTabBody.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            dashboardTabBody.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            dashboardTab.addView(dashboardTabBody);

            RelativeLayout.LayoutParams dashboardTabParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dashboardTabParams2.addRule(RelativeLayout.BELOW, previousDashboardTabId);
            dashboardTabParams2.setMargins(dpToPx(requireContext(),30), dpToPx(requireContext(),30), dpToPx(requireContext(),30), 0);
            dashboardTab.setPadding(10, 10, 10, 10);
            dashboardTab.setLayoutParams(dashboardTabParams2);


            activitiesRelativeLayout.addView(dashboardTab);

            previousDashboardTabBodyId = newDashboardTabBodyId;
            previousDashboardTabDateId = newDashboardTabDateId;
            previousDashboardTabId = newDashboardTabId;
            previousDashboardTabTitleId = newDashboardTabTitleId;

        }

        for (int j = 0; j < activitiesRelativeLayout.getChildCount(); j++) {

            RelativeLayout childDashboard = (RelativeLayout) activitiesRelativeLayout.getChildAt(j);
            for (int i = 0; i < childDashboard.getChildCount(); i++) {
                View childView = childDashboard.getChildAt(i);

                if (childView instanceof ImageView) {
                    final int imageViewId = childView.getId();
                    childView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            handleButtonClick(imageViewId);
                        }
                    });
                }
                if (childView instanceof Button) {
                    final int buttonId = childView.getId();

                    //daca e admin facem butonul de delete visibil
                    if(MainActivity.USER_TYPE == 1)
                        ((Button)rootView.findViewById(buttonId)).setVisibility(View.VISIBLE);
                    else ((Button)rootView.findViewById(buttonId)).setVisibility(View.GONE);

                    RelativeLayout dashboardParent = (RelativeLayout) ((Button)rootView.findViewById(buttonId)).getParent();
                    childView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            activitiesRelativeLayout.removeView(dashboardParent);
                            if(dashboardTabIds.indexOf((Object)dashboardParent.getId()) == dashboardTabIds.size() - 1 && dashboardTabIds.size() > 1){
                                //is the last element in the dashboardTab list and the list is not made of only 1 element
                                int previousId = dashboardTabIds.get(dashboardTabIds.indexOf((Object)dashboardParent.getId()) - 1);
                                previousDashboardTabId = previousId;
                            }
                            else if(dashboardTabIds.size() > 1){
                                int nextId = dashboardTabIds.get(dashboardTabIds.indexOf((Object)dashboardParent.getId()) + 1);

                                if(dashboardTabIds.indexOf((Object)dashboardParent.getId()) != 0){
                                    int previousId = dashboardTabIds.get(dashboardTabIds.indexOf((Object)dashboardParent.getId()) - 1);
                                    RelativeLayout nextDashboard = rootView.findViewById(nextId);

                                    RelativeLayout.LayoutParams dashboardTabParamsToDelete = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    dashboardTabParamsToDelete.addRule(RelativeLayout.BELOW, previousId);
                                    dashboardTabParamsToDelete.setMargins(dpToPx(requireContext(),30), dpToPx(requireContext(),30), dpToPx(requireContext(),30), 0);
                                    nextDashboard.setPadding(10, 10, 10, 10);
                                    nextDashboard.setLayoutParams(dashboardTabParamsToDelete);

                                }
                            }
                            dashboardTabIds.remove((Object)dashboardParent.getId());
                            tabIdList.remove(String.valueOf((Object)dashboardParent.getId()));
                            allIds.remove(String.valueOf((Object)dashboardParent.getId()));

                            /**
                             *  poate ar trebui sterse si image, title, body ids din tabTitleIdList, tabImageIdList, etc.
                             */


                            //vezi daca mai trebuie instantiat sqLiteDatabaseObj
                            sqLiteDatabaseObj = sqLiteHelperDashboard.getWritableDatabase();
                            int deletedRows = sqLiteDatabaseObj.delete(SQLiteHelperDashboard.TABLE_NAME, "dashboard_tab_id = ?", new String[]{String.valueOf(dashboardParent.getId())});

//                            activitiesRelativeLayout.removeView(dashboardParent);
//                            if(dashboardTabIds.size() == 1)
//                                previousDashboardTabId = R.id.failSafe;
//                            else{
//                                int nextId = dashboardTabIds.get(1);
//                                previousDashboardTabId = nextId;
//                            }
//                            dashboardTabIds.remove((Object)dashboardParent.getId());
//                            //vezi daca mai trebuie instantiat sqLiteDatabaseObj
//                            sqLiteDatabaseObj = sqLiteHelperDashboard.getWritableDatabase();
//                            int deletedRows = sqLiteDatabaseObj.delete(SQLiteHelperDashboard.TABLE_NAME, "dashboard_tab_id = ?", new String[]{String.valueOf(dashboardParent.getId())});
//

                        }
                    });
                }
            }
        }

        buttonCreateDashboardTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(fillDashboardTabInfo);
                fillDashboardTabInfo.setVisibility(View.VISIBLE);
            }
        });
        addDashboardTabInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(previousDashboardTabId == 0){
                    previousDashboardTabId = R.id.dashboardTab;
                    previousDashboardTabTitleId = R.id.dashboardTabTitle;
                    previousDashboardTabDateId = R.id.dashboardTabDate;
                    previousDashboardTabBodyId = R.id.dashboardTabBody;
                    dashboardTabIds.add(previousDashboardTabId);
                }

                // Generate dynamic view IDs
                int newDashboardTabDateId = View.generateViewId();
                while(allIds.contains(String.valueOf(newDashboardTabDateId)))
                    newDashboardTabDateId = View.generateViewId();

                int newDashboardTabTitleId = View.generateViewId();
                while(allIds.contains(String.valueOf(newDashboardTabTitleId)))
                    newDashboardTabTitleId = View.generateViewId();

                int newDashboardTabBodyId = View.generateViewId();
                while(allIds.contains(String.valueOf(newDashboardTabBodyId)))
                    newDashboardTabBodyId = View.generateViewId();

                int newDashboardTabId = View.generateViewId();
                while(allIds.contains(String.valueOf(newDashboardTabId)))
                    newDashboardTabId = View.generateViewId();

                int newDashboardImageId = View.generateViewId();
                while(allIds.contains(String.valueOf(newDashboardImageId)))
                    newDashboardImageId = View.generateViewId();

                int newDashboardDeleteId = View.generateViewId();
                while(allIds.contains(String.valueOf(newDashboardDeleteId)))
                    newDashboardDeleteId = View.generateViewId();

                dashboardTabIds.add(newDashboardTabId);

                RelativeLayout dashboardTab = new RelativeLayout(v.getContext());
                dashboardTab.setId(newDashboardTabId);
                RelativeLayout.LayoutParams dashboardTabParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabParams.setMargins(dpToPx(v.getContext(),30), dpToPx(v.getContext(),30), dpToPx(v.getContext(),30), 0);
                dashboardTab.setPadding(10, 10, 10, 10);
                dashboardTab.setLayoutParams(dashboardTabParams);
                dashboardTab.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.dashboard_article_background));

                tabIdList.add(String.valueOf(newDashboardTabId));
                allIds.add(String.valueOf(newDashboardTabId));

                TextView dashboardTabDate = new TextView(v.getContext());
                dashboardTabDate.setId(newDashboardTabDateId);
                RelativeLayout.LayoutParams dashboardTabDateParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabDateParams.addRule(RelativeLayout.ALIGN_PARENT_END);
                dashboardTabDateParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                dashboardTabDate.setLayoutParams(dashboardTabDateParams);
                dashboardTabDate.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.lavender_border));
                dashboardTabDate.setPadding(dpToPx(v.getContext(),8), dpToPx(v.getContext(),4), dpToPx(v.getContext(),8), dpToPx(v.getContext(),4));
                dashboardTabDate.setText(sdf.format(new Date()));
                dashboardTabDate.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                dashboardTabDate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                dashboardTab.addView(dashboardTabDate);

                tabDateIdList.add(String.valueOf(newDashboardTabDateId));
                allIds.add(String.valueOf(newDashboardTabDateId));

                ImageView dashboardTabImage = new ImageView(v.getContext());
                dashboardTabImage.setLayoutParams(new RelativeLayout.LayoutParams(dpToPx(v.getContext(),30), dpToPx(v.getContext(),30)));
                dashboardTabImage.setImageResource(R.drawable.baseline_open_in_new_24);
                dashboardTabImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                dashboardTabImage.setId(newDashboardImageId);
                RelativeLayout.LayoutParams dashboardTabImageParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabImageParams.addRule(RelativeLayout.ALIGN_PARENT_START);
                dashboardTabImageParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                dashboardTabImage.setLayoutParams(dashboardTabImageParams);
                dashboardTab.addView(dashboardTabImage);

                tabImageIdList.add(String.valueOf(newDashboardImageId));
                allIds.add(String.valueOf(newDashboardImageId));

                dashboardTabImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleButtonClick(dashboardTabImage.getId());
                    }
                });
                imageLinkList.add(addDashboardLink.getText().toString());

                Button dashboardTabDelete = new Button(v.getContext());
                dashboardTabDelete.setLayoutParams(new RelativeLayout.LayoutParams(dpToPx(v.getContext(),30), dpToPx(v.getContext(),30)));
                dashboardTabDelete.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.baseline_close_24));
                dashboardTabDelete.setId(newDashboardDeleteId);

                RelativeLayout.LayoutParams dashboardTabDeleteParams = new RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams(dpToPx(v.getContext(),30), dpToPx(v.getContext(),30)));
                dashboardTabDeleteParams.addRule(RelativeLayout.ALIGN_PARENT_START);
                dashboardTabDeleteParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                dashboardTabDeleteParams.setMargins(dpToPx(v.getContext(),40), 0,0,0);
                dashboardTabDelete.setLayoutParams(dashboardTabDeleteParams);
                dashboardTab.addView(dashboardTabDelete);

                tabDeleteIdList.add(String.valueOf(newDashboardDeleteId));
                allIds.add(String.valueOf(newDashboardDeleteId));

                dashboardTabDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activitiesRelativeLayout.removeView(dashboardTab);
                        if(dashboardTabIds.indexOf((Object)dashboardTab.getId()) == dashboardTabIds.size() - 1 && dashboardTabIds.size() > 1){
                            //is the last element in the dashboardTab list and the list is not made of only 1 element
                            int previousId = dashboardTabIds.get(dashboardTabIds.indexOf((Object)dashboardTab.getId()) - 1);
                            previousDashboardTabId = previousId;
                        }
                        else if(dashboardTabIds.size() > 1){
                            int nextId = dashboardTabIds.get(dashboardTabIds.indexOf((Object)dashboardTab.getId()) + 1);

                            if(dashboardTabIds.indexOf((Object)dashboardTab.getId()) != 0){
                                int previousId = dashboardTabIds.get(dashboardTabIds.indexOf((Object)dashboardTab.getId()) - 1);
                                RelativeLayout nextDashboard = rootView.findViewById(nextId);

                                RelativeLayout.LayoutParams dashboardTabParamsToDelete = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dashboardTabParamsToDelete.addRule(RelativeLayout.BELOW, previousId);
                                dashboardTabParamsToDelete.setMargins(dpToPx(v.getContext(),30), dpToPx(v.getContext(),30), dpToPx(v.getContext(),30), 0);
                                nextDashboard.setPadding(10, 10, 10, 10);
                                nextDashboard.setLayoutParams(dashboardTabParamsToDelete);

                            }
                        }
                        dashboardTabIds.remove((Object)dashboardTab.getId());
                        allIds.remove(String.valueOf((Object)dashboardTab.getId()));
                        tabIdList.remove(String.valueOf((Object)dashboardTab.getId()));

                        /**
                         *  poate ar trebui sterse si image, title, body, etc. ids din tabTitleIdList, tabImageIdList, etc.
                         */

                        //vezi daca mai trebuie instantiat sqLiteDatabaseObj
                        sqLiteDatabaseObj = sqLiteHelperDashboard.getWritableDatabase();
                        int deletedRows = sqLiteDatabaseObj.delete(SQLiteHelperDashboard.TABLE_NAME, "dashboard_tab_id = ?", new String[]{String.valueOf(dashboardTab.getId())});
                    }
                });

                TextView dashboardTabTitle = new TextView(v.getContext());
                dashboardTabTitle.setId(newDashboardTabTitleId);
                RelativeLayout.LayoutParams dashboardTabTitleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabTitleParams.addRule(RelativeLayout.BELOW, newDashboardTabDateId);
                dashboardTabTitle.setLayoutParams(dashboardTabTitleParams);
                dashboardTabTitle.setText(addDashboardTitle.getText());
                dashboardTabTitle.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                dashboardTabTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                dashboardTab.addView(dashboardTabTitle);

                tabTitleIdList.add(String.valueOf(newDashboardTabTitleId));
                allIds.add(String.valueOf(newDashboardTabTitleId));


                TextView dashboardTabBody = new TextView(v.getContext());
                dashboardTabBody.setId(newDashboardTabBodyId);
                RelativeLayout.LayoutParams dashboardTabBody2Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabBody2Params.addRule(RelativeLayout.BELOW, newDashboardTabTitleId);
                dashboardTabBody.setLayoutParams(dashboardTabBody2Params);
                dashboardTabBody.setPadding(0, dpToPx(v.getContext(),5), 0, 0);
                dashboardTabBody.setEllipsize(TextUtils.TruncateAt.END);
                dashboardTabBody.setMaxLines(3);
                dashboardTabBody.setText(addDashboardBody.getText());
                dashboardTabBody.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                dashboardTabBody.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                dashboardTab.addView(dashboardTabBody);

                tabBodyIdList.add(String.valueOf(newDashboardTabBodyId));
                allIds.add(String.valueOf(newDashboardTabBodyId));

                RelativeLayout.LayoutParams dashboardTabParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dashboardTabParams2.addRule(RelativeLayout.BELOW, previousDashboardTabId);
                dashboardTabParams2.setMargins(dpToPx(v.getContext(),30), dpToPx(v.getContext(),30), dpToPx(v.getContext(),30), 0);
                dashboardTab.setPadding(10, 10, 10, 10);
                dashboardTab.setLayoutParams(dashboardTabParams2);


                activitiesRelativeLayout.addView(dashboardTab);


                if(TextUtils.isEmpty(addDashboardTitle.getText().toString()) || TextUtils.isEmpty(addDashboardBody.getText().toString()) || TextUtils.isEmpty(addDashboardLink.getText().toString()))
                {
                    Toast.makeText(requireContext(),"Va rog completati toate datele!", Toast.LENGTH_LONG).show();
                }
                else {
                    String SQLiteDataBaseQueryHolder;

                    sqLiteDatabaseObj = sqLiteHelperDashboard.getWritableDatabase();
                    // SQLite query to insert data into table.
                    SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelperDashboard.TABLE_NAME+" (titlu,data,image_link,body,dashboard_tab_id,dashboard_tab_date_id,dashboard_tab_image_id,dashboard_tab_delete_id,dashboard_tab_title_id,dashboard_tab_body_id)" +
                            " VALUES('"+addDashboardTitle.getText().toString()+"', '"+sdf.format(new Date())+"', '"+addDashboardLink.getText().toString()+"', '"+addDashboardBody.getText().toString()+"', '"+newDashboardTabId+"', '"+newDashboardTabDateId+"', '"+newDashboardImageId+"', '"+newDashboardDeleteId+"'," +
                            "'"+newDashboardTabTitleId+"', '"+newDashboardTabBodyId+"');";

                    sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
                    sqLiteDatabaseObj.close();
                }

                previousDashboardTabBodyId = newDashboardTabBodyId;
                previousDashboardTabDateId = newDashboardTabDateId;
                previousDashboardTabId = newDashboardTabId;
                previousDashboardTabTitleId = newDashboardTabTitleId;

                addDashboardBody.setText("");
                addDashboardLink.setText("");
                addDashboardTitle.setText("");

                TransitionManager.beginDelayedTransition(fillDashboardTabInfo);
                fillDashboardTabInfo.setVisibility(View.GONE);

            }
        });


        ArrayList<String> titles = getDashboardTabTitles();
        ArrayList<String> bodies = getDashboardTabBodies();

        DashboardActivity.searchView.setQueryHint("Cauta...");

        SearchView sc = new SearchView(DashboardActivity.searchView.getContext());
        sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform the search operation based on the submitted query
                //performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sqLiteDatabaseObj = sqLiteHelperDashboard.getWritableDatabase();
                for (int i = 0; i < titles.size(); i++) {
                    if (titles.get(i).toLowerCase().contains(newText.toLowerCase())) {
                        Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperDashboard.TABLE_NAME, new String[]{SQLiteHelperDashboard.Table_Column_5_Dashboard_Tab_Id}, SQLiteHelperDashboard.Table_Column_1_Titlu + " = ?", new String[]{titles.get(i)}, null, null, null);
                        if (cursor.moveToFirst()) {
                            String dashboardIdExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelperDashboard.Table_Column_5_Dashboard_Tab_Id));
                            RelativeLayout dash = rootView.findViewById(Integer.valueOf(dashboardIdExtras));
                            focusOnView(dash);
                            cursor.close();
                        }
                    }
                }
                for (int i = 0; i < bodies.size(); i++) {
                    if (bodies.get(i).toLowerCase().contains(newText.toLowerCase())) {
                        Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperDashboard.TABLE_NAME, new String[]{SQLiteHelperDashboard.Table_Column_5_Dashboard_Tab_Id}, SQLiteHelperDashboard.Table_Column_4_Body + " = ?", new String[]{bodies.get(i)}, null, null, null);
                        if (cursor.moveToFirst()) {
                            String dashboardIdExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelperDashboard.Table_Column_5_Dashboard_Tab_Id));
                            RelativeLayout dash = rootView.findViewById(Integer.valueOf(dashboardIdExtras));
                            focusOnView(dash);
                            cursor.close();
                        }
                    }
                }
                return true;
            }
        });

        return rootView;
    }

    private void handleButtonClick(int buttonId) {

        for(int k = 0; k< tabImageIdList.size(); k ++){
            if(buttonId == Integer.valueOf(tabImageIdList.get(k))){
                goToUrl(imageLinkList.get(k));
                }
        }
    }

    private void goToUrl(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
    boolean isWithinEditTextBounds(int xPoint, int yPoint) {
        int[] l = new int[2];
        fillDashboardTabInfo.getLocationOnScreen(l);
        int x = l[0];
        int y = l[1];
        int w = fillDashboardTabInfo.getWidth();
        int h = fillDashboardTabInfo.getHeight();

        if (xPoint< x || xPoint> x + w || yPoint< y || yPoint> y + h) {
            return false;
        }
        return true;
    }
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (fillDashboardTabInfo.getVisibility() == View.VISIBLE && !isWithinEditTextBounds((int) ev.getRawX(), (int) ev.getRawY())){
            TransitionManager.beginDelayedTransition(fillDashboardTabInfo);
            fillDashboardTabInfo.setVisibility(View.GONE);
            addDashboardBody.setText("");
            addDashboardLink.setText("");
            addDashboardTitle.setText("");
            return true;
        }
        return false;
    }

    public static int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    int[] toIntArray(ArrayList<Integer> list)  {
        int[] ret = new int[list.size()];
        int i = 0;
        for (Integer e : list)
            ret[i++] = e;
        return ret;
    }

    private final void focusOnView(View myTextView){
        scrollViewDashboards.post(new Runnable() {
            @Override
            public void run() {
                scrollViewDashboards.smoothScrollTo(0, myTextView.getBottom());

                /*
                int vTop = view.getTop();
                int vBottom = view.getBottom();
                int sHeight = scroll.getBottom();
                scroll.smoothScrollTo(0, ((vTop + vBottom - sHeight) / 2));
                 */
            }
        });
    }
}