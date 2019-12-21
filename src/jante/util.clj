(ns jante.util
  (:require [clojure.pprint :refer [pprint]]))

(defn nil-if-empty
  [arg]
  (if (empty? arg)
    nil
    arg))

(defn log
  [& args]
  (pprint args)
  (print ">")
  (flush))
