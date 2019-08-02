package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private UserAdapter userAdapter;
    private RecyclerView userList;
    private ProgressDialog progressDialog;
    private List<User> users;
    private ApiEndPoints api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        showProgress();
        getUserData();
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading .....");
        progressDialog.show();
    }

    private void getUserData() {
        api = RetrofitInstance.getRetrofitInstance().create(ApiEndPoints.class);

        // Get List of Users
        //getUsersList();

        // Get a single User
        //getUser("xeobarc");

        // Create a Post
        //createPost();

        // Get list of posts with id=1
        getPosts(4);

    }

    private void getPosts(int userId) {
        Call<List<Post>> callPostWithID = api.getPosts(userId);
        callPostWithID.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response != null) {
                    progressDialog.dismiss();
                    if (response.isSuccessful() && response.body().size() != 0) {
                        List<Post> posts = response.body();
                        String string = "";
                        for (int i = 0; i < posts.size(); i++) {
                            Post ps = posts.get(i);
                            string += "ID : " + ps.getId() + "\n" +
                                    "User ID : " + ps.getUserId() + "\n" +
                                    "Title : " + ps.getTitle() + "\n\n";
                        }
                        Log.d("MainActivity", string);
                    } else {
                        Log.d("MainActivity", "Invalid User ID !!");
                    }
                } else {
                    Log.d("MainActivity", "Invalid Request !!");
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("MainActivity", t.toString());
            }
        });
    }

    private void createPost() {
        Post post = new Post(10, "Hello World", "Welcome !!");

        Call<Post> callPost = api.createPost(post);
        callPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    String s = "";
                    s += "Code: " + response.code() + "\n";
                    s += "ID: " + response.body().getId() + "\n";
                    s += "Title: " + response.body().getTitle() + "\n";
                    s += "Body: " + response.body().getBody() + "\n";

                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Request !!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUser(String name) {
        Call<User> callUser = api.getUser(name);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    User user = response.body();
                    users = new ArrayList<>();
                    users.add(user);
                    userAdapter = new UserAdapter(MainActivity.this, users);
                    userList.setAdapter(userAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "No Such User Exists :|", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUsersList() {
        Call<List<User>> callUsers = api.getUsers();
        callUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                progressDialog.dismiss();
                List<User> users = response.body();
                userAdapter = new UserAdapter(MainActivity.this, users);
                userList.setAdapter(userAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        userList = (RecyclerView) findViewById(R.id.userList);
        userList.setLayoutManager(new LinearLayoutManager(this));
    }
}
