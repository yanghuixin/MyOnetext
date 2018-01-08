package com.example.administrator.myonetext.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.adapter.AreaAdapter;
import com.example.administrator.myonetext.adapter.SelectedCityAdapter;
import com.example.administrator.myonetext.bean.AreaBean;
import com.example.administrator.myonetext.bean.CityEvent;
import com.example.administrator.myonetext.utils.ConvertUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChoseStartArriveCityActivity extends AppCompatActivity {
    private Unbinder bind;

    @BindView(R.id.re_clickBack)
    RelativeLayout reClickBack;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView_1;//一级地址
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView_2;//二级地址
    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView_3;//三级地址
    @BindView(R.id.rv_selected_cities)
    RecyclerView rvSelectedCities;//选择的目的地
    @BindView(R.id.text_selected_country)
    TextView selectedCountry;
    @BindView(R.id.re_selected_cities)
    RelativeLayout re_selected_cities;//选择的到达城市
    @BindView(R.id.city_number)
    TextView textCityNumber;
    @BindView(R.id.edit_search)
    EditText editSearch;


    private ArrayList<AreaBean> allCities = new ArrayList<>();

    private int length;//所有城市的个数

    private ArrayList<AreaBean> selectedCities = new ArrayList<>();//选择的到达城市
    private SelectedCityAdapter selectedCityAdapter;
    private ArrayList<AreaBean> city1 = new ArrayList<>();//一级地址
    private AreaAdapter adapter1;//一级地址适配器
    private int index1 = -1;//一级地址当前选中项
    private ArrayList<AreaBean> city2 = new ArrayList<>();//一级地址
    private AreaAdapter adapter2;//一级地址适配器
    private int index2 = -1;//一级地址当前选中项
    private ArrayList<AreaBean> city3 = new ArrayList<>();//一级地址
    private AreaAdapter adapter3;//一级地址适配器
    private int index3 = -1;//一级地址当前选中项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_start_arrive_city);
        bind = ButterKnife.bind(this);
        initViewsAndEvents(savedInstanceState);
        loadData();
    }

    protected void initViewsAndEvents(Bundle savedInstanceState) {
        re_selected_cities.setVisibility(android.view.View.VISIBLE);
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) editSearch.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(ChoseStartArriveCityActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    // 搜索
                    searchCity();
                    return true;
                }
                return false;
            }
        });
    }
    private void searchCity() {
        String str = editSearch.getText().toString();
        city1.clear();
        city2.clear();
        city3.clear();
        AreaBean city = null;//搜索到的城市
        for (int i = 0; i < length; i++) {
            if (allCities.get(i).getAreaName().contains(str)) {
                city = allCities.get(i);
            }
        }
        //根据搜索到的城市显示结果
        if (city != null) {

            if (city.getAreaDeep() == 1) {//是第一级城市
                city1.add(city);
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
                adapter3.notifyDataSetChanged();
            } else if (city.getAreaDeep() == 2) {//城市是二级城市
                int cityParent = city.getAreaParentId();
                for (int i = 0; i < length; i++) {
                    if (allCities.get(i).getAreaId() == cityParent) {
                        city1.add(allCities.get(i));
                    }
                }
                city2.add(city);
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
                adapter3.notifyDataSetChanged();
            } else if (city.getAreaDeep() >= 3) {
                city3.add(city);
                int secendId = city.getAreaParentId();//二级城市的id

                for (int i = 0; i < length; i++) {
                    if (allCities.get(i).getAreaId() == secendId) {
                        city2.add(allCities.get(i));
                    }
                }
                int firstId = city2.get(0).getAreaParentId();//一级城市的id
                for (int i = 0; i < length; i++) {
                    if (allCities.get(i).getAreaId() == firstId) {
                        city1.add(allCities.get(i));
                    }
                }

                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
                adapter3.notifyDataSetChanged();
            }
        }
    }
    protected void loadData() {
        //初始化所有城市
        try {
            String json = ConvertUtils.toString(getAssets().open("area.json"));
            allCities = new Gson().fromJson(json, new TypeToken<ArrayList<AreaBean>>() {
            }.getType());
        } catch (Exception e) {
        }

        for (int i = 0; i < 7; i++) {
            city1.add(allCities.get(i));
        }
        length = allCities.size();
        //初始化第一层地址
        adapter1 = new AreaAdapter();
        adapter1.setData(city1);
        recyclerView_1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView_1.setAdapter(adapter1);
        //初始化第二层地址
        adapter2 = new AreaAdapter();
        adapter2.setData(city2);
        recyclerView_2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView_2.setAdapter(adapter2);
        //初始化第三层地址
        adapter3 = new AreaAdapter();
        adapter3.setData(city3);
        recyclerView_3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView_3.setAdapter(adapter3);

        //初始化已选择城市
        selectedCityAdapter = new SelectedCityAdapter();
        selectedCityAdapter.setData(selectedCities);
        rvSelectedCities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvSelectedCities.setAdapter(selectedCityAdapter);
        selectedCityAdapter.setOnItemClickListener(new SelectedCityAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                //上面字变未选中颜色
                AreaBean temp = selectedCities.get(position);//要删除的元素
                int tempid = temp.getAreaId();
                for (int i = 0; i < city3.size(); i++) {
                    if (city3.get(i).getAreaId() == tempid) {
                        city3.get(i).setSelected(false);
                        adapter3.notifyDataSetChanged();
                    }
                }
                //从选中城市中删除
                selectedCities.remove(position);
                textCityNumber.setText(selectedCities.size() + "");
                //从下面列表删除
                selectedCityAdapter.notifyDataSetChanged();

            }
        });
        adapter1.setOnItemClickListener(new AreaAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                if (index1 == -1) {//当前还没有选 中项
                    city1.get(position).setSelected(true);
                    index1 = position;
                    adapter1.notifyItemChanged(position);
                    selectedCountry.setText(city1.get(position).getAreaName());
                } else {
                    city1.get(index1).setSelected(false);
                    city1.get(position).setSelected(true);
                    adapter1.notifyItemChanged(index1);
                    adapter1.notifyItemChanged(position);
                    index1 = position;
                    selectedCountry.setText(city1.get(position).getAreaName());
                }
                //显示二级地址
                city2.clear();//先清空
                int parentId = city1.get(position).getAreaId();
                for (int i = 0; i < length; i++) {
                    if (allCities.get(i).getAreaParentId() == parentId) {
                        city2.add(allCities.get(i));
                    }
                }
                adapter2.notifyDataSetChanged();
                city3.clear();
                adapter3.notifyDataSetChanged();

            }
        });
        adapter2.setOnItemClickListener(new AreaAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                if (index2 == -1) {//当前还没有选 中项
                    city2.get(position).setSelected(true);
                    index2 = position;
                    adapter2.notifyItemChanged(position);
                } else {
                    city2.get(index2).setSelected(false);
                    city2.get(position).setSelected(true);
                    adapter2.notifyItemChanged(index2);
                    adapter2.notifyItemChanged(position);
                    index2 = position;
                }
                //显示三级地址
                city3.clear();//先清空
                int parentId = city2.get(position).getAreaId();
                for (int i = 0; i < length; i++) {
                    if (allCities.get(i).getAreaParentId() == parentId) {
                        city3.add(allCities.get(i));
                    }
                }
                adapter3.notifyDataSetChanged();
            }
        });

        adapter3.setOnItemClickListener(new AreaAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                if (index3 == -1) {//当前还没有选 中项
                    city3.get(position).setSelected(true);
                    index3 = position;
                    adapter3.notifyItemChanged(position);
                } else {
                    city3.get(index3).setSelected(false);
                    city3.get(position).setSelected(true);
                    adapter3.notifyItemChanged(index3);
                    adapter3.notifyItemChanged(position);
                    index3 = position;
                }
                //向homefragment发送选择的出发城市
                EventBus.getDefault().post(new CityEvent(true, city3.get(position).getAreaName()));
                finish();
            }
        });
    }


    @OnClick({R.id.re_clickBack,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
