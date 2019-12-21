(ns jante.plugin.stats
  (:require [jante.get :refer [get-recipient]]))

(defn listen
  {:events {:on-message (fn [data] true)}}
  [plugin-state message]
  (update plugin-state (get-recipient message)
          (fn [messages-count]
            (if (nil? messages-count)
              1
              (inc messages-count)))))
