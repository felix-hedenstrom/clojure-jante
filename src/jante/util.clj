(ns jante.util
  (:require [clojure.pprint :refer [pprint]]))

(defn nil-if-empty
  [arg]
  (if (empty? arg)
    nil
    arg))

(def end-of-print
  (str "\u001b[0m\n>")) ; resets terminal color

(defn clear-screen
  []
  (print "\u001b[2J"))

(defn log
  [& text]
  (println)
  (print "\u001b[33m") ; Yellow
  (print (str (System/currentTimeMillis) ": "))
  (apply print text)
  (print end-of-print)
  (flush))

(defn debug
  [& args]
  (println)
  (print "\u001b[32m") ; Green color terminal
  (doseq [arg args]
    (pprint arg))
  (print end-of-print)
  (flush))
