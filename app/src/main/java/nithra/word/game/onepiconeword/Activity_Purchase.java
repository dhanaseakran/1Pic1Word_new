package nithra.word.game.onepiconeword;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import nithra.word.game.onepiconeword.extra.SharedPreference;
import nithra.word.game.onepiconeword.purchase.IAPHelper;


public class Activity_Purchase extends Activity implements IAPHelper.IAPHelperListener{

    SharedPreference sp = new SharedPreference();

    private static String TAG = "wordsearch";

    private static int PURCHASE_ID = 1001;

    IAPHelper iapHelper;
    HashMap<String, SkuDetails> skuDetailsHashMap = new HashMap<>();
    //For non_consumable tag "nc" is used at start
    final String oneK_coins = "1000_coins";
    final String twoK_coins = "2500_coins";
    final String fiveK_coins = "5000_coins";

    private List<String> skuList = Arrays.asList(oneK_coins, twoK_coins, fiveK_coins);

    Intent returnIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        returnIntent = new Intent();
        returnIntent.putExtra("Purchase_coins",0);
        setResult(1000, returnIntent);

        iapHelper = new IAPHelper(this, Activity_Purchase.this, skuList);

        ImageView close = (ImageView) findViewById(R.id.close);
        ImageView prs1 = (ImageView) findViewById(R.id.prs1);
        ImageView prs25 = (ImageView) findViewById(R.id.prs2);
        ImageView prs5 = (ImageView) findViewById(R.id.prs3);
        ImageView cancel = (ImageView) findViewById(R.id.cancel);
        ImageView remove_ads_img = (ImageView) findViewById(R.id.remove_ads_img);
        TextView ad_txt = (TextView) findViewById(R.id.ad_txt);

        if (sp.getString(Activity_Purchase.this, "PointsPurchase").equals("1K") ||
                sp.getString(Activity_Purchase.this, "PointsPurchase").equals("2.5K") ||
                sp.getString(Activity_Purchase.this, "PointsPurchase").equals("5K")) {

            ad_txt.setText("");
            remove_ads_img.setVisibility(View.INVISIBLE);
        }

        prs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch(oneK_coins);
            }
        });

        prs25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch(twoK_coins);
            }
        });

        prs5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch(fiveK_coins);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    public void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(Activity_Purchase.this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    private void launch(String sku) {
        if (!skuDetailsHashMap.isEmpty())
            iapHelper.launchBillingFLow(skuDetailsHashMap.get(sku));

    }

    @Override
    public void onSkuListResponse(HashMap<String, SkuDetails> skuDetails) {

        skuDetailsHashMap = skuDetails;
    }

    @Override
    public void onPurchasehistoryResponse(List<Purchase> purchasedItems) {
        if (purchasedItems != null) {
            for (Purchase purchase : purchasedItems) {
                //Update UI and backend according to purchased items if required
                // Like in this project I am updating UI for purchased items
                String sku = purchase.getSku();
                switch (sku) {

                }
            }
        }
    }

    @Override
    public void onPurchaseCompleted(Purchase purchase) {
        Toast.makeText(getApplicationContext(), "Purchase Successful", Toast.LENGTH_SHORT).show();
        updatePurchase(purchase);
    }

    private void updatePurchase(Purchase purchase) {
        String sku = purchase.getSku();
        switch (sku) {
            case oneK_coins:
                returnIntent.putExtra("Purchase_coins",1000);
                sp.putString(Activity_Purchase.this, "PointsFlag", "Purchased");

                sp.putString(Activity_Purchase.this, "PointsPurchase", "1K");
                sp.putInt(Activity_Purchase.this, "1kPurchase", sp.getInt(Activity_Purchase.this, "1kPurchase") + 1);

                //Implement the add points code here

                Toast.makeText(Activity_Purchase.this, "1000 coins credited", Toast.LENGTH_SHORT).show();
                sp.putInt(Activity_Purchase.this, "puc_coin", 1000);

                break;

            case twoK_coins:
                returnIntent.putExtra("Purchase_coins",2500);

                sp.putString(Activity_Purchase.this,"PointsFlag","Purchased");

                sp.putString(Activity_Purchase.this, "PointsPurchase", "2.5K");
                sp.putInt(Activity_Purchase.this, "2.5kPurchase", sp.getInt(Activity_Purchase.this, "2.5kPurchase") + 1);

                Toast.makeText(Activity_Purchase.this, "2500 coins credited", Toast.LENGTH_SHORT).show();

                sp.putInt(Activity_Purchase.this,"puc_coin",2500);
                break;

            case fiveK_coins:
                returnIntent.putExtra("Purchase_coins",5000);

                sp.putString(Activity_Purchase.this,"PointsFlag","Purchased");

                sp.putString(Activity_Purchase.this, "PointsPurchase", "5K");
                sp.putInt(Activity_Purchase.this, "5kPurchase", sp.getInt(Activity_Purchase.this, "5kPurchase") + 1);

                Toast.makeText(Activity_Purchase.this, "5000 coins credited", Toast.LENGTH_SHORT).show();
                sp.putInt(Activity_Purchase.this,"puc_coin",5000);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iapHelper != null)
            iapHelper.endConnection();
    }

}
