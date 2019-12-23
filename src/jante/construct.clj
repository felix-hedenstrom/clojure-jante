(ns jante.construct
  (:require [jante.io.io-manager :refer [new-io-manager]]
            [clojure.test :refer [is]]))

(defn get-io-manager
  [state]
  (:io-manager state))

(defn get-internal-messages
  [state]
  (jante.io.io-manager/get-internal-messages (get-io-manager state)))

(defn get-plugin-state
  [state plugin]
  (get-in state [:plugin-states plugin]))

(defn new-bot
  []
  {:io-manager (new-io-manager)
   :plugin-states {}})

(defn set-plugin-state
  [state plugin plugin-state]
  (assoc-in state [:plugin-states plugin] plugin-state))

(defn add-messages
  {:test (fn []
           (is
            (-> (new-bot)
                (add-messages [(jante.message/new-message {:text "Hello World!"})])
                (get-io-manager)
                (jante.io.io-manager/get-internal-messages)
                (count))
            (= 1)))}
  [state messages]
  (update state :io-manager #(jante.io.io-manager/add-messages % messages)))

(defn update-internal-messages
  {:test (fn []
           (-> (new-bot)
               (add-messages [(jante.message/new-message {:text "Hello World!"})])
               (update-internal-messages rest)
               (get-io-manager)
               (jante.io.io-manager/get-all-messages)
               (empty?)))}
  [state fn]
  (update state :io-manager #(jante.io.io-manager/update-internal-messages % fn)))

(defn send-and-recieve
  [state]
  (update state :io-manager jante.io.io-manager/send-and-recieve))


