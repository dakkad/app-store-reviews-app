package com.commercetest.reviewreviews;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrap up reading a csv file or stream.
 *
 * @author damienkallison@gmail.com
 */
public class ReviewReader {

  public static final String
      PACKAGE_NAME = "Package Name",
      APP_VERSION_CODE = "App Version Code",
      APP_VERSION_NAME = "App Version Name",
      REVIEWER_LANGUAGE = "Reviewer Language",
      DEVICE = "Device",
      REVIEW_DATE_TIME = "Review Submit Date and Time",
      REVIEW_MILLIS = "Review Submit Millis Since Epoch",
      REVIEW_LAST_UPDATE_DATETIME = "Review Last Update Date and Time",
      REVIEW_LAST_UPDATE_MILLIS = "Review Last Update Millis Since Epoch",
      STAR_RATING = "Star Rating",
      REVIEW_TITLE = "Review Title",
      REVIEW_TEXT = "Review Text",
      DEVELOPER_REPLY_DATETIME = "Developer Reply Date and Time",
      DEVELOPER_REPLY_MILLIS = "Developer Reply Millis Since Epoch",
      DEVELOPER_REPLY = "Developer Reply Text",
      REVIEW_LINK = "Review Link";

  private final BufferedReader reader;
  private final Map<String, Integer> fields = new HashMap<String, Integer>();

  private ReviewReader(BufferedReader reader) {
    this.reader = reader;
  }

  public static ReviewReader fromFilename(String filename)
      throws IOException {
    return fromStream(new FileInputStream(filename));
  }

  public static ReviewReader fromStream(InputStream stream) throws IOException {
    return new ReviewReader(new BufferedReader(new InputStreamReader(new BufferedInputStream(stream)))).index();
  }

  public Review next() throws IOException {
    return fromRow(nextRow());
  }

  private Review fromRow(String[] row) {
    if (null == row) {
      return null;
    }
    // TODO Map fields from the CSV header
    return new Review();
  }

  ReviewReader index() throws IOException {
    String[] items = nextRow();
    index(PACKAGE_NAME, items);
    return this;
  }

  void index(String name, String[] values) {
    for (int i = 0; i < values.length; i++) {
      if (values[i].equals(name)) {
        fields.put(name, i);
        return;
      }
    }
  }

  String[] nextRow() throws IOException {
    String line = reader.readLine();
    return null == line? null : line.split(",");
  }
}
