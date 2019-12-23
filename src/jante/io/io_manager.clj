(ns jante.io.io-manager
  (:require [jante.io.cli :refer [incoming-messages send-outgoing-messages]]
            [clojure.test :refer [is]]
            [jante.message :refer [internal?]]
            [jante.util :refer [log]]))

(def default-io-manager
  {:internal-messages '()
   :unclassified-messages '()
   :outgoing-messages '()})

(defn new-io-manager
  []
  default-io-manager)

(defn get-internal-messages
  [io-manager]
  (:internal-messages io-manager))

(defn get-outgoing-messages
  [io-manager]
  (:outgoing-messages io-manager))

(defn get-unclassified-messages
  [io-manager]
  (:unclassified-messages io-manager))

(defn get-all-messages
  [io-manager]
  (concat (get-unclassified-messages io-manager) (get-outgoing-messages io-manager) (get-internal-messages io-manager)))

(defn classify-messages
  [io-manager]
  (let [unclassified (get-unclassified-messages io-manager)
        new-outgoing-messages (filter #(not (internal? %)) unclassified)
        new-internal-messages (filter internal? unclassified)]
    (-> (update io-manager :internal-messages #(concat % new-internal-messages))
        (update :outgoing-messages #(concat % new-outgoing-messages))
        (assoc :unclassified-messages '()))))

(defn add-messages
  ([io-manager messages]
   (add-messages io-manager messages :unclassified-messages))
  ([io-manager messages message-type]
   (-> (update io-manager message-type #(concat % messages))
       (classify-messages))))

(defn clear-messages
  [io-manager message-type]
  (assoc io-manager message-type '()))

(defn send-and-recieve
  [io-manager]
  ; Has side effects through io
  (-> (get-outgoing-messages io-manager)
      (send-outgoing-messages))
  ; Gets messages through io
  (let [messages (incoming-messages)]
    (-> (add-messages io-manager messages)
        (clear-messages :outgoing-messages))))

(defn update-internal-messages
  {:test (fn []
           (is (-> (new-io-manager)
                   (assoc :internal-messages '(1 2))
                   (update-internal-messages rest)
                   (get :internal-messages)
                   (count)
                   (= 1))))}
  [io-manager fn]
  (update io-manager :internal-messages fn))

