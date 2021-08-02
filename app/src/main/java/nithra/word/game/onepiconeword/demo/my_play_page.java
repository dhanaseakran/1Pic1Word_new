package nithra.word.game.onepiconeword.demo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import nithra.word.game.onepiconeword.MainActivity;
import nithra.word.game.onepiconeword.R;




public class my_play_page extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    SQLiteDatabase mydb_copy;
    Cursor cursor = null;

    ImageView game_image, solve_ans, delete_wrong, random_hint, special_hint;


    TextView count_txt,
            ans_txt1, ans_txt2, ans_txt3, ans_txt4, ans_txt5, ans_txt6, ans_txt7, ans_txt8, ans_txt9, ans_txt10, ans_txt11, ans_txt12,
            qus_txt1, qus_txt2, qus_txt3, qus_txt4, qus_txt5, qus_txt6, qus_txt7, qus_txt8, qus_txt9, qus_txt10, qus_txt11, qus_txt12;

    StringBuilder replace_logoname;

    String word_of_image = "",Dailytest_ok="", logo_Ans = "", ans_done = "", suffuleword = "", my_hinder = "", hind1 = "", hind2 = "", correct_answer = "", well_correct_answer = "";
    int id = 0, check_count = 0, my_hind = 0, my_cc = 0;

    ArrayList<Integer> ori_word_id = new ArrayList<>();
    ArrayList<Integer> randon_safe = new ArrayList<Integer>();
    ArrayList<Integer> randon_reassign = new ArrayList<>();
    ArrayList<Integer> original_qus = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
    ArrayList<Integer> original_ans = new ArrayList<Integer>();
    ArrayList<Integer> ans_tag = new ArrayList<Integer>();
    ArrayList<String> originalletter = new ArrayList<String>(Arrays.asList("nb", "nb", "nb", "nb", "nb", "nb", "nb", "nb", "nb", "nb", "nb", "nb"));

    Random random_hind = new Random();


    public void find_id() {
        game_image = (ImageView) findViewById(R.id.game_image);
        solve_ans = (ImageView) findViewById(R.id.solve_ans);
        delete_wrong = (ImageView) findViewById(R.id.delete_wrong);
        random_hint = (ImageView) findViewById(R.id.random_hint);
        special_hint = (ImageView) findViewById(R.id.special_hint);

        count_txt = (TextView) findViewById(R.id.count_txt);

        ans_txt1 = (TextView) findViewById(R.id.ans_txt1);
        ans_txt2 = (TextView) findViewById(R.id.ans_txt2);
        ans_txt3 = (TextView) findViewById(R.id.ans_txt3);
        ans_txt4 = (TextView) findViewById(R.id.ans_txt4);
        ans_txt5 = (TextView) findViewById(R.id.ans_txt5);
        ans_txt6 = (TextView) findViewById(R.id.ans_txt6);
        ans_txt7 = (TextView) findViewById(R.id.ans_txt7);
        ans_txt8 = (TextView) findViewById(R.id.ans_txt8);
        ans_txt9 = (TextView) findViewById(R.id.ans_txt9);
        ans_txt10 = (TextView) findViewById(R.id.ans_txt10);
        ans_txt11 = (TextView) findViewById(R.id.ans_txt11);
        ans_txt12 = (TextView) findViewById(R.id.ans_txt12);

        qus_txt1 = (TextView) findViewById(R.id.qus_txt1);
        qus_txt2 = (TextView) findViewById(R.id.qus_txt2);
        qus_txt3 = (TextView) findViewById(R.id.qus_txt3);
        qus_txt4 = (TextView) findViewById(R.id.qus_txt4);
        qus_txt5 = (TextView) findViewById(R.id.qus_txt5);
        qus_txt6 = (TextView) findViewById(R.id.qus_txt6);
        qus_txt7 = (TextView) findViewById(R.id.qus_txt7);
        qus_txt8 = (TextView) findViewById(R.id.qus_txt8);
        qus_txt9 = (TextView) findViewById(R.id.qus_txt9);
        qus_txt10 = (TextView) findViewById(R.id.qus_txt10);
        qus_txt11 = (TextView) findViewById(R.id.qus_txt11);
        qus_txt12 = (TextView) findViewById(R.id.qus_txt12);

        ans_txt1.setTag(0);
        ans_txt2.setTag(0);
        ans_txt3.setTag(0);
        ans_txt4.setTag(0);
        ans_txt5.setTag(0);
        ans_txt6.setTag(0);
        ans_txt7.setTag(0);
        ans_txt8.setTag(0);
        ans_txt9.setTag(0);
        ans_txt10.setTag(0);
        ans_txt11.setTag(0);
        ans_txt12.setTag(0);

        qus_txt1.setTag(1);
        qus_txt2.setTag(2);
        qus_txt3.setTag(3);
        qus_txt4.setTag(4);
        qus_txt5.setTag(5);
        qus_txt6.setTag(6);
        qus_txt7.setTag(7);
        qus_txt8.setTag(8);
        qus_txt9.setTag(9);
        qus_txt10.setTag(10);
        qus_txt11.setTag(11);
        qus_txt12.setTag(12);


        ans_txt1.setOnTouchListener(this);
        ans_txt2.setOnTouchListener(this);
        ans_txt3.setOnTouchListener(this);
        ans_txt4.setOnTouchListener(this);
        ans_txt5.setOnTouchListener(this);
        ans_txt6.setOnTouchListener(this);
        ans_txt7.setOnTouchListener(this);
        ans_txt8.setOnTouchListener(this);
        ans_txt9.setOnTouchListener(this);
        ans_txt10.setOnTouchListener(this);
        ans_txt11.setOnTouchListener(this);
        ans_txt12.setOnTouchListener(this);


        game_image.setOnClickListener(this);
        solve_ans.setOnClickListener(this);
        delete_wrong.setOnClickListener(this);
        random_hint.setOnClickListener(this);
        special_hint.setOnClickListener(this);

        qus_txt1.setOnClickListener(this);
        qus_txt2.setOnClickListener(this);
        qus_txt3.setOnClickListener(this);
        qus_txt4.setOnClickListener(this);
        qus_txt5.setOnClickListener(this);
        qus_txt6.setOnClickListener(this);
        qus_txt7.setOnClickListener(this);
        qus_txt8.setOnClickListener(this);
        qus_txt9.setOnClickListener(this);
        qus_txt10.setOnClickListener(this);
        qus_txt11.setOnClickListener(this);
        qus_txt12.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_play_page);

        mydb_copy = this.openOrCreateDatabase("findtheword_copy.db", MODE_PRIVATE, null);
        find_id();
        all_function();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.game_image: {
                Toast.makeText(this, "game_image", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.solve_ans: {
                solve_answer();
            }
            break;
            case R.id.delete_wrong: {
                delete_letter();
            }
            break;
            case R.id.random_hint: {
                random_letter();
            }
            break;
            case R.id.special_hint: {
                free_hint();
            }
            break;

            case R.id.qus_txt1: {
                /* if (click_question < answer.length()) {
                    txt_flow_function(qus_txt1.getText().toString(), (int) qus_txt1.getTag(), qus_txt1);
                }*/


                if (check_count < logo_Ans.length()) {
                    check_count = check_count + 1;
                  /*  contentValues.clear();
                    contentValues.put("check_count", check_count);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                    txt_flow_function(qus_txt1.getText().toString(), (Integer) qus_txt1.getTag());
                    qus_txt1.setVisibility(View.INVISIBLE);

                    int get_tag = (int) qus_txt1.getTag();
                    String get_value = qus_txt1.getText().toString();
                    for (int i = 0; i < ori_word_id.size(); i++) {

                        if ((get_tag) == ori_word_id.get(i)) {

                            for (int j = 0; j < replace_logoname.length(); j++) {

                                hind2 = String.valueOf(replace_logoname.charAt(j)).toUpperCase();

                                if (get_value.equals(hind2)) {
                                    replace_logoname.setCharAt(j, '0');
                                   /* contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                                System.out.println("replace_logoname qus: " + replace_logoname);
                            }
                            break;
                        }
                    }


                }


            }
            break;
            case R.id.qus_txt2: {

                if (check_count < logo_Ans.length()) {
                    check_count = check_count + 1;
                   /* contentValues.clear();
                    contentValues.put("check_count", check_count);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    txt_flow_function(qus_txt2.getText().toString(), (Integer) qus_txt2.getTag());
                    qus_txt2.setVisibility(View.INVISIBLE);

                    int get_tag = (int) qus_txt2.getTag();
                    String get_value = qus_txt2.getText().toString();
                    for (int i = 0; i < ori_word_id.size(); i++) {

                        if ((get_tag) == ori_word_id.get(i)) {

                            for (int j = 0; j < replace_logoname.length(); j++) {

                                hind2 = String.valueOf(replace_logoname.charAt(j)).toUpperCase();

                                if (get_value.equals(hind2)) {
                                    replace_logoname.setCharAt(j, '0');
                                   /* contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                                System.out.println("replace_logoname qus: " + replace_logoname);
                            }
                            break;
                        }
                    }
                }

            }
            break;
            case R.id.qus_txt3: {
                if (check_count < logo_Ans.length()) {
                    check_count = check_count + 1;
                  /*  contentValues.clear();
                    contentValues.put("check_count", check_count);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    txt_flow_function(qus_txt3.getText().toString(), (Integer) qus_txt3.getTag());
                    qus_txt3.setVisibility(View.INVISIBLE);

                    int get_tag = (int) qus_txt3.getTag();
                    String get_value = qus_txt3.getText().toString();
                    for (int i = 0; i < ori_word_id.size(); i++) {

                        if ((get_tag) == ori_word_id.get(i)) {

                            for (int j = 0; j < replace_logoname.length(); j++) {

                                hind2 = String.valueOf(replace_logoname.charAt(j)).toUpperCase();

                                if (get_value.equals(hind2)) {
                                    replace_logoname.setCharAt(j, '0');
                                    /*contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                                System.out.println("replace_logoname qus: " + replace_logoname);
                            }
                            break;
                        }
                    }
                }

            }
            break;
            case R.id.qus_txt4: {

                if (check_count < logo_Ans.length()) {
                    check_count = check_count + 1;
                  /*  contentValues.clear();
                    contentValues.put("check_count", check_count);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    txt_flow_function(qus_txt4.getText().toString(), (Integer) qus_txt4.getTag());
                    qus_txt4.setVisibility(View.INVISIBLE);

                    int get_tag = (int) qus_txt4.getTag();
                    String get_value = qus_txt4.getText().toString();
                    for (int i = 0; i < ori_word_id.size(); i++) {

                        if ((get_tag) == ori_word_id.get(i)) {

                            for (int j = 0; j < replace_logoname.length(); j++) {

                                hind2 = String.valueOf(replace_logoname.charAt(j)).toUpperCase();

                                if (get_value.equals(hind2)) {
                                    replace_logoname.setCharAt(j, '0');
                                  /*  contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                                System.out.println("replace_logoname qus: " + replace_logoname);
                            }
                            break;
                        }
                    }
                }
            }
            break;
            case R.id.qus_txt5: {

                if (check_count < logo_Ans.length()) {
                    check_count = check_count + 1;
                   /* contentValues.clear();
                    contentValues.put("check_count", check_count);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    txt_flow_function(qus_txt5.getText().toString(), (Integer) qus_txt5.getTag());
                    qus_txt5.setVisibility(View.INVISIBLE);

                    int get_tag = (int) qus_txt5.getTag();
                    String get_value = qus_txt5.getText().toString();
                    for (int i = 0; i < ori_word_id.size(); i++) {

                        if ((get_tag) == ori_word_id.get(i)) {

                            for (int j = 0; j < replace_logoname.length(); j++) {

                                hind2 = String.valueOf(replace_logoname.charAt(j)).toUpperCase();

                                if (get_value.equals(hind2)) {
                                    replace_logoname.setCharAt(j, '0');
                                 /*   contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                                System.out.println("replace_logoname qus: " + replace_logoname);
                            }
                            break;
                        }
                    }
                }
            }
            break;
            case R.id.qus_txt6: {

                if (check_count < logo_Ans.length()) {
                    check_count = check_count + 1;
                  /*  contentValues.clear();
                    contentValues.put("check_count", check_count);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    txt_flow_function(qus_txt6.getText().toString(), (Integer) qus_txt6.getTag());
                    qus_txt6.setVisibility(View.INVISIBLE);

                    int get_tag = (int) qus_txt6.getTag();
                    String get_value = qus_txt6.getText().toString();
                    for (int i = 0; i < ori_word_id.size(); i++) {

                        if ((get_tag) == ori_word_id.get(i)) {

                            for (int j = 0; j < replace_logoname.length(); j++) {

                                hind2 = String.valueOf(replace_logoname.charAt(j)).toUpperCase();

                                if (get_value.equals(hind2)) {
                                    replace_logoname.setCharAt(j, '0');
                                  /*  contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                                System.out.println("replace_logoname qus: " + replace_logoname);
                            }
                            break;
                        }
                    }
                }
            }
            break;
            case R.id.qus_txt7: {

                if (check_count < logo_Ans.length()) {
                    check_count = check_count + 1;
                   /* contentValues.clear();
                    contentValues.put("check_count", check_count);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    txt_flow_function(qus_txt7.getText().toString(), (Integer) qus_txt7.getTag());
                    qus_txt7.setVisibility(View.INVISIBLE);

                    int get_tag = (int) qus_txt7.getTag();
                    String get_value = qus_txt7.getText().toString();
                    for (int i = 0; i < ori_word_id.size(); i++) {

                        if ((get_tag) == ori_word_id.get(i)) {

                            for (int j = 0; j < replace_logoname.length(); j++) {

                                hind2 = String.valueOf(replace_logoname.charAt(j)).toUpperCase();

                                if (get_value.equals(hind2)) {
                                    replace_logoname.setCharAt(j, '0');
                                   /* contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                                System.out.println("replace_logoname qus: " + replace_logoname);
                            }
                            break;
                        }
                    }
                }
            }
            break;
            case R.id.qus_txt8: {

                if (check_count < logo_Ans.length()) {
                    check_count = check_count + 1;
                   /* contentValues.clear();
                    contentValues.put("check_count", check_count);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    txt_flow_function(qus_txt8.getText().toString(), (Integer) qus_txt8.getTag());
                    qus_txt8.setVisibility(View.INVISIBLE);

                    int get_tag = (int) qus_txt8.getTag();
                    String get_value = qus_txt8.getText().toString();
                    for (int i = 0; i < ori_word_id.size(); i++) {

                        if ((get_tag) == ori_word_id.get(i)) {

                            for (int j = 0; j < replace_logoname.length(); j++) {

                                hind2 = String.valueOf(replace_logoname.charAt(j)).toUpperCase();

                                if (get_value.equals(hind2)) {
                                    replace_logoname.setCharAt(j, '0');
                                  /*  contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                                System.out.println("replace_logoname qus: " + replace_logoname);
                            }
                            break;
                        }
                    }
                }
            }
            break;
            case R.id.qus_txt9: {

                if (check_count < logo_Ans.length()) {
                    check_count = check_count + 1;
                   /* contentValues.clear();
                    contentValues.put("check_count", check_count);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    txt_flow_function(qus_txt9.getText().toString(), (Integer) qus_txt9.getTag());
                    qus_txt9.setVisibility(View.INVISIBLE);

                    int get_tag = (int) qus_txt9.getTag();
                    String get_value = qus_txt9.getText().toString();
                    for (int i = 0; i < ori_word_id.size(); i++) {

                        if ((get_tag) == ori_word_id.get(i)) {

                            for (int j = 0; j < replace_logoname.length(); j++) {

                                hind2 = String.valueOf(replace_logoname.charAt(j)).toUpperCase();

                                if (get_value.equals(hind2)) {
                                    replace_logoname.setCharAt(j, '0');
                                  /*  contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                                System.out.println("replace_logoname qus: " + replace_logoname);
                            }
                            break;
                        }
                    }
                }
            }
            break;
            case R.id.qus_txt10: {

                if (check_count < logo_Ans.length()) {
                    check_count = check_count + 1;
                   /* contentValues.clear();
                    contentValues.put("check_count", check_count);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    txt_flow_function(qus_txt10.getText().toString(), (Integer) qus_txt10.getTag());
                    qus_txt10.setVisibility(View.INVISIBLE);

                    int get_tag = (int) qus_txt10.getTag();
                    String get_value = qus_txt10.getText().toString();
                    for (int i = 0; i < ori_word_id.size(); i++) {

                        if ((get_tag) == ori_word_id.get(i)) {

                            for (int j = 0; j < replace_logoname.length(); j++) {

                                hind2 = String.valueOf(replace_logoname.charAt(j)).toUpperCase();

                                if (get_value.equals(hind2)) {
                                    replace_logoname.setCharAt(j, '0');
                                    /*contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                                System.out.println("replace_logoname qus: " + replace_logoname);
                            }
                            break;
                        }
                    }
                }
            }
            break;
            case R.id.qus_txt11: {
                if (check_count < logo_Ans.length()) {
                    check_count = check_count + 1;
                   /* contentValues.clear();
                    contentValues.put("check_count", check_count);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    txt_flow_function(qus_txt11.getText().toString(), (Integer) qus_txt11.getTag());
                    qus_txt11.setVisibility(View.INVISIBLE);

                    int get_tag = (int) qus_txt11.getTag();
                    String get_value = qus_txt11.getText().toString();
                    for (int i = 0; i < ori_word_id.size(); i++) {

                        if ((get_tag) == ori_word_id.get(i)) {

                            for (int j = 0; j < replace_logoname.length(); j++) {

                                hind2 = String.valueOf(replace_logoname.charAt(j)).toUpperCase();

                                if (get_value.equals(hind2)) {
                                    replace_logoname.setCharAt(j, '0');
                                   /* contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                                System.out.println("replace_logoname qus: " + replace_logoname);
                            }
                            break;
                        }
                    }
                }

            }
            break;
            case R.id.qus_txt12: {

                if (check_count < logo_Ans.length()) {
                    check_count = check_count + 1;
                   /* contentValues.clear();
                    contentValues.put("check_count", check_count);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    txt_flow_function(qus_txt12.getText().toString(), (Integer) qus_txt12.getTag());
                    qus_txt12.setVisibility(View.INVISIBLE);

                    int get_tag = (int) qus_txt12.getTag();
                    String get_value = qus_txt12.getText().toString();
                    for (int i = 0; i < ori_word_id.size(); i++) {

                        if ((get_tag) == ori_word_id.get(i)) {

                            for (int j = 0; j < replace_logoname.length(); j++) {

                                hind2 = String.valueOf(replace_logoname.charAt(j)).toUpperCase();

                                if (get_value.equals(hind2)) {
                                    replace_logoname.setCharAt(j, '0');
                                    /*contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                                System.out.println("replace_logoname qus: " + replace_logoname);
                            }
                            break;
                        }
                    }
                }
            }
            break;
        }

    }

    public void solve_answer() {
        /*if (total_coin >= 40) {
            total_coin -= 40;
            contentValues.clear();
            contentValues.put("coins", total_coin);
            mydb.update(table_name1, contentValues, "id='" + 1 + "'", null);
            *//*coin_earned_anim(total_coin);*//*
            //  coin_earned_anim(total_coin,sp.getString(getActivity(),"game_name"));

            contentValues.clear();
            contentValues.put("isfinish", 1);
            contentValues.put("solve_opt", 1);
            mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);
            ans_set();
        } else {
            Toast.makeText(getActivity(), "no coins", Toast.LENGTH_SHORT).show();
        }*/

        ans_set();
    }


    public void delete_letter() {
        int total_coin = 30;

        if (total_coin >= 30) {
            total_coin -= 30;
           /* contentValues.clear();
            contentValues.put("coins", total_coin);
            mydb.update(table_name1, contentValues, "id='" + 1 + "'", null);*/

            System.out.println("-*****----****tt ori_word_id : " + ori_word_id);
            original_qus.retainAll(ori_word_id);


            /*contentValues.clear();
            qus_maintain = android.text.TextUtils.join(",", original_qus);
            contentValues.put("qus_maintain", qus_maintain);
            mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

            qus_set_maintain();

            ArrayList<Integer> ans_duplicate_remove = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            ans_maintain();

            System.out.println("check_value ori : " + ori_word_id);
            int available = 0;

            for (int j = 0; j < ori_word_id.size(); j++) {

                int value = ori_word_id.get(j);
                System.out.println("check_value ori j: " + value);

                for (int k = 0; k < original_ans.size(); k++) {
                    if (value == original_ans.get(k)) {
                        ans_duplicate_remove.set(k, value);
                        available += 1;
                        System.out.println("check_value ans : " + ans_duplicate_remove);
                        break;
                    }

                }
            }

            check_count = check_count - available;
            System.out.println("check_value check_count : " + check_count);
           /* contentValues.clear();
            contentValues.put("check_count", check_count);
            contentValues.put("delete_opt", 1);
            mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

            original_ans = (ArrayList<Integer>) ans_duplicate_remove.clone();
          /*  contentValues.clear();
            qus_maintain = android.text.TextUtils.join(",", original_ans);
            contentValues.put("ans_maintain", qus_maintain);
            mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
            ans_set_maintain();
        } else {
            Toast.makeText(getApplicationContext(), "no coins", Toast.LENGTH_SHORT).show();
        }

    }

    public void random_letter() {

        int total_coin = 30;

        if (total_coin >= 5) {
            total_coin -= 5;
           /* contentValues.clear();
            contentValues.put("coins", total_coin);
            mydb.update(table_name1, contentValues, "id='" + 1 + "'", null);*/


            randon_safe.clear();
            randon_safe = (ArrayList<Integer>) original_qus.clone();

            randon_safe.retainAll(ori_word_id);

            System.out.println("suffuleword my_randon_safe :" + randon_safe);

            if (randon_safe.size() > 0) {

                // hint_opt += 1;


                my_hind = randon_safe.get(random_hind.nextInt(randon_safe.size()));
                hind1 = String.valueOf(suffuleword.charAt(my_hind - 1));

                original_qus.remove(new Integer(my_hind));

               /* contentValues.clear();
                qus_maintain = android.text.TextUtils.join(",", original_qus);
                contentValues.put("qus_maintain", qus_maintain);
                contentValues.put("hint_opt", hint_opt);
                mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);
                System.out.println("suffuleword my_hind :" + my_hind + " hind l: " + hind1);*/

                hind_flow();
            }
        } else {
            Toast.makeText(getApplicationContext(), "no coins", Toast.LENGTH_SHORT).show();
        }
    }

    public void free_hint() {

        int total_free_hint = 5;
        if (total_free_hint > 0) {
            my_hinder = "my_hinder";

            show_my_hind();
        } else {
            Toast.makeText(getApplicationContext(), "no free hint", Toast.LENGTH_SHORT).show();
        }

    }

    public void show_my_hind() {

       /* originalletter.clear();

        qus_maintain = mycursor.getString(mycursor.getColumnIndex("not_enable"));
        List<String> values1 = Arrays.asList(qus_maintain.split(","));
        for (int i = 0; i < values1.size(); i++) {
            originalletter.add(values1.get(i));
        }*/

        System.out.println("-----notenable_array originalletter : " + originalletter.size());

        if (originalletter.get(0).equals("0")) {
            ans_txt1.setEnabled(false);
        } else {
            ans_txt1.setText("");
            ans_txt1.setEnabled(true);
            ans_txt1.setBackgroundResource(R.drawable.special_hint);
        }

        if (originalletter.get(1).equals("1")) {
            ans_txt2.setEnabled(false);
        } else {
            ans_txt2.setText("");
            ans_txt2.setEnabled(true);
            ans_txt2.setBackgroundResource(R.drawable.special_hint);
        }

        if (originalletter.get(2).equals("2")) {
            ans_txt3.setEnabled(false);
        } else {
            ans_txt3.setText("");
            ans_txt3.setEnabled(true);
            ans_txt3.setBackgroundResource(R.drawable.special_hint);
        }

        if (originalletter.get(3).equals("3")) {
            ans_txt4.setEnabled(false);
        } else {
            ans_txt4.setText("");
            ans_txt4.setEnabled(true);
            ans_txt4.setBackgroundResource(R.drawable.special_hint);
        }

        if (originalletter.get(4).equals("4")) {
            ans_txt5.setEnabled(false);
        } else {
            ans_txt5.setText("");
            ans_txt5.setEnabled(true);
            ans_txt5.setBackgroundResource(R.drawable.special_hint);
        }

        if (originalletter.get(5).equals("5")) {
            ans_txt6.setEnabled(false);
        } else {
            ans_txt6.setText("");
            ans_txt6.setEnabled(true);
            ans_txt6.setBackgroundResource(R.drawable.special_hint);
        }

        if (originalletter.get(6).equals("6")) {
            ans_txt7.setEnabled(false);
        } else {
            ans_txt7.setText("");
            ans_txt7.setEnabled(true);
            ans_txt7.setBackgroundResource(R.drawable.special_hint);
        }

        if (originalletter.get(7).equals("7")) {
            ans_txt8.setEnabled(false);
        } else {
            ans_txt8.setText("");
            ans_txt8.setEnabled(true);
            ans_txt8.setBackgroundResource(R.drawable.special_hint);

        }

        if (originalletter.get(8).equals("8")) {
            ans_txt9.setClickable(false);
        } else {
            ans_txt9.setText("");
            ans_txt9.setEnabled(true);
            ans_txt9.setBackgroundResource(R.drawable.special_hint);
        }

        if (originalletter.get(9).equals("9")) {
            ans_txt10.setEnabled(false);
        } else {
            ans_txt10.setText("");
            ans_txt10.setEnabled(true);
            ans_txt10.setBackgroundResource(R.drawable.special_hint);

        }

        if (originalletter.get(10).equals("10")) {
            ans_txt11.setEnabled(false);
        } else {
            ans_txt11.setText("");
            ans_txt11.setEnabled(true);
            ans_txt11.setBackgroundResource(R.drawable.special_hint);
        }

        if (originalletter.get(11).equals("11")) {
            ans_txt12.setEnabled(false);
        } else {
            ans_txt12.setText("");
            ans_txt12.setEnabled(true);
            ans_txt12.setBackgroundResource(R.drawable.special_hint);
        }
    }


    public void hind_flow() {

        int cc = 0;

        if (my_hinder.equals("my_hinder")) {

            System.out.println("my_hinder hind_flow my_cc : " + my_cc + "  hind1 : " + hind1);
            cc = my_cc;

            replace_logoname.setCharAt(cc, '0');
        } else {
            answer();
            System.out.println("suffuleword my_correct_answer :" + correct_answer);


            randon_reassign.clear();
            for (int j = 0; j < logo_Ans.length(); j++) {
                hind2 = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
                if (hind2.equals(hind1)) {
                    randon_reassign.add(j);
                }
            }

            System.out.println("suffuleword my_randon_reassign :" + randon_reassign);

            well_correct_answer = "";
            for (int i = 0; i < logo_Ans.length(); i++) {
                well_correct_answer = well_correct_answer + String.valueOf(correct_answer.charAt(i));
            }
            System.out.println("suffuleword my_well_correct_answer b :" + well_correct_answer);

            well_correct_answer = well_correct_answer.replaceAll("0", "");

            System.out.println("suffuleword my_well_correct_answer a :" + well_correct_answer);

            System.out.println("suffuleword my_replace_logoname :" + replace_logoname);


            if (!well_correct_answer.equals("")) {

                for (int i = 0; i < randon_reassign.size(); i++) {

                    hind2 = String.valueOf(correct_answer.charAt(randon_reassign.get(i)));

                    if (hind1.equals(hind2)) {

                        for (int j = 0; j < randon_reassign.size(); j++) {

                            hind2 = String.valueOf(correct_answer.charAt(randon_reassign.get(j)));

                            if (!hind1.equals(hind2)) {
                                cc = randon_reassign.get(j);
                                replace_logoname.setCharAt(cc, '0');
                                break;
                            }
                        }
                    } else {

                        hind2 = String.valueOf(replace_logoname.charAt(randon_reassign.get(i))).toUpperCase();
                        if (hind1.equals(hind2)) {
                            cc = randon_reassign.get(i);
                            replace_logoname.setCharAt(cc, '0');
                            break;
                        }
                    }
                }
            } else {
                for (int i = 0; i < replace_logoname.length(); i++) {

                    hind2 = String.valueOf(replace_logoname.charAt(i)).toUpperCase();

                    if (hind1.equals(hind2)) {
                        cc = i;
                        replace_logoname.setCharAt(cc, '0');
                        break;
                    }
                }
            }
        }

        System.out.println("suffuleword my_replace_logoname cc :" + replace_logoname + " cc :" + cc);

       /* contentValues.clear();
        contentValues.put("replace_ans", "" + replace_logoname);
        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/


        //**************************Not enable the answer txt maintain operation **********************************************

       /* originalletter.clear();
        qus_maintain = mycursor.getString(mycursor.getColumnIndex("not_enable"));
        List<String> values4 = Arrays.asList(qus_maintain.split(","));
        for (int i = 0; i < values4.size(); i++) {
            originalletter.add(values4.get(i));
        }

        originalletter.set(cc, "" + cc);
        contentValues.clear();
        qus_maintain = android.text.TextUtils.join(",", originalletter);
        contentValues.put("not_enable", qus_maintain);
        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);

        mycursor = mydb.rawQuery("select * from " + table_name + " where id='" + id_list.get(pos) + "'", null);
        mycursor.moveToNext();
        replace_logoname = new StringBuilder(mycursor.getString(mycursor.getColumnIndex("replace_ans")));
        check_count = mycursor.getInt(mycursor.getColumnIndex("check_count"));*/

        //set_enable_False();

        originalletter.set(cc, "" + cc);

        int getTag = 0;
        String get_value = "";

        switch (cc) {
            case 0: {

                if (ans_txt1.getText().toString().length() > 0) {

                    getTag = Integer.parseInt(ans_txt1.getTag().toString());
                    hind2 = ans_txt1.getText().toString();

                    original_qus.add(new Integer(getTag));
                   /* qus_maintain = android.text.TextUtils.join(",", original_qus);
                    contentValues.clear();
                    contentValues.put("qus_maintain", qus_maintain);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                    System.out.println("suffuleword_my_getTag :" + getTag + " hind2 :" + hind2);
                    System.out.println("suffuleword_my_original_qus :" + original_qus);
                    System.out.println("suffuleword_my_replace_logoname b :" + replace_logoname);

                    for (int i = 0; i < original_qus.size(); i++) {

                        if (original_qus.get(i) == (getTag)) {

                            randon_reassign.clear();
                            for (int j = 0; j < logo_Ans.length(); j++) {
                                get_value = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
                                if (get_value.equals(hind2)) {
                                    randon_reassign.add(j);
                                }
                            }

                            for (int index = 0; index < randon_reassign.size(); index++) {
                                String s = String.valueOf(replace_logoname.charAt(randon_reassign.get(index)));
                                if (s.equals("0")) {
                                    replace_logoname.setCharAt(randon_reassign.get(index), hind2.charAt(0));
                                   /* contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                                    System.out.println("suffuleword_my_replace_logoname a :" + replace_logoname);
                                    break;
                                }
                            }

                            break;
                        }
                    }

                } else {
                    if (check_count < logo_Ans.length()) {
                        check_count = check_count + 1;
                       /* contentValues.clear();
                        contentValues.put("check_count", check_count);
                        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    }
                }

                ans_txt1.setEnabled(false);
                ans_txt1.setText(hind1);
                ans_txt1.setTag(my_hind);

                ans_maintain();
                qus_set_maintain();
                ans_check();
            }
            break;
            case 1: {

                if (ans_txt2.getText().toString().length() > 0) {

                    getTag = Integer.parseInt(ans_txt2.getTag().toString());
                    hind2 = ans_txt2.getText().toString();

                    original_qus.add(new Integer(getTag));
                   /* qus_maintain = android.text.TextUtils.join(",", original_qus);
                    contentValues.clear();
                    contentValues.put("qus_maintain", qus_maintain);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                    System.out.println("suffuleword_my_getTag :" + getTag + " hind2 :" + hind2);
                    System.out.println("suffuleword_my_original_qus :" + original_qus);
                    System.out.println("suffuleword_my_replace_logoname b :" + replace_logoname);

                    for (int i = 0; i < original_qus.size(); i++) {

                        if (original_qus.get(i) == (getTag)) {

                            randon_reassign.clear();
                            for (int j = 0; j < logo_Ans.length(); j++) {
                                get_value = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
                                if (get_value.equals(hind2)) {
                                    randon_reassign.add(j);
                                }
                            }

                            for (int index = 0; index < randon_reassign.size(); index++) {
                                String s = String.valueOf(replace_logoname.charAt(randon_reassign.get(index)));
                                if (s.equals("0")) {
                                    replace_logoname.setCharAt(randon_reassign.get(index), hind2.charAt(0));
                                   /* contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                                    System.out.println("suffuleword_my_replace_logoname a :" + replace_logoname);

                                    break;
                                }
                            }

                            break;
                        }
                    }

                } else {
                    if (check_count < logo_Ans.length()) {
                        check_count = check_count + 1;
                       /* contentValues.clear();
                        contentValues.put("check_count", check_count);
                        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    }
                }

                ans_txt2.setEnabled(false);
                ans_txt2.setText(hind1);
                ans_txt2.setTag(my_hind);

                ans_maintain();
                qus_set_maintain();
                ans_check();
            }
            break;
            case 2: {

                if (ans_txt3.getText().toString().length() > 0) {

                    getTag = Integer.parseInt(ans_txt3.getTag().toString());
                    hind2 = ans_txt3.getText().toString();

                    original_qus.add(new Integer(getTag));
                   /* qus_maintain = android.text.TextUtils.join(",", original_qus);
                    contentValues.clear();
                    contentValues.put("qus_maintain", qus_maintain);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                    System.out.println("suffuleword_my_getTag :" + getTag + " hind2 :" + hind2);
                    System.out.println("suffuleword_my_original_qus :" + original_qus);
                    System.out.println("suffuleword_my_replace_logoname b :" + replace_logoname);

                    for (int i = 0; i < original_qus.size(); i++) {

                        if (original_qus.get(i) == (getTag)) {

                            randon_reassign.clear();
                            for (int j = 0; j < logo_Ans.length(); j++) {
                                get_value = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
                                if (get_value.equals(hind2)) {
                                    randon_reassign.add(j);
                                }
                            }

                            for (int index = 0; index < randon_reassign.size(); index++) {
                                String s = String.valueOf(replace_logoname.charAt(randon_reassign.get(index)));
                                if (s.equals("0")) {
                                    replace_logoname.setCharAt(randon_reassign.get(index), hind2.charAt(0));
//                                    contentValues.clear();
//                                    contentValues.put("replace_ans", "" + replace_logoname);
//                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);
                                    System.out.println("suffuleword_my_replace_logoname a :" + replace_logoname);

                                    break;
                                }
                            }

                            break;
                        }
                    }

                } else {
                    if (check_count < logo_Ans.length()) {
                        check_count = check_count + 1;
                        /*contentValues.clear();
                        contentValues.put("check_count", check_count);
                        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    }
                }

                ans_txt3.setEnabled(false);
                ans_txt3.setText(hind1);
                ans_txt3.setTag(my_hind);

                ans_maintain();
                qus_set_maintain();
                ans_check();
            }
            break;
            case 3: {

                if (ans_txt4.getText().toString().length() > 0) {

                    getTag = Integer.parseInt(ans_txt4.getTag().toString());
                    hind2 = ans_txt4.getText().toString();

                    original_qus.add(new Integer(getTag));
                    /*qus_maintain = android.text.TextUtils.join(",", original_qus);
                    contentValues.clear();
                    contentValues.put("qus_maintain", qus_maintain);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                    System.out.println("suffuleword_my_getTag :" + getTag + " hind2 :" + hind2);
                    System.out.println("suffuleword_my_original_qus :" + original_qus);
                    System.out.println("suffuleword_my_replace_logoname b :" + replace_logoname);
                    for (int i = 0; i < original_qus.size(); i++) {

                        if (original_qus.get(i) == (getTag)) {

                            randon_reassign.clear();
                            for (int j = 0; j < logo_Ans.length(); j++) {
                                get_value = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
                                if (get_value.equals(hind2)) {
                                    randon_reassign.add(j);
                                }
                            }

                            for (int index = 0; index < randon_reassign.size(); index++) {
                                String s = String.valueOf(replace_logoname.charAt(randon_reassign.get(index)));
                                if (s.equals("0")) {
                                    replace_logoname.setCharAt(randon_reassign.get(index), hind2.charAt(0));
                                   /* contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    System.out.println("suffuleword_my_replace_logoname a :" + replace_logoname);

                                    break;
                                }
                            }

                            break;
                        }
                    }

                } else {
                    if (check_count < logo_Ans.length()) {
                        check_count = check_count + 1;
                       /* contentValues.clear();
                        contentValues.put("check_count", check_count);
                        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    }
                }

                ans_txt4.setEnabled(false);
                ans_txt4.setText(hind1);
                ans_txt4.setTag(my_hind);

                ans_maintain();
                qus_set_maintain();
                ans_check();
            }
            break;
            case 4: {

                if (ans_txt5.getText().toString().length() > 0) {

                    getTag = Integer.parseInt(ans_txt5.getTag().toString());
                    hind2 = ans_txt5.getText().toString();

                    original_qus.add(new Integer(getTag));
                   /* qus_maintain = android.text.TextUtils.join(",", original_qus);
                    contentValues.clear();
                    contentValues.put("qus_maintain", qus_maintain);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                    System.out.println("suffuleword_my_getTag :" + getTag + " hind2 :" + hind2);
                    System.out.println("suffuleword_my_original_qus :" + original_qus);
                    System.out.println("suffuleword_my_replace_logoname b :" + replace_logoname);
                    for (int i = 0; i < original_qus.size(); i++) {

                        if (original_qus.get(i) == (getTag)) {

                            randon_reassign.clear();
                            for (int j = 0; j < logo_Ans.length(); j++) {
                                get_value = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
                                if (get_value.equals(hind2)) {
                                    randon_reassign.add(j);
                                }
                            }

                            for (int index = 0; index < randon_reassign.size(); index++) {
                                String s = String.valueOf(replace_logoname.charAt(randon_reassign.get(index)));
                                if (s.equals("0")) {
                                    replace_logoname.setCharAt(randon_reassign.get(index), hind2.charAt(0));
                                  /*  contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    System.out.println("suffuleword_my_replace_logoname a :" + replace_logoname);

                                    break;
                                }
                            }

                            break;
                        }
                    }

                } else {
                    if (check_count < logo_Ans.length()) {
                        check_count = check_count + 1;
                        /*contentValues.clear();
                        contentValues.put("check_count", check_count);
                        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    }
                }

                ans_txt5.setEnabled(false);
                ans_txt5.setText(hind1);
                ans_txt5.setTag(my_hind);

                ans_maintain();
                qus_set_maintain();
                ans_check();
            }
            break;
            case 5: {

                if (ans_txt6.getText().toString().length() > 0) {

                    getTag = Integer.parseInt(ans_txt6.getTag().toString());
                    hind2 = ans_txt6.getText().toString();

                    original_qus.add(new Integer(getTag));
                    /*qus_maintain = android.text.TextUtils.join(",", original_qus);
                    contentValues.clear();
                    contentValues.put("qus_maintain", qus_maintain);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                    System.out.println("suffuleword_my_getTag :" + getTag + " hind2 :" + hind2);
                    System.out.println("suffuleword_my_original_qus :" + original_qus);
                    System.out.println("suffuleword_my_replace_logoname b :" + replace_logoname);
                    for (int i = 0; i < original_qus.size(); i++) {

                        if (original_qus.get(i) == (getTag)) {

                            randon_reassign.clear();
                            for (int j = 0; j < logo_Ans.length(); j++) {
                                get_value = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
                                if (get_value.equals(hind2)) {
                                    randon_reassign.add(j);
                                }
                            }

                            for (int index = 0; index < randon_reassign.size(); index++) {
                                String s = String.valueOf(replace_logoname.charAt(randon_reassign.get(index)));
                                if (s.equals("0")) {
                                    replace_logoname.setCharAt(randon_reassign.get(index), hind2.charAt(0));
                                   /* contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                            }

                            break;
                        }
                    }

                } else {
                    if (check_count < logo_Ans.length()) {
                        check_count = check_count + 1;
                       /* contentValues.clear();
                        contentValues.put("check_count", check_count);
                        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    }
                }

                ans_txt6.setEnabled(false);
                ans_txt6.setText(hind1);
                ans_txt6.setTag(my_hind);

                ans_maintain();
                qus_set_maintain();
                ans_check();
            }
            break;
            case 6: {

                if (ans_txt7.getText().toString().length() > 0) {

                    getTag = Integer.parseInt(ans_txt7.getTag().toString());
                    hind2 = ans_txt7.getText().toString();

                    original_qus.add(new Integer(getTag));
                   /* qus_maintain = android.text.TextUtils.join(",", original_qus);
                    contentValues.clear();
                    contentValues.put("qus_maintain", qus_maintain);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);
*/
                    System.out.println("suffuleword_my_getTag :" + getTag + " hind2 :" + hind2);
                    System.out.println("suffuleword_my_original_qus :" + original_qus);
                    System.out.println("suffuleword_my_replace_logoname b :" + replace_logoname);
                    for (int i = 0; i < original_qus.size(); i++) {

                        if (original_qus.get(i) == (getTag)) {

                            randon_reassign.clear();
                            for (int j = 0; j < logo_Ans.length(); j++) {
                                get_value = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
                                if (get_value.equals(hind2)) {
                                    randon_reassign.add(j);
                                }
                            }

                            for (int index = 0; index < randon_reassign.size(); index++) {
                                String s = String.valueOf(replace_logoname.charAt(randon_reassign.get(index)));
                                if (s.equals("0")) {
                                    replace_logoname.setCharAt(randon_reassign.get(index), hind2.charAt(0));
                                  /*  contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                            }

                            break;
                        }
                    }

                } else {
                    if (check_count < logo_Ans.length()) {
                        check_count = check_count + 1;
                       /* contentValues.clear();
                        contentValues.put("check_count", check_count);
                        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    }
                }

                ans_txt7.setEnabled(false);
                ans_txt7.setText(hind1);
                ans_txt7.setTag(my_hind);

                ans_maintain();
                qus_set_maintain();
                ans_check();
            }
            break;
            case 7: {

                if (ans_txt8.getText().toString().length() > 0) {

                    getTag = Integer.parseInt(ans_txt8.getTag().toString());
                    hind2 = ans_txt8.getText().toString();

                    original_qus.add(new Integer(getTag));
                  /*  qus_maintain = android.text.TextUtils.join(",", original_qus);
                    contentValues.clear();
                    contentValues.put("qus_maintain", qus_maintain);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                    System.out.println("suffuleword_my_getTag :" + getTag + " hind2 :" + hind2);
                    System.out.println("suffuleword_my_original_qus :" + original_qus);
                    System.out.println("suffuleword_my_replace_logoname b :" + replace_logoname);
                    for (int i = 0; i < original_qus.size(); i++) {

                        if (original_qus.get(i) == (getTag)) {

                            randon_reassign.clear();
                            for (int j = 0; j < logo_Ans.length(); j++) {
                                get_value = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
                                if (get_value.equals(hind2)) {
                                    randon_reassign.add(j);
                                }
                            }

                            for (int index = 0; index < randon_reassign.size(); index++) {
                                String s = String.valueOf(replace_logoname.charAt(randon_reassign.get(index)));
                                if (s.equals("0")) {
                                    replace_logoname.setCharAt(randon_reassign.get(index), hind2.charAt(0));
                                   /* contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                            }

                            break;
                        }
                    }

                } else {
                    if (check_count < logo_Ans.length()) {
                        check_count = check_count + 1;
//                        contentValues.clear();
//                        contentValues.put("check_count", check_count);
//                        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);
                    }
                }

                ans_txt8.setEnabled(false);
                ans_txt8.setText(hind1);
                ans_txt8.setTag(my_hind);

                ans_maintain();
                qus_set_maintain();
                ans_check();
            }
            break;
            case 8: {

                if (ans_txt9.getText().toString().length() > 0) {

                    getTag = Integer.parseInt(ans_txt9.getTag().toString());
                    hind2 = ans_txt9.getText().toString();

                    original_qus.add(new Integer(getTag));
                  /*  qus_maintain = android.text.TextUtils.join(",", original_qus);
                    contentValues.clear();
                    contentValues.put("qus_maintain", qus_maintain);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                    System.out.println("suffuleword_my_getTag :" + getTag + " hind2 :" + hind2);
                    System.out.println("suffuleword_my_original_qus :" + original_qus);
                    System.out.println("suffuleword_my_replace_logoname b :" + replace_logoname);
                    for (int i = 0; i < original_qus.size(); i++) {

                        if (original_qus.get(i) == (getTag)) {

                            randon_reassign.clear();
                            for (int j = 0; j < logo_Ans.length(); j++) {
                                get_value = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
                                if (get_value.equals(hind2)) {
                                    randon_reassign.add(j);
                                }
                            }

                            for (int index = 0; index < randon_reassign.size(); index++) {
                                String s = String.valueOf(replace_logoname.charAt(randon_reassign.get(index)));
                                if (s.equals("0")) {
                                    replace_logoname.setCharAt(randon_reassign.get(index), hind2.charAt(0));
                                   /* contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                            }

                            break;
                        }
                    }

                } else {
                    if (check_count < logo_Ans.length()) {
                        check_count = check_count + 1;
                      /*  contentValues.clear();
                        contentValues.put("check_count", check_count);
                        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    }
                }

                ans_txt9.setEnabled(false);
                ans_txt9.setText(hind1);
                ans_txt9.setTag(my_hind);

                ans_maintain();
                qus_set_maintain();
                ans_check();
            }
            break;
            case 9: {

                if (ans_txt10.getText().toString().length() > 0) {

                    getTag = Integer.parseInt(ans_txt10.getTag().toString());
                    hind2 = ans_txt10.getText().toString();

                    original_qus.add(new Integer(getTag));
                   /* qus_maintain = android.text.TextUtils.join(",", original_qus);
                    contentValues.clear();
                    contentValues.put("qus_maintain", qus_maintain);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                    System.out.println("suffuleword_my_getTag :" + getTag + " hind2 :" + hind2);
                    System.out.println("suffuleword_my_original_qus :" + original_qus);
                    System.out.println("suffuleword_my_replace_logoname b :" + replace_logoname);
                    for (int i = 0; i < original_qus.size(); i++) {

                        if (original_qus.get(i) == (getTag)) {

                            randon_reassign.clear();
                            for (int j = 0; j < logo_Ans.length(); j++) {
                                get_value = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
                                if (get_value.equals(hind2)) {
                                    randon_reassign.add(j);
                                }
                            }

                            for (int index = 0; index < randon_reassign.size(); index++) {
                                String s = String.valueOf(replace_logoname.charAt(randon_reassign.get(index)));
                                if (s.equals("0")) {
                                    replace_logoname.setCharAt(randon_reassign.get(index), hind2.charAt(0));
                                  /*  contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                            }

                            break;
                        }
                    }

                } else {
                    if (check_count < logo_Ans.length()) {
                        check_count = check_count + 1;
                       /* contentValues.clear();
                        contentValues.put("check_count", check_count);
                        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    }
                }

                ans_txt10.setEnabled(false);
                ans_txt10.setText(hind1);
                ans_txt10.setTag(my_hind);

                ans_maintain();
                qus_set_maintain();
                ans_check();
            }
            break;
            case 10: {

                if (ans_txt11.getText().toString().length() > 0) {

                    getTag = Integer.parseInt(ans_txt11.getTag().toString());
                    hind2 = ans_txt11.getText().toString();

                    original_qus.add(new Integer(getTag));
                    /*qus_maintain = android.text.TextUtils.join(",", original_qus);
                    contentValues.clear();
                    contentValues.put("qus_maintain", qus_maintain);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                    System.out.println("suffuleword_my_getTag :" + getTag + " hind2 :" + hind2);
                    System.out.println("suffuleword_my_original_qus :" + original_qus);
                    System.out.println("suffuleword_my_replace_logoname b :" + replace_logoname);
                    for (int i = 0; i < original_qus.size(); i++) {

                        if (original_qus.get(i) == (getTag)) {

                            randon_reassign.clear();
                            for (int j = 0; j < logo_Ans.length(); j++) {
                                get_value = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
                                if (get_value.equals(hind2)) {
                                    randon_reassign.add(j);
                                }
                            }

                            for (int index = 0; index < randon_reassign.size(); index++) {
                                String s = String.valueOf(replace_logoname.charAt(randon_reassign.get(index)));
                                if (s.equals("0")) {
                                    replace_logoname.setCharAt(randon_reassign.get(index), hind2.charAt(0));
                                  /*  contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                            }

                            break;
                        }
                    }

                } else {
                    if (check_count < logo_Ans.length()) {
                        check_count = check_count + 1;
                      /*  contentValues.clear();
                        contentValues.put("check_count", check_count);
                        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    }
                }

                ans_txt11.setEnabled(false);
                ans_txt11.setText(hind1);
                ans_txt11.setTag(my_hind);

                ans_maintain();
                qus_set_maintain();
                ans_check();
            }
            break;
            case 11: {

                if (ans_txt12.getText().toString().length() > 0) {

                    getTag = Integer.parseInt(ans_txt12.getTag().toString());
                    hind2 = ans_txt12.getText().toString();

                    original_qus.add(new Integer(getTag));
                    /*qus_maintain = android.text.TextUtils.join(",", original_qus);
                    contentValues.clear();
                    contentValues.put("qus_maintain", qus_maintain);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                    System.out.println("suffuleword_my_getTag :" + getTag + " hind2 :" + hind2);
                    System.out.println("suffuleword_my_original_qus :" + original_qus);
                    System.out.println("suffuleword_my_replace_logoname b :" + replace_logoname);
                    for (int i = 0; i < original_qus.size(); i++) {

                        if (original_qus.get(i) == (getTag)) {

                            randon_reassign.clear();
                            for (int j = 0; j < logo_Ans.length(); j++) {
                                get_value = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
                                if (get_value.equals(hind2)) {
                                    randon_reassign.add(j);
                                }
                            }

                            for (int index = 0; index < randon_reassign.size(); index++) {
                                String s = String.valueOf(replace_logoname.charAt(randon_reassign.get(index)));
                                if (s.equals("0")) {
                                    replace_logoname.setCharAt(randon_reassign.get(index), hind2.charAt(0));
                                   /* contentValues.clear();
                                    contentValues.put("replace_ans", "" + replace_logoname);
                                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                                    break;
                                }
                            }

                            break;
                        }
                    }

                } else {
                    if (check_count < logo_Ans.length()) {
                        check_count = check_count + 1;
                       /* contentValues.clear();
                        contentValues.put("check_count", check_count);
                        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                    }
                }

                ans_txt12.setEnabled(false);
                ans_txt12.setText(hind1);
                ans_txt12.setTag(my_hind);

                ans_maintain();
                qus_set_maintain();
                ans_check();
            }
            break;
        }


        my_hinder = "";
        hind2 = "";
        hind1 = "";
        getTag = 0;
    }

    public void right_ans_background() {
        ans_txt1.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt2.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt3.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt4.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt5.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt6.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt7.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt8.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt9.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt10.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt11.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt12.setBackgroundResource(R.drawable.txt_answer_bg);
    }

    public void answer() {
        String t1 = "", t2 = "", t3 = "", t4 = "", t5 = "", t6 = "", t7 = "", t8 = "", t9 = "", t10 = "", t11 = "", t12 = "";
        correct_answer = "";

        if (ans_txt1.getText().length() != 0) {
            t1 = ans_txt1.getText().toString();
        } else {
            t1 = "0";
        }
        if (ans_txt2.getText().length() != 0) {
            t2 = ans_txt2.getText().toString();
        } else {
            t2 = "0";
        }
        if (ans_txt3.getText().length() != 0) {
            t3 = ans_txt3.getText().toString();
        } else {
            t3 = "0";
        }
        if (ans_txt4.getText().length() != 0) {
            t4 = ans_txt4.getText().toString();
        } else {
            t4 = "0";
        }
        if (ans_txt5.getText().length() != 0) {
            t5 = ans_txt5.getText().toString();
        } else {

            t5 = "0";
        }
        if (ans_txt6.getText().length() != 0) {
            t6 = ans_txt6.getText().toString();
        } else {
            t6 = "0";
        }
        if (ans_txt7.getText().length() != 0) {
            t7 = ans_txt7.getText().toString();
        } else {
            t7 = "0";
        }
        if (ans_txt8.getText().length() != 0) {
            t8 = ans_txt8.getText().toString();
        } else {
            t8 = "0";
        }
        if (ans_txt9.getText().length() != 0) {
            t9 = ans_txt9.getText().toString();
        } else {
            t9 = "0";
        }
        if (ans_txt10.getText().length() != 0) {
            t10 = ans_txt10.getText().toString();
        } else {
            t10 = "0";
        }
        if (ans_txt11.getText().length() != 0) {
            t11 = ans_txt11.getText().toString();
        } else {
            t11 = "0";
        }
        if (ans_txt12.getText().length() != 0) {
            t12 = ans_txt12.getText().toString();
        } else {
            t12 = "0";
        }
        correct_answer = t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8 + t9 + t10 + t11 + t12;
    }


    public void all_function() {

        if (Dailytest_ok.equals("")) {
            cursor = mydb_copy.rawQuery("select * from onepiconewords where isshow='" + 0 + "' limit 1", null);

        } else {
            cursor = mydb_copy.rawQuery("SELECT * FROM onepiconewords ORDER BY RANDOM() LIMIT 1;", null);
        }

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            word_of_image = cursor.getString(cursor.getColumnIndex("words")).toLowerCase();
            logo_Ans = cursor.getString(cursor.getColumnIndex("words")).toLowerCase();
            id = cursor.getInt(cursor.getColumnIndex("id"));

            int img_resource_value = getResources().getIdentifier(word_of_image.toLowerCase(), "drawable", getPackageName());
            System.out.println("-----====jjj word_of_image img_resource_value " + img_resource_value);
            if (img_resource_value != 0) {
                game_image.setImageResource(img_resource_value);
            }else
            {
                Toast.makeText(this, "no picture check ", Toast.LENGTH_SHORT).show();
            }
          //  game_image.setBackground(getResources().getDrawable(getResources().getIdentifier(word_of_image, "drawable", getPackageName())));

            replace_logoname = new StringBuilder(word_of_image);

            suffuleword = suffuleword();
            ori_word_id.clear();
            for (int i = 0; i < logo_Ans.length(); i++) {

                for (int j = 0; j < suffuleword.length(); j++) {

                    if (String.valueOf(logo_Ans.charAt(i)).equalsIgnoreCase(String.valueOf(suffuleword.charAt(j)))) {
                        if (!ori_word_id.contains(j + 1)) {
                            ori_word_id.add(j + 1);
                        }

                    }
                }
            }

            System.out.println("-*****----****tt ori_word_id all_function : " + ori_word_id);


            all_text_value_set();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(my_play_page.this, MainActivity.class);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        startActivity(intent);
    }

    public String suffuleword() {
        ArrayList<String> ans_input = new ArrayList<String>();
        String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ", random_value = "", store_value = "", logo_name = "";
        Random random = new Random();

        logo_name = logo_Ans;

        int count = 12 - logo_name.length();

        for (int r = 0; r < logo_name.length(); r++) {
            String s1 = String.valueOf(logo_name.charAt(r)).toUpperCase();

            for (int d = 0; d < AB.length(); d++) {
                String s2 = String.valueOf(AB.charAt(d));
                if (s1.equals(s2)) {
                    AB = AB.replace(s1, "");
                }
            }
        }

        for (int j = 0; j < count; j++) {
            char s = AB.charAt(random.nextInt(AB.length()));
            random_value = random_value + String.valueOf(s);
        }

        logo_name = logo_name + random_value;
        for (int k = 0; k < logo_name.length(); k++) {
            ans_input.add(String.valueOf(logo_name.charAt(k)).toUpperCase());
        }

        Collections.shuffle(ans_input);
        for (int s = 0; s < ans_input.size(); s++) {
            store_value = store_value + ans_input.get(s);
        }
        ans_input.clear();


        return store_value;
    }


    public void all_text_value_set() {
        qus_txt1.setText(String.valueOf(suffuleword.charAt(0)).toUpperCase());
        qus_txt2.setText(String.valueOf(suffuleword.charAt(1)).toUpperCase());
        qus_txt3.setText(String.valueOf(suffuleword.charAt(2)).toUpperCase());
        qus_txt4.setText(String.valueOf(suffuleword.charAt(3)).toUpperCase());
        qus_txt5.setText(String.valueOf(suffuleword.charAt(4)).toUpperCase());
        qus_txt6.setText(String.valueOf(suffuleword.charAt(5)).toUpperCase());
        qus_txt7.setText(String.valueOf(suffuleword.charAt(6)).toUpperCase());
        qus_txt8.setText(String.valueOf(suffuleword.charAt(7)).toUpperCase());
        qus_txt9.setText(String.valueOf(suffuleword.charAt(8)).toUpperCase());
        qus_txt10.setText(String.valueOf(suffuleword.charAt(9)).toUpperCase());
        qus_txt11.setText(String.valueOf(suffuleword.charAt(10)).toUpperCase());
        qus_txt12.setText(String.valueOf(suffuleword.charAt(11)).toUpperCase());

        ans_txt1.setVisibility(View.GONE);
        ans_txt2.setVisibility(View.GONE);
        ans_txt3.setVisibility(View.GONE);
        ans_txt4.setVisibility(View.GONE);
        ans_txt5.setVisibility(View.GONE);
        ans_txt6.setVisibility(View.GONE);
        ans_txt7.setVisibility(View.GONE);
        ans_txt8.setVisibility(View.GONE);
        ans_txt9.setVisibility(View.GONE);
        ans_txt10.setVisibility(View.GONE);
        ans_txt11.setVisibility(View.GONE);
        ans_txt12.setVisibility(View.GONE);

        for (int i = 0; i < logo_Ans.length(); i++) {
            if (i == 1) {
                ans_txt1.setVisibility(View.VISIBLE);
                ans_txt2.setVisibility(View.VISIBLE);
            } else if (i == 2) {
                ans_txt3.setVisibility(View.VISIBLE);
            } else if (i == 3) {
                ans_txt4.setVisibility(View.VISIBLE);
            } else if (i == 4) {
                ans_txt5.setVisibility(View.VISIBLE);
            } else if (i == 5) {
                ans_txt6.setVisibility(View.VISIBLE);
            } else if (i == 6) {
                ans_txt7.setVisibility(View.VISIBLE);
            } else if (i == 7) {
                ans_txt8.setVisibility(View.VISIBLE);
            } else if (i == 8) {
                ans_txt9.setVisibility(View.VISIBLE);
            } else if (i == 9) {
                ans_txt10.setVisibility(View.VISIBLE);
            } else if (i == 10) {
                ans_txt11.setVisibility(View.VISIBLE);
            } else if (i == 11) {
                ans_txt12.setVisibility(View.VISIBLE);
            }
        }

        ans_txt1.setEnabled(true);
        ans_txt2.setEnabled(true);
        ans_txt3.setEnabled(true);
        ans_txt4.setEnabled(true);
        ans_txt5.setEnabled(true);
        ans_txt6.setEnabled(true);
        ans_txt7.setEnabled(true);
        ans_txt8.setEnabled(true);
        ans_txt9.setEnabled(true);
        ans_txt10.setEnabled(true);
        ans_txt11.setEnabled(true);
        ans_txt12.setEnabled(true);

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        /*   Cursor not_enable_cur = null;

     try {
            not_enable_cur = mydb.rawQuery("select not_enable from " + table_name + " where id='" + id_list.get(pos) + "'", null);
            not_enable_cur.moveToFirst();
            qus_maintain = "";
            notenable_array.clear();
            qus_maintain = not_enable_cur.getString(not_enable_cur.getColumnIndex("not_enable"));
            if (!qus_maintain.equals("")) {
                List<String> values1 = Arrays.asList(qus_maintain.split(","));
                System.out.println("values1  : " + values1);
                for (int i = 0; i < values1.size(); i++) {
                    notenable_array.add(values1.get(i));
                }
            }

        } finally {
            if (not_enable_cur != null) {
                not_enable_cur.close();
            }
        }
*/
        if (my_hinder.equals("my_hinder")) {

           /* total_free_hint -= 1;
            contentValues.clear();
            contentValues.put("free_hint", total_free_hint);
            mydb.update(table_name1, contentValues, "id='" + 1 + "'", null);*/


            default_ans_tag();

            int viewtag = -1 * (int) view.getTag();
            System.out.println("my_hinder viewtag : " + viewtag);

            ans_txt1.setTag(ans_tag.get(0));
            ans_txt2.setTag(ans_tag.get(1));
            ans_txt3.setTag(ans_tag.get(2));
            ans_txt4.setTag(ans_tag.get(3));
            ans_txt5.setTag(ans_tag.get(4));
            ans_txt6.setTag(ans_tag.get(5));
            ans_txt7.setTag(ans_tag.get(6));
            ans_txt8.setTag(ans_tag.get(7));
            ans_txt9.setTag(ans_tag.get(8));
            ans_txt10.setTag(ans_tag.get(9));
            ans_txt11.setTag(ans_tag.get(10));
            ans_txt12.setTag(ans_tag.get(11));

           /* ans_set_maintain();
            ans_maintain();*/
            System.out.println("my_hinder ans_tag : " + ans_tag);

            ans_maintain();
            ans_set_maintain();


            special_hind(viewtag);

        } else {

            // if (check_enable(view)) {
            if ((int) view.getTag() > 0) {
                   /* try {

                    } finally {
                        // this gets called even if there is an exception somewhere above
                        if (mycursor != null) {
                            mycursor.close();
                        }
                    }*/
                   /* mycursor = mydb.rawQuery("select * from " + table_name + " where id='" + id_list.get(pos) + "'", null);
                    mycursor.moveToNext();*/

                  /*  replace_logoname = new StringBuilder(mycursor.getString(mycursor.getColumnIndex("replace_ans")));
                    check_count = mycursor.getInt(mycursor.getColumnIndex("check_count"));*/

                check_count = check_count - 1;
                   /* contentValues.clear();
                    contentValues.put("check_count", check_count);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/


                txt_maintain((Integer) view.getTag());
                view.setTag(0);
                TextView dropTarget = (TextView) view;
                dropTarget.setText("");
            }
            // }
        }


        return false;
    }

    public void default_ans_tag() {
        ans_tag.clear();
        for (int i = 0; i < 1; i++) {
            ans_tag.add(0, (Integer) ans_txt1.getTag());
            ans_tag.add(1, (Integer) ans_txt2.getTag());
            ans_tag.add(2, (Integer) ans_txt3.getTag());
            ans_tag.add(3, (Integer) ans_txt4.getTag());
            ans_tag.add(4, (Integer) ans_txt5.getTag());
            ans_tag.add(5, (Integer) ans_txt6.getTag());
            ans_tag.add(6, (Integer) ans_txt7.getTag());
            ans_tag.add(7, (Integer) ans_txt8.getTag());
            ans_tag.add(8, (Integer) ans_txt9.getTag());
            ans_tag.add(9, (Integer) ans_txt10.getTag());
            ans_tag.add(10, (Integer) ans_txt11.getTag());
            ans_tag.add(11, (Integer) ans_txt12.getTag());
        }

        ans_txt1.setTag(-1);
        ans_txt2.setTag(-2);
        ans_txt3.setTag(-3);
        ans_txt4.setTag(-4);
        ans_txt5.setTag(-5);
        ans_txt6.setTag(-6);
        ans_txt7.setTag(-7);
        ans_txt8.setTag(-8);
        ans_txt9.setTag(-9);
        ans_txt10.setTag(-10);
        ans_txt11.setTag(-11);
        ans_txt12.setTag(-12);
    }

    public void ans_maintain() {
        //qus_maintain = "";
        original_ans.clear();
        for (int i = 0; i < 1; i++) {
            original_ans.add(0, (Integer) ans_txt1.getTag());
            original_ans.add(1, (Integer) ans_txt2.getTag());
            original_ans.add(2, (Integer) ans_txt3.getTag());
            original_ans.add(3, (Integer) ans_txt4.getTag());
            original_ans.add(4, (Integer) ans_txt5.getTag());
            original_ans.add(5, (Integer) ans_txt6.getTag());
            original_ans.add(6, (Integer) ans_txt7.getTag());
            original_ans.add(7, (Integer) ans_txt8.getTag());
            original_ans.add(8, (Integer) ans_txt9.getTag());
            original_ans.add(9, (Integer) ans_txt10.getTag());
            original_ans.add(10, (Integer) ans_txt11.getTag());
            original_ans.add(11, (Integer) ans_txt12.getTag());

           /* contentValues.clear();
            qus_maintain = android.text.TextUtils.join(",", original_ans);
            contentValues.put("ans_maintain", qus_maintain);
            mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

            System.out.println("my_hinder hind_flow original_ans : " + original_ans);

            System.out.println(" maintain a_u :" + original_ans);
        }


    }

    public void qus_set_maintain() {

       /* try {
        } finally {
            if (mycursor != null)
                mycursor.close();
        }

        mycursor = mydb.rawQuery("select qus_maintain from " + table_name + " where id='" + id_list.get(pos) + "'", null);
        mycursor.moveToFirst();

        original_qus.clear();
        qus_maintain = "";
        qus_maintain = mycursor.getString(mycursor.getColumnIndex("qus_maintain"));
        if (!qus_maintain.equals("")) {
            List<String> values = Arrays.asList(qus_maintain.split(","));

            for (int i = 0; i < values.size(); i++) {
                original_qus.add(Integer.valueOf(values.get(i)));

            }

        }*/

        qus_txt1.setVisibility(View.INVISIBLE);
        qus_txt2.setVisibility(View.INVISIBLE);
        qus_txt3.setVisibility(View.INVISIBLE);
        qus_txt4.setVisibility(View.INVISIBLE);
        qus_txt5.setVisibility(View.INVISIBLE);
        qus_txt6.setVisibility(View.INVISIBLE);
        qus_txt7.setVisibility(View.INVISIBLE);
        qus_txt8.setVisibility(View.INVISIBLE);
        qus_txt9.setVisibility(View.INVISIBLE);
        qus_txt10.setVisibility(View.INVISIBLE);
        qus_txt11.setVisibility(View.INVISIBLE);
        qus_txt12.setVisibility(View.INVISIBLE);

        System.out.println("-*****----****tt original_qus : " + original_qus);

        // if (ori_word_id.size() != 0) {

        for (int d = 0; d < original_qus.size(); d++) {

            switch (original_qus.get(d)) {
                case 1: {
                    qus_txt1.setVisibility(View.VISIBLE);
                }
                break;
                case 2: {
                    qus_txt2.setVisibility(View.VISIBLE);
                }
                break;
                case 3: {
                    qus_txt3.setVisibility(View.VISIBLE);
                }
                break;
                case 4: {
                    qus_txt4.setVisibility(View.VISIBLE);
                }
                break;
                case 5: {
                    qus_txt5.setVisibility(View.VISIBLE);
                }
                break;
                case 6: {
                    qus_txt6.setVisibility(View.VISIBLE);
                }
                break;
                case 7: {
                    qus_txt7.setVisibility(View.VISIBLE);
                }
                break;
                case 8: {
                    qus_txt8.setVisibility(View.VISIBLE);
                }
                break;
                case 9: {
                    qus_txt9.setVisibility(View.VISIBLE);
                }
                break;
                case 10: {
                    qus_txt10.setVisibility(View.VISIBLE);
                }
                break;
                case 11: {
                    qus_txt11.setVisibility(View.VISIBLE);
                }
                break;
                case 12: {
                    qus_txt12.setVisibility(View.VISIBLE);
                }
                break;
            }
        }
        //  }
    }

    public void ans_set_maintain() {
        int a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12;
        a1 = (original_ans.get(0) - 1);
        a2 = (original_ans.get(1) - 1);
        a3 = (original_ans.get(2) - 1);
        a4 = (original_ans.get(3) - 1);
        a5 = (original_ans.get(4) - 1);
        a6 = (original_ans.get(5) - 1);
        a7 = (original_ans.get(6) - 1);
        a8 = (original_ans.get(7) - 1);
        a9 = (original_ans.get(8) - 1);
        a10 = (original_ans.get(9) - 1);
        a11 = (original_ans.get(10) - 1);
        a12 = (original_ans.get(11) - 1);

        String set_ans;
        if (a1 >= 0) {
            set_ans = String.valueOf(suffuleword.charAt(a1));
            ans_txt1.setText(set_ans);
            ans_txt1.setTag(original_ans.get(0));
        } else {
            ans_txt1.setText("");
            ans_txt1.setTag(0);
        }
        if (a2 >= 0) {
            set_ans = String.valueOf(suffuleword.charAt(a2));
            ans_txt2.setText(set_ans);
            ans_txt2.setTag(original_ans.get(1));
        } else {
            ans_txt2.setText("");
            ans_txt2.setTag(0);
        }
        if (a3 >= 0) {
            set_ans = String.valueOf(suffuleword.charAt(a3));
            ans_txt3.setText(set_ans);
            ans_txt3.setTag(original_ans.get(2));
        } else {
            ans_txt3.setText("");
            ans_txt3.setTag(0);
        }
        if (a4 >= 0) {
            set_ans = String.valueOf(suffuleword.charAt(a4));
            ans_txt4.setText(set_ans);
            ans_txt4.setTag(original_ans.get(3));
        } else {
            ans_txt4.setText("");
            ans_txt4.setTag(0);
        }
        if (a5 >= 0) {
            set_ans = String.valueOf(suffuleword.charAt(a5));
            ans_txt5.setText(set_ans);
            ans_txt5.setTag(original_ans.get(4));
        } else {
            ans_txt5.setText("");
            ans_txt5.setTag(0);
        }
        if (a6 >= 0) {
            set_ans = String.valueOf(suffuleword.charAt(a6));
            ans_txt6.setText(set_ans);
            ans_txt6.setTag(original_ans.get(5));
        } else {
            ans_txt6.setText("");
            ans_txt6.setTag(0);
        }
        if (a7 >= 0) {
            set_ans = String.valueOf(suffuleword.charAt(a7));
            ans_txt7.setText(set_ans);
            ans_txt7.setTag(original_ans.get(6));
        } else {
            ans_txt7.setText("");
            ans_txt7.setTag(0);
        }
        if (a8 >= 0) {
            set_ans = String.valueOf(suffuleword.charAt(a8));
            ans_txt8.setText(set_ans);
            ans_txt8.setTag(original_ans.get(7));
        } else {
            ans_txt8.setText("");
            ans_txt8.setTag(0);
        }
        if (a9 >= 0) {
            set_ans = String.valueOf(suffuleword.charAt(a9));
            ans_txt9.setText(set_ans);
            ans_txt9.setTag(original_ans.get(8));
        } else {
            ans_txt9.setText("");
            ans_txt9.setTag(0);
        }
        if (a10 >= 0) {
            set_ans = String.valueOf(suffuleword.charAt(a10));
            ans_txt10.setText(set_ans);
            ans_txt10.setTag(original_ans.get(9));
        } else {
            ans_txt10.setText("");
            ans_txt10.setTag(0);
        }
        if (a11 >= 0) {
            set_ans = String.valueOf(suffuleword.charAt(a11));
            ans_txt11.setText(set_ans);
            ans_txt11.setTag(original_ans.get(10));
        } else {
            ans_txt11.setText("");
            ans_txt11.setTag(0);
        }
        if (a12 >= 0) {
            set_ans = String.valueOf(suffuleword.charAt(a12));
            ans_txt12.setText(set_ans);
            ans_txt12.setTag(original_ans.get(11));
        } else {
            ans_txt12.setText("");
            ans_txt12.setTag(0);
        }
    }


    public void special_hind(int tag) {
        right_ans_background();


        // free_hint_opt += 1;
        randon_safe.clear();
        randon_safe = (ArrayList<Integer>) original_qus.clone();

        System.out.println("my_hinder randon_safe b : " + randon_safe);

        randon_safe.retainAll(ori_word_id);
        Collections.shuffle(randon_safe);

        System.out.println("my_hinder randon_safe a : " + randon_safe);

        if (randon_safe.size() > 0) {

            System.out.println("my_hinder _hint rvalue: " + randon_safe);

            boolean not_check = true;
            String get_ans_char = "" + logo_Ans.charAt(tag - 1);

            for (int i = 0; i < randon_safe.size(); i++) {

                String check_ans = "" + suffuleword.charAt(randon_safe.get(i) - 1);
                if (get_ans_char.equalsIgnoreCase(check_ans)) {

                    my_hind = randon_safe.get(i);
                    System.out.println("my_hinder check_ans : " + check_ans + " hint = " + my_hind + " i= " + i);

                    not_check = false;
                    break;
                }

            }

            System.out.println("my_hinder not_check : " + not_check);


            if (not_check) {
                check_count -= 1;
                System.out.println("check_count not_check : " + check_count);

               /* contentValues.clear();
                if (check_count < logo_Ans.length()) {
                    contentValues.put("check_count", check_count);
                    contentValues.put("free_hint_opt", free_hint_opt);
                    mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);
                }*/

                System.out.println("my_hinder not_check : " + not_check);

                /*ans_maintain();*/
                my_cc = tag - 1;
                hind1 = String.valueOf(logo_Ans.charAt(tag - 1)).toUpperCase();
                retrive_fun();
            } else {

                original_qus.remove(new Integer(my_hind));

              /*  contentValues.clear();
                qus_maintain = android.text.TextUtils.join(",", original_qus);
                contentValues.put("qus_maintain", qus_maintain);
                contentValues.put("free_hint_opt", free_hint_opt);
                mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

                hind1 = String.valueOf(logo_Ans.charAt(tag - 1)).toUpperCase();
                my_cc = tag - 1;
                hind_flow();

            }

        } else {
            check_count -= 1;
            System.out.println("check_count null : " + check_count);
           /* contentValues.clear();
            contentValues.put("check_count", check_count);
            contentValues.put("free_hint_opt", free_hint_opt);
            mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

            /* ans_setmaintain();*/

            my_cc = tag - 1;
            hind1 = String.valueOf(logo_Ans.charAt(tag - 1)).toUpperCase();
            retrive_fun();
        }
    }

    public void retrive_fun() {
        String a1 = ans_txt1.getText().toString();
        if (ans_txt1.getText().toString().equals("") || !originalletter.get(0).equals("nb")) {
            a1 = "0";
        }
        String a2 = ans_txt2.getText().toString();
        if (ans_txt2.getText().toString().equals("") || !originalletter.get(1).equals("nb")) {
            a2 = "0";
        }
        String a3 = ans_txt3.getText().toString();
        if (ans_txt3.getText().toString().equals("") || !originalletter.get(2).equals("nb")) {
            a3 = "0";
        }
        String a4 = ans_txt4.getText().toString();
        if (ans_txt4.getText().toString().equals("") || !originalletter.get(3).equals("nb")) {
            a4 = "0";
        }
        String a5 = ans_txt5.getText().toString();
        if (ans_txt5.getText().toString().equals("") || !originalletter.get(4).equals("nb")) {
            a5 = "0";
        }
        String a6 = ans_txt6.getText().toString();
        if (ans_txt6.getText().toString().equals("") || !originalletter.get(5).equals("nb")) {
            a6 = "0";
        }
        String a7 = ans_txt7.getText().toString();
        if (ans_txt7.getText().toString().equals("") || !originalletter.get(6).equals("nb")) {
            a7 = "0";
        }
        String a8 = ans_txt8.getText().toString();
        if (ans_txt8.getText().toString().equals("") || !originalletter.get(7).equals("nb")) {
            a8 = "0";
        }
        String a9 = ans_txt9.getText().toString();
        if (ans_txt9.getText().toString().equals("") || !originalletter.get(8).equals("nb")) {
            a9 = "0";
        }
        String a10 = ans_txt10.getText().toString();
        if (ans_txt10.getText().toString().equals("") || !originalletter.get(9).equals("nb")) {
            a10 = "0";
        }
        String a11 = ans_txt11.getText().toString();
        if (ans_txt11.getText().toString().equals("") || !originalletter.get(10).equals("nb")) {
            a11 = "0";
        }
        String a12 = ans_txt12.getText().toString();
        if (ans_txt12.getText().toString().equals("") || !originalletter.get(11).equals("nb")) {
            a12 = "0";
        }

        String all_ans = a1 + a2 + a3 + a4 + a5 + a6 + a7 + a8 + a9 + a10 + a11 + a12;
        System.out.println("my_hinder all_ans : " + all_ans + "  hind1 : " + hind1);

        int retrive_value = 0;
        for (int i = 0; i < all_ans.length(); i++) {
            System.out.println("process of i :" + i);

            if (hind1.equals("" + all_ans.charAt(i))) {
                retrive_value = i;
                break;
            }
        }

        System.out.println("my_hinder retrive_value : " + retrive_value);


        retrive_ans(retrive_value);
        hind_flow();

    }

    public void retrive_ans(int retrive_value) {
        switch (retrive_value) {
            case 0: {
                my_hind = Integer.parseInt(ans_txt1.getTag().toString());
                ans_txt1.setText("");
                ans_txt1.setTag(0);
            }
            break;
            case 1: {
                my_hind = Integer.parseInt(ans_txt2.getTag().toString());
                ans_txt2.setText("");
                ans_txt2.setTag(0);
            }
            break;
            case 2: {
                my_hind = Integer.parseInt(ans_txt3.getTag().toString());
                ans_txt3.setText("");
                ans_txt3.setTag(0);
            }
            break;
            case 3: {
                my_hind = Integer.parseInt(ans_txt4.getTag().toString());
                ans_txt4.setText("");
                ans_txt4.setTag(0);
            }
            break;
            case 4: {
                my_hind = Integer.parseInt(ans_txt5.getTag().toString());
                ans_txt5.setText("");
                ans_txt5.setTag(0);
            }
            break;
            case 5: {
                my_hind = Integer.parseInt(ans_txt6.getTag().toString());
                ans_txt6.setText("");
                ans_txt6.setTag(0);
            }
            break;
            case 6: {
                my_hind = Integer.parseInt(ans_txt7.getTag().toString());
                ans_txt7.setText("");
                ans_txt7.setTag(0);
            }
            break;
            case 7: {
                my_hind = Integer.parseInt(ans_txt8.getTag().toString());
                ans_txt8.setText("");
                ans_txt8.setTag(0);
            }
            break;
            case 8: {
                my_hind = Integer.parseInt(ans_txt9.getTag().toString());
                ans_txt9.setText("");
                ans_txt9.setTag(0);
            }
            break;
            case 9: {
                my_hind = Integer.parseInt(ans_txt10.getTag().toString());
                ans_txt10.setText("");
                ans_txt10.setTag(0);
            }
            break;
            case 10: {
                my_hind = Integer.parseInt(ans_txt11.getTag().toString());
                ans_txt11.setText("");
                ans_txt11.setTag(0);
            }
            break;
            case 11: {
                my_hind = Integer.parseInt(ans_txt12.getTag().toString());
                ans_txt12.setText("");
                ans_txt12.setTag(0);
            }
            break;
        }
    }


    public void txt_maintain(int tag) {

      /*  try {

        } finally {
            // this gets called even if there is an exception somewhere above
            if (mycursor != null) {
                mycursor.close();
            }
        }

        mycursor = mydb.rawQuery("select * from " + table_name + " where id='" + id_list.get(pos) + "'", null);
        mycursor.moveToNext();

        qus_maintain = "";
        original_ans.clear();
        qus_maintain = mycursor.getString(mycursor.getColumnIndex("ans_maintain"));
        if (!qus_maintain.equals("")) {
            List<String> values1 = Arrays.asList(qus_maintain.split(","));
            System.out.println("values1  : " + values1);
            for (int i = 0; i < values1.size(); i++) {
                original_ans.add(Integer.valueOf(values1.get(i)));
            }
        }
        contentValues.clear();
        for (int i = 0; i < original_ans.size(); i++) {
            if (tag == original_ans.get(i)) {
                original_ans.set(i, 0);
                break;
            }
        }
        qus_maintain = android.text.TextUtils.join(",", original_ans);
        contentValues.put("ans_maintain", qus_maintain);
        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

        switch (tag) {
            case 1: {
                qus_txt1.setVisibility(TextView.VISIBLE);
            }
            break;
            case 2: {
                qus_txt2.setVisibility(TextView.VISIBLE);
            }
            break;
            case 3: {
                qus_txt3.setVisibility(TextView.VISIBLE);
            }
            break;
            case 4: {
                qus_txt4.setVisibility(TextView.VISIBLE);
            }
            break;
            case 5: {
                qus_txt5.setVisibility(TextView.VISIBLE);
            }
            break;
            case 6: {
                qus_txt6.setVisibility(TextView.VISIBLE);
            }
            break;
            case 7: {
                qus_txt7.setVisibility(TextView.VISIBLE);
            }
            break;
            case 8: {
                qus_txt8.setVisibility(TextView.VISIBLE);
            }
            break;
            case 9: {
                qus_txt9.setVisibility(TextView.VISIBLE);
            }
            break;
            case 10: {
                qus_txt10.setVisibility(TextView.VISIBLE);
            }
            break;
            case 11: {
                qus_txt11.setVisibility(TextView.VISIBLE);
            }
            break;
            case 12: {
                qus_txt12.setVisibility(TextView.VISIBLE);
            }
            break;
        }

        /* original_qus.add(tag);
        contentValues.clear();
        qus_maintain = android.text.TextUtils.join(",", original_qus);
        contentValues.put("qus_maintain", qus_maintain);
        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/

        randon_reassign.clear();

        hind2 = String.valueOf(suffuleword.charAt(tag - 1)).toUpperCase();
        for (int j = 0; j < logo_Ans.length(); j++) {
            hind1 = String.valueOf(logo_Ans.charAt(j)).toUpperCase();
            if (hind1.equals(hind2)) {
                randon_reassign.add(j);
            }
        }
        for (int index = 0; index < randon_reassign.size(); index++) {
            String s = String.valueOf(replace_logoname.charAt(randon_reassign.get(index)));
            if (s.equals("0")) {
                replace_logoname.setCharAt(randon_reassign.get(index), hind2.charAt(0));
               /* contentValues.clear();
                contentValues.put("replace_ans", "" + replace_logoname);
                mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);*/
                break;
            }
        }

        System.out.println("replace_logoname qus txt_maintain : " + replace_logoname);


    }


    public void txt_flow_function(String get_value, int get_tag) {

        if (ans_txt1.length() == 0) {
            ans_txt1.setText(get_value);
            ans_txt1.setTag(get_tag);
            ans_check();
        } else if (ans_txt2.length() == 0) {
            ans_txt2.setText(get_value);
            ans_txt2.setTag(get_tag);
            ans_check();
        } else if (ans_txt3.length() == 0) {
            ans_txt3.setText(get_value);
            ans_txt3.setTag(get_tag);
            ans_check();
        } else if (ans_txt4.length() == 0) {
            ans_txt4.setText(get_value);
            ans_txt4.setTag(get_tag);
            ans_check();
        } else if (ans_txt5.length() == 0) {
            ans_txt5.setText(get_value);
            ans_txt5.setTag(get_tag);
            ans_check();
        } else if (ans_txt6.length() == 0) {
            ans_txt6.setText(get_value);
            ans_txt6.setTag(get_tag);
            ans_check();
        } else if (ans_txt7.length() == 0) {
            ans_txt7.setText(get_value);
            ans_txt7.setTag(get_tag);
            ans_check();
        } else if (ans_txt8.length() == 0) {
            ans_txt8.setText(get_value);
            ans_txt8.setTag(get_tag);
            ans_check();
        } else if (ans_txt9.length() == 0) {
            ans_txt9.setText(get_value);
            ans_txt9.setTag(get_tag);
            ans_check();
        } else if (ans_txt10.length() == 0) {
            ans_txt10.setText(get_value);
            ans_txt10.setTag(get_tag);
            ans_check();
        } else if (ans_txt11.length() == 0) {
            ans_txt11.setText(get_value);
            ans_txt11.setTag(get_tag);
            ans_check();
        } else if (ans_txt12.length() == 0) {
            ans_txt12.setText(get_value);
            ans_txt12.setTag(get_tag);
            ans_check();
        }

        original_qus.remove(new Integer(get_tag));

       /* contentValues.clear();

      qus_maintain = android.text.TextUtils.join(",", original_qus);
        contentValues.put("qus_maintain", qus_maintain);
        mydb.update(table_name, contentValues, "id='" + id_list.get(pos) + "'", null);

        ans_maintain();*/
    }

    public void ans_check() {

        String at1 = ans_txt1.getText().toString();
        String at2 = ans_txt2.getText().toString();
        String at3 = ans_txt3.getText().toString();
        String at4 = ans_txt4.getText().toString();
        String at5 = ans_txt5.getText().toString();
        String at6 = ans_txt6.getText().toString();
        String at7 = ans_txt7.getText().toString();
        String at8 = ans_txt8.getText().toString();
        String at9 = ans_txt9.getText().toString();
        String at10 = ans_txt10.getText().toString();
        String at11 = ans_txt11.getText().toString();
        String at12 = ans_txt12.getText().toString();
        ans_done = (at1 + at2 + at3 + at4 + at5 + at6 + at7 + at8 + at9 + at10 + at11 + at12).trim();

        if (ans_done != null) {
            if (ans_done.equalsIgnoreCase(logo_Ans)) {
                ans_set();
               /* mydb.execSQL("UPDATE " + table_name + " SET isfinish='" + 1 + "' where id='" + id_list.get(pos) + "'");
                if (delete_opt == 0 && hint_opt == 0 && free_hint_opt == 0) {
                    total_coin += 20;
                    total_point += 20;
                    contentValues.clear();
                    contentValues.put("point", total_point);
                    contentValues.put("coins", total_coin);
                    mydb.update(table_name1, contentValues, "id='" + 1 + "'", null);
                } else {
                    total_coin += 10;
                    total_point += 20;
                    contentValues.clear();
                    contentValues.put("point", total_point);
                    contentValues.put("coins", total_coin);
                    mydb.update(table_name1, contentValues, "id='" + 1 + "'", null);
                }


                Cursor fav_cur = null;
                try {
                    fav_cur = mydb.rawQuery("select favourite from '" + table_name + "' where id='" + id_list.get(pos) + "'", null);
                    if (fav_cur.getCount() != 0) {
                        fav_cur.moveToFirst();
                        int isfavrite = fav_cur.getInt(fav_cur.getColumnIndex("favourite"));

                        if (isfavrite == 1) {
                            contentValues.clear();
                            contentValues.put("fav_isfinish", isfavrite);
                            mydb.update("update_favorite", contentValues, "id='" + id_list.get(pos) + "'", null);
                        }
                    }
                } finally {

                    if (fav_cur != null)
                        fav_cur.close();

                }*/


            }
        }
    }

    public void ans_set() {
        for (int i = 0; i < logo_Ans.length(); i++) {
            if (i == 1) {
                ans_txt1.setText(String.valueOf(logo_Ans.charAt(0)).toUpperCase());
                ans_txt2.setText(String.valueOf(logo_Ans.charAt(1)).toUpperCase());
                ans_txt1.setEnabled(false);
                ans_txt2.setEnabled(false);
            } else if (i == 2) {
                ans_txt3.setText(String.valueOf(logo_Ans.charAt(i)).toUpperCase());
                ans_txt3.setEnabled(false);
            } else if (i == 3) {
                ans_txt4.setText(String.valueOf(logo_Ans.charAt(i)).toUpperCase());
                ans_txt4.setEnabled(false);
            } else if (i == 4) {
                ans_txt5.setText(String.valueOf(logo_Ans.charAt(i)).toUpperCase());
                ans_txt5.setEnabled(false);
            } else if (i == 5) {
                ans_txt6.setText(String.valueOf(logo_Ans.charAt(i)).toUpperCase());
                ans_txt6.setEnabled(false);
            } else if (i == 6) {
                ans_txt7.setText(String.valueOf(logo_Ans.charAt(i)).toUpperCase());
                ans_txt7.setEnabled(false);
            } else if (i == 7) {
                ans_txt8.setText(String.valueOf(logo_Ans.charAt(i)).toUpperCase());
                ans_txt8.setEnabled(false);
            } else if (i == 8) {
                ans_txt9.setText(String.valueOf(logo_Ans.charAt(i)).toUpperCase());
                ans_txt9.setEnabled(false);
            } else if (i == 9) {
                ans_txt10.setText(String.valueOf(logo_Ans.charAt(i)).toUpperCase());
                ans_txt10.setEnabled(false);
            } else if (i == 10) {
                ans_txt11.setText(String.valueOf(logo_Ans.charAt(i)).toUpperCase());
                ans_txt11.setEnabled(false);
            } else if (i == 11) {
                ans_txt12.setText(String.valueOf(logo_Ans.charAt(i)).toUpperCase());
                ans_txt12.setEnabled(false);
            }
        }
        game_image.setImageBitmap(null);
      /*  hint_lay.setVisibility(View.GONE);
        input_layout.setVisibility(View.GONE);
        info_lay.setVisibility(View.VISIBLE);*/
    }

}
