package com.commercetest.reviewreviews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Code to manage the creation, update and initial population of the database.
 */

public class ReviewsDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "reviews";
    public static final String GOOGLE_PLAY_REVIEW = "GOOGLE_PLAY_REVIEW";
    private static final int DB_VERSION = 1;

    ReviewsDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("CREATE TABLE " + GOOGLE_PLAY_REVIEW + " (");
        sqlStatement.append("_id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlStatement.append("package TEXT NOT NULL, ");
        sqlStatement.append("app_version_code INTEGER, ");
        sqlStatement.append("app_version_name TEXT, ");
        sqlStatement.append("reviewer_language TEXT, ");
        sqlStatement.append("device TEXT, ");
        sqlStatement.append("review_submitted TEXT NOT NULL, ");
        sqlStatement.append("review_submitted_millis INTEGER, ");
        sqlStatement.append("review_last_update TEXT, ");
        sqlStatement.append("review_last_update_millis INTEGER, ");
        sqlStatement.append("star_rating INTEGER NOT NULL, ");
        sqlStatement.append("review_title TEXT, ");
        sqlStatement.append("review_text TEXT, ");
        sqlStatement.append("developer_reply TEXT, ");
        sqlStatement.append("developer_reply_millis INTEGER, ");
        sqlStatement.append("developer_reply_text TEXT, ");
        sqlStatement.append("review_link TEXT");
        sqlStatement.append(");");
        db.execSQL(sqlStatement.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Nothing to do yet.
    }

    /**
     * Add a review from Google Play App Store to the database
     * @param db open database connection
     * @param javaPackageName the Java package name of the app that was reviewed (a mandatory field)
     * @param appVersionCode the app's version code
     * @param appVersionName the app's name as reported in the review
     * @param reviewerLanguage the language setting of the reviewer's device
     * @param device the device model
     * @param reviewSubmitted when the review was submitted as formatted text (a mandatory field)
     * @param reviewSubmittedTimeInMsecs when the review was submitted in milli-seconds
     * @param reviewLastUpdated when the review was last updated as formatted text
     * @param reviewLastUpdatedInMsecs when the review was last updated in milli-seconds
     * @param starRating the star rating of the review (a mandatory field)
     * @param reviewTitle the title of the review
     * @param reviewText the text of the review in the original language
     * @param developerReply when the developer replied as formatted text
     * @param developerReplyTimeInMsecs when the developer replied in milli-seconds
     * @param developerReplyText what the developer wrote
     * @param reviewURL link to the review online
     */
    public static void insertGooglePlayReview(SQLiteDatabase db, String javaPackageName,
                                    String appVersionCode, int appVersionName,
                                    String reviewerLanguage, String device,
                                    String reviewSubmitted, int reviewSubmittedTimeInMsecs,
                                    String reviewLastUpdated, int reviewLastUpdatedInMsecs,
                                    int starRating, String reviewTitle, String reviewText,
                                    String developerReply, int developerReplyTimeInMsecs,
                                    String developerReplyText, String reviewURL) {
        ContentValues reviewValues = new ContentValues();
        reviewValues.put("package", javaPackageName);
        reviewValues.put("app_version_code", appVersionCode);
        reviewValues.put("app_version_name", appVersionName);
        reviewValues.put("reviewer_language", reviewerLanguage);
        reviewValues.put("device", device);
        reviewValues.put("review_submitted", reviewSubmitted);
        reviewValues.put("review_submitted_millis", reviewSubmittedTimeInMsecs);
        reviewValues.put("review_last_update", reviewLastUpdated);
        reviewValues.put("review_last_update_millis", reviewLastUpdatedInMsecs);
        reviewValues.put("star_rating", starRating);
        reviewValues.put("review_title", reviewTitle);
        reviewValues.put("review_text", reviewText);
        reviewValues.put("developer_reply", developerReply);
        reviewValues.put("developer_reply_millis", developerReplyTimeInMsecs);
        reviewValues.put("developer_reply_text", developerReplyText);
        reviewValues.put("review_link", reviewURL);
        db.insert(GOOGLE_PLAY_REVIEW, null, reviewValues);
        long count = DatabaseUtils.queryNumEntries(db, GOOGLE_PLAY_REVIEW);
        Log.i("Review added", reviewTitle + ", count now: " + count);
    }

}
