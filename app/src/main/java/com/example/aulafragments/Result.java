package com.example.aulafragments;
import com.example.aulafragments.user.User;

import java.util.ArrayList;

public class Result {
   public ArrayList<User> results;

   public Result(ArrayList<User> results) {
      this.results = results;
   }

   public ArrayList<User> getResults() {
      return results;
   }

   public void setResults(ArrayList<User> results) {
      this.results = results;
   }
}


