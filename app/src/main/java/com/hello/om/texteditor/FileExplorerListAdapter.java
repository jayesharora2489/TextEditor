package com.hello.om.texteditor;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hello.om.texteditor.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class FileExplorerListAdapter extends ArrayAdapter<File> {
    Context ctx;
    Uri rootUri,fileUri;
    ArrayList<File> filesList;

    public FileExplorerListAdapter( Context ctx) {
        super(ctx, 0);
        this.ctx = ctx;
        rootUri=Uri.parse(Environment.getExternalStorageDirectory().toURI().toString());
        fileUri=rootUri;
        File currDir=new File(rootUri.getPath());
        File[] files=currDir.listFiles();
        filesList.addAll(Arrays.asList(files));
    }

    @Override
    public int getCount() {
        return filesList.size();
    }

    @Nullable
    @Override
    public File getItem(int position) {
        return filesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder{
        ImageView icon;
        TextView name,date;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.file_explorer_item, parent, false);
            vh = new ViewHolder();
            vh.icon = convertView.findViewById(R.id.iv);
            vh.name = convertView.findViewById(R.id.tv_name);
            vh.date = convertView.findViewById(R.id.tv_date);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.icon.setImageResource(filesList.get(position).isDirectory() ? R.drawable.folder : R.drawable.file);
        vh.name.setText(filesList.get(position).getName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        vh.date.setText((sdf.format(filesList.get(position).lastModified())));
        return convertView;
    }

    void loadFileItemsFromPosition(int p){
        File clickeddir=filesList.get(p);
        File[] dirContent=clickeddir.listFiles();
        filesList.addAll(Arrays.asList(dirContent));
        notifyDataSetChanged();
        fileUri= Uri.parse(clickeddir.toURI().toString());
    }

    boolean isItemDirectory(int position){
        return filesList.get(position).isDirectory();
    }

    Uri getUriFromPosition(int p){
        return Uri.parse(filesList.get(p).toURI().toString());
    }

    Uri getCurrentDirectoryUri(){
        return fileUri;
    }
}
