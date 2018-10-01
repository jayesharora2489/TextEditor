package com.hello.om.texteditor;

import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyFragment extends Fragment {
    private Uri fileUri;
    private EditText et;

    public MyFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_my, container, false);
        et = layout.findViewById(R.id.editText_content);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args=getArguments();
        if(args!=null){
            fileUri=args.getParcelable("URI");
            if(fileUri!=null && getActivity() != null){
                try {
                    InputStream is=getActivity().getContentResolver().openInputStream(fileUri);
                    if (is != null) {
                    BufferedReader br=new BufferedReader(new InputStreamReader(is));
                    String s ="";
                    String line;
                    while((line=br.readLine())!=null){
                        s+=line+"\n";
                    }
                    is.close();
                    et.setText(s);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
        String getContentFileName(int position) {
            if (fileUri == null) {
                return "Untitled " + position;
            }
            else {
                return new File(fileUri.getPath()).getName();
            }
        }

    }



