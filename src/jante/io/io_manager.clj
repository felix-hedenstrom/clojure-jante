(ns jante.io.io-manager
  (:require [jante.io.cli :refer [incoming-messages outgoing-messages]]
            [jante.util :refer [log]]))

(defn send-and-recieve
  [messages]
  (outgoing-messages messages)
  (let [messages (incoming-messages)]
    (log messages)
    messages))

(def default-io-manager
  {:messages '()})

(defn new-io-manager
  []
  default-io-manager)

(defn get-messages
  [io-manager]
  (:outgoing-messages io-manager))
