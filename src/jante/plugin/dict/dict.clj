(ns jante.plugin.dict.dict
  (:require [jante.util :refer [nil-if-empty]]
            [jante.message :refer [get-text respond]]))

(defn put-pair
  [local-state key_ value]
  (assoc-in local-state [:pairs key_] value))

(defn get-key
  [local-state key_]
  (get-in local-state [:pairs key_]))

(def prefix "dict")

(defn parse
  {:events
   {:on-message
    (fn [{recipient :recipient text :text}]
      (and
       (= recipient :local)
       (-> (str "c!" prefix #"\s+(.*)")
           (re-pattern)
           (re-matches text))))}}
  [local-state message]
  (if-let [[k v] (nil-if-empty (rest (re-matches #"\s*(.+?)\s*:=\s*(.+?)\s*" (:text message))))]
    [(put-pair local-state k v) [(respond message (str "Added key-value pair " k ":= " v))]]
    [local-state
     [(->> (if-let [answer (get-key local-state (:text message))]
             answer
             "Could not find key")
           (respond message))]]))
