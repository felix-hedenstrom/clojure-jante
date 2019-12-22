(ns jante.io.io-manager
  (:require [jante.io.cli :refer [incoming-messages send-outgoing-messages]]
            [jante.util :refer [log]]))

(def default-io-manager
  {:internal-messages '()
   :unclassified-messages '()
   :outgoing-messages '()})

(defn get-internal-messages
  [io-manager]
  (:internal-messages io-manager))

(defn get-outgoing-messages
  [io-manager]
  (:outgoing-messages io-manager))

(defn get-unclassified-messages
  [io-manager]
  (:unclassified-messages io-manager))

(defn classify-messages
  [io-manager]
  (-> (update io-manager :internal-messages #(concat % (get-unclassified-messages io-manager)))
      (assoc :unclassified-messages '())))

(defn send-and-recieve
  [io-manager]
  ; Has side effects through io
  (-> (get-outgoing-messages io-manager)
      (send-outgoing-messages))
  ; Gets messages through io
  (let [messages (incoming-messages)]
    (-> (update io-manager :unclassified-messages #(concat % messages))
        (classify-messages))))

(defn update-incoming-messages
  [io-manager fn]
  (update io-manager :internal-messages fn))

(defn new-io-manager
  []
  default-io-manager)
