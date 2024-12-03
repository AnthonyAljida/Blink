package edu.oakland.blink.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import edu.oakland.blink.models.UserModel;

public class AndroidUtil {
  public static void showToast(Context context, String Message) {

    Toast.makeText(context, Message, Toast.LENGTH_LONG).show();
  }

  public static void passUserModelAsIntent(Intent intent, UserModel model) {
    intent.putExtra("username", model.getUsername());
    intent.putExtra("phone", model.getPhone());
    intent.putExtra("userId", model.getUserId());
    intent.putExtra("fcmToken", model.getFcmToken());
  }

  public static UserModel getUserModelFromIntent(Intent intent) {
    UserModel userModel = new UserModel();
    userModel.setUsername(intent.getStringExtra("username"));
    userModel.setPhone(intent.getStringExtra("phone"));
    userModel.setUserId(intent.getStringExtra("userId"));
    userModel.setFcmToken(intent.getStringExtra("fcmToken"));
    return userModel;
  }

//  public static void setProfilePic(Context context, Uri imageUri, ImageView imageView) {
//    Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
//  }
}
