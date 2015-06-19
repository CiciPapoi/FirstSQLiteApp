//package com.example.user.firstsqliteapp.utils;
//
//import android.content.Context;
//import android.util.Log;
//
//import org.apache.mahout.cf.taste.common.TasteException;
//import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
//import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
//import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
//import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
//import org.apache.mahout.cf.taste.model.DataModel;
//import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
//import org.apache.mahout.cf.taste.recommender.RecommendedItem;
//import org.apache.mahout.cf.taste.recommender.Recommender;
//import org.apache.mahout.cf.taste.similarity.UserSimilarity;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
///**
// * Created by user on 14.06.2015.
// */
//public class RecommenderIntro {
//    private static final String TAG = "RecommenderIntro";
//    private  Context myContext;
//    public RecommenderIntro(Context context) {
//        myContext = context;
//    }
//
//    public void getRecommendation() throws TasteException {
//
//        DataModel model = null;
//        try {
//            File file = new File( myContext.getFilesDir() + "/file.csv");
//            file.createNewFile();
//
//            Log.d(TAG, myContext.getFilesDir().toString() );
//            Log.d(TAG, file.getAbsolutePath() );
//            model = new FileDataModel(file);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
//        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
//
//        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
//        List<RecommendedItem> recommendations = recommender.recommend(1, 1); // userId, howMany
//
//        for (RecommendedItem rec : recommendations) {
//            Log.d(TAG, "RECOMMENDATION " + rec);
//        }
//
//    }
//}
