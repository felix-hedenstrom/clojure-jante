(ns jante.core-test
  (:require [clojure.test :refer :all]
            [jante.core :refer :all]))

(deftest test-all
  (let [namespaces (->> (all-ns)
                        (map str)
                        (filter (fn [x] (re-matches #"jante\..*" x)))
                        (remove (fn [x] (= "jante.core-test" x)))
                        (map symbol))]
    (is (successful? (time (apply run-tests namespaces))))))

