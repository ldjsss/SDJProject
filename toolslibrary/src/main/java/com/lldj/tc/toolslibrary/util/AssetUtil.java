package com.lldj.tc.toolslibrary.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class AssetUtil {
    public static String getText(Context pContext, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

            BufferedReader bf = new BufferedReader(new InputStreamReader(pContext.getAssets().open(fileName)));
             String line;
             while ((line = bf.readLine()) != null) {
                 stringBuilder.append(line);

            }
        } catch (Exception e) {
             e.printStackTrace();

        }  return stringBuilder.toString();

    }
}
