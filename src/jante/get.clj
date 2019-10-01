(ns jante.get)

(defn get-plugins
  [state]
  (keys (get state :plugin-states)))

(defn get-messages
  [state]
  (:messages state))

(defn get-recipient
  [message]
  (get message :recipient))


(defn get-plugin-messages
  [state plugin]
  (get-in state [:plugin-states plugin :messages]))
