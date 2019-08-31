(ns jante.core
  (:require [jante.io.io-manager :refer [new-io-manager send-messages]]))


(defn new-bot
  []
  {:io-manager (new-io-manager)
   })

(defn get-io-manager
  [state]
  (:io-manager state))

(defn main-loop
  [state]
  (loop [state state]
    (recur (update state :io-manager send-messages)))) 

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (main-loop (new-bot)))
  
