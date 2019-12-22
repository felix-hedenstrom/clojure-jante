(ns jante.construct
  (:require [jante.io.io-manager :refer [new-io-manager]]))

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

(defn update-incoming-messages
  [state fn]
  (update state :io-manager #(jante.io.io-manager/update-incoming-messages % fn)))

(defn send-and-recieve
  [state]
  (update state :io-manager jante.io.io-manager/send-and-recieve))

(defn add-messages
  [state messages]
  (update state :messages #(concat % messages)))


