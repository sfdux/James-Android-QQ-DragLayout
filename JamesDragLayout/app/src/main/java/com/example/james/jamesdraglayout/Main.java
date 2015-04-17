package com.example.james.jamesdraglayout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener, SlideView.OnSlideListener {

    private DragLayout dl;
    //    private GridView gv_img;
    private ListViewCompat petListView;
    private ListView lv;
    private ImageView iv_icon;
    private ImageView iv_bottom;
    private TextView username;
    private TextView actionTitle;
    private MyRelativeLayout myRelativeLayout;

    private List<PetItem> petItems = new ArrayList<PetItem>();
    private SlideView mLastSlideViewWithStatusOn;

    private SlideAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDragLayout();
        initView();
    }

    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {
                lv.smoothScrollToPosition(new Random().nextInt(30));
            }

            @Override
            public void onClose() {
                Log.d("test", "Close now...");
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(iv_icon, 1 - percent);
            }
        });
    }

    private void initView() {
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
        username = (TextView) findViewById(R.id.textUsername);
        actionTitle = (TextView) findViewById(R.id.textActionTitle);
        myRelativeLayout = (MyRelativeLayout) findViewById(R.id.relativeLayout);

        username.setText("admin");
        actionTitle.setText("Management");

        petListView = (ListViewCompat) findViewById(R.id.listViewPet);
        lv = (ListView) findViewById(R.id.lv);


//        petListView.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.listcell, new String[]{"Lucky", "GOOD", "uit", "James1",
//                "James2",
//                "James3",
//                "James4",
//                "James2",
//                "James3",
//                "James4",
//                "James2",
//                "James3",
//                "James4",
//                "James2",
//                "James3",
//                "James4", "James2",
//                "James3",
//                "James4", "James2",
//                "James3",
//                "James4", "James2",
//                "James3",
//                "James4",}));

        for (int i = 0; i < 3; i++) {
            PetItem item = new PetItem();
            item.setPetName(String.format("Name %d", i));
            item.setPetBattery(String.format("Battery %d", i));

            petItems.add(item);
        }


        adapter = new SlideAdapter();
        petListView.setAdapter(adapter);
        petListView.setOnItemClickListener(this);

//        myRelativeLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return false;
//            }
//        });

        petListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });


//        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1,
//                                    int position, long arg3) {
//                Log.d("test", String.format("Click %s", position));
//            }
//        });

        lv.setAdapter(new ArrayAdapter<String>(this,
                R.layout.item_text, new String[]{"Management", "Log", "Settings", "Logout"}));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Log.d("test", String.format("Click %s", position));
            }
        });
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dl.open();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drag_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        new Invoker(new Callback() {
            @Override
            public boolean onRun() {
                return true;
            }

            @Override
            public void onBefore() {

            }

            @Override
            public void onAfter(boolean b) {

            }
        }).start();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.holder:

                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, "click now", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
            dl.onCloseChildViewTouch();
        }

        if (status == SLIDE_STATUS_OFF) {
            Log.d("test", "Close");
            dl.onOpenChildViewTouch();
        }
    }


    private class SlideAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return petItems.size();
        }

        @Override
        public Object getItem(int position) {
            return petItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.item_context, null);

                slideView = new SlideView(Main.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(Main.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }

            PetItem item = petItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

            holder.textPetName.setText(item.getPetName());
            holder.textPetBattery.setText(item.getPetBattery());
            holder.deleteHolder.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    petItems.remove(position);
                    adapter.notifyDataSetChanged();

                }
            });

            if (!item.getPetName().equals("Pet 0")) {
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.progressBar.start();
            }

            return slideView;
        }

    }

    public class PetItem extends PetListItem {
        public SlideView slideView;
    }

    public static class ViewHolder {

        public TextView textPetName;
        public TextView textPetBattery;
        public ViewGroup deleteHolder;
        public DotsProgressBar progressBar;

        ViewHolder(View view) {
            textPetName = (TextView) view.findViewById(R.id.textPetName);
            textPetBattery = (TextView) view.findViewById(R.id.textBattery);
            deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
            progressBar = (DotsProgressBar) view.findViewById(R.id.dotsProgressBar);
            progressBar.setDotsCount(4);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
