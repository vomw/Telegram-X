package org.thunderdog.challegram.util;

import android.content.Context;

import androidx.annotation.Nullable;

import me.vkryl.core.lambda.RunnableData;

public class LanguageDetector {
  public static void detectLanguage (Context context, String text, RunnableData<String> onSuccess, @Nullable RunnableData<Throwable> onFail) {
    if (onFail != null) {
      onFail.runWithData(new UnsupportedOperationException("Language detection unavailable."));
    }
  }
}
