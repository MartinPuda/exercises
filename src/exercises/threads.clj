(ns exercises.threads
  (:import (java.util.concurrent CountDownLatch))
  (:gen-class))

;
;Write three Clojure interview coding exercises for senior programmers that include working
; with Java Threads.
;
;Write a function that takes a sequence of Runnable objects as input and creates a new
; thread for each Runnable, running each in parallel. The function should return a
; sequence of the resulting Thread objects. For example:

;(run-parallel [#(println "Hello, world!")
;               #(println "Goodbye, world!")])
;; => [#<Thread Thread-1>, #<Thread Thread-2>]

(defn run-parallel [xs]
  (mapv #(doto (Thread. ^Runnable %)
           .start)
        xs))

;Write a function that takes a sequence of Callable objects as input and creates a
; new thread for each Callable, running each in parallel. The function should return
; a sequence of the results of each Callable, in the same order as the input. For example:

;(call-parallel [#(str "Hello, " %) "Alice"
;                #(str "Goodbye, " %) "Bob"])
;; => ["Hello, Alice", "Goodbye, Bob"]

(defn call-parallel [xs]
  (->> (partition 2 xs)
       (mapv (fn [[r v]] (let [result (volatile! nil)
                               t (doto (Thread. ^Runnable (fn [] (vreset! result (r v))))
                                   .start
                                   .join)]
                           @result)))))


;Write a function that takes a sequence of Runnable objects as input and creates a
; new thread for each Runnable, running each in parallel. The function should return
; a CountDownLatch that will be triggered when all of the threads have completed.
; For example:

;(with-parallel-threads [#(println "Hello, world!")
;                        #(println "Goodbye, world!")])
;; => #<CountDownLatch java.util.concurrent.CountDownLatch@1f32e575>

(defn with-parallel-threads [xs]
  (let [cdlatch (CountDownLatch. (count xs))]
    (doseq [x xs]
      (.start (Thread. ^Runnable (fn [] (.countDown cdlatch) (x)))))
    cdlatch))
