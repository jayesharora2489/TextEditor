package com.hello.om.texteditor;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class FileExplorerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explorer);
        ListView lv=findViewById(R.id.lv);
        FloatingActionButton fab=findViewById(R.id.fab);
        final FileExplorerListAdapter ad=new FileExplorerListAdapter(FileExplorerActivity.this);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(ad.isItemDirectory(position)){
                    ad.loadFileItemsFromPosition(position);
                }
                else
                {
                    final Uri filesUri=ad.getUriFromPosition(position);
                    AlertDialog.Builder b=new AlertDialog.Builder(FileExplorerActivity.this);
                    b.setTitle("Overwrite").setMessage("Do you want to Overwrite ?");
                    b.setPositiveButton("Overwrite", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent();
                            intent.setData(filesUri);
                            setResult(0,intent);
                        }
                    });
                    b.setNeutralButton("Cancel", null);
                    b.create();
                    b.show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri dirUri=ad.getCurrentDirectoryUri();

            }
        });
    }

}
