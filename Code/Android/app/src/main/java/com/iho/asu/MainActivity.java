package com.iho.asu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.iho.asu.Database.DisplayDataFromDB.EventsFragment;
import com.iho.asu.Database.DisplayDataFromDB.FeaturedNewsFragment;
import com.iho.asu.Database.DisplayDataFromDB.GalleryFragment;
import com.iho.asu.Database.DisplayDataFromDB.LecturerFragment;
import com.iho.asu.Database.DisplayDataFromDB.NewsFragment;
import com.iho.asu.Database.DisplayDataFromDB.ScienceFragment;
import com.iho.asu.Database.DisplayDataFromDB.TravelFragment;
import com.iho.asu.Pages.About;
import com.iho.asu.Pages.Connect;
import com.iho.asu.Pages.Credits;
import com.iho.asu.Pages.Donate;
import com.iho.asu.Pages.FieldNotes;
import com.iho.asu.Pages.Gallery;
import com.iho.asu.Pages.Lucy;
import com.iho.asu.Pages.MainFragment;
import com.iho.asu.Pages.NewsEvents;

import static android.view.View.OnClickListener;



public class MainActivity extends Activity implements OnClickListener{
    public static Fragment fragment = new MainFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Log.d("MainActivity",ft.isEmpty()+"");
        ft.add(R.id.main_layout, fragment);
        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Uri uri;
        Intent intent;
        switch (v.getId()) {
            case R.id.about:
                fragment = new About();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.customConnectBackButton:
            case R.id.connect:
                fragment = new Connect(R.layout.fragment_connect);
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.customNEBackButton:
            case R.id.customNewsEBackbutton:
            case R.id.ne:
                fragment = new NewsEvents();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.donate:
                fragment = new Donate();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.customGalleryBackButton:
            case R.id.gallery:
                fragment = new Gallery();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.investButton:
                uri = Uri.parse("https://iho.asu.edu/support-iho");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.linkToLocation:
                uri = Uri.parse("https://iho.asu.edu/contact-us");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.linkToContact:
                uri = Uri.parse("https://iho.asu.edu/contact-us");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.fbButton:
                uri = Uri.parse("https://www.facebook.com/pages/Lucy-and-ASU-Institute-of-Human-Origins/146317035387367");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.twButton:
                uri = Uri.parse("https://twitter.com/HumanOriginsASU");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.instaButton:
                uri = Uri.parse("https://www.instagram.com/human_origins_asu/");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.creditsButton:
                fragment = new Credits();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.customBackButton:
                fragment = new MainFragment();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.youtubeBtn:
                uri = Uri.parse("https://www.youtube.com/user/LucyASUIHO");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.vimeoBtn:
                uri = Uri.parse("http://vimeo.com/user5956652");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.connect1:
                fragment = new Connect(R.layout.fragment_iho);
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.connect2:
                fragment = new Connect(R.layout.fragment_bh);
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.connect3:
//                fragment = new Connect(R.layout.fragment_sign_news);
//                fragmentTransaction.replace(R.id.main_layout, fragment);
//                fragmentTransaction.commit();
//                break;
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setData(Uri.parse("mailto:"));
                email.setType("text/plain");
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "bbaburaj@asu.edu"});
                email.putExtra(Intent.EXTRA_SUBJECT, "IHO E-News Subscription");
                startActivity(Intent.createChooser(email, "Choose an Email Client:"));
                break;
            case R.id.connect4:
                fragment = new Connect(R.layout.fragment_anthropologist);
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.website:
                uri = Uri.parse("https://iho.asu.edu/");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.visitBecomingHumanBtn:
                uri = Uri.parse("http://www.becominghuman.org/");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.visitAskAnAnthropologist:
                uri = Uri.parse("https://askananthropologist.asu.edu/");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.galleryBtn:
                fragmentTransaction.remove(fragment);
                fragment = new GalleryFragment();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.mapItBtn:
                uri = Uri.parse("https://www.google.com/maps/place/951+Cady+Mall/@33.419944,-111.9345045,17z/data=!3m1!4b1!4m2!3m1!1s0x872b08db89afde97:0x2a9f7cbb1d7f4e64");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.customFNBackbutton:
            case R.id.field:
                fragment = new FieldNotes();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.customLecturerBackbutton:
            case R.id.fn1:
                fragmentTransaction.remove(fragment);
                fragment = new LecturerFragment();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.fn2:
                fragmentTransaction.remove(fragment);
                fragment = new ScienceFragment();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();

                break;
            case R.id.fn3:
                fragment = new Lucy();
                fragmentTransaction.replace(R.id.field_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.studentBlogLayout:
            case R.id.blogTextView:
            case R.id.fn4:
                uri = Uri.parse("http://asuiho.wordpress.com/");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.ne1:
                fragmentTransaction.remove(fragment);
                fragment = new NewsFragment();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.ne4:
                fragmentTransaction.remove(fragment);
                fragment = new FeaturedNewsFragment();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.ne2:
                fragmentTransaction.remove(fragment);
                fragment = new EventsFragment();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.ne3:
                fragmentTransaction.remove(fragment);
                fragment = new TravelFragment();
                fragmentTransaction.replace(R.id.main_layout, fragment);
                fragmentTransaction.commit();
                break;
           case R.id.enewsSubmit:
//                Intent email = new Intent(Intent.ACTION_SEND);
//                email.setData(Uri.parse("mailto:"));
//                email.setType("text/plain");
//                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "bbaburaj@asu.edu"});
//                email.putExtra(Intent.EXTRA_SUBJECT, "IHO E-News Subscription");
//                startActivity(Intent.createChooser(email, "Choose an Email Client:"));
//                break;
//                Editable txt = ((EditText)fragment.getView().findViewById(R.id.subscriberId)).getText();
//                String emailId = (txt!=null)?txt.toString():"";
//                if(emailId.length()>1 &&emailId.contains("@")) {
//                    new SendEmailTask(emailId).execute();
//                    Toast.makeText(getApplicationContext(), "Thank you for subscribing to our news letter", Toast.LENGTH_LONG).show();
//                } else{
//                    Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_LONG).show();
//                }
//                break;
            case R.id.clearEmailId:
                ((EditText)fragment.getView().findViewById(R.id.subscriberId)).setText("");
                break;
        }
    }

    class SendEmailTask extends AsyncTask<Void, Void, Boolean> {
        String emailId="";
        SendEmailTask(String emailId){
            this.emailId = emailId;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            GMailSender sender = new GMailSender("eNewsSubscription@gmail.com","ihoasu2014");
            try {
                sender.sendMail("E News Subscription"," Please send eNews to "+emailId,emailId, "iho@asu.edu");
                System.out.println("send");
            } catch (Exception e) {
                System.err.println("err"+e);
                Log.e("SendMail", e.getMessage(), e);
            }
            return null;
        }
    }
}
