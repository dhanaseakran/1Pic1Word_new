package nithra.word.game.onepiconeword.extra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {

    SharedPreference sp=new SharedPreference();


    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context) {
        super(context);
        init();
    }

    private void init() {

        System.out.println("--------------Fonts init() : "+sp.getString(getContext(), "Fonts"));

        if(sp.getString(getContext(),"Fonts").equals("")){

            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "but.ttf");
            setTypeface(tf);
        }else{

            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), sp.getString(getContext(),"Fonts"));
            setTypeface(tf);
        }

        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(), sp.getString(getContext(),"Fonts"));
        ///setTypeface(tf);
    }

}
