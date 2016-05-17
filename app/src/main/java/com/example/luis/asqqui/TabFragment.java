package com.example.luis.asqqui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Luis on 18/03/2016.
 */
public class TabFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_main, container, false);

        Context context = getContext();

        int value = getArguments().getInt("VALUE");

        System.out.println("valor: " + value);

//        TextView view = (TextView)root.findViewById(R.id.main_text);

        RecyclerView rv = (RecyclerView) root.findViewById(R.id.main_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        MainRVAdapter adapter;
        rv.addItemDecoration(new DividerItemDecoration(context));


        switch (value){
            case 0:
                adapter = new MainRVAdapter(context, MainActivity.VEREADOR);
                rv.setAdapter(adapter);
//                view.setText("Governador");
                break;
            case 1:
                adapter = new MainRVAdapter(context, MainActivity.PREFEITO);
                rv.setAdapter(adapter);
//                view.setText("Prefeito");
                break;
            case 2:
                adapter = new MainRVAdapter(context, MainActivity.DEP_ESTADUAL);
                rv.setAdapter(adapter);
//                view.setText("Vereador");
                break;
        }
        return root;
    }
}
