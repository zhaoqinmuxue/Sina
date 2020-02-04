package org.aoli.weibo.util;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.aoli.weibo.R;
import org.aoli.weibo.application.Aoli;

import java.util.ArrayList;
import java.util.List;

public class RichTextUtil {
    //##
    private static void setSymolClick(final String text,SpannableStringBuilder richText){
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0;i < text.length();i++){
            if (text.charAt(i) == '#'){
                indexes.add(i);
            }
        }
        int color = ColorUtil.getAccentBackColor();
        for (int i = 2;i <= indexes.size();i+=2){
            String word = text.substring(indexes.get(i-2)+1,indexes.get(i-1));
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Toast.makeText(Aoli.getApplicationContext(),word,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    ds.setColor(color);
                    ds.setUnderlineText(false);
                }
            };
            richText.setSpan(clickableSpan,indexes.get(i-2),indexes.get(i-1)+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    //@
    private static void setAtClick(final String text,SpannableStringBuilder richText){
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0;i < text.length();i++){
            if (text.charAt(i) == '@'){
                indexes.add(i);
                for (int j = i+1;j < text.length();j++){
                    if (text.charAt(j)==' ' || text.charAt(j)==':' || text.charAt(j)== '，' || text.charAt(j)== ','){
                        indexes.add(j);
                        i = j;
                        break;
                    }
                }
            }
        }
        int color = ColorUtil.getAccentBackColor();
        for (int i = 2;i <= indexes.size();i+=2){
            String word = text.substring(indexes.get(i-2)+1,indexes.get(i-1));
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Toast.makeText(Aoli.getApplicationContext(),word,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    ds.setColor(color);
                    ds.setUnderlineText(false);
                }
            };
            richText.setSpan(clickableSpan,indexes.get(i-2),indexes.get(i-1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    //查看全文
    private static Holder setLastAllClick(String text){
        int all = text.lastIndexOf("全文：");
        if (all != -1) {
            String allUrl = text.substring(all + "全文：".length()).trim();
            String temp = text.substring(0, all);
            int color = ColorUtil.getAccentBackColor();
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Toast.makeText(Aoli.getApplicationContext(),allUrl,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    ds.setColor(color);
                    ds.setUnderlineText(false);
                }
            };
            SpannableStringBuilder richText = new SpannableStringBuilder(temp);
            richText.append("查看全文",clickableSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return new Holder(temp,richText);
        }else {
            SpannableStringBuilder richText = new SpannableStringBuilder(text);
            return new Holder(text,richText);
        }
    }

    @SuppressWarnings("all")
    public static SpannableStringBuilder toRichText(final String text){
        Holder holder = setLastAllClick(text);
        setSymolClick(holder.text,holder.richText);
        setAtClick(holder.text,holder.richText);
        return holder.richText;
    }

    static class Holder{
        public String text;
        public SpannableStringBuilder richText;

        public Holder(String text,SpannableStringBuilder richText){
            this.text = text;
            this.richText = richText;
        }
    }
}
