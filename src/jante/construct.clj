(ns jante.construct)

(defn new-bot
  []
  {:messages '()
   :plugin-states {}})

(defn set-plugin-state
  [state plugin plugin-state]
  (assoc-in state [:plugin-states plugin] plugin-state))

(defn add-messages
  [state messages]
  (update state :messages #(concat % messages)))

(defn get-plugin-state
  [state plugin]
  (get-in state [:plugin-states plugin]))

