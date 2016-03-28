package com.example.luis.asqqui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Luis on 18/03/2016.
 */
public class TabFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_main, container, false);

        int value = getArguments().getInt("VALUE");

        TextView view = (TextView)root.findViewById(R.id.main_text);


        switch (value){
            case 0:
                view.setText("Governador");
                break;
            case 1:
                view.setText("Prefeito");
                break;
            case 2:
                view.setText("Vereador");
                break;
        }
        return root;
    }
}
