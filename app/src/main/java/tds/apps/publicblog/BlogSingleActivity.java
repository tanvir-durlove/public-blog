package tds.apps.publicblog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BlogSingleActivity extends AppCompatActivity {

    private String  mPost_key = null;
    private DatabaseReference mDatabase;

    private ImageView mBlogSingleImage;
    private TextView mBlogSingleTitle,mBlogsingleDesc;

    private Button mRemoveBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_single);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mAuth = FirebaseAuth.getInstance();

        mPost_key = getIntent().getExtras().getString("blog_id");
        mBlogSingleImage = (ImageView)findViewById(R.id.singlepostimage) ;
        mBlogsingleDesc=(TextView)findViewById(R.id.singlepostdesc) ;
        mBlogSingleTitle=(TextView)findViewById(R.id.singleposttitle) ;
        mRemoveBtn = (Button)findViewById(R.id.singleRemoveBtn);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_title = (String)dataSnapshot.child("title").getValue();
                String post_des = (String)dataSnapshot.child("value").getValue();
                String post_image = (String)dataSnapshot.child("image").getValue();
                String post_uid = (String)dataSnapshot.child("uid").getValue();

                mBlogSingleTitle.setText(post_title);
                mBlogsingleDesc.setText(post_des);

                Picasso.with(BlogSingleActivity.this).load(post_image).into(mBlogSingleImage);

                if (mAuth.getCurrentUser().getUid().equals(post_uid)){

                    mRemoveBtn.setVisibility(View.VISIBLE);

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(mPost_key).removeValue();

                Intent i = new Intent(BlogSingleActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}
