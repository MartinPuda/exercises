(ns exercises.javatime
  (:import (java.time.format DateTimeFormatter)
           (java.time ZonedDateTime Duration))
  (:gen-class))

;Write three Clojure interview coding exercises for senior programmers that include working with Java Time.
;
;Write a function that takes a sequence of Java Instant objects as input
; and returns the maximum instant. The function should return nil if the
; input is empty. For example:
;Copy code
;(max-instant [#inst "2022-12-14T10:00:00.000-00:00"
;              #inst "2022-12-15T11:00:00.000-00:00"
;              #inst "2022-12-16T12:00:00.000-00:00"])
;; => #inst "2022-12-16T12:00:00.000-00:00"
;(max-instant []) ; => nil

(defn max-instant [xs]
  (when (seq xs)
    (first (reverse (sort xs)))))

;Write a function that takes a sequence of Java ZonedDateTime objects as
; input and returns a sequence of the dates in the input, formatted
; as strings using the format "yyyy-MM-dd". The function should return an empty
; sequence if the input is empty. For example:
;Copy code
;(format-date-strings [#"2022-12-14T10:00:00.000-00:00"
;                      #"2022-12-15T11:00:00.000-00:00"
;                      #"2022-12-16T12:00:00.000-00:00"])
;; => ["2022-12-14", "2022-12-15", "2022-12-16"]
;(format-date-strings []) ; => []

(defn format-date-strings [xs]
  (map #(.format (DateTimeFormatter/ofPattern "yyyy-MM-dd")
                 (ZonedDateTime/parse (str %))) xs))

;Write a function that takes a sequence of Java Duration objects as input and
; returns the sum of the durations in the input. The function should return
; nil if the input is empty. For example:
;Copy code
;(sum-durations [#"PT1H" #"PT2H" #"PT3H"]) ; => #"PT6H"
;(sum-durations []) ; => nil

(defn sum-durations [xs]
  (when (seq xs)
    (->> xs
         (map #(Duration/parse (str %1)))
         (reduce #(.plus %1 %2))
         str)))
