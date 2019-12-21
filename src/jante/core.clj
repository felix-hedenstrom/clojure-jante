(ns jante.core
  (:require
   [jante.message :refer [print-message get-text]]
   [jante.io.io-manager :refer [send-and-recieve]]
   [jante.get :refer [get-plugins
                      get-recipient
                      get-messages
                      get-plugin-messages]]
   [jante.event :refer [trigger-event]]
   [jante.util :refer [nil-if-empty log]]
   [jante.construct :refer [new-bot]]))

(defn main-loop
  [state]
  (loop [state state]
    (if-let [messages (nil-if-empty (get-messages state))]
      (let [message (first messages)]
        (log "found message" message)
        (cond
          (= (get-text message) "c!quit")
          nil
          ; This should be remade
          (= (get-text message) "c!state")
          (do (log state)
              (recur (update state :messages rest)))
          :else
              ; Run all plugins and update their internal states
          (recur (-> (trigger-event state :on-message message)
                     (update :messages rest)))))
      (recur (update state :messages send-and-recieve)))))

(defn -main
  [& args]
  (print ">")
  (flush)
  (main-loop (new-bot)))

