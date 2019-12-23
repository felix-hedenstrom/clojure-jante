(ns jante.util
  (:require [clojure.pprint :refer [pprint]]))

(defn nil-if-empty
  [arg]
  (if (empty? arg)
    nil
    arg))

(defn log
  [& text]
  (print (str (System/currentTimeMillis) ": "))
  (apply print text)
  (println)
  (print ">")
  (flush))

(defn debug
  [& args]
  (println)
  (doseq [arg args]
    (pprint arg))
  (print ">")
  (flush))
