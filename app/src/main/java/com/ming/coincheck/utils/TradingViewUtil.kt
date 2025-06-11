package com.ming.coincheck.utils

import android.util.Base64

object TradingViewUtil {
    private const val template = "<body style=\"margin: 0; padding: 0\">\n" +
            "<div class=\"tradingview-widget-container\">\n" +
            "  <div id=\"tradingview_90696\"></div>\n" +
            "  <script type=\"text/javascript\" src=\"https://s3.tradingview.com/tv.js\"></script>\n" +
            "  <script type=\"text/javascript\">\n" +
            "  new TradingView.widget(\n" +
            "  {\n" +
            "  \"autosize\": true,\n" +
            "  \"symbol\": \"UPBIT:%s\",\n" +
            "  \"interval\": \"D\",\n" +
            "  \"timezone\": \"Etc/UTC\",\n" +
            "  \"theme\": \"%s\",\n" +
            "  \"style\": \"1\",\n" +
            "  \"locale\": \"kr\",\n" +
            "  \"toolbar_bg\": \"#f1f3f6\",\n" +
            "  \"enable_publishing\": false,\n" +
            "  \"hide_legend\": true,\n" +
            "  \"save_image\": false,\n" +
            "  \"container_id\": \"tradingview_90696\"\n" +
            "}\n" +
            "  );\n" +
            "  </script>\n" +
            "</div>\n" +
            "</body>"

    fun getScript(symbol: String, isDarkMode: Boolean): String {
        val theme = if (isDarkMode) {
            "dark"
        } else {
            "light"
        }
        return Base64.encodeToString(
            String.format(template, symbol, theme).toByteArray(),
            Base64.NO_PADDING
        )
    }
}