package org.gpginc.ntateam.projectwkff;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.gpginc.ntateam.projectwkff.database.User;
import org.gpginc.ntateam.projectwkff.databinding.BootActivityBinding;

import java.util.Arrays;
import java.util.List;

import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;

public class Boot extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private final String TAG = "Google LOGin";
    private BootActivityBinding binder;
    private Handler handler = new Handler();
    private FirebaseAuth auth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;

    /*anim stuff*/
    private Runnable showProgress = () ->
    {
        binder.bar.setVisibility(View.VISIBLE);
        binder.bar.setAlpha(1);
    };
    private Runnable show = () -> {
        if(binder!=null)
        {
            binder.title.animate().alpha(1).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                    showProgress.run();
                    wainAndLOGin();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    showProgress.run();
                    wainAndLOGin();
                }
            }).start();

        } else throw new NullPointerException("Binder must not be null!");
    };

    /*-------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binder = DataBindingUtil.setContentView(this, R.layout._boot_activity);
        auth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        binder.signin.setOnClickListener(v -> signIn());

    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(show, 250);
    }

    private void wainAndLOGin()
    {
        final FirebaseUser user = auth.getCurrentUser();
        if(user!=null)
        {
            Toast.makeText(this, R.string.welcomeback, Toast.LENGTH_SHORT);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else
        {
            //TODO: Show LOGin Form
            binder.bar.animate().setDuration(150).alpha(0).start();
            binder.title.setTranslationY(binder.title.getTranslationY() + 75);
            binder.title.animate().setDuration(350).translationYBy(-75).setListener(new AnimatorListenerAdapter() {
               @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    showUpGoogleBtn();
                }
            }).start();
        }
    }

    private void showUpGoogleBtn()
    {
        binder.signin.setTranslationY(binder.signin.getTranslationY() + 35);
        binder.signin.animate().setDuration(300).alpha(1).translationYBy(-35).start();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        binder.signin.setVisibility(View.GONE);
        showProgress.run();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                LOG.w(TAG, "Google sign in failed, \n" + e);
                binder.signin.setVisibility(View.VISIBLE);
                binder.bar.setVisibility(View.GONE);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        LOG.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        createUserNode();
                        LOG.d(TAG, "signInWithCredential:success");
                    } else {
                        // If sign in fails, display a message to the user.
                        LOG.w(TAG, "signInWithCredential:failure\n" + task.getException());
                    }
                });
    }

    private void createUserNode()
    {
        User user1 =User.fromDB(auth.getCurrentUser());
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(user1.getUid())) {
                    db.child(user1.getUid()).setValue(user1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        justGo(null);
    }

    public void justGo(View v)
    {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
