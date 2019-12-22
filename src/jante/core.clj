(ns jante.core
  (:require
   [jante.message :refer [get-text]]
   [jante.get :refer [get-plugins
                      get-recipient
                      get-messages
                      get-plugin-messages]]
   [jante.event :refer [trigger-event]]
   [jante.util :refer [nil-if-empty log]]
   [jante.construct :refer [new-bot update-incoming-messages get-internal-messages send-and-recieve]]))

(defn main-loop
  [state]
  (loop [state state]
    (if-let [messages (-> (get-internal-messages state)
                          (nil-if-empty))]
      (let [message (first messages)]
        (log "this was the message" message)
        (cond
          (= (get-text message) "c!quit")
          nil
          ; This should be remade
          (= (get-text message) "c!state")
          (do (log state)
              (recur (update-incoming-messages state rest)))
          :else
              ; Run all plugins and update their internal states
          (recur (-> (trigger-event state :on-message message)
                     (update-incoming-messages rest)))))
      (recur (send-and-recieve state)))))

(defn -main
  [& args]
  (print ">")
  (flush)
  (main-loop (new-bot)))

