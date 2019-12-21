(ns jante.core-test
  (:require [clojure.test :refer :all]
            [jante.core :refer :all]))

(deftest test-all
  "Bootstrapping with the required namespaces, finds all the firestone.* namespaces (except this one),
             requires them, and runs all their tests."
  (let [namespaces (->> (all-ns)
                        (map str)
                        (filter (fn [x] (re-matches #"jante\..*" x)))
                        (remove (fn [x] (= "jante.core-test" x)))
                        (map symbol))]
    (is (successful? (time (apply run-tests namespaces))))))

