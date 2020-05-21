package com.mobilecomp.viswa.emoguess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

public class ViewPagerAdapter extends PagerAdapter {

    //    String mResources[] = {"To start off lets understand what exactly Android CardView is? Its a new widget for Android, which can be used to display a card sort of a layout in android. As you may know Android material design is inspired from paper and ink concept. Mostly it displays views on top of each other, with shadows. In simple terms, Android CardView is such a view which has all material design properties, most importantly showing shadows according the elevation. The best part about this view is that it extends FrameLayout and it can be displayed on all the platforms of android since it’s available through the Support v7 library. Lets have a look at some of its properties:", "To start off lets understand what exactly Android CardView is? Its a new widget for Android, which can be used to display a card sort of a layout in android. As you may know Android material design is inspired from paper and ink concept. Mostly it displays views on top of each other, with shadows. In simple terms, Android CardView is such a view which has all material design properties, most importantly showing shadows according the elevation. The best part about this view is that it extends FrameLayout and it can be displayed on all the platforms of android since it’s available through the Support v7 library. Lets have a look at some of its properties:", "To start off lets understand what exactly Android CardView is? Its a new widget for Android, which can be used to display a card sort of a layout in android. As you may know Android material design is inspired from paper and ink concept. Mostly it displays views on top of each other, with shadows. In simple terms, Android CardView is such a view which has all material design properties, most importantly showing shadows according the elevation. The best part about this view is that it extends FrameLayout and it can be displayed on all the platforms of android since it’s available through the Support v7 library. Lets have a look at some of its properties:", "To start off lets understand what exactly Android CardView is? Its a new widget for Android, which can be used to display a card sort of a layout in android. As you may know Android material design is inspired from paper and ink concept. Mostly it displays views on top of each other, with shadows. In simple terms, Android CardView is such a view which has all material design properties, most importantly showing shadows according the elevation. The best part about this view is that it extends FrameLayout and it can be displayed on all the platforms of android since it’s available through the Support v7 library. Lets have a look at some of its properties:"};
    static ImageFragment.Emo[] mEmotions;
    static String eText;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ViewPagerAdapter(Context context, ImageFragment.Emo[] emotions) {
        mContext = context;
        mEmotions = emotions;

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mEmotions.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.view_pager, container, false);

        TextView emoText = itemView.findViewById(R.id.textViewEmotions);
        ImageView image = itemView.findViewById(R.id.imageView);

        emoText.setText(mEmotions[position].getText());
        eText= mEmotions[position].getText();

        System.out.println(position+" "+eText);
        /*********/
        Uri img = Uri.parse("android.resource://"
                + mContext.getPackageName() + "/raw/"
                + mEmotions[position].getImage());
        InputStream imageIS = mContext.getResources().openRawResource(mEmotions[position].getImage());
        Bitmap image_org = BitmapFactory.decodeStream(imageIS);//loading the large bitmap is fine.
        int w = image_org.getWidth();//get width
        int h = image_org.getHeight();//get height
        int aspRat = w / h;//get aspect ratio
        int W = 500;//do whatever you want with width. Fixed, screen size, anything
        int H = W * aspRat;//set the height based on width and aspect ratio

        Bitmap b = Bitmap.createScaledBitmap(image_org, W, H, false);//scale the bitmap
        image.setImageBitmap(b);//set the image view
        image_org = null;//save memory on the bitmap called 'image'
        /*********/



//        System.out.println("Image\n"+ mEmotions[position].getImage());
       // image.setImageResource(mEmotions[position].getImage());
        random();

        container.addView(itemView);
        return itemView;
    }

    public static ImageFragment.Emo[] random() {
       // System.out.println("s " +mEmotions[1].getText());
        Collections.shuffle(Arrays.asList(mEmotions));

        return mEmotions;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CardView) object);
    }
}