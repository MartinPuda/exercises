(ns exercises.core
  (:require
    [clojure.string :as s]
    [malli.core :as m]
    [clojure.math :as math])
  (:gen-class))

; https://www.reddit.com/r/Clojure/comments/34qhha/clojure_coding_job_interview_experience/
; http://www.smashcompany.com/technology/embarrassing-code-i-wrote-under-stress-at-a-job-interview

; https://www.reddit.com/r/Clojure/comments/lcqkl5/a_random_job_interview_challenge_in_clojure/
; https://savo.rocks/posts/a-random-job-interview-challenge-in-clojure/

(defn -main
  [& args]
  (println "Exercises..."))

;Write three Clojure interview coding exercises.

;Write a function that takes a list of numbers and returns the sum of
; the squares of all the numbers in the list. For example, given the input (1 2 3 4),
; the function should return 30 (1^2 + 2^2 + 3^2 + 4^2 = 30).
;
;Write a function that takes a list of strings and returns a list of all the strings
; that have a length greater than 4 characters. For example, given the input
; ("hello" "world" "foo" "bar" "baz"), the function should return ("hello" "world").
;
;Write a function that takes a list of numbers and returns a new list containing
; only the prime numbers from the original list. For example, given the input
; (1 2 3 4 5 6 7 8 9), the function should return (2 3 5 7).

(defn f1 [xs]
  {:pre  [(m/validate [:sequential :int] xs)]
   :post [(m/validate :int %)]}
  (->> xs
       (map #(math/pow % 2))
       (reduce +)
       int))

(defn f2 [xs]
  {:pre  [(m/validate [:sequential :string] xs)]
   :post [(m/validate [:sequential :string] %)]}
  (->> xs
       (filter #(> (count %) 4))))

(defn divides? [m n]
  (zero? (rem m n)))

(defn prime? [n]
  (and (< 1 n) (not-any? #(divides? n %) (range 2 n))))

(defn f3 [xs]
  {:pre  [(m/validate [:sequential :int] xs)]
   :post [(m/validate [:sequential :int] %)]}
  (->> xs
       (filter prime?)))

; Write three Clojure interview coding exercises for medior programmers.

;Write a function that takes a list of numbers and returns a new list with
; every element multiplied by 2. However, if the element is evenly
; divisible by 3, it should be left unchanged. For example, given the
; input (1 2 3 4 5 6 7 8 9), the function should return (2 4 3 8 10 6 14 16 18).
;
;Write a function that takes a list of strings and returns a new list with all the
; strings converted to upper case. However, if the string starts with the letter
; 'a' or 'A', it should be converted to lower case. For example, given the input
; ("hello" "world" "foo" "bar" "baz"), the function should return
; ("HELLO" "WORLD" "FOO" "BAR" "baz").
;
;Write a function that takes a list of numbers and returns the largest prime
; number in the list. For example, given the input (1 2 3 4 5 6 7 8 9),
; the function should return 7. If there are no prime numbers in the list,
; the function should return nil.

(defn f4 [xs]
  (->> xs
       (map #(if (divides? % 3) % (* % 2)))))

(defn f5 [xs]
  (->> xs
       (map #(if (s/starts-with? % "a")
               (s/lower-case %)
               (s/upper-case %)))))

(defn f6 [xs]
  (let [primes (filter prime? xs)]
    (when (seq primes)
      (apply max-key identity primes))))

;Write three Clojure interview coding exercises for senior programmers.

;Implement a function (collatz-length n) that returns the length of the Collatz sequence
; starting with the positive integer n. For example, the Collatz sequence
; for the number 10 is [10, 5, 16, 8, 4, 2, 1], so the function should return
; 7 for the input 10.

; If the number is even, divide it by two.
;If the number is odd, triple it and add one.
;
;Implement a function (unique-values xs) that takes a list of integers and returns
; a new list containing only the unique values from the input list.
; For example, the input list [1, 2, 3, 2, 4, 3] should produce the output list
; [1, 2, 3, 4].
;
;Implement a function (calculate-primes n) that returns a list of all prime numbers
; up to and including the positive integer n. For example, the input 10 should
; produce the output [2, 3, 5, 7].

(defn collatz [n]
  (if (even? n) (/ n 2) (inc (* n 3))))

(defn collatz-length [n]
  (->> (iterate collatz n)
       (take-while #(not= % 1))
       count
       inc))

(defn unique-values [xs]
  (distinct xs))

(defn calculate-primes [x]
  (->> (range 2 (inc x))
       (filter prime?)))

;Write three Clojure interview coding exercises for senior programmers.

;Write a function that takes a sequence of integers as input and returns the average
; of the sequence. The function should return 0 if the input is empty. For example:
;Copy code
;(average [1 2 3 4 5]) ; => 3
;(average []) ; => 0

;Write a function that takes a sequence of strings as input and returns a map
; where the keys are the unique strings in the input and the values are the number
; of times each string appears in the input. For example:
;Copy code
;(string-counts ["foo" "bar" "baz" "foo" "qux" "foo"]) ; => {"foo" 3, "bar" 1, "baz" 1, "qux" 1}
;(string-counts []) ; => {}

;Write a function that takes a sequence of maps as input, where each map
; represents an employee with their name, age, and salary. The function should return
; a map where the keys are the names of the employees and the values are their
; average salary across all the years they have worked. For example:
;Copy code
;(average-salaries [{"name" "Alice", "age" 30, "salary" 100000}
;                   {"name" "Bob", "age" 35, "salary" 120000}
;                   {"name" "Charlie", "age" 40, "salary" 140000}])
;; => {"Alice" 100000, "Bob" 120000, "Charlie" 140000}
;(average-salaries []) ; => {}

(defn average [xs]
  (if (seq xs)
    (/ (reduce + xs) (count xs))
    0))

(defn string-counts [xs]
  (frequencies xs))

(defn average-salaries [xs]
  (->> xs
       (map #(vector (% "name") (% "salary")))
       (into {})))


